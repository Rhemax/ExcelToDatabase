package com.fly2.statistics;

import java.util.Date;

public class FlugInfo {

    private int flugId;
    private int fluglinieId;
    private String fluggesellschaft;
    private String fluggesellschaftKode;
    private String vonFlughafenKode;
    private String vonStadt;
    private String vonLand;
    private String nachFlughafenKode;
    private String nachStadt;
    private String nachLand;
    private Date datum;
    private double preis;
    private String flugzeugtyp;
    private long dauer;
    private long belegteSitze;
    private int totalSitze;

    public int getFlugId() {
        return flugId;
    }

    public void setFlugId(int flugId) {
        this.flugId = flugId;
    }

    public int getFluglinieId() {
        return fluglinieId;
    }

    public void setFluglinieId(int fluglinieId) {
        this.fluglinieId = fluglinieId;
    }

    public String getFluggesellschaft() {
        return fluggesellschaft;
    }

    public void setFluggesellschaft(String fluggesellschaft) {
        this.fluggesellschaft = fluggesellschaft;
    }

    public String getFluggesellschaftKode() {
        return fluggesellschaftKode;
    }

    public void setFluggesellschaftKode(String fluggesellschaftKode) {
        this.fluggesellschaftKode = fluggesellschaftKode;
    }

    public String getVonFlughafenKode() {
        return vonFlughafenKode;
    }

    public void setVonFlughafenKode(String vonFlughafenKode) {
        this.vonFlughafenKode = vonFlughafenKode;
    }

    public String getVonStadt() {
        return vonStadt;
    }

    public void setVonStadt(String vonStadt) {
        this.vonStadt = vonStadt;
    }

    public String getVonLand() {
        return vonLand;
    }

    public void setVonLand(String vonLand) {
        this.vonLand = vonLand;
    }

    public String getNachFlughafenKode() {
        return nachFlughafenKode;
    }

    public void setNachFlughafenKode(String nachFlughafenKode) {
        this.nachFlughafenKode = nachFlughafenKode;
    }

    public String getNachStadt() {
        return nachStadt;
    }

    public void setNachStadt(String nachStadt) {
        this.nachStadt = nachStadt;
    }

    public String getNachLand() {
        return nachLand;
    }

    public void setNachLand(String nachLand) {
        this.nachLand = nachLand;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }

    public long getBelegteSitze() {
        return belegteSitze;
    }

    public void setBelegteSitze(long belegteSitze) {
        this.belegteSitze = belegteSitze;
    }

    public int getTotalSitze() {
        return totalSitze;
    }

    public void setTotalSitze(int totalSitze) {
        this.totalSitze = totalSitze;
    }

}
