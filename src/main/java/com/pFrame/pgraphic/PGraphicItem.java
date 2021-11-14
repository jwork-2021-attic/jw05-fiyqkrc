package com.pFrame.pgraphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import com.pFrame.Pixel;
import com.pFrame.Position;

import imageTransFormer.GraphicItemGenerator;
import imageTransFormer.ObjectTransFormer;

public class PGraphicItem {
    protected int width;
    protected int height;
    protected Pixel[][] graphic;
    protected Position p;
    static protected HashMap<String, PGraphicItem> items = new HashMap<>();
    protected PGraphicScene parentScene;

    public void setParentScene(PGraphicScene scene){
        this.parentScene=scene;
    }

    public void removeParentScene(){
        this.parentScene=null;
    }

    public Position getPosition() {
        return this.p;
    }

    public void setPosition(Position pos) {
        this.p = pos;
        if(this.parentScene!=null)
            this.parentScene.repaintItem(this);
    }

    public PGraphicItem(String path, int width, int height) {
        File file = new File(path);
        if (PGraphicItem.items.containsKey(file.getName())) {
            PGraphicItem modal = PGraphicItem.items.get(file.getName());
            if (width == modal.width && height == modal.height) {
                this.graphic = Pixel.pixelsCopy(modal.getPixels());
                this.height = modal.height;
                this.width = modal.width;
            } else {
                this.graphic = Pixel.valueOf(ObjectTransFormer.toBufferedImage(Pixel.toBufferedImage(modal.getPixels())
                        .getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH)));
                this.height = height;
                this.width = width;
            }
            this.p = Position.getPosition(0, 0);
        } else {

            PGraphicItem item = GraphicItemGenerator.generateItem(file, width, height);
            this.graphic = item.getPixels();
            if (this.graphic != null) {
                this.height = this.graphic.length;
                this.width = this.graphic[0].length;
            } else {
                this.width = 0;
                this.height = 0;
            }
            p = Position.getPosition(0, 0);
            PGraphicItem.items.put(file.getName(), this);
        }
    }

    public PGraphicItem(File file, int width, int height) {

        if (PGraphicItem.items.containsKey(file.getName())) {
            PGraphicItem modal = PGraphicItem.items.get(file.getName());
            if (width == modal.width && height == modal.height) {
                this.graphic = Pixel.pixelsCopy(modal.getPixels());
                this.height = modal.height;
                this.width = modal.width;
            } else {
                this.graphic = Pixel.valueOf(ObjectTransFormer.toBufferedImage(Pixel.toBufferedImage(modal.getPixels())
                        .getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH)));
                this.height = height;
                this.width = width;
            }
            this.p = Position.getPosition(0, 0);
        } else {

            PGraphicItem item = GraphicItemGenerator.generateItem(file, width, height);
            this.graphic = item.getPixels();
            if (this.graphic != null) {
                this.height = this.graphic.length;
                this.width = this.graphic[0].length;
            } else {
                this.width = 0;
                this.height = 0;
            }
            p = Position.getPosition(0, 0);
            PGraphicItem.items.put(file.getName(), this);
        }
    }

    public PGraphicItem(Pixel[][] pixels) {
        this.graphic = pixels;
        if (pixels != null) {
            this.height = pixels.length;
            this.width = pixels[0].length;
        } else {
            this.width = 0;
            this.height = 0;
        }
        p = Position.getPosition(0, 0);
    }

    public Pixel[][] getPixels() {
        return graphic;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean includePosition(Position pos) {
        return (pos.getX() > this.p.getX() && pos.getX() < this.p.getX() + this.height)
                && (pos.getY() > this.p.getY() && pos.getY() < this.p.getY() + width);
    }

}
