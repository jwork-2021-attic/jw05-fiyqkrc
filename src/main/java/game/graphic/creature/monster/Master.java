package game.graphic.creature.monster;

import game.world.World;

public class Master extends Monster{
    public Master(){
        super(Pangolin.class.getClassLoader().getResource("image/monster/Master/").getPath(), World.tileSize,World.tileSize);
    }
}
