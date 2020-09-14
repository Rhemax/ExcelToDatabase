/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fly2.beans;

import com.fly2.statistics.Statistic;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sgrisciuc
 */
@Stateless
public class StatisticsBean implements StatisticsBeanRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Statistic getCurrentStatistic() {
        Statistic stat = new Statistic();
        stat.setPrivateKunden(getPersonsCount());
        stat.setGeschaftKunden(getGeschaftsCount());
        stat.setUndefinedKunden(getUndefinedCount());
        stat.setBuchungen(getBuchungenCount());
        stat.setFlugen(getFlugenCount());
        stat.setFluglinien(getFluglinienCount());
        stat.setFluggesellschaften(getFluggesellschaftenCount());
        stat.setFlugzeugtypen(getFlugzeugtypenCount());
        stat.setHersteller(getHerstellersCount());
        stat.setStadte(getStadtenCount());
        stat.setLanden(getLandenCount());

        return stat;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private long getPersonsCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Person p", Long.class);
        return query.getResultList().get(0);
    }

    private long getGeschaftsCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Geschaft g", Long.class);
        return query.getResultList().get(0);
    }

    private long getUndefinedCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Undefined u", Long.class);
        return query.getResultList().get(0);
    }

    private long getBuchungenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Buchung b", Long.class);
        return query.getResultList().get(0);
    }

    private long getFlugenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Flug f", Long.class);
        return query.getResultList().get(0);
    }

    private long getFluglinienCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Fluglinie f", Long.class);
        return query.getResultList().get(0);
    }

    private long getFluggesellschaftenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Fluggeselschaft f", Long.class);
        return query.getResultList().get(0);
    }

    private long getStadtenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Stadt s", Long.class);
        return query.getResultList().get(0);
    }

    private long getLandenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Land ld", Long.class);
        return query.getResultList().get(0);
    }

    private long getHerstellersCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Hersteller h", Long.class);
        return query.getResultList().get(0);
    }

    private long getFlugzeugtypenCount() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Flugzeug f", Long.class);
        return query.getResultList().get(0);
    }
}
