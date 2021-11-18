package game.controller;

import java.util.Random;

public class AlogrithmController extends CreatureController implements Runnable {
    protected boolean aim;
    protected double direction;
    protected double lastSearchAim=System.currentTimeMillis();
    protected Random random = new Random();



    public AlogrithmController() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void tryMove(){
        if (random.nextDouble(1) > 0.65) {
            if (!controllable.move(direction)) {
                if(random.nextDouble(1)>0.8)
                    direction = random.nextDouble(Math.PI * 2);
            }
        }
    }

    public void trySearchAim(){
        if(System.currentTimeMillis()-lastSearchAim>2000) {
            aim = controllable.searchAim();
            lastSearchAim=System.currentTimeMillis();
        }
    }

    @Override
    public void run(){
        while (!stop && !Thread.interrupted()) {
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
            } catch (Exception e) {
                e.printStackTrace();
                aim = false;
                direction = 0;
            }
        }
    }
}
