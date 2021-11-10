package com.pFrame.pgraphic;

import java.io.File;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pwidget.PWidget;

import imageTransFormer.GraphicItemGenerator;

public class PImage extends PWidget{

    protected Pixel[][] content;
    protected File file;

    public Pixel[][] getPixels(){
        return this.content;
    }

    public PImage(PWidget parent, Position p) {
        super(parent, p);
        this.content=null;
        this.file=null;
    }

    public PImage(PWidget parent, Position p, File file) {
        super(parent, p);
        this.content=null;
        this.file=file;
        this.load(file);
    }

    @Override
    protected void sizeChanged() {
        if(this.file!=null)
            this.load(file);
    }
    
    public void load(File file){
        if(this.getWidgetHeight()!=0&&this.getWidgetWidth()!=0){
            System.out.printf("%d %d\n",this.getWidgetHeight(),this.getWidgetWidth());
            this.content=Pixel.valueOf(GraphicItemGenerator.generateItem(file, this.getWidgetWidth(), this.getWidgetHeight()));
        
        }
    }

    @Override
    public Pixel[][] displayOutput() {
        Pixel[][] pixels=super.displayOutput();
        return Pixel.pixelsAdd(pixels, this.content, this.position);
    }
}