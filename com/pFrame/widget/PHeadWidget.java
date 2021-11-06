package com.pFrame.widget;

public class PHeadWidget extends Widget{
    PFrame pFrame;

    public PHeadWidget(Widget parent, Position p,PFrame  pFrame) {
        super(parent, p);
        this.changeWidgetSize(pFrame.getFrameWidth(), pFrame.getFrameHeight());
        this.pFrame=pFrame;
        this.pFrame.setHeadWidget(this);
        this.pFrame.setDefaultCloseOperation(PFrame.EXIT_ON_CLOSE);
        this.pFrame.setVisible(true);
        this.pFrame.repaint();
        this.layout=new Layout(this,null,1,1);
    }

    @Override
    protected void update() {
        this.pFrame.repaint();
    }
}