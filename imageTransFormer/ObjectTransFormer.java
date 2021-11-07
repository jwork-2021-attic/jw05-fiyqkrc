package imageTransFormer;

import java.awt.Color;
import java.awt.image.*;
import com.pFrame.Pixel;

import log.Log;

public class ObjectTransFormer {

    public static Pixel[][] transform(BufferedImage image,int width,int height){
        if(image==null||width<=0||height<=0){
            Log.ErrorLog(ObjectTransFormer.class, "illgel input");
            return null;
        }
        Pixel[][] pixels=new Pixel[height][width];
        int image_width=image.getWidth();
        int image_height=image.getHeight();

        int[] width_=new int[width];
        for(int i=0;i<image_width%width;i++)
            width_[i]=image_width/width+1;
        for(int i=image_width%width;i<width;i++)
            width_[i]=image_width/width;
        int[] height_=new int[height];
        for(int i=0;i<image_height%height;i++)
            height_[i]=image_height/height+1;
        for(int i=image_height%height;i<height;i++)
            height_[i]=image_height/height;

        int w=0;
        int h=0;
        for(int i=0;i<height;i++){
            w=0;
            for(int j=0;j<width;j++){
                pixels[i][j]=ObjectTransFormer.subPixelsTransform(h, w, width_[j], height_[i], image);
                w+=width_[j];
            }
            h+=height_[i];
        }

        return pixels;
    }

    private static Pixel subPixelsTransform(int x,int y,int width,int height,BufferedImage image){
        if(width<=0||height<=0){
            Log.WarningLog(ObjectTransFormer.class, "something error the file to transform may too small");
            return null;
        }
        int r=0;
        int g=0;
        int b=0;
        int ascii=0;
        int count=0;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                int rgb=image.getRGB(y+i, x+j);
                r+=(rgb>>16)&0xff;
                g+=(rgb>>8)&0xff;
                b+=rgb&0xff;
                ascii+=(rgb>>24)&0xff;
                count++;
            }
        }
        r=r/count;
        g=g/count;
        b=b/count;
        ascii=ascii/count;
        if(ascii<=64){
            ascii=0;
            return null;
        }
        else{
            int newRgb=(ascii<<24)+(r<<16)+(g<<8)+b;
            return new Pixel(new Color(newRgb),(char)0xf0);
        }
        
    }
    
}
