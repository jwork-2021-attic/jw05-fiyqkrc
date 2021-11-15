package com.pFrame;

import java.awt.Color;
import java.awt.event.*;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;

import com.pFrame.pwidget.PWidget;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class PFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseWheelListener {

    private AsciiPanel terminal;
    protected PWidget headWidget;

    protected ObjectUserInteractive keyListener;
    protected ObjectUserInteractive mouseListener;
    protected ObjectUserInteractive mouseWheelListener;

    protected ObjectUserInteractive focusWidget;

    protected int frameWidth;
    protected int frameHeight;
    protected int charWidth;

    public void setKeyListener(ObjectUserInteractive keyListener){
        this.keyListener=keyListener;
    }

    public void freeKeyListener(){
        this.keyListener=null;
    }

    public void setMouseListener(ObjectUserInteractive mouseListener){
        this.mouseListener=mouseListener;
    }

    public void freeMouseListener(){
        this.mouseListener=null;
    }

    public void setMouseWheelListener(ObjectUserInteractive mouseWheelListener){
        this.mouseWheelListener=mouseWheelListener;
    }

    public void freeMouseWheelListener(){
        this.mouseWheelListener=null;
    }

    public int getFrameWidth() {
        return this.frameWidth;
    }

    public int getFrameHeight() {
        return this.frameHeight;
    }

    public void setHeadWidget(PWidget widget) {
        this.headWidget = widget;
    }

    public PFrame(int width, int height, AsciiFont asciiFont) {
        super();
        this.frameHeight = height;
        this.frameWidth = width;
        this.focusWidget = null;
        terminal = new AsciiPanel(width, height, asciiFont);
        this.charWidth = terminal.getCharWidth();
        add(terminal);
        pack();
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void repaint() {
        Pixel[][] pixels = this.headWidget.displayOutput();
        if (pixels != null) {
            int height = pixels.length;
            int width = pixels[0].length;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (pixels[i][j] != null)
                        this.terminal.write(pixels[i][j].getCh(), j, i, pixels[i][j].getColor());
                    else
                        this.terminal.write((char) 0x00, j, i, Color.BLACK);
                }
            }
        }
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(this.keyListener!=null){
            this.keyListener.keyTyped(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.keyListener!=null){
            this.keyListener.keyPressed(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(this.keyListener!=null){
            this.keyListener.keyReleased(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        Position p = mouseToPosition(arg0);
        if (this.mouseListener != null) {
            this.mouseListener.mouseClicked(arg0,
                    Position.getPosition(p.getX() - mouseListener.getRealPosition().getX(),
                            p.getY() - mouseListener.getRealPosition().getY()));

        } else {

            ArrayList<PWidget> list = this.headWidget.getWidgetsAt(p);
            this.focusWidget = list.get(list.size() - 1);

            Position realPosition = this.focusWidget.getRealPosition();
            Position pos = Position.getPosition(p.getX() - realPosition.getX(), p.getY() - realPosition.getY());

            this.focusWidget.mouseClicked(arg0, pos);
        }
    }

    protected Position mouseToPosition(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        return Position.getPosition((y - this.getInsets().top) / this.charWidth,
                (x - this.getInsets().left) / this.charWidth);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        this.headWidget.mouseEntered(arg0);
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        this.headWidget.mouseExited(arg0);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        Position p = mouseToPosition(arg0);
        if (this.mouseListener != null) {
            this.mouseListener.mousePressed(arg0, Position.getPosition(p.getX() - mouseListener.getRealPosition().getX(),
                    p.getY() - mouseListener.getRealPosition().getY()));
        } else if (this.focusWidget != null) {
            this.focusWidget.mousePressed(arg0, Position.getPosition(p.getX() - focusWidget.getRealPosition().getX(),
                    p.getY() - focusWidget.getRealPosition().getY()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        Position p = mouseToPosition(arg0);
        if (this.mouseListener != null) {
            this.mouseListener.mouseReleased(arg0, Position.getPosition(p.getX() - mouseListener.getRealPosition().getX(),
                    p.getY() - mouseListener.getRealPosition().getY()));
        } else if (this.focusWidget != null) {
            this.focusWidget.mouseReleased(arg0, Position.getPosition(p.getX() - focusWidget.getRealPosition().getX(), p.getY() - focusWidget.getRealPosition().getY()));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (this.mouseWheelListener != null) {
            this.mouseWheelListener.mouseWheelMoved(e);
        } else if (this.focusWidget != null) {
            this.focusWidget.mouseWheelMoved(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.repaint();
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
