package com.pFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.pFrame.pwidget.PWidget;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class PFrame extends JFrame implements KeyListener, MouseListener{

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

    public PFrame(int width,int height) {
        super();
        this.frameHeight=height;
        this.frameWidth=width;
        this.focusWidget=null;
        terminal = new AsciiPanel(width, height, AsciiFont.pFrame_8x8);
        this.charWidth=terminal.getCharWidth();
        add(terminal);
        pack();
        addKeyListener(this);
        addMouseListener(this);
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
                    this.terminal.write(pixels[i][j].getch(),j,i,pixels[i][j].getColor());
                }
            }
        }
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.focusWidget!=null)
            this.focusWidget.keyPressed(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

        Position p=mouseToPosition(arg0);

        ArrayList<PWidget> list=this.headWidget.getWidgetsAt(p);
        this.focusWidget=list.get(list.size()-1);

        Position realPosition=this.focusWidget.getRealPosition();
        Position pos=new Position(p.getX()-realPosition.getX(),p.getY()-realPosition.getY());

        this.focusWidget.mouseClicked(arg0, pos);

        repaint();
    }

    protected Position mouseToPosition(MouseEvent e){
        int x=e.getX();
        int y=e.getY();
        return new Position((y-this.getInsets().top)/this.charWidth,(x-this.getInsets().left)/this.charWidth);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {  
    }

}
