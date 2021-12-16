package game.controller;

import com.pFrame.pwidget.PFrameKeyListener;
import game.graphic.Direction;
import game.graphic.creature.Controllable;
import game.graphic.interactive.GameThread;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class KeyBoardController extends CreatureController implements PFrameKeyListener, Runnable {

    Thread thread;

    public KeyBoardController() {
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
    public void keyPressed(KeyEvent e) {
        if (allKeyCode.contains(e.getKeyChar())) {
            if (keyArray.contains(e.getKeyChar())) {
            } else
                keyArray.add(e.getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()=='j')
            controllable.responseToEnemy();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(allKeyCode.contains(e.getKeyChar())) {
            if (keyArray.contains(e.getKeyChar()))
                keyArray.remove((Character) e.getKeyChar());
        }
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
