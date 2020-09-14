package com.fly2.beans;

import com.fly2.statistics.FlugInfo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface FlugBeanRemote {

    List<FlugInfo> getFlugen();

    List<FlugInfo> getFlugen(String von, String nach, Date ab, Date bis);

    List<FlugInfo> getFlugen(int kundeId);

}
