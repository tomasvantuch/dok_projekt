/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller;

import dok.model.Result;
import dok.model.Status;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Tomas
 */
class ExtractAction implements ActionListener {
    
    private MainController controller;

    public ExtractAction(MainController aThis) {
        this.controller = aThis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String code = controller.getMainFrame().getCodeTextFileld().getText();
        Result result = getResultFrom(code);
        int len = result.getAllImage();
        byte [] bimage = new byte[len];
        byte [] message = new byte[result.getAllMessage()];
        
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("stego.png"));
        } catch (IOException ex) {
            Logger.getLogger(ExtractAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        byte[] imageData = get_byte_data(image);
        int index= result.getFrom();
        
        for(int b=0; b<bimage.length; ++b )
        {
                for(int i=0; i<8; ++i, index += (result.getColor()*result.getEach()))
                {
                        bimage[b] = (byte)((bimage[b] << 1) | (imageData[index] & 1));
                }
        }
        
        
        for(int b=0; b<message.length; ++b )
        {
                for(int i=0; i<8; ++i, index += (result.getColor()*result.getEach()))
                {
                        message[b] = (byte)((message[b] << 1) | (imageData[index] & 1));
                }
        }
        
        controller.getMainFrame().getjTextArea1().setText(new String(message));
        
        InputStream is = new ByteArrayInputStream(bimage);
        BufferedImage bi= null;
        try {
            bi = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(ExtractAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        File f = new File("extract.png");
        f.delete();
        try {
            ImageIO.write(bi, "png", f);
            } catch (IOException ex) {
            Logger.getLogger(ExtractAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BufferedImage bi1 = ImageIO.read(f);
            Image i = Toolkit.getDefaultToolkit().createImage(bi1.getSource());
            controller.getMainFrame().getLblWrittenImage().setIcon(new ImageIcon(i));
        } catch (IOException ex) {
            Logger.getLogger(ExtractAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller.getModel().addStatus(new Status("========================================="));
        controller.getModel().addStatus(new Status("vypisany v programe."));
        controller.getModel().addStatus(new Status("Obrazok bol ulozeny v subore extract.png a text"));
        controller.getModel().addStatus(new Status("Data boli uspesne extrahovane."));
    }
    
    
        private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
        
        private Result getResultFrom(String code) {
            String [] data = code.split("#");
            int each = Integer.parseInt(""+data[0].charAt(0));
            int color = Integer.parseInt(""+data[0].charAt(1));
            int from = Integer.parseInt(""+data[0].charAt(2)+code.charAt(3));
            int allimage = Integer.parseInt(""+data[0].substring(4, data[0].length()));
            int allmessage = Integer.parseInt(""+data[1]);
            return new Result(color, each, allimage, allmessage, from);        
    }
}
