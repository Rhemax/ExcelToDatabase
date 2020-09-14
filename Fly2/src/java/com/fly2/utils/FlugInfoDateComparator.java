package com.fly2.utils;

import com.fly2.statistics.FlugInfo;
import java.util.Comparator;

public class FlugInfoDateComparator implements Comparator<FlugInfo> {

    @Override
    public int compare(FlugInfo f1, FlugInfo f2) {
        long d1 = f1.getDatum().getTime();
        long d2 = f2.getDatum().getTime();
        if (d1 > d2) {
            return 1;
        } else if (d1 < d2) {
            return -1;
        }
        return 0;
    }

}
