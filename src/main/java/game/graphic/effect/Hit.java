package game.graphic.effect;

import com.pFrame.PTimer;
import com.pFrame.PTimerTask;
import com.pFrame.Pixel;
import com.pFrame.Position;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class Hit extends Effect{
    public static Pixel[][] HitImage= GraphicItemGenerator.generateItem(Hit.class.getClassLoader().getResource("image/effect/hit.png").getFile(), World.tileSize,World.tileSize).getPixels();

    public Hit(){
        super();
        graphic=HitImage;
        width=World.tileSize;
        height=World.tileSize;

        repeat=false;
        time=100;
        task=new Task(this);
    }

    class Task implements PTimerTask{
        public Effect effect;

        public Task(Effect effect){
            this.effect=effect;
        }

        @Override
        public void doTask() {
            world.removeItem(effect);
        }
    }
}
