package com.pFrame;

import java.awt.event.*;

public interface ObjectUserInteractive {
    public void mouseClicked(MouseEvent e, Position p);

    public void keyPressed(KeyEvent e);

    public void keyTyped(KeyEvent e);

    public void keyReleased(KeyEvent e);

    public void mouseEntered(MouseEvent arg0);

    public void mouseExited(MouseEvent arg0);

    public void mousePressed(MouseEvent arg0, Position p);

    public void mouseReleased(MouseEvent arg0, Position p);

    public void mouseWheelMoved(MouseWheelEvent e);

    public Position getRealPosition();
}
