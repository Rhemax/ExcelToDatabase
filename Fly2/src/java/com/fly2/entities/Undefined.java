package com.fly2.entities;

import javax.persistence.Entity;

@Entity
public class Undefined extends Kunde {
   
    @Override
    public String toString() {
        return "Undefined{" + "id=" + id + ", name=" + name + ", plz=" + plz + ", stadt=" + stadt + ", strasse=" + strasse + ", land=" + land + ", buchungs=" + buchungs + '}';
    }

}
