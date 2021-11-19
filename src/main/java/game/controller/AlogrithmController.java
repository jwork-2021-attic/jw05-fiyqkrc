package game.controller;

import game.graphic.interactive.GameThread;

import java.util.Random;

public class AlogrithmController extends CreatureController implements Runnable {
    protected boolean aim;
    protected double direction;
    protected double lastSearchAim = System.currentTimeMillis();
    protected Random random = new Random();
    protected Thread thread;



    public AlogrithmController() {
        thread = new Thread(this);
        GameThread.threadSet.add(thread);
        thread.start();
    }

    public void tryMove() {
        if (random.nextDouble(1) > 0.65) {
            if (!controllable.move(direction)) {
                if (random.nextDouble(1) > 0.8)
                    direction = random.nextDouble(Math.PI * 2);
            }
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
                    controllable.dead();
                    break;
                } else if (!aim) {
                    tryMove();
                    trySearchAim();
                } else {
                    controllable.attack();
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                aim = false;
                direction = 0;
            }
        }
        GameThread.threadSet.remove(Thread.currentThread());
    }
}
