package com.fly2.beans;

import com.fly2.entities.Buchung;
import com.fly2.entities.Flug;
import com.fly2.entities.Geschaft;
import com.fly2.entities.Kunde;
import com.fly2.entities.Person;
import com.fly2.entities.Undefined;
import com.fly2.statistics.FlugInfo;
import com.fly2.utils.FlugInfoDateComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@Stateless
public class FlugBean implements FlugBeanRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FlugInfo> getFlugen() {
        TypedQuery<Flug> query = em.createQuery("SELECT f FROM Flug f", Flug.class);
        List<Flug> flugen = query.getResultList();
        List<FlugInfo> flugInfos = toFlugInfoList(flugen);

        Collections.sort(flugInfos, new FlugInfoDateComparator());

        return flugInfos;
    }

    @Override
    public List<FlugInfo> getFlugen(int kundeId) {
        Kunde kunde = getKunde(kundeId);
        //  List<Flug> flugen = getPersonFlugs(kundeId);
        List<FlugInfo> flugInfos = toFlugInfoList(kunde.getBuchungs());

        Collections.sort(flugInfos, new FlugInfoDateComparator());

        return flugInfos;
    }

    private List<FlugInfo> toFlugInfoList(Set<Buchung> buchungen) {
        List<FlugInfo> flugInfos = new ArrayList<>();
        for (Buchung buchung : buchungen) {
            FlugInfo info = new FlugInfo();
            Flug flug = buchung.getFlug();
            info.setFlugId(flug.getId());
            info.setFluglinieId(flug.getFluglinie().getId());
            info.setFluggesellschaft(flug.getFluglinie().getFluggeselschaft().getName());
            info.setFluggesellschaftKode(flug.getFluglinie().getFluggeselschaft().getId());
            info.setVonFlughafenKode(flug.getFluglinie().getVon().getCode());
            info.setVonStadt(flug.getFluglinie().getVon().getStadt().getName());
            info.setVonLand(flug.getFluglinie().getVon().getLand().getName());
            info.setNachFlughafenKode(flug.getFluglinie().getNach().getCode());
            info.setNachStadt(flug.getFluglinie().getNach().getStadt().getName());
            info.setNachLand(flug.getFluglinie().getNach().getLand().getName());
            info.setDatum(flug.getDatum());
            info.setPreis(flug.getPreis());
            info.setFlugzeugtyp(flug.getFlugzeug().getTyp());
            info.setDauer(flug.getDauer());
            info.setBelegteSitze(getBelegteSitzenAnzahl(flug.getId()));
            info.setTotalSitze(flug.getFlugzeug().getSitzanzahl());
            flugInfos.add(info);
        }
        return flugInfos;
    }

    private List<FlugInfo> toFlugInfoList(List<Flug> flugen) {
        List<FlugInfo> flugInfos = new ArrayList<>();
        for (Flug flug : flugen) {
            FlugInfo info = new FlugInfo();
            info.setFlugId(flug.getId());
            info.setFluglinieId(flug.getFluglinie().getId());
            info.setFluggesellschaft(flug.getFluglinie().getFluggeselschaft().getName());
            info.setFluggesellschaftKode(flug.getFluglinie().getFluggeselschaft().getId());
            info.setVonFlughafenKode(flug.getFluglinie().getVon().getCode());
            info.setVonStadt(flug.getFluglinie().getVon().getStadt().getName());
            info.setVonLand(flug.getFluglinie().getVon().getLand().getName());
            info.setNachFlughafenKode(flug.getFluglinie().getNach().getCode());
            info.setNachStadt(flug.getFluglinie().getNach().getStadt().getName());
            info.setNachLand(flug.getFluglinie().getNach().getLand().getName());
            info.setDatum(flug.getDatum());
            info.setPreis(flug.getPreis());
            info.setFlugzeugtyp(flug.getFlugzeug().getTyp());
            info.setDauer(flug.getDauer());
            info.setBelegteSitze(getBelegteSitzenAnzahl(flug.getId()));
            info.setTotalSitze(flug.getFlugzeug().getSitzanzahl());
            flugInfos.add(info);
        }
        return flugInfos;
    }

    private long getBelegteSitzenAnzahl(int flugId) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Buchung b WHERE b.flug.id = :flugId", Long.class);
        query.setParameter("flugId", flugId);
        return query.getResultList().get(0);
    }

    public List<FlugInfo> getFlugen(String von, String nach, Date ab, Date bis) {
        String sql = getSql(von, nach, ab, bis);
        TypedQuery<Flug> query = em.createQuery(sql, Flug.class);
        if (isValid(von) && isValid(nach)) {
            query.setParameter("von", von);
            query.setParameter("nach", nach);
        } else if (isValid(von) && !isValid(nach)) {
            query.setParameter("von", von);
        } else if (!isValid(von) && isValid(nach)) {
            query.setParameter("nach", nach);
        }

        if (ab != null && bis != null) {
            query.setParameter("ab", ab, TemporalType.DATE);
            query.setParameter("bis", bis, TemporalType.DATE);
        } else if (ab != null && bis == null) {
            query.setParameter("ab", ab, TemporalType.DATE);
        } else if (ab == null && bis != null) {
            query.setParameter("bis", bis, TemporalType.DATE);
        }
        List<Flug> flugen = query.getResultList();
        List<FlugInfo> flugInfos = toFlugInfoList(flugen);

        return flugInfos;
    }

    private String getSql(String von, String nach, Date ab, Date bis) {
        String sql = "SELECT f FROM Flug f";
        if (isValid(von) || isValid(nach) || ab != null || bis != null) {
            sql += " WHERE";
        }
        if (isValid(von) && isValid(nach)) {
            sql += " f.fluglinie.von.stadt.name LIKE UPPER(CONCAT('%', :von, '%')) AND f.fluglinie.nach.stadt.name LIKE UPPER(CONCAT('%', :nach, '%'))";
        } else if (isValid(von) && !isValid(nach)) {
            sql += " f.fluglinie.von.stadt.name LIKE UPPER(CONCAT('%', :von, '%'))";
        } else if (!isValid(von) && isValid(nach)) {
            sql += " f.fluglinie.nach.stadt.name LIKE UPPER(CONCAT('%', :nach, '%'))";
        }

        if ((isValid(von) || isValid(nach)) && (ab != null || bis != null)) {
            sql += " AND";
        }

        if (ab != null && bis != null) {
            sql += " f.datum >= :ab AND f.datum <= :bis";
        } else if (ab != null && bis == null) {
            sql += " f.datum >= :ab";
        } else if (ab == null && bis != null) {
            sql += " f.datum <= :bis";
        }

        return sql;
    }

    /**
     * Prüft ob die String ist null oder leer
     */
    private boolean isValid(String input) {
        boolean isValid = false;
        if (input == null) {
            return false;
        }
        input = input.trim();
        isValid = !input.equals("");

        return isValid;
    }

    private List<Flug> getPersonFlugs(int kundeId) {
        String sql = "select f.* from person p join person_buchung pb on p.id = pb.person_id join buchung b on b.id = pb.buchungs_id join flug f on f.id = b.flug_id where p.id = :kundeId";
        Query query = em.createNativeQuery(sql, Flug.class);
        query.setParameter("kundeId", kundeId);
        return query.getResultList();
    }

    private Kunde getKunde(int kundeId) {
        Kunde kunde;
        kunde = em.find(Person.class, kundeId);
        if (kunde != null) {
            return kunde;
        }
        kunde = em.find(Geschaft.class, kundeId);
        if (kunde != null) {
            return kunde;
        }
        kunde = em.find(Undefined.class, kundeId);

        return kunde;

    }

}
