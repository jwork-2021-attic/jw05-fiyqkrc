package com.pFrame.pwidget;

import com.pFrame.PFont;
import com.pFrame.Pixel;
import com.pFrame.Position;
import java.awt.Color;

public class PLabel extends PWidget {
    protected String text = "";
    protected Color color = Color.WHITE;
    protected Pixel[][] content;
    protected int fontScale = 1;

    public PLabel(PWidget parent, Position p) {
        super(parent, p);
        this.content = Pixel.emptyPixels(this.getWidgetWidth(), this.getWidgetHeight());
        this.fontScale = 1;
        this.text = "";
    }

    @Override
    public Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            return this.content;
        }
    }

    public void setText(String text, int size, Color color) {
        this.text = text;
        if (color != null)
            this.color = color;
        this.fontScale = size;
        this.updateDraw();
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();

        this.content = Pixel.emptyPixels(this.getWidgetWidth(), this.getWidgetHeight());
        this.updateDraw();
    }

    protected void updateDraw() {
        if (this.content != null && this.fontScale != 0 && this.color != null) {
            int line = (1 >= (this.getWidgetHeight() / (PFont.fontBaseSize * this.fontScale))) ? 1
                    : this.getWidgetHeight() / (PFont.fontBaseSize * this.fontScale);
            int charsInLine = (1 >= (this.getWidgetWidth() / (PFont.fontBaseSize * this.fontScale))) ? 1
                    : this.getWidgetWidth() / (PFont.fontBaseSize * this.fontScale);
            int chIndex = 0;
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < charsInLine; j++) {
                    if (chIndex < this.text.length()){
                        Pixel[][] fontPixels=Pixel.pixelsCopy(PFont.getCharByPixels(this.text.charAt(chIndex)));
                        fontPixels=Pixel.pixelsSetColor(fontPixels,this.color);
                        Pixel.pixelsAdd(this.content,fontPixels,Position.getPosition(PFont.fontBaseSize * i * fontScale,PFont.fontBaseSize * j * fontScale));
                        chIndex++;
                    }

                }
            }
        }
    }
}
