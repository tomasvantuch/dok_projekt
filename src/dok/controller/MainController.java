/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;
import dok.controller.actions.OpenPictureAction;
import dok.controller.actions.*;
import dok.model.*;
import dok.view.Main_Frame;
import javax.swing.DefaultListModel;

/**
 *
 * @author tomas
 */
public final class MainController {
    
    private Model model;
    
    private Main_Frame view;

    public MainController(final Model model, final Main_Frame view) {
        this.model = model;
        this.view = view;
        this.view.AddOpenPictureAction(new OpenPictureAction(this));
        this.view.AddProceedActionListener(new ProceedAction(this));
        this.view.AddOpenPictureToCoverAction(new OpenPictureToCoverAction(this));
        this.model.setCapacityChangedListener(new ICapacityListener() {

            @Override
            public void refreshCapacity() {
                view.getLblCharCount().setText(model.getCapacity()+"");
            }
        });
        this.model.setAddStatusListener(new IAddStatusListener() {

            @Override
            public void refreshStatuses() {
                view.getjList1().removeAll();
                DefaultListModel listModel = new DefaultListModel();
                for(Status status : model.getStatuses()){
                    listModel.addElement(status);
                }
                view.getjList1().setModel(listModel);
            }
        });
        this.view.getBtnExtract().addActionListener(new ExtractAction(this));
    }
    
    public Main_Frame getMainFrame(){
        return this.view;
    }
    
    public Model getModel(){
        return this.model;
    }
    
    
}
