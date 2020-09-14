package com.fly2.beans;

import java.io.File;
import java.io.IOException;
import javax.ejb.Remote;

@Remote
public interface Persist2BeanRemote {

    public void parseFile(String path) throws IOException;
    
    public void parseFile(File file) throws IOException;
}
