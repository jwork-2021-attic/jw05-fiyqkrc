package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.pFrame.ObjectUserInteractive;
import com.pFrame.Position;

import game.role.Controllable;
import game.role.Direction;

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
        case 'w':
            aim.move(Direction.Up);
            break;
        case 'a':
            aim.move(Direction.Left);
            break;
        case 'd':
            aim.move(Direction.Right);
            break;
        case 's':
            aim.move(Direction.Down);
            break;
        default:
            System.out.println("Undefined keycode:" + String.valueOf(key.getKeyChar()));
            break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        respondToUserInput(e);

    }

    @Override
    public void keyTyped(KeyEvent e) {

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
        return Position.getPosition(0,0);
    }

}
