package processus_inscription;

import java.util.HashMap;

import javax.faces.event.ActionEvent;

public class BeanInsPed extends HashMap {
    
    public BeanInsPed() {
        super();
    }
    
    @Override
    public Boolean get(Object key) {
        boolean skip = false;
        Object keyValue =  super.get(key);
        if(keyValue != null){
            //key found. Can we parse it to boolean?
            //TODO do you want to log the missing configuration ?
            try {
                skip = Boolean.parseBoolean((String)keyValue);
            } catch (Exception e) {
                //if it cannot be parsed, set skip to false
                skip = false;
                //TODO log failed parsing
            }
        }
        return skip;
    }
}
