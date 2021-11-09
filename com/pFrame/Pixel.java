package com.pFrame;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Pixel {
    private Color color;
    private char ch;

    static private Map<Color, Map<Character, Pixel>> PixelsPool=new HashMap<>(); 

    private Pixel(Color color, char ch){
        this.ch=ch;
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
}
