package com.pFrame.pwidget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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

    protected ArrayList<PWidget> childWidgets;

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
        this.childWidgets=new ArrayList<>();
        if (this.parent != null)
            this.parent.addChildWidget(this, p);
        else {
            this.widgetHeight = 0;
            this.widgetWidth = 0;
            this.position = Position.getPosition(0, 0);
        }
    }

    public void addChildWidget(PWidget widget, Position p) {
        if (this.layout != null)
            this.layout.addChildWidget(widget,p);
            //this.layout.autoSetPosition(widget, p);
        else {
            widget.setParent(this);
            if(p==null)
                widget.setPosition(p);
            else{
                widget.setPosition(Position.getPosition(0,0));
            }
            this.childWidgets.add(widget);
            //widget.changeWidgetSize(this.getWidgetWidth(), this.getWidgetHeight());
        }
    }

    public void resetLayout(PLayout layout){
        for(PWidget widget:childWidgets){
            if(widget==this.layout) {
                childWidgets.remove(widget);
                childWidgets.add(layout);
            }
        }
        this.layout=layout;
        if(layout!=null) {
            this.layout.setPosition(Position.getPosition(0, 0));
            this.layout.changeWidgetSize(this.widgetWidth, this.widgetHeight);
        }
    }

    protected void setLayout(PLayout layout) {
        this.layout = layout;
    }

    public PLayout getLayout() {
        return this.layout;
    }

    public void setParent(PWidget widget) {
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
                //do nothing
            } else {
                pixels = Pixel.pixelsAdd(pixels, this.background.displayOutput(), this.background.getPosition());
            }/*
            if(this.layout==null){
                //do nothing
            }
            else{
                pixels=Pixel.pixelsAdd(pixels,this.layout.displayOutput(),this.layout.getPosition());
            }*/
            for(PWidget widget:this.childWidgets){

                Pixel.pixelsAdd(pixels,widget.displayOutput(),widget.getPosition());
            }
            return pixels;
        }
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
        if(this.background!=null){
            this.background.changeWidgetSize(this.widgetWidth,this.widgetHeight);
        }
        if(this.layout!=null){
            this.layout.changeWidgetSize(this.widgetWidth,this.widgetHeight);
        }
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
        res.addAll(childWidgets);
        return res;
    }

    public ArrayList<PWidget> getWidgetsAt(Position p) {
        ArrayList<PWidget> res = new ArrayList<PWidget>();
        res.add(this);
        if (this.layout != null)
            res.addAll(this.layout.getWidgetsAt(p));
        for(PWidget widget:this.childWidgets) {
            if (widget != layout)
                if (WidgetRange.inRange(widget.position, widget.widgetWidth, widget.widgetHeight, p)) {
                    res.add(widget);
                }
        }
        return res;
    }

    public static class WidgetRange {
        public static boolean inRange(Position area, int width, int height, Position src) {
            if (src.getX() >= area.getX() && src.getX() < area.getX() + height)
                return src.getY() >= area.getY() && src.getY() < area.getY() + width;
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

    protected Method clickMethod;
    protected Object clickMethodObject;

    public void setClickFunc(Object object,Method method){
        clickMethod=method;
        clickMethodObject=object;
    }

    public void mouseClicked(MouseEvent mouseEvent, Position p) {
        if(clickMethod!=null&&clickMethodObject!=null) {
            try {
                clickMethod.invoke(clickMethodObject, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
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
