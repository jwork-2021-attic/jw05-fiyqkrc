package com.pFrame.pwidget;

import com.pFrame.Pixel;
import com.pFrame.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseWheelListener {

    protected PWidget headWidget;

    protected ObjectUserInteractive focusWidget;

    protected int frameWidth;
    protected int frameHeight;
    protected static final int charWidth = 2;

    protected BufferedImage graphicImage;
    protected Pixel[][] pixels;

    protected PFrameKeyListener[] pFrameKeyListeners;


    public int getFrameWidth() {
        return this.frameWidth;
    }

    public int getFrameHeight() {
        return this.frameHeight;
    }

    public void setHeadWidget(PWidget widget) {
        this.headWidget = widget;
        this.headWidget.changeWidgetSize((getWidth() - getInsets().left - getInsets().right) / charWidth, (getHeight() - getInsets().top - getInsets().bottom) / charWidth);
        this.frameWidth = (getWidth() - getInsets().left - getInsets().right) / charWidth;
        this.frameHeight = (getHeight() - getInsets().top - getInsets().bottom) / charWidth;
    }

    public PFrame(int width, int height) {
        super();
        pack();
        setSize(width * charWidth, height * charWidth);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frameHeight = height;
        this.frameWidth = width;
        this.focusWidget = null;
        pFrameKeyListeners=new PFrameKeyListener[256];
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        graphicImage = new BufferedImage(frameWidth * charWidth, frameHeight * charWidth, BufferedImage.TYPE_INT_ARGB);
    }


    @Override
    public void paint(Graphics g) {
        try {
            pixels = this.headWidget.displayOutput();

            if (pixels != null) {
                for (int i = 0; i < frameWidth * charWidth; i++) {
                    for (int j = 0; j < frameHeight * charWidth; j++) {
                        if (pixels[j / charWidth][i / charWidth] == null) {
                            graphicImage.setRGB(i, j, 0xff000000);
                        } else
                            graphicImage.setRGB(i, j, pixels[j / charWidth][i / charWidth].getColor().getRGB() + (pixels[j / charWidth][i / charWidth].getColor().getAlpha() >> 24));
                    }
                }
                g.drawImage(graphicImage, getInsets().left, getInsets().top, this);
            }
        }catch (ArrayIndexOutOfBoundsException ignored){

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void addPFrameKeyListener(int ch,PFrameKeyListener pFrameKeyListener){
        pFrameKeyListeners[ch]=pFrameKeyListener;
    }

    public void freePFrameKeyListener(int ch,PFrameKeyListener pFrameKeyListener){
        pFrameKeyListeners[ch]=null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(pFrameKeyListeners[e.getKeyChar()]!=null){
            pFrameKeyListeners[e.getKeyChar()].keyTyped(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(pFrameKeyListeners[e.getKeyChar()]!=null){
            pFrameKeyListeners[e.getKeyChar()].keyPressed(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(pFrameKeyListeners[e.getKeyChar()]!=null){
            pFrameKeyListeners[e.getKeyChar()].keyReleased(e);
        }
        else if (this.focusWidget != null) {
            this.focusWidget.keyReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        Position p = mouseToPosition(arg0);

        ArrayList<PWidget> list = this.headWidget.getWidgetsAt(p);
        this.focusWidget = list.get(list.size() - 1);

        Position realPosition = this.focusWidget.getRealPosition();
        Position pos = Position.getPosition(p.getX() - realPosition.getX(), p.getY() - realPosition.getY());

        this.focusWidget.mouseClicked(arg0, pos);
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
        if (this.focusWidget != null) {
            this.focusWidget.mousePressed(arg0, Position.getPosition(p.getX() - focusWidget.getRealPosition().getX(),
                    p.getY() - focusWidget.getRealPosition().getY()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        Position p = mouseToPosition(arg0);

        if (this.focusWidget != null) {
            this.focusWidget.mouseReleased(arg0, Position.getPosition(p.getX() - focusWidget.getRealPosition().getX(), p.getY() - focusWidget.getRealPosition().getY()));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (this.focusWidget != null) {
            this.focusWidget.mouseWheelMoved(e);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.repaint();
                Thread.sleep(30);

                if ((getWidth() - getInsets().left - getInsets().right) / charWidth != frameWidth || this.frameHeight != (getHeight() - getInsets().top - getInsets().bottom) / charWidth) {
                    setHeadWidget(headWidget);
                    graphicImage = new BufferedImage(frameWidth * charWidth, frameHeight * charWidth, BufferedImage.TYPE_INT_ARGB);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
