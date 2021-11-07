package com.pFrame;

import java.awt.event.MouseEvent;

import com.pFrame.pwidget.PWidget;
import java.awt.Color;

import log.Log;

public class PButton extends PWidget {

    PLabel textLabel;

    public PButton(PWidget parent, Position p) {
        super(parent, p);
        this.textLabel=new PLabel(this, null);
    }
    

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        Log.InfoLog(this, "be click");
        super.mouseClicked(e, p);
    }

    @Override
    public Pixel[][] displayOutput() {
        return this.textLabel.displayOutput();
    }
}
