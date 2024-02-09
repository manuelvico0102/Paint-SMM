/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mva.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author Manuel Vico
 */
public class TonoColoresOp extends BufferedImageOpAdapter{
    private int fi;

    public TonoColoresOp(int fi) {
        this.fi = fi;
    }
    
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        
        int[] rgbPixel = new int[srcRaster.getNumBands()];
        int[] rgbNuevo = new int[srcRaster.getNumBands()];
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, rgbPixel);
                
                float[] hsb  = new float[3];
                Color.RGBtoHSB(rgbPixel[0], rgbPixel[1], rgbPixel[2], hsb);
                
                float hResultado = (float) (((hsb[0]*360+this.fi)%360)/360.0);
                int colorInt = Color.HSBtoRGB(hResultado, hsb[1], hsb[2]);
                
                rgbNuevo[0] = (colorInt >> 16) & 0xFF;
                rgbNuevo[1] = (colorInt >> 8) & 0xFF;
                rgbNuevo[2] = colorInt & 0xFF;
                
                destRaster.setPixel(x, y, rgbNuevo);
            }
        }
        return dest;
    }
    
}
