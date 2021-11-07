package com.pFrame.pwidget;

import java.awt.Color;

import com.pFrame.Pixel;
import com.pFrame.Position;

public class PLabel extends PWidget {

    public PLabel(PWidget parent, Position p) {
        super(parent, p);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            Pixel[][] pixels = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];
            for(int i=0;i<this.getWidgetHeight();i++){
                for(int j=0;j<this.getWidgetWidth();j++){
                    pixels[i][j]=new Pixel(Color.GREEN,(char) 0xf0);
                }
            }
            return pixels;
        }
    }
}
