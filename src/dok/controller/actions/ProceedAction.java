/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller.actions;

import dok.controller.MainController;
import dok.controller.actions.thread.CheckCountOfChangesThread;
import dok.model.Result;
import dok.model.Status;
import dok.view.ShowString;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author tomas
 */
public class ProceedAction implements ActionListener{
    
    private MainController mc;
    private List<Result> results = new ArrayList<Result>();
    
    public ProceedAction(MainController mc){
        this.mc = mc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(mc.getModel().getImagePath());
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(ProceedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        String message = mc.getMainFrame().getjTextArea1().getText();
        byte[] byteMessage = message.getBytes();
        byte[] byteImage = new byte[1];
        File f = new File("cover.jpg");
        if(f.exists()){
            try {
                FileInputStream fis = new FileInputStream(f);
                byteImage = new byte[(int)f.length()];
                try {
                    fis.read(byteImage);
                } catch (IOException ex) {
                    Logger.getLogger(ProceedAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProceedAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        makeAnalyzis(image, byteImage, byteMessage);
        byte [] bmessage = new byte[byteImage.length + byteMessage.length];
        System.arraycopy(byteImage, 0, bmessage, 0, byteImage.length);
        System.arraycopy(byteMessage, 0, bmessage, byteImage.length, byteMessage.length);
        Collections.sort(results);
        System.out.println("winner is: "+results.get(0).getCount());
        System.out.println("the worst is: "+results.get(results.size()-1).getCount());
        System.out.println("There was checked "+results.size()+" options.");
        mc.getModel().setResult(results.get(0));
        try {
            makeAllChangesOnPicture(results.get(0), image, bmessage);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProceedAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProceedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        mc.getModel().addStatus(new Status("============================================="));
        mc.getModel().addStatus(new Status("vdaka predoslej analyze sa usetrilo "+getPart(results.get(0).getCount(),results.get(results.size()-1).getCount())+"% zmien."));
        mc.getModel().addStatus(new Status("Celkovo bolo upravenych "+results.get(0).getCount()+" bitov,"));
        mc.getModel().addStatus(new Status("Nazov obrazku so skrytou spravou je stego.png"));
        mc.getModel().addStatus(new Status("Data boli uspesne zapisane do zvoleneho obrazku."));
    }


    
    
    public void addResult(Result r){
        this.results.add(r);
    }

    private void makeAllChangesOnPicture(Result ret, BufferedImage image, byte[] message) throws FileNotFoundException, IOException {

        byte[] imageData = get_byte_data(image);
        int index = ret.getFrom();
        if(message.length * 8 > imageData.length/3)
	{
		throw new IllegalArgumentException("File not long enough!");
	}
        for(int i=0; i<message.length; ++i)
        {
                int mess = message[i];
                for(int bit=7; bit>=0; --bit, index += (ret.getColor()*ret.getEach())) 
                {
                        int b = (mess >>> bit) & 1;
                        
                        imageData[index] = (byte)((imageData[index] & 0xFE) | b );
                }
        }
        
        File outFile = new File("stego.png");
        if(outFile.exists()){
            outFile.delete();
        }
        ImageIO.write(image, "png", outFile);
        ShowString showString = new ShowString();
        showString.getjLabel1().setText("Obraz bol uspesne zapisany. Vas kod pre opatovnu extrakciu:");
        if(ret.getFrom()<10){
            showString.getjTextField1().setText(ret.getEach()+""+ret.getColor()+"0"+ret.getFrom()+""+ret.getAllImage()+"#"+ret.getAllMessage());
        }else{
            showString.getjTextField1().setText(ret.getEach()+""+ret.getColor()+""+ret.getFrom()+""+ret.getAllImage()+"#"+ret.getAllMessage());
        }
        showString.setVisible(true);
    }
    
    
        private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}

    private int getPart(int min, int max) {
        double out = 100.0 - ((100.0 / (double)max)*(double)min);
        return (int)out;
    }

    private void makeAnalyzis(BufferedImage image, byte[] byteImage, byte[] byteMessage) {
        List<Thread> threads = new ArrayList<Thread>();
        for(int i = 1; i < 4; i++){
            for(int k=1; k<3; k++){
                for(int j = 0; j<20; j++){
                    Random r = new Random();
                    Thread t = new Thread(new CheckCountOfChangesThread(image, byteImage, byteMessage, k, i, r.nextInt(50), this));
                    threads.add(t);
                    t.start();
                }
            }
        }
        for (Thread thread : threads) {
            while(thread.isAlive()){}
        }
    }
    
}
