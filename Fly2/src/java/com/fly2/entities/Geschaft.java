package com.fly2.entities;

import javax.persistence.Entity;

@Entity
public class Geschaft extends Kunde {

    @Override
    public String toString() {
        return "Geschaft{" + "id=" + id + ", name=" + name + ", plz=" + plz + ", stadt=" + stadt + ", strasse=" + strasse + ", land=" + land + ", buchungs=" + buchungs + '}';
    }
}
