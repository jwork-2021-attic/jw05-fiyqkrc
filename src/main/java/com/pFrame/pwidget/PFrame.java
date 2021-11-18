package com.pFrame.pwidget;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import com.pFrame.Pixel;
import com.pFrame.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseWheelListener {

    private final AsciiPanel terminal;
    protected PWidget headWidget;

    protected ObjectUserInteractive focusWidget;

    protected int frameWidth;
    protected int frameHeight;
    protected int charWidth;

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
        if (this.focusWidget != null) {
            this.focusWidget.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.focusWidget != null) {
            this.focusWidget.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.focusWidget != null) {
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
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
