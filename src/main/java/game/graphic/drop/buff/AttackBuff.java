package game.graphic.drop.buff;

import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class AttackBuff extends Buff {
    public AttackBuff(){
        image= GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/buff/2-0.png").getFile(),World.tileSize,World.tileSize).getPixels();
        graphic=image;
        width= World.tileSize;
        height=World.tileSize;

        health=0;
        attack=0.2;
        resistance=0;
        speed=0;
        timeOnly=true;
        time=120000;
    }
}
