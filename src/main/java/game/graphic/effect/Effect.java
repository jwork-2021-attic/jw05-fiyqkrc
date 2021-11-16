package game.graphic.effect;

import com.pFrame.PTimer;
import com.pFrame.PTimerTask;
import game.graphic.Thing;

public class Effect extends Thing {
    PTimerTask task;
    boolean repeat;
    int time;
    PTimer timer;

    public Effect() {
        super(null);
        time=1000;
        repeat=false;
        timer=new PTimer();
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        timer.schedule(task,repeat,time);
        Thread thread=new Thread(timer);
        thread.start();
    }
}
