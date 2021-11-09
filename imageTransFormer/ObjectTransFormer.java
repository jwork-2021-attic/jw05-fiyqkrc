package imageTransFormer;

import java.awt.Color;
import java.awt.image.*;
import java.util.Map;
import java.util.HashMap;

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
        Map<String, Integer> colorMap=new HashMap<>();
        int r=0;
        int g=0;
        int b=0;
        int ascii=0;
        int count=0;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                
                int rgb=image.getRGB(y+i, x+j);
                if(((rgb>>24)&0xff)>64)
                {
                    r+=(rgb>>16)&0xff;
                    g+=(rgb>>8)&0xff;
                    b+=rgb&0xff;
                    ascii+=(rgb>>24)&0xff;
                    String colorStr=String.format("%s-%s-%s", ((rgb>>16)&0xff)>>4,((rgb>>8)&0xff)>>4,(rgb&0xff)>>4);
                    if(colorMap.containsKey(colorStr)){
                        colorMap.put(colorStr, colorMap.get(colorStr)+1);
                    }
                    else{
                        colorMap.put(colorStr, 1);
                    }
                    count++;
                }
            }
        }
        if(count>0){
            r=r/count;
            g=g/count;
            b=b/count;
            ascii=ascii/count;
        }


        if(count<=width*height/4){
            ascii=0;
            return null;
        }
        else{
            int maxcount=0;
            String maxColor="";
            for(String key:colorMap.keySet()){
                //Log.InfoLog(ObjectTransFormer.class, String.format("%s %d", key,colorMap.get(key)));
                if(colorMap.get(key)>maxcount){
                    maxcount=colorMap.get(key);
                    maxColor=key;
                }
            }
            if(maxcount>=0 && maxcount!=0){
                //Log.InfoLog(ObjectTransFormer.class,maxColor);
                String[] rgbstr=maxColor.split("-");
                int newRgb=((ascii<<24)+(Integer.valueOf(rgbstr[0])<<20)+(Integer.valueOf(rgbstr[1])<<12)+(Integer.valueOf(rgbstr[2])<<4));
                return Pixel.getPixel(new Color(newRgb),(char)0xf0);
            }
            else{
                int newRgb=(ascii<<24)+(r<<16)+(g<<8)+b;
                return Pixel.getPixel(new Color(newRgb),(char)0xf0);
            }
        }
        
    }
    
}
