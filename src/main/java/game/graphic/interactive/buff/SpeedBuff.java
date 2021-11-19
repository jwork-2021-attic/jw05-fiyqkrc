package game.graphic.interactive.buff;

import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class SpeedBuff extends Buff {
    public SpeedBuff() {
        image = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/buff/2-3.png").getFile(), World.tileSize, World.tileSize).getPixels();
        graphic = image;
        width = World.tileSize;
        height = World.tileSize;

        health = 0;
        attack = 0;
        resistance = 0;
        speed = 0.5;
        timeOnly = true;
        time = 50000;
    }
}
