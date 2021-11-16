package game.graphic.effect;

import com.pFrame.*;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.util.Random;


public class Swoon extends Effect{
    public Pixel[][] image1;
    public Pixel[][] image2;

    public Swoon(Position position){
        super();
        this.width = World.tileSize;
        this.height = World.tileSize;
        image1= GraphicItemGenerator.generateItem(Task.class.getClassLoader().getResource("image/effect/swoon/1.png").getFile(),width,height).getPixels();
        image2= GraphicItemGenerator.generateItem(Task.class.getClassLoader().getResource("image/effect/swoon/2.png").getFile(),width,height).getPixels();
        this.graphic = image1;
        repeat = true;
        time = 500;
        task = new Swoon.Task(this,timer);
        this.p = Position.getPosition(position.getX(), position.getY());
    }

    public void setPixel(Pixel[][] pixels){
        this.graphic=pixels;
    }

    class Task implements PTimerTask {
        Swoon dialog;
        PTimer timer;
        int times=0;

        public Task(Swoon dialog, PTimer timer) {
            this.dialog = dialog;
            this.timer=timer;
        }

        @Override
        public void doTask() {
            times++;
            if(times<=6){
                if(dialog.getPixels()==dialog.image1){
                    dialog.setPixel(dialog.image2);
                }
                else
                    dialog.setPixel(dialog.image1);
            }
            else{
                dialog.getWorld().removeItem(dialog);
                this.timer.stop();
            }
        }
    }
}