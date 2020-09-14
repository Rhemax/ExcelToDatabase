package com.fly2.beans;

import com.fly2.utils.BuchungInfo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BuchungsBeanRemote {

    List<BuchungInfo> getBuchungs(int id);
}
