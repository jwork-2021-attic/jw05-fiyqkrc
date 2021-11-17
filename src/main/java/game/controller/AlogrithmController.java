package game.controller;

import java.util.Random;

public class AlogrithmController extends CreatureController implements Runnable {
    protected boolean aim;
    protected double direction;


    public AlogrithmController() {

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        while (!stop) {
            try {
                if (controllable.isDead()) {
                    controllable.dead();
                    break;
                } else if (!aim) {
                    Random random = new Random();
                    if (random.nextDouble(1) > 0.75) {
                        if (!controllable.move(direction)) {
                            direction = random.nextDouble(Math.PI * 2);
                        }
                    }
                    aim = controllable.searchAim();
                } else {
                    if (controllable.searchAim()) {
                        controllable.attack();
                    } else
                        aim = false;
                }
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
                aim = false;
                direction = 0;
            }
        }
    }
}
