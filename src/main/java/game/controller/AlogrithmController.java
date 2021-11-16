package game.controller;

public class AlogrithmController extends CreatureController implements Runnable{

    public AlogrithmController(){
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
                controllable.speak("? ? ?");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
