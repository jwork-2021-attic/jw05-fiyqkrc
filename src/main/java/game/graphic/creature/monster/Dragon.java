package game.graphic.creature.monster;

import game.world.World;

public class Dragon extends Monster {
    public Dragon(){
        super(Pangolin.class.getClassLoader().getResource("image/monster/Dragon/").getPath(), World.tileSize,World.tileSize);
    }
}
