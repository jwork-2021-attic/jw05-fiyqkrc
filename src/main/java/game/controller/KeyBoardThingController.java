package game.controller;

import com.pFrame.Position;
import com.pFrame.pwidget.ObjectUserInteractive;
import game.graphic.interactive.GameThread;
import game.graphic.Controllable;
import game.graphic.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class KeyBoardThingController extends CreatureController implements ObjectUserInteractive, Runnable {

    Thread thread;

    public KeyBoardThingController() {
        super();
        allKeyCode.add('a');
        allKeyCode.add('s');
        allKeyCode.add('w');
        allKeyCode.add('d');
        thread = new Thread(this);
        thread.start();
        GameThread.threadSet.add(thread);
    }

    public void setThing(Controllable controllable) {
        this.controllable = controllable;
    }

    public ArrayList<Character> allKeyCode = new ArrayList<>();

    CopyOnWriteArrayList<Character> keyArray = new CopyOnWriteArrayList<>();

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        controllable.attack();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (allKeyCode.contains(e.getKeyChar())) {
            if (keyArray.contains(e.getKeyChar())) {
            } else
                keyArray.add(e.getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!allKeyCode.contains(e.getKeyChar())){
            ArrayList<ObjectUserInteractive> objectUserInteractives=controllable.getAroundInteractive();
            for(ObjectUserInteractive objectUserInteractive:objectUserInteractives)
                objectUserInteractive.keyTyped(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyArray.contains(e.getKeyChar()))
            keyArray.remove((Character) e.getKeyChar());
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

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (keyArray.size() == 0) {

                } else if (keyArray.size() == 1) {
                    controllable.move(calDirection(keyArray.get(0)));
                } else {
                    double d1 = calDirection(keyArray.get(0));
                    double d2 = calDirection(keyArray.get(1));
                    controllable.move((d1 + d2) / 2);
                }
                Thread.sleep(33);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GameThread.threadSet.remove(thread);
    }

    public double calDirection(char ch) {
        double direction = 0;
        switch (ch) {
            case 'w' -> direction = Direction.Up;
            case 'a' -> direction = Direction.Left;
            case 'd' -> direction = Direction.Right;
            case 's' -> direction = Direction.Down;
            default -> {
            }
        }
        return direction;
    }
}
