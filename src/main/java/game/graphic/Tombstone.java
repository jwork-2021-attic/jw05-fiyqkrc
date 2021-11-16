package game.graphic;

import game.world.World;

public class Tombstone extends Thing{
    public Tombstone() {
        super(Tombstone.class.getClassLoader().getResource("image/deadRole.png").getFile(), World.tileSize*3/4, World.tileSize);
        beCoverAble=true;
    }
}
