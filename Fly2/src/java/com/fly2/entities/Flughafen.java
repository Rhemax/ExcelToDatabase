package com.fly2.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Flughafen")
public class Flughafen implements Serializable {

    private String code;
    private Stadt stadt;
    private Land land;

    public Flughafen() {
    }

    public Flughafen(String code, Stadt stadt, Land land) {
        this.code = code;
        this.stadt = stadt;
        this.land = land;
    }

    @Id
    @Column(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Stadt getStadt() {
        return stadt;
    }

    public void setStadt(Stadt stadt) {
        this.stadt = stadt;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    @Override
    public String toString() {
        return "Flughafen{" + "code=" + code + ", stadt=" + stadt + ", land=" + land + '}';
    }

}
