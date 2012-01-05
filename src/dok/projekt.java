/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok;

import dok.controller.MainController;
import dok.model.*;
import dok.view.MainFrame;
import dok.view.Main_Frame;

/**
 *
 * @author tomas
 */
public class projekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Model model = new Model();
        Main_Frame view = new Main_Frame(model);
        view.getLblImageToWriteInto().setText("");
        view.getLblWrittenImage().setText("");
        MainController controller = new MainController(model,view);
        view.setVisible(true);
        model.addStatus(new Status("Vitajte v programe."));
        model.addStatus(new Status("Ako prvy krok vyberte nosny obrazok."));
    }
}
