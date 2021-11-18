package game.graphic;

import com.pFrame.Pixel;
import game.graphic.creature.Creature;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class Coin  extends Thing implements Runnable{
    public Coin(Creature creature) {
        super(null);
        Pixel[][] pixels= GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/coin.png").getFile(), World.tileSize,World.tileSize).getPixels();
        this.graphic=pixels;
        this.p=creature.getPosition();
        this.width=World.tileSize;
        this.height=World.tileSize;

    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        Thread thread=new Thread(this);
        thread.start();
    }
}
