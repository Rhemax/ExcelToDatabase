package com.fly2.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Flugzeug")
public class Flugzeug implements Serializable {

    private int id;
    private Hersteller hersteller;
    private String typ;
    private int sitzanzahl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Hersteller getHersteller() {
        return hersteller;
    }

    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getSitzanzahl() {
        return sitzanzahl;
    }

    public void setSitzanzahl(int sitzanzahl) {
        this.sitzanzahl = sitzanzahl;
    }

    @Override
    public String toString() {
        return "Flugzeug{" + "id=" + id + ", hersteller=" + hersteller + ", typ=" + typ + ", sitzanzahl=" + sitzanzahl + '}';
    }

}
