package com.pFrame.pgraphic;

import java.io.File;

import com.pFrame.Pixel;
import com.pFrame.Position;

import log.Log;

public class PGraphicItem {
    protected int width;
    protected int height;
    protected Pixel[][] graphic;
    protected Position p;

    public Position getPosition(){
        return this.p;
    }

    public void setPosition(Position pos){
        this.p=pos;
    }

    public PGraphicItem(File file){
        Log.ErrorLog(this, "this method :'PGraphicItem(String path)' need finish ");

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

    public boolean includePosition(Position pos){
        return (pos.getX()>this.p.getX()&&pos.getX()<this.p.getX()+this.height)&&(pos.getY()>this.p.getY()&&pos.getY()<this.p.getY()+width);
    }

}
