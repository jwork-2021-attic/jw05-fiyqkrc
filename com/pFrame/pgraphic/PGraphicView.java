package com.pFrame.pgraphic;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pwidget.PWidget;

import log.Log;

public class PGraphicView extends PWidget implements PView {

    protected Position viewPosition = Position.getPosition(0, 0);
    protected PGraphicScene scene;
    protected PGraphicItem focus;

    @Override
    public void setViewPosition(Position p) {
        this.viewPosition = p;
    }

    @Override
    public Position getViewPosition() {
        return this.viewPosition;
    }

    public PGraphicView(PWidget parent, Position p, PGraphicScene world) {
        super(parent, p);
        this.scene = world;
        this.viewPosition = Position.getPosition(0, 0);
        this.focus = null;
    }

    @Override
    public Pixel[][] displayOutput() {
        adjustViewPosition();
        return this.scene.displayOutput(this.getViewPosition(), this.getWidgetWidth(), this.getWidgetHeight());
    }

    @Override
    public PGraphicItem getFocus() {
        return focus;
    }

    @Override
    public void setFocus(PGraphicItem thing) {
        this.focus = thing;
        this.viewPosition = Position.getPosition(thing.getPosition().getX() - this.getWidgetHeight() / 2,
                thing.getPosition().getY() - this.getWidgetWidth() / 2);
    }

    protected void adjustViewPosition() {
        if (this.getFocus() == null) {
            Log.WarningLog(this, "focus thing not be set,so not exec adjustViewPosition");
            return;
        } else {
            Position focusPosition = this.getFocus().getPosition();
            Position viewPosition = this.getViewPosition();
            int absX = focusPosition.getX() - viewPosition.getX();
            int absY = focusPosition.getY() - viewPosition.getY();
            int resX = 0;
            int resY = 0;
            if (absX < this.getWidgetHeight() / 5) {
                resX = viewPosition.getX() - 1;
            } else if (absX > this.getWidgetHeight() * 4 / 5) {
                resX = viewPosition.getX() + 1;
            } else {
                resX = viewPosition.getX();
            }

            if (absY < this.getWidgetWidth() / 5) {
                resY = viewPosition.getY() - 1;
            } else if (absY > this.getWidgetWidth() * 4 / 5) {
                resY = viewPosition.getY() + 1;
            } else {
                resY = viewPosition.getY();
            }
            this.viewPosition = Position.getPosition(resX, resY);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        System.out.printf("%s\n",p);
        super.mouseClicked(e, p);
    }
}
