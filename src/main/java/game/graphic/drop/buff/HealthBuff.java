package game.graphic.drop.buff;

import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class HealthBuff extends Buff {
    public HealthBuff() {
        image = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/buff/2-1.png").getFile(), World.tileSize, World.tileSize).getPixels();
        graphic = image;
        width = World.tileSize;
        height = World.tileSize;

        health = 0.6;
        attack = 0;
        resistance = 0;
        speed = 0;
        timeOnly = false;
    }
}
