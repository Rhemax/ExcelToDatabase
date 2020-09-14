package com.fly2.statistics;

import java.io.Serializable;

public class Statistic implements Serializable{

    private long privateKunden;
    private long geschaftKunden;
    private long undefinedKunden;
    private long buchungen;
    private long flugen;
    private long fluglinien;
    private long fluggesellschaften;
    private long hersteller;
    private long flugzeugtypen;
    private long stadte;
    private long landen;

    public long getPrivateKunden() {
        return privateKunden;
    }

    public void setPrivateKunden(long privateKunden) {
        this.privateKunden = privateKunden;
    }

    public long getGeschaftKunden() {
        return geschaftKunden;
    }

    public void setGeschaftKunden(long geschaftKunden) {
        this.geschaftKunden = geschaftKunden;
    }

    public long getUndefinedKunden() {
        return undefinedKunden;
    }

    public void setUndefinedKunden(long undefinedKunden) {
        this.undefinedKunden = undefinedKunden;
    }

    public long getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(long buchungen) {
        this.buchungen = buchungen;
    }

    public long getFlugen() {
        return flugen;
    }

    public void setFlugen(long flugen) {
        this.flugen = flugen;
    }

    public long getFluglinien() {
        return fluglinien;
    }

    public void setFluglinien(long fluglinien) {
        this.fluglinien = fluglinien;
    }

    public long getFluggesellschaften() {
        return fluggesellschaften;
    }

    public void setFluggesellschaften(long fluggesellschaften) {
        this.fluggesellschaften = fluggesellschaften;
    }

    public long getStadte() {
        return stadte;
    }

    public void setStadte(long stadte) {
        this.stadte = stadte;
    }

    public long getLanden() {
        return landen;
    }

    public void setLanden(long landen) {
        this.landen = landen;
    }

    public long getHersteller() {
        return hersteller;
    }

    public void setHersteller(long hersteller) {
        this.hersteller = hersteller;
    }

    public long getFlugzeugtypen() {
        return flugzeugtypen;
    }

    public void setFlugzeugtypen(long flugzeugtypen) {
        this.flugzeugtypen = flugzeugtypen;
    }

    @Override
    public String toString() {
        return "Statistic{" + "privateKunden=" + privateKunden + ", geschaftKunden=" + geschaftKunden + ", undefinedKunden=" + undefinedKunden + ", buchungen=" + buchungen + ", flugen=" + flugen + ", fluglinien=" + fluglinien + ", fluggesellschaften=" + fluggesellschaften + ", hersteller=" + hersteller + ", flugzeugtypen=" + flugzeugtypen + ", stadte=" + stadte + ", landen=" + landen + '}';
    }

}
