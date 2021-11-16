package game.graphic.creature.monster;

import game.controller.AlogrithmController;
import game.world.World;

public class Pangolin extends Monster {
    public Pangolin() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Pangolin/").getPath(), World.tileSize,World.tileSize);
    }
}
