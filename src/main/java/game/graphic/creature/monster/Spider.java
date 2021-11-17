package game.graphic.creature.monster;

import game.world.World;

public class Spider extends Monster{
    public Spider() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Spider/").getPath(), World.tileSize,World.tileSize);
    }
}
