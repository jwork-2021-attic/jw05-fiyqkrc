package com.pFrame.pwidget;

import java.util.ArrayList;

import com.pFrame.ObjectUserInteractive;
import com.pFrame.PLayout;
import com.pFrame.Pixel;
import com.pFrame.Position;

import java.awt.event.*;

public class PWidget implements ObjectUserInteractive {
    protected int widgetHeight;
    protected int widgetWidth;
    protected Position position;
    protected PLayout layout;
    protected PWidget parent;
    protected PWidget background;

    public void addBackground(PWidget background) {
        this.background = background;
        this.background.setPosition(Position.getPosition(0, 0));
        this.background.changeWidgetSize(this.getWidgetWidth(), this.getWidgetHeight());
        this.background.setParent(this);
    }

    public PWidget getBackground() {
        return this.background;
    }

    public PWidget(PWidget parent, Position p) {
        this.parent = parent;
        if (this.parent != null)
            this.parent.addChildWidget(this, p);
        else {
            this.widgetHeight = 0;
            this.widgetWidth = 0;
            this.position = Position.getPosition(0, 0);
        }
    }

    protected void addChildWidget(PWidget widget, Position p) {
        if (this.layout != null)
            this.layout.autoSetPosition(widget, p);
        else {
            widget.setPosition(Position.getPosition(0, 0));
            widget.changeWidgetSize(this.getWidgetWidth(), this.getWidgetHeight());
        }
    }

    protected void setLayout(PLayout layout) {
        this.layout = layout;
    }

    public PLayout getLayout() {
        return this.layout;
    }

    protected void setParent(PWidget widget) {
        this.parent = widget;
    }

    public PWidget getParentWidget() {
        return this.parent;
    }

    public Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            Pixel[][] pixels = Pixel.emptyPixels(this.widgetWidth, this.widgetHeight);
            if (this.background == null) {
            } else {
                pixels = Pixel.pixelsAdd(pixels, this.background.displayOutput(), this.background.getPosition());
            }
            ArrayList<PWidget> childWidget = new ArrayList<>();
            if (this.layout != null)
                childWidget.add(this.layout);
            for (PWidget widget : childWidget) {
                Pixel[][] childPixels = widget.displayOutput();
                Pixel.pixelsAdd(pixels, childPixels, widget.getPosition());
            }
            return pixels;
        }
    }

    public void update() {
        if (this.parent != null)
            this.parent.update();
    }

    public int getWidgetHeight() {
        return this.widgetHeight;

    }

    public int getWidgetWidth() {
        return this.widgetWidth;
    }

    public void changeWidgetSize(int width, int height) {
        this.widgetHeight = height;
        this.widgetWidth = width;
        this.sizeChanged();

    }

    protected void sizeChanged() {

    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;

    }

    public ArrayList<PWidget> getChildWidget() {
        ArrayList<PWidget> res = new ArrayList<PWidget>();
        res.add(this);
        if (this.layout != null)
            res.addAll(this.layout.getChildWidget());
        return res;
    }

    public ArrayList<PWidget> getWidgetsAt(Position p) {
        ArrayList<PWidget> res = new ArrayList<PWidget>();
        res.add(this);
        if (this.layout != null)
            res.addAll(this.layout.getWidgetsAt(p));
        return res;
    }

    public static class WidgetRange {
        public static boolean inRange(Position area, int width, int height, Position src) {
            if (src.getX() >= area.getX() && src.getX() < area.getX() + height)
                if (src.getY() >= area.getY() && src.getY() < area.getY() + width)
                    return true;
                else {
                    return false;
                }
            else
                return false;
        }
    }

    public Position getRealPosition() {
        if (this.parent != null)
            return Position.getPosition(this.getPosition().getX() + this.getParentWidget().getRealPosition().getX(),
                    this.getPosition().getY() + this.getParentWidget().getRealPosition().getY());
        else {
            return this.getPosition();
        }
    }

    public void mouseClicked(MouseEvent e, Position p) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0, Position p) {
    }

    public void mouseReleased(MouseEvent arg0, Position p) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public void addKeyListener(ObjectUserInteractive widget){
        this.parent.addKeyListener(widget);
    }

    public void freeKeyListener(){
        this.parent.freeKeyListener();
    }

    public void addMouseListener(ObjectUserInteractive widget){
        this.parent.addMouseListener(widget);
    }

    public void freeMouseListener(){
        this.parent.freeMouseListener();
    }

    public void addMouseWheelListener(ObjectUserInteractive widget){
        this.parent.addMouseWheelListener(widget);
    }

    public void freeMouseWheelListener(){
        this.parent.freeMouseWheelListener();
    }
}
