package com.fly2.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Fluggeselschaft")
public class Fluggeselschaft implements Serializable {

    private String id;
    private String name;

    public Fluggeselschaft() {
    }

    public Fluggeselschaft(String id, String name) {
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
        return "Fluggeselschaft{" + "id=" + id + ", name=" + name + '}';
    }

}
