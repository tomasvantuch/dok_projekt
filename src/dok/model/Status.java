/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.model;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas
 */
public class Status implements Comparable<Status> {
    
    
    private String status;
    
    private Long time;

    @Override
    public int compareTo(Status o) {
        if(o.time<this.time){
            return -1;
        }else{
            return 1;
        }
    }

    public Status(String status) {
        this.status = status;
        this.time = new Date().getTime();
        sleepme();
    }
    
    
    @Override
    public String toString(){
        return status;
    }
    
    
    public void sleepme(){
        try {
            Thread.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
