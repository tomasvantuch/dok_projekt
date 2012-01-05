/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.model;


import dok.controller.IAddStatusListener;
import dok.controller.ICapacityListener;
import dok.controller.actions.OpenPictureToCoverAction;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomas
 */
public class Model{
    
    private Image image;
    
    private Image cover;
    
    private String imagePath;
    
    private String coverPath;
    
    private String eCode;
    
    private int capacity;
    
    private ICapacityListener capacityChangedListener;
    
    private Result result;
    
    private List<Status> statuses;
    
    private IAddStatusListener addStatusListener;
    
    public Model(){
        capacity = 0;
        statuses = new ArrayList<Status>();
        
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        try {
            refreshCapacity();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
        try {
            refreshCapacity();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        
    }

    public void setCapacity(int i) {
        capacity = i;
        capacityChangedListener.refreshCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public ICapacityListener getCapacityChangedListener() {
        return capacityChangedListener;
    }

    public void setCapacityChangedListener(ICapacityListener capacityChangedListener) {
        this.capacityChangedListener = capacityChangedListener;
    }

    private void refreshCapacity() throws FileNotFoundException, IOException {
        int c = (image!=null)?image.getHeight(null)*image.getWidth(null):0;
        this.setCapacity(c);
        if(coverPath != null){
            File f = new File(coverPath);
            byte[] data= new byte[(int)f.length()];
            FileInputStream fis = new FileInputStream(f);
            fis.read(data);
            c -= (data.length*8);
            this.setCapacity(c);
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String geteCode() {
        return eCode;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
    
    public void addStatus(Status status){
        this.statuses.add(status);
        Collections.sort(statuses);
        addStatusListener.refreshStatuses();
    }

    public IAddStatusListener getAddStatusListener() {
        return addStatusListener;
    }

    public void setAddStatusListener(IAddStatusListener addStatusListener) {
        this.addStatusListener = addStatusListener;
    }
    
    
}
