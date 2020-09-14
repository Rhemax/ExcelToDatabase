package com.fly2.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class Kunde implements Serializable {

    protected int id;
    protected String name;
    protected String plz;
    protected Stadt stadt;
    protected String strasse;
    protected Land land;
    protected Set<Buchung> buchungs;

    public Kunde() {
        if (buchungs == null) {
            buchungs = new HashSet<>();
        }
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Stadt getStadt() {
        return stadt;
    }

    public void setStadt(Stadt stadt) {
        this.stadt = stadt;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Buchung> getBuchungs() {
        return buchungs;
    }

    public void setBuchungs(Set<Buchung> buchungs) {
        this.buchungs = buchungs;
    }

    public void addBuchung(Buchung buchung) {
        buchungs.add(buchung);
    }

}
