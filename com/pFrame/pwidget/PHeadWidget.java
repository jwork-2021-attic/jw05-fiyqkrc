package com.pFrame.pwidget;

import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;

public class PHeadWidget extends PWidget{
    PFrame pFrame;

    public PHeadWidget(PWidget parent, Position p,PFrame  pFrame) {
        super(parent, p);
        this.changeWidgetSize(pFrame.getFrameWidth(), pFrame.getFrameHeight());
        this.pFrame=pFrame;
        this.pFrame.setHeadWidget(this);
        this.pFrame.setDefaultCloseOperation(PFrame.EXIT_ON_CLOSE);
        this.pFrame.setVisible(true);
        this.pFrame.repaint();
        this.layout=new PLayout(this,null,1,1);
    }

    @Override
    public void update() {
        this.pFrame.repaint();
    }
}