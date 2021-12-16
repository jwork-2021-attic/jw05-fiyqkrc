package game.controller;

import game.graphic.creature.Creature;
import game.graphic.interactive.GameThread;
import game.server.client.Accepter;
import game.server.client.ClientMain;

import java.util.Random;

public class NetAlController extends CreatureController implements Runnable {
    protected Creature aim;
    protected double direction;
    protected double lastSearchAim = System.currentTimeMillis();
    protected Random random = new Random();
    protected Thread thread;
    protected boolean lastMoveSuccess;

    public NetAlController() {
        thread = new Thread(this);
        lastMoveSuccess = true;
        GameThread.threadSet.add(thread);
        thread.start();
    }

    public void tryMove() {

        if (random.nextDouble(1) > 0.8) {
            direction = random.nextDouble(Math.PI * 2);
        } else {
            ClientMain.getInstance().getCommandListener().submit(Accepter.MoveMessage(controllable, direction));
        }
    }

    public void trySearchAim() {
        if (System.currentTimeMillis() - lastSearchAim > 2000) {
            aim = controllable.searchAim();
            lastSearchAim = System.currentTimeMillis();
        }
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (controllable.isDead()) {
                    ClientMain.getInstance().getCommandListener().submit(Accepter.deadMessage(controllable));
                    break;
                }
                trySearchAim();
                if (aim == null) {
                    tryMove();
                } else {
                    ClientMain.getInstance().getCommandListener().submit(Accepter.attackMessage(controllable));
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                aim = null;
                direction = 0;
            }
        }
        GameThread.threadSet.remove(Thread.currentThread());
    }
}