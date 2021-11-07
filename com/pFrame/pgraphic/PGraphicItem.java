package com.pFrame.pgraphic;

import com.pFrame.Pixel;

public class PGraphicItem {
    protected int width;
    protected int height;
    protected Pixel[][] graphic;

    public PGraphicItem(String path){

    }

    public PGraphicItem(Pixel[][] pixels){

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
