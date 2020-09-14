package com.fly2.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Buchung implements Serializable {

    private int id;
    private Flug flug;
    private Date datum;
    private Kategorie kategorie;
    private List<Kunde> kunden;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Flug getFlug() {
        return flug;
    }

    public void setFlug(Flug flug) {
        this.flug = flug;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    @Transient
    public List<Kunde> getKunden() {
        return kunden;
    }

    public void setKunden(List<Kunde> kunden) {
        this.kunden = kunden;
    }

    @Override
    public String toString() {
        return "Buchung{" + "id=" + id + ", flugId=" + flug.getId() + ", flugDatum=" + flug.getDatum() + ", Buchungsdatum=" + datum + ", kategorie=" + kategorie.getName() + '}';
    }

}
