package com.pFrame.pwidget;

import java.awt.event.MouseEvent;

import com.pFrame.Pixel;
import com.pFrame.Position;
import java.awt.Color;
import java.util.ArrayList;

import log.Log;

public class PButton extends PWidget {

    PLabel textLabel;

    public PButton(PWidget parent, Position p) {

        super(parent, p);
        this.textLabel = new PLabel(this, Position.getPosition(0,0));
        this.textLabel.changeWidgetSize(this.widgetWidth,this.widgetHeight);

    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        Log.InfoLog(this, "be click");
        super.mouseClicked(e, p);
    }

    @Override
    public Pixel[][] displayOutput() {
        Pixel[][] pixels = super.displayOutput();
        return Pixel.pixelsAdd(pixels, this.textLabel.displayOutput(), this.textLabel.getPosition());
    }

    public void setText(String text, int size, Color color) {
        this.textLabel.setText(text, size, color);
    }

    @Override
    public ArrayList<PWidget> getWidgetsAt(Position p) {
        ArrayList<PWidget> res=new  ArrayList<PWidget>();
        res.add(this);
        return res;
    }

    @Override
    public ArrayList<PWidget> getChildWidget() {
        return getWidgetsAt(null);
    }
}
