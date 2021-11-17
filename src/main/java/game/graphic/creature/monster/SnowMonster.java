package game.graphic.creature.monster;

import game.world.World;

public class SnowMonster extends Monster{
    public SnowMonster(){
        super(Pangolin.class.getClassLoader().getResource("image/monster/SnowMonster/").getPath(), World.tileSize,World.tileSize);
    }

}
