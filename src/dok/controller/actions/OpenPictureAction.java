/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller.actions;

import com.sun.xml.internal.messaging.saaj.soap.ImageDataContentHandler;
import dok.controller.MainController;
import dok.model.Status;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import net.sf.image4j.codec.bmp.BMPConstants;
import net.sf.image4j.codec.bmp.BMPDecoder;

/**
 *
 * @author tomas
 */
public class OpenPictureAction implements ActionListener {

    private MainController mc;
    
    public OpenPictureAction(MainController mc) {
        this.mc = mc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mc.getMainFrame().getjFileChooser().showOpenDialog(null);
        mc.getModel().setImagePath(mc.getMainFrame().getjFileChooser().getSelectedFile().getPath());
        try{
            ImageIcon imgIco = new ImageIcon(mc.getModel().getImagePath());
            mc.getModel().setImage(imgIco.getImage());
            
        }catch(Exception ex){
            Logger.getLogger(OpenPictureAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(mc.getModel().getImagePath().contains(".bmp")){
             try {
                BufferedImage bf = BMPDecoder.read(new File(mc.getModel().getImagePath()));
                Image image = Toolkit.getDefaultToolkit().createImage(bf.getSource());
                mc.getModel().setImage(image);
            } catch (IOException ex1) {
                Logger.getLogger(OpenPictureAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        
        
        mc.getMainFrame().getLblImageToWriteInto().setIcon(new ImageIcon(mc.getModel().getImage()));
        mc.getMainFrame().getTxfImagePath().setText(mc.getModel().getImagePath());
        mc.getMainFrame().getLblCharCount().setText("Pocet volnych bytov(znakov):"+mc.getModel().getCapacity()/8+"");
        
        
        mc.getModel().addStatus(new Status("================================================"));
        mc.getModel().addStatus(new Status("vlozte tento kod do po policka nizsie a dajte extrahovat."));
        mc.getModel().addStatus(new Status("Pokial mate kod pre extrahovanie spravy z tohto obrazku,"));
        mc.getModel().addStatus(new Status("alebo napiste spravu, ktora sa takisto don zakoduje."));
        mc.getModel().addStatus(new Status("Dalej pre zakodovanie vyberte obrazok, ktory bude vlozeny,"));
        mc.getModel().addStatus(new Status(mc.getModel().getImagePath()));
        mc.getModel().addStatus(new Status("Bol otvoreny obrazok s nazvom: "));
        
    }
    

    
}
