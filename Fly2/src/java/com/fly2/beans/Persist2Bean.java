package com.fly2.beans;

import com.fly2.entities.Buchung;
import com.fly2.entities.Flug;
import com.fly2.entities.Fluggeselschaft;
import com.fly2.entities.Flughafen;
import com.fly2.entities.Fluglinie;
import com.fly2.entities.Flugzeug;
import com.fly2.entities.Geschaft;
import com.fly2.entities.Hersteller;
import com.fly2.entities.Kategorie;
import com.fly2.entities.Kunde;
import com.fly2.entities.Land;
import com.fly2.entities.Person;
import com.fly2.entities.Stadt;
import com.fly2.entities.Undefined;
import com.fly2.utils.ExcelParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class Persist2Bean implements Persist2BeanRemote {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void parseFile(File file) throws IOException {
        ExcelParser parser = new ExcelParser(file);
        Set<Kunde> kunden = parser.getKunden();

        for (Kunde kunde : kunden) {
            Kunde dbKunde;
            if (kunde instanceof Person) {
                Person person = entityManager.find(Person.class, kunde.getId());
                dbKunde = person;
            } else if (kunde instanceof Geschaft) {
                Geschaft geschaft = entityManager.find(Geschaft.class, kunde.getId());
                dbKunde = geschaft;
            } else {
                Undefined undefined = entityManager.find(Undefined.class, kunde.getId());
                dbKunde = undefined;
            }

            if (dbKunde == null) {
                save(kunde);
                entityManager.flush();
                entityManager.clear();
            } else {
                kunde.setBuchungs(getActualEntity(kunde.getBuchungs()));
                List<Buchung> newBuchungen = new ArrayList<>();

                Iterator<Buchung> iterator = kunde.getBuchungs().iterator();

                while (iterator.hasNext()) {
                    Buchung newBuchung = iterator.next();
                    for (Buchung oldBuchung : dbKunde.getBuchungs()) {

                        if (isGleicherFlug(newBuchung, oldBuchung)) {
                            if (newBuchung.getDatum().getTime() > oldBuchung.getDatum().getTime()) {
                                oldBuchung.setDatum(newBuchung.getDatum());
                            }
                        } else {
                            boolean contains = false;
                            for (Buchung buchung : newBuchungen) {
                                if (newBuchung.getFlug().getId() == buchung.getFlug().getId()) {
                                    if (newBuchung.getFlug().getDatum().getTime() == buchung.getFlug().getDatum().getTime()) {
                                        if (newBuchung.getDatum().getTime() == buchung.getDatum().getTime()) {
                                            contains = true;
                                        }
                                    }
                                }
                            }
                            if (contains) {
                                newBuchung = getActualEntity(newBuchung);
                                newBuchungen.add(newBuchung);
                            }
                        }
                    }

                }
                dbKunde.getBuchungs().addAll(newBuchungen);
            }
        }
    }

    @Override
    public void parseFile(String path) throws IOException {
        //    log.setLevel(Level.OFF);
        ExcelParser parser = new ExcelParser(path);
        Set<Kunde> kunden = parser.getKunden();
        int counter = 0;
        for (Kunde kunde : kunden) {
            save(kunde);
            if (counter % 10 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    private void save(Kunde kunde) {
        kunde.setStadt(getActualEntity(kunde.getStadt())); // Stadt merge
        kunde.setLand(getActualEntity(kunde.getLand()));  // Land merge
        kunde.setBuchungs(getActualEntity(kunde.getBuchungs()));
        entityManager.merge(kunde);
    }

    private Stadt getActualEntity(Stadt stadt) {
        TypedQuery<Stadt> query = entityManager.createQuery("SELECT s FROM Stadt s WHERE s.name = :name", Stadt.class);
        query.setParameter("name", stadt.getName());
        List<Stadt> stadts = query.getResultList();
        if (stadts.size() > 0) {
            stadt = stadts.get(0);
        } else {
            stadt = entityManager.merge(stadt);
        }

        return stadt;
    }

    private Land getActualEntity(Land land) {
        TypedQuery<Land> query = entityManager.createQuery("SELECT ld FROM Land ld WHERE ld.name = :name", Land.class);
        query.setParameter("name", land.getName());
        List<Land> lands = query.getResultList();
        if (lands.size() > 0) {
            return lands.get(0);
        }
        land = entityManager.merge(land);
        return land;
    }

    private Kategorie getActualEntity(Kategorie kategorie) {
        TypedQuery<Kategorie> query = entityManager.createQuery("SELECT k FROM Kategorie k WHERE k.name = :name", Kategorie.class);
        query.setParameter("name", kategorie.getName());
        List<Kategorie> kategories = query.getResultList();
        if (kategories.size() > 0) {
            return kategories.get(0);
        }
        kategorie = entityManager.merge(kategorie);

        return kategorie;
    }

    private Set<Buchung> getActualEntity(Set<Buchung> buchungs) {
        for (Buchung buchung : buchungs) {
            buchung.setKategorie(getActualEntity(buchung.getKategorie()));
            buchung.setFlug(getActualEntity(buchung.getFlug()));
        }

        return buchungs;
    }

    private Buchung getActualEntity(Buchung buchung) {
        buchung.setKategorie(getActualEntity(buchung.getKategorie()));
        buchung.setFlug(getActualEntity(buchung.getFlug()));

        return buchung;
    }

    /**
     * Return true falls die Buchung b1 ist für gleiches Flug wie die Buchung b2
     */
    private boolean isGleicherFlug(Buchung b1, Buchung b2) {
        int fluglinieId_1 = b1.getFlug().getFluglinie().getId();
        int fluglinieId_2 = b2.getFlug().getFluglinie().getId();
        if (fluglinieId_1 == fluglinieId_2) {
            long datum_1 = b1.getFlug().getDatum().getTime();
            long datum_2 = b2.getFlug().getDatum().getTime();
            if (datum_1 == datum_2) {
                return true;
            }
        }
        return false;
    }

    private Flug getActualEntity(Flug flug) {
        TypedQuery<Flug> query = entityManager.createQuery("SELECT f FROM Flug f WHERE f.fluglinie.id = :flugLinieId and f.datum = :datum and f.fluglinie.fluggeselschaft.name = :fluggesellschaftName", Flug.class);

        query.setParameter("flugLinieId", flug.getFluglinie().getId());
        query.setParameter("datum", flug.getDatum());
        query.setParameter("fluggesellschaftName", flug.getFluglinie().getFluggeselschaft().getName());
        List<Flug> flugs = query.getResultList();

        if (flugs.size() > 0) {
            return flugs.get(0);
        }

        Fluglinie fluglinie = getActualEntity(flug.getFluglinie());
        flug.setFluglinie(fluglinie);

        Flugzeug flugzeug = getActualEntity(flug.getFlugzeug());
        flug.setFlugzeug(flugzeug);

        return flug;
    }

    private Fluglinie getActualEntity(Fluglinie fluglinie) {
        Fluglinie tempLinie = entityManager.find(Fluglinie.class, fluglinie.getId());

        if (tempLinie != null) {
            return tempLinie;
        }
        Fluggeselschaft fluggeselschaft = getActualEntity(fluglinie.getFluggeselschaft());
        fluglinie.setFluggeselschaft(fluggeselschaft);

        Flughafen vonFlughafen = getActualEntity(fluglinie.getVon());
        fluglinie.setVon(vonFlughafen);

        Flughafen nachFlughafen = getActualEntity(fluglinie.getNach());
        fluglinie.setNach(nachFlughafen);

        fluglinie = entityManager.merge(fluglinie);

        return fluglinie;
    }

    private Fluggeselschaft getActualEntity(Fluggeselschaft fluggeselschaft) {
        Fluggeselschaft result = entityManager.find(Fluggeselschaft.class, fluggeselschaft.getId());
        if (result != null) {
            return result;
        }
        fluggeselschaft = entityManager.merge(fluggeselschaft);
        return fluggeselschaft;
    }

    private Flughafen getActualEntity(Flughafen flughafen) {
        Flughafen result = entityManager.find(Flughafen.class, flughafen.getCode());
        if (result != null) {
            return result;
        }
        Stadt stadt = getActualEntity(flughafen.getStadt());
        flughafen.setStadt(stadt);

        Land land = getActualEntity(flughafen.getLand());
        flughafen.setLand(land);

        return flughafen;
    }

    private Flugzeug getActualEntity(Flugzeug flugzeug) {
        TypedQuery<Flugzeug> query = entityManager.createQuery("SELECT f FROM Flugzeug f WHERE f.typ = :typ", Flugzeug.class);
        query.setParameter("typ", flugzeug.getTyp());
        List<Flugzeug> flugzeugs = query.getResultList();
        if (flugzeugs.size() > 0) {
            return flugzeugs.get(0);
        }

        Hersteller hersteller = getActualEntity(flugzeug.getHersteller());
        flugzeug.setHersteller(hersteller);

        flugzeug = entityManager.merge(flugzeug);
        return flugzeug;
    }

    private Hersteller getActualEntity(Hersteller hersteller) {
        TypedQuery<Hersteller> query = entityManager.createQuery("SELECT h FROM Hersteller h WHERE h.name = :name", Hersteller.class);
        query.setParameter("name", hersteller.getName());
        List<Hersteller> herstellers = query.getResultList();
        if (herstellers.size() > 0) {
            return herstellers.get(0);
        }
        hersteller = entityManager.merge(hersteller);

        return hersteller;
    }

}
