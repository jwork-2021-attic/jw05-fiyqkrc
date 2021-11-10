package com.pFrame;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PImage;
import com.pFrame.pgraphic.PMovie;

public class Pixel {
    private Color color;
    private char ch;

    static private Map<Color, Map<Character, Pixel>> PixelsPool=new HashMap<>(); 

    private Pixel(Color color, char ch){
        this.ch=ch;
        this.color=color;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public Color getColor(){
        return this.color;
    }

    public char getch(){
        return this.ch;
    }
    
    public Pixel copy(){
        return new Pixel(color, ch);
    }

    static private Pixel findPixel(Color color,char ch){
        if(PixelsPool.containsKey(color)){
            Map<Character, Pixel> map=PixelsPool.get(color);
            if(map.containsKey(ch)){
                return map.get(ch);
            }
            else{
                Pixel pixel=new Pixel(color, ch);
                map.put(ch, pixel);
                return pixel;
            }
        }
        else{
            Map<Character, Pixel> map=new HashMap<>();
            Pixel pixel=new Pixel(color, ch);
            PixelsPool.put(color, map);
            map.put(ch,pixel);
            return pixel;
        }
    }

    public static Pixel getPixel(Color color, char ch){
        return new Pixel(color, ch);
    }

    static public Pixel[][] pixelsAdd(Pixel[][] src,Pixel[][] dest,Position position){
        if(src==null){
            return null;
        }
        else if(dest==null){
            return src;
        }
        else{
            int h=dest.length;
            int w=dest[0].length;
            for(int i=0;i<h;i++){
                for(int j=0;j<w;j++){
                    if((position.getX()+i<src.length&&position.getX()+i>=0)&&(position.getY()+j<src[0].length&&position.getY()+j>=0)){
                        if(dest[i][j]!=null)
                            src[position.getX()+i][position.getY()+j]=dest[i][j];
                    }
                }
            }
            return src;
        }
    } 

    static public Pixel[][] emptyPixels(int width,int height){
        Pixel[][] pixels=new Pixel[height][width];
        for(int i=0;i<height;i++)   
            for(int j=0;j<width;j++)
                pixels[i][j]=null;
        return pixels;
    }

    static public Pixel[][] valueOf(PGraphicItem item){
        return item.getPixels();
    }

    static public Pixel[][] valueOf(PImage item){
        return null;
        //TODO;
    }

    static public Pixel[][] valueOfThisTime(PMovie movie){
        return null;
        //TODO
    }

    static public Pixel[][][] allValuesOf(PMovie movie){
        //TODO
        return null;
    }

    static public Pixel[][] subPixels(Pixel[][] pixels,Position p,int width,int height){
        Pixel[][] res=Pixel.emptyPixels(width, height);
        if(pixels==null)
            return null;
        int h=pixels.length;
        int w=pixels[0].length;
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++){
                if((p.getX()+i<h&&p.getX()+i>=0)&&(p.getY()+j<w&&p.getY()+j>0)){
                    res[i][j]=pixels[p.getX()+i][p.getY()+j];
                }
            }
        return res;
    }

    static public Pixel[][] pixelsScaleLarger(Pixel[][] pixels,int scale){
        if(pixels==null||scale==1)
            return Pixel.pixelsCopy(pixels);
        else{
            int originHeight=pixels.length;
            int originWidth=pixels[0].length;
            Pixel[][] res= Pixel.emptyPixels(originWidth*scale, originHeight*scale);
            for(int i=0;i<originHeight;i++){
                for(int j=0;j<originWidth;j++){
                    for(int a=0;a<scale;a++){
                        for(int b=0;b<scale;b++){
                            res[i*scale+a][j*scale+b]=pixels[i][j];
                        }
                    }
                }
            }
            return res;
        }
    }

    static public Pixel[][] pixelsCopy(Pixel[][] pixels){
        if(pixels==null)    
            return null;
        else{
            int width=pixels[0].length;
            int height=pixels.length;
            Pixel[][] res=Pixel.emptyPixels(width, height);
            for(int i=0;i<height;i++)
                for(int j=0;j<width;j++){
                    res[i][j]=pixels[i][j];
                }
            return res;
        }
    }

    static public Pixel[][] pixelsSetColor(Pixel[][] pixels,Color color){
        if(pixels==null||color==null)
            return null;
        else{
            int w=pixels[0].length;
            int h=pixels.length;
            for(int i=0;i<h;i++)
                for(int j=0;j<w;j++){
                    if(pixels[i][j]!=null)
                        pixels[i][j].setColor(color);
                }
            return pixels;
        }
    }

    static public PGraphicItem toItem(Pixel[][] pixels){
        return new PGraphicItem(pixels);
    }

    static public PImage toImage(Pixel[][] pixels){
        return new PImage();
        //TODO
    }
}
