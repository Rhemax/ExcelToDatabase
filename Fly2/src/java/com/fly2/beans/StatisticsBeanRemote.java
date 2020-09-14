/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fly2.beans;

import com.fly2.statistics.Statistic;
import javax.ejb.Remote;

/**
 *
 * @author sgrisciuc
 */
@Remote
public interface StatisticsBeanRemote {
    
    Statistic getCurrentStatistic();
    
}
