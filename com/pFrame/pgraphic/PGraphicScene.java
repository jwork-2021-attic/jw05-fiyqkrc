package com.pFrame.pgraphic;

import java.util.ArrayList;

import com.pFrame.Pixel;
import com.pFrame.Position;

public class PGraphicScene {
    protected int width;
    protected int height;
    protected ArrayList<PGraphicItem> Items;
    protected PImage backImage;
    protected Pixel[][] pixels;

    public ArrayList<PGraphicItem> getItems() {
        return Items;
    }

    public PGraphicScene(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Pixel[height][width];
        this.backImage = null;
        this.Items = new ArrayList<>();
    }

    public ArrayList<PGraphicItem> getItemsAt(Position p) {
        ArrayList<PGraphicItem> res = new ArrayList<>();
        for (PGraphicItem item : this.Items)
            if (item.includePosition(p))
                res.add(item);
        return res;
    }

    public void setBackground(PImage image) {
        this.backImage = image;
    }

    public PGraphicItem getTopItemAt(Position p) {
        PGraphicItem res = null;
        for (PGraphicItem item : this.Items) {
            if (item.includePosition(p))
                res = item;
        }
        return res;
    }

    public Pixel[][] displayOutput(Position p, int width, int height) {
        this.updatePixels();
        Pixel[][] pix = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((p.getX() + i > 0 && p.getX() + i < this.height)
                        && (p.getY() + j > 0 && p.getY() + j < this.width)) {
                    pix[i][j] = this.pixels[p.getX() + i][p.getY() + j];
                } else {
                    pix[i][j] = null;
                }
            }
        }
        return pix;
    }

    protected void updatePixels() {
        if (this.backImage != null) {
            // TODO
        }
        for (PGraphicItem item : this.Items) {
            int w = item.getWidth();
            int h = item.getHeight();
            Pixel[][] pix = item.getPixels();
            int x = item.getPosition().getX();
            int y = item.getPosition().getY();
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if ((x + i > 0 && x + i < this.height) && (y + j > 0 && y + j < this.width)) {
                        if (pix[i][j] != null)
                            this.pixels[x + i][y + j] = pix[i][j];
                    }
                }
            }
        }
    }

    public boolean removeItem(PGraphicItem item) {
        return this.Items.remove(item);
    }

    public boolean addItem(PGraphicItem item) {
        this.Items.add(item);
        return true;
    }

    public boolean addItem(PGraphicItem item, Position p) {
        item.setPosition(p);
        this.Items.add(item);
        return true;
    }

    public Pixel[][] getPixels(int x, int y, int width, int height) {
        this.updatePixels();
        Pixel[][] res = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res[i][j] = this.pixels[x + i][y + j];
            }
        }
        return res;
    }

    public Pixel[][] getPixels(Position p, int width, int height) {
        this.updatePixels();
        Pixel[][] res = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res[i][j] = this.pixels[p.getX() + i][p.getY() + j];
            }
        }
        return res;
    }
}
