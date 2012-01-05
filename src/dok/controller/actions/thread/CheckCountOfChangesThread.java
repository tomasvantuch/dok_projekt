/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.controller.actions.thread;

import dok.controller.actions.ProceedAction;
import dok.model.Result;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 *
 * @author Tomas
 */
public class CheckCountOfChangesThread implements Runnable{
    
    private BufferedImage image;
    private byte[] message;
    private byte[] bimage;
    private int each;
    private int color;
    private int count;
    private ProceedAction pa;
    private int from;
    private int actualPosition;

    public CheckCountOfChangesThread(BufferedImage image, byte[] bimage, byte[] message, int each, int color, int from, ProceedAction pa){
        this.each = each;
        this.from = from;
        this.color = color;
        this.image = image;
        this.bimage = bimage;
        this.message = message;
        this.pa = pa;
        actualPosition = 0;
        count= 0;
    }
    
    @Override
    public void run() {
        byte[] imageData = get_byte_data(image);
        
        if(bimage.length * 8 > imageData.length/3)
	{
		throw new IllegalArgumentException("File not long enough!");
	}
        int index = from;
        for(int i=from; i<bimage.length; ++i)
        {
                int bim = bimage[i];
                for(int bit=7; bit>=0; --bit, index += (each*color)) 
                {
                        int b = (bim >>> bit) & 1;
                        
                        if((imageData[index]%2)!=b){
                            count++;
                        }
                }
        }
        pa.addResult(new Result(count,color,each, bimage.length,message.length, from));
    }


    
    
     private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
    
}
