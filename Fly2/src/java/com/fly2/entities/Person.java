package com.fly2.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "vorname"))
public class Person extends Kunde {

    private String nachname;
    private Geschlecht geschlecht;

    @Transient
    public String getVorname() {
        return name;
    }

    public void setVorname(String vorname) {
        this.name = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    private String kriegVorname(String name) {

        String vorname = "";
        String[] arr = name.split(" ");
        for (int i = 0; i < arr.length - 1; i++) {
            vorname = vorname + arr[i];
        }
        return vorname;
    }

    private String kriegNachname(String name) {
        String nachname = "";
        String[] arr = name.split(" ");
        for (int i = 0; i < arr.length - 2; i++) {
            nachname = nachname + arr[i];
        }
        nachname = arr[arr.length - 1];

        return nachname;
    }

    @PrePersist
    private void resolveNamen() {
        String tVorname = kriegVorname(name);
        String tNachname = kriegNachname(name);
        if (this.nachname == null) {
            setNachname(tNachname);
            setVorname(tVorname);
            setName(tVorname);
        }
    }

    @Override
    public String toString() {
        return "Person{vorname=" + name + "nachname=" + nachname + ", geschlecht=" + geschlecht + '}';
    }

}
