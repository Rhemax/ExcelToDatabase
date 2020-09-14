package com.fly2.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Fluglinie")
public class Fluglinie implements Serializable {

    private int id;
    private Flughafen von;
    private Flughafen nach;
    private Fluggeselschaft fluggeselschaft;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Flughafen getVon() {
        return von;
    }

    public void setVon(Flughafen von) {
        this.von = von;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Flughafen getNach() {
        return nach;
    }

    public void setNach(Flughafen nach) {
        this.nach = nach;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Fluggeselschaft getFluggeselschaft() {
        return fluggeselschaft;
    }

    public void setFluggeselschaft(Fluggeselschaft fluggeselschaft) {
        this.fluggeselschaft = fluggeselschaft;
    }

    @Override
    public String toString() {
        return "Fluglinie{" + "id=" + id + ", von=" + von + ", nach=" + nach + ", fluggeselschaft=" + fluggeselschaft + '}';
    }

}
