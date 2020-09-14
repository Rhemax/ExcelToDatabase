package com.fly2.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Geschlecht implements Serializable {

    private String id;
    private String name;

    public Geschlecht() {
    }

    public Geschlecht(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Geschlecht{" + "id=" + id + ", name=" + name + '}';
    }

}
