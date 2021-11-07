package com.pFrame.pgraphic;

import com.pFrame.Pixel;
import com.pFrame.Position;

public class PGraphicItem {
    protected int width;
    protected int height;
    protected Pixel[][] graphic;
    protected Position p;

    public Position getPosition(){
        return this.p;
    }

    public PGraphicItem(String path){

    }

    public PGraphicItem(Pixel[][] pixels){
        this.graphic=pixels;
        if(pixels!=null){
            this.height=pixels.length;
            this.width=pixels[0].length;
        }
        else{
            this.width=0;
            this.height=0;
        }
        p=new Position(0, 0);
    }

    public Pixel[][] getPixels(){
        return graphic;

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
