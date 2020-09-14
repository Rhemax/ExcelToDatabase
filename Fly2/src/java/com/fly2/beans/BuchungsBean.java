package com.fly2.beans;

import com.fly2.entities.Buchung;
import com.fly2.entities.Geschaft;
import com.fly2.entities.Kunde;
import com.fly2.entities.Person;
import com.fly2.entities.Undefined;
import com.fly2.utils.BuchungInfo;
import com.fly2.utils.KategorieTyp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class BuchungsBean implements BuchungsBeanRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BuchungInfo> getBuchungs(int flugId) {
        TypedQuery<Buchung> query = em.createQuery("SELECT b FROM Buchung b WHERE b.flug.id = :flugId", Buchung.class);
        query.setParameter("flugId", flugId);
        List<Buchung> buchungen = query.getResultList();
        List<BuchungInfo> infos = new ArrayList<>();
        for (Buchung buchung : buchungen) {
            BuchungInfo info = new BuchungInfo();
            info.setBuchungId(buchung.getId());
            Kunde kunde = getKunde(buchung);
            info.setKundeId(kunde.getId());
            info.setAnrede(getAnrede(kunde));
            if (kunde instanceof Person) {
                Person person = (Person) kunde;
                info.setName(person.getName() + " " + person.getNachname());
            } else {
                info.setName(kunde.getName());
            }
            info.setDate(buchung.getDatum());
            info.setAdresse(kunde.getStrasse());
            info.setPlz(kunde.getPlz());
            info.setStadt(kunde.getStadt().getName());
            info.setLand(kunde.getLand().getName());
            infos.add(info);
        }
        return infos;
    }

    private Kunde getKunde(Buchung buchung) {
        Kunde kunde;
        switch (buchung.getKategorie().getName()) {
            case KategorieTyp.PERSON:
                String sql = "select p.* from person p join person_buchung pb on p.id = pb.person_id where pb.buchungs_id = :id";
                Query query = em.createNativeQuery(sql, Person.class);
                query.setParameter("id", buchung.getId());
                List<Person> persons = query.getResultList();
                if (persons.size() > 0) {
                    kunde = persons.get(0);
                } else {
                    kunde = new Person(); // Falls die Person wurde nicht gefunen --> shicken zurück eine neue und leere person, damit der Klient kriegt kein NullPointerException bei Abruf variables des Persons
                }
                break;

            case KategorieTyp.GESCHAFT:
                sql = "select g.* from Geschaft g join geschaft_buchung gb on g.id = gb.geschaft_id where gb.buchungs_id = :id";
                query = em.createNativeQuery(sql, Geschaft.class);
                query.setParameter("id", buchung.getId());
                List<Geschaft> geschafts = query.getResultList();
                if (geschafts.size() > 0) {
                    kunde = geschafts.get(0);
                } else {
                    kunde = new Geschaft();
                }
                break;
            default:
                sql = "select u.* from Undefined u join undefined_buchung ub on u.id = ub.undefined_id where ub.buchungs_id = :id";
                query = em.createNativeQuery(sql, Undefined.class);
                query.setParameter("id", buchung.getId());
                List<Undefined> undefineds = query.getResultList();
                if (undefineds.size() > 0) {
                    kunde = undefineds.get(0);
                } else {
                    kunde = new Undefined();
                }
                break;
        }
        return kunde;
    }

    private String getAnrede(Kunde kunde) {
        String anrede;
        if (kunde instanceof Person) {
            Person person = (Person) kunde;
            String geschlecht = person.getGeschlecht().getId();
            anrede = geschlecht.equals("m") ? "Herr" : "Frau";
        } else if (kunde instanceof Geschaft) {
            anrede = "Firma";
        } else {
            anrede = "-";
        }
        return anrede;
    }

}
