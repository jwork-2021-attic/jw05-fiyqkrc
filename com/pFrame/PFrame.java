package com.pFrame;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.pFrame.pwidget.PWidget;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import log.Log;

public class PFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseWheelListener{

    private AsciiPanel terminal;
    protected PWidget headWidget;
    protected PWidget focusWidget;
    protected int frameWidth;
    protected int frameHeight;
    protected int charWidth;

    

    public int getFrameWidth(){
        return this.frameWidth;
    }

    public int getFrameHeight(){
        return this.frameHeight;
    }

    public void setHeadWidget(PWidget widget){
        this.headWidget=widget;
    }

    public PFrame(int width,int height,AsciiFont asciiFont) {
        super();
        this.frameHeight=height;
        this.frameWidth=width;
        this.focusWidget=null;
        terminal = new AsciiPanel(width, height, asciiFont);
        this.charWidth=terminal.getCharWidth();
        add(terminal);
        pack();
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void repaint() {
        terminal.clear();
        Pixel[][] pixels=this.headWidget.displayOutput();
        if(pixels!=null){
            int height=pixels.length;
            int width=pixels[0].length;
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    if(pixels[i][j]!=null)
                        this.terminal.write(pixels[i][j].getch(),j,i,pixels[i][j].getColor());
                    else
                        this.terminal.write((char)0x00,j,i,Color.BLACK);
                }
            }
        }
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(this.focusWidget!=null){
            this.focusWidget.keyTyped(e);
            //repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.focusWidget!=null){
            this.focusWidget.keyPressed(e);
            //repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(this.focusWidget!=null){
            this.focusWidget.keyReleased(e);
            //repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

        Position p=mouseToPosition(arg0);

        ArrayList<PWidget> list=this.headWidget.getWidgetsAt(p);
        this.focusWidget=list.get(list.size()-1);

        Position realPosition=this.focusWidget.getRealPosition();
        Position pos=Position.getPosition(p.getX()-realPosition.getX(),p.getY()-realPosition.getY());

        this.focusWidget.mouseClicked(arg0, pos);

        //repaint();
    }

    protected Position mouseToPosition(MouseEvent e){
        int x=e.getX();
        int y=e.getY();
        return Position.getPosition((y-this.getInsets().top)/this.charWidth,(x-this.getInsets().left)/this.charWidth);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        this.headWidget.mouseEntered(arg0);
        //repaint();
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        this.headWidget.mouseExited(arg0);
        //repaint();
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        Position p=mouseToPosition(arg0);

        ArrayList<PWidget> list=this.headWidget.getWidgetsAt(p);
        PWidget topWidget=list.get(list.size()-1);

        Position realPosition=topWidget.getRealPosition();
        Position pos=Position.getPosition(p.getX()-realPosition.getX(),p.getY()-realPosition.getY());

        topWidget.mousePressed(arg0, pos);

        //repaint();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {  
        Position p=mouseToPosition(arg0);

        ArrayList<PWidget> list=this.headWidget.getWidgetsAt(p);
        PWidget topWidget=list.get(list.size()-1);

        Position realPosition=topWidget.getRealPosition();
        Position pos=Position.getPosition(p.getX()-realPosition.getX(),p.getY()-realPosition.getY());

        topWidget.mouseReleased(arg0, pos);

        //repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(this.focusWidget!=null){
            this.focusWidget.mouseWheelMoved(e);
            //repaint();
        }
    }

    @Override
    public void run() {
        int i=0;
        while(true){
            try {
                this.repaint();
                Thread.sleep(50);
                i++;
                //Log.ErrorLog(this, String.format("flash windows %d times",i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
