/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller;

import java.util.EventListener;

/**
 *
 * @author Tomas
 */
public interface IAddStatusListener extends EventListener{
    
    void refreshStatuses();
}
