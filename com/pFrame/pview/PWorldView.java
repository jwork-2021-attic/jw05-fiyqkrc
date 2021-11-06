package com.pFrame.pview;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.creature.Thing;
import game.world.GameWorld;
import log.Log;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pwidget.PWidget;

public class PWorldView extends PWidget implements PView {

    protected Position viewPosition = new Position(0, 0);
    protected GameWorld world;
    protected Thing focus;

    @Override
    public void setViewPosition(Position p) {
        this.viewPosition = p;
    }

    @Override
    public Position getViewPosition() {
        return this.viewPosition;
    }

    public PWorldView(PWidget parent, Position p, GameWorld world) {
        super(parent, p);
        this.world = world;
        this.viewPosition = new Position(0, 0);
        this.focus = null;
    }

    @Override
    public Pixel[][] displayOutput() {
        adjustViewPosition();
        Pixel pixels[][] = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];

        for (int x = 0; x < this.getWidgetHeight(); x++) {
            for (int y = 0; y < this.getWidgetWidth(); y++) {
                pixels[x][y] = new Pixel(
                        world.get(this.getViewPosition().getX() + x, this.getViewPosition().getY() + y).getColor(),
                        world.get(this.getViewPosition().getX() + x, this.getViewPosition().getY() + y).getGlyph());
            }
        }
        return pixels;
    }

    @Override
    public Thing getFocus() {
        return focus;
    }

    @Override
    public void setFocus(Thing thing) {
        this.focus = thing;
        this.viewPosition = new Position(thing.getX() - this.getWidgetHeight() / 2,
                thing.getY() - this.getWidgetWidth() / 2);
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
            this.viewPosition = new Position(resX, resY);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (this.focus != null && this.focus.getController() != null) {
            this.focus.getController().respondToUserInput(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        System.out.printf("%s\n",p);
        super.mouseClicked(e, p);
    }
}
