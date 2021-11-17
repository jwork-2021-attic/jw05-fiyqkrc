package game.controller;

import com.pFrame.Position;
import com.pFrame.pwidget.ObjectUserInteractive;
import game.graphic.Controllable;
import game.graphic.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class KeyBoardThingController extends CreatureController implements ObjectUserInteractive {

    public KeyBoardThingController() {
        super();
    }

    public void setThing(Controllable controllable) {
        this.controllable = controllable;
    }

    public void respondToUserInput(KeyEvent key) {
        Controllable aim = this.controllable;
        switch (key.getKeyChar()) {
            case 'w' -> aim.move(Direction.Up);
            case 'a' -> aim.move(Direction.Left);
            case 'd' -> aim.move(Direction.Right);
            case 's' -> aim.move(Direction.Down);
            case 'j' -> controllable.attack();
            default -> {
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        respondToUserInput(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0, Position p) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0, Position p) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public Position getRealPosition() {
        return Position.getPosition(0, 0);
    }

}
