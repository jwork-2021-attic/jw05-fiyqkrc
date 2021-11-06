package com.pFrame.widget;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class Widget {
    protected int widgetHeight;
    protected int widgetWidth;
    protected Position position;
    protected Layout layout;
    protected Widget parent;

    public Widget(Widget parent, Position p) {
        this.parent = parent;
        if (this.parent != null)
            this.parent.addChildWidget(this, p);
        else{
            this.widgetHeight=0;
            this.widgetWidth=0;
            this.position=new Position(0, 0);
        }
    }

    protected void addChildWidget(Widget widget, Position p) {
        if(this.layout!=null)
            this.layout.autoSetPosition(widget, p);
        else{
            widget.setPosition(new Position(0, 0));
            widget.changeWidgetSize(this.getWidgetWidth(), this.getWidgetHeight());
        }
    }

    protected void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return this.layout;
    }

    protected void setParent(Widget widget) {
        this.parent = widget;
    }

    public Widget getParentWidget() {
        return this.parent;
    }

    protected Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            Pixel[][] pixels = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];
            for(int i=0;i<this.getWidgetHeight();i++){
                for(int j=0;j<this.getWidgetWidth();j++){
                    pixels[i][j]=new Pixel(Color.gray,(char) 0xb1);
                }
            }
            ArrayList<Widget> childWidget=new ArrayList<>();
            if(this.layout!=null)
                childWidget.add(this.layout);
            for (Widget widget : childWidget) {
                Pixel[][] childPixels = widget.displayOutput();
                if (childPixels != null) {
                    Position pos = widget.getPosition();
                    int x_base = pos.getX();
                    int y_base = pos.getY();
                    int width = widget.getWidgetWidth();
                    int height = widget.getWidgetHeight();
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            pixels[x_base + i][y_base + j] = childPixels[i][j];
                        }
                    }
                }
            }
            return pixels;
        }
    }

    protected void update() {
        this.parent.update();
    }

    public int getWidgetHeight() {
        return this.widgetHeight;

    }

    public int getWidgetWidth() {
        return this.widgetWidth;
    }

    protected void changeWidgetSize(int width, int height) {
        this.widgetHeight = height;
        this.widgetWidth = width;
    }

    protected void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;

    }

    public ArrayList<Widget> getChildWidget() {
        ArrayList<Widget> res=new ArrayList<Widget>();
        res.add(this);
        if(this.layout!=null)
            res.addAll(this.layout.getChildWidget());
        return res;
    }

    public ArrayList<Widget> getWidgetsAt(Position p) {
        ArrayList<Widget> res = new ArrayList<Widget>();
        res.add(this);
        if(this.layout!=null)
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

    protected void mouseClicked(MouseEvent arg0){
    }

    protected void keyPressed(KeyEvent e){
    }
}
