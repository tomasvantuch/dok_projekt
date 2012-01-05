/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller.actions;

import DCTUtils.JpegEncoder;
import dok.controller.MainController;
import dok.model.Status;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import net.sf.image4j.codec.bmp.BMPDecoder;

/**
 *
 * @author Tomas
 */
public class OpenPictureToCoverAction implements ActionListener{

    private MainController controller;
    private int i;
    
    public OpenPictureToCoverAction(MainController aThis) {
        this.controller = aThis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.getMainFrame().getjFileChooser().showOpenDialog(null);
        controller.getModel().setCoverPath(controller.getMainFrame().getjFileChooser().getSelectedFile().getPath());
        try{
            controller.getModel().setCover(new ImageIcon(controller.getModel().getCoverPath()).getImage());
            
        }catch(Exception ex){
            Logger.getLogger(OpenPictureAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(controller.getModel().getCoverPath().contains(".bmp")){
             try {
                BufferedImage bf = BMPDecoder.read(new File(controller.getModel().getCoverPath()));
                Image image = Toolkit.getDefaultToolkit().createImage(bf.getSource());
                controller.getModel().setCover(image);
            } catch (IOException ex1) {
                Logger.getLogger(OpenPictureAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        try {
            FileOutputStream fos = new FileOutputStream(new File("cover.jpg"));
            JpegEncoder encoder = new JpegEncoder(controller.getModel().getCover(), 12, fos);
            encoder.Compress();
            controller.getModel().setCoverPath("cover.jpg");
            controller.getModel().setCover(new ImageIcon(controller.getModel().getCoverPath()).getImage());
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(OpenPictureToCoverAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenPictureToCoverAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            checkCapacity();
        controller.getMainFrame().getLblCharCount().setText("Pocet volnych bytov(znakov):"+controller.getModel().getCapacity()/8+"");
        controller.getMainFrame().getTxfCoverPath().setText(controller.getModel().getCoverPath());
        } catch (IOException ex) {
            Logger.getLogger(OpenPictureToCoverAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller.getModel().addStatus(new Status("=================================================="));
        controller.getModel().addStatus(new Status("Vpiste alebo upravte spravu a dajte moznost zapis."));
        controller.getModel().addStatus(new Status("pomocou DCT pre znizenie velkosti."));
        controller.getModel().addStatus(new Status("Bol vybrany obrazok na zapis, ktory sa nasledne upravil"));
        
        
        
    }

    private void checkCapacity() throws FileNotFoundException, IOException {
        File file = new File("cover.jpg");
        BufferedImage bimage = ImageIO.read(file);
        i = 12;
        while((controller.getModel().getCapacity()<0)){
            i -= 4;
            FileOutputStream fos = new FileOutputStream(new File("cover.jpg"));
            JpegEncoder encoder = new JpegEncoder(controller.getModel().getCover(), i, fos);
            encoder.Compress();
            controller.getModel().setCoverPath("cover.jpg");
            controller.getModel().setCover(new ImageIcon(controller.getModel().getCoverPath()).getImage());
            File file1 = new File("cover.jpg");
            bimage = ImageIO.read(file1);
            System.out.println("kvalita komprimacie: "+i+"%");
            if(i==0) break;
        }
        controller.getModel().setCover(bimage);
            controller.getMainFrame().getLblWrittenImage().setIcon(new ImageIcon(bimage));
            
            
    }
    
}
