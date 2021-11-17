package game.controller;

import game.graphic.creature.Creature;

import java.util.Random;

public class AlogrithmController extends CreatureController implements Runnable{

    public AlogrithmController(Creature creature){
        setThing(creature);
        creature.setController(this);
        Thread thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(true){
            if(controllable.isDead()){
                controllable.dead();
                break;
            }
            else {
                Random random=new Random();
                double direction=random.nextDouble(2*Math.PI);
                controllable.move(direction);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
