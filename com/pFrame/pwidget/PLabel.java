package com.pFrame.pwidget;

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
            return Pixel.emptyPixels(this.getWidgetWidth(), this.getWidgetHeight());
        }
    }
}
