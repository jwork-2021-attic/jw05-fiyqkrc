package com.pFrame.pwidget;

import java.awt.Color;
import java.util.ArrayList;

import com.pFrame.PLayout;
import com.pFrame.Pixel;
import com.pFrame.Position;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class PWidget {
    protected int widgetHeight;
    protected int widgetWidth;
    protected Position position;
    protected PLayout layout;
    protected PWidget parent;

    public PWidget(PWidget parent, Position p) {
        this.parent = parent;
        if (this.parent != null)
            this.parent.addChildWidget(this, p);
        else{
            this.widgetHeight=0;
            this.widgetWidth=0;
            this.position=new Position(0, 0);
        }
    }

    protected void addChildWidget(PWidget widget, Position p) {
        if(this.layout!=null)
            this.layout.autoSetPosition(widget, p);
        else{
            widget.setPosition(new Position(0, 0));
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
            Pixel[][] pixels = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];
            for(int i=0;i<this.getWidgetHeight();i++){
                for(int j=0;j<this.getWidgetWidth();j++){
                    pixels[i][j]=new Pixel(Color.gray,(char) 0xb1);
                }
            }
            ArrayList<PWidget> childWidget=new ArrayList<>();
            if(this.layout!=null)
                childWidget.add(this.layout);
            for (PWidget widget : childWidget) {
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

    public void changeWidgetSize(int width, int height) {
        this.widgetHeight = height;
        this.widgetWidth = width;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;

    }

    public ArrayList<PWidget> getChildWidget() {
        ArrayList<PWidget> res=new ArrayList<PWidget>();
        res.add(this);
        if(this.layout!=null)
            res.addAll(this.layout.getChildWidget());
        return res;
    }

    public ArrayList<PWidget> getWidgetsAt(Position p) {
        ArrayList<PWidget> res = new ArrayList<PWidget>();
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

    public Position getRealPosition(){
        if(this.parent!=null)
            return new Position(this.getPosition().getX()+this.getParentWidget().getRealPosition().getX(), this.getPosition().getY()+this.getParentWidget().getRealPosition().getY());
        else{
            return this.getPosition();
        }
    }

    public void mouseClicked(MouseEvent e, Position p){
        
    }

    public void keyPressed(KeyEvent e){
    }
}
