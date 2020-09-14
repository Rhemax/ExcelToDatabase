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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fluglinie_id", "datum", "flugzeug_id"})})
public class Flug implements Serializable {

    private int id;
    private Fluglinie fluglinie;
    private Date datum;
    private double preis;
    private Flugzeug flugzeug;
    private long dauer;
    private List<Buchung> buchungen;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Fluglinie getFluglinie() {
        return fluglinie;
    }

    public void setFluglinie(Fluglinie fluglinie) {
        this.fluglinie = fluglinie;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Flugzeug getFlugzeug() {
        return flugzeug;
    }

    public void setFlugzeug(Flugzeug flugzeug) {
        this.flugzeug = flugzeug;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flug")
    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(List<Buchung> buchungen) {
        this.buchungen = buchungen;
    }

    @Override
    public String toString() {
        return "Flug{" + "id=" + id + ", fluglinie=" + fluglinie + ", datum=" + datum + ", preis=" + preis + ", flugzeug=" + flugzeug + ", dauer=" + dauer + ", buchungen=" + buchungen + '}';
    }

}
