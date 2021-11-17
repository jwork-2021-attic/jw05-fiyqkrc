package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Location;
import game.world.World;

import java.util.Date;

public class Master extends Monster{
    protected long lastAttack;
    protected long codeTime=12000;

    public Master(){
        super(Pangolin.class.getClassLoader().getResource("image/monster/Master/").getPath(), World.tileSize,World.tileSize);
    }

    @Override
    public void attack() {
        super.attack();
        if(new Date().getTime()-lastAttack>this.codeTime){
            int x=this.world.getTileByLocation(getPosition()).x();
            int y=this.world.getTileByLocation(p).y();
            for(int i=x-2;i<=x+2;i++) {
                for (int j = y - 2; j < y + 2; j++) {
                    if (!getWorld().locationOutOfBound(new Location(i, j))&&world.findThing(new Location(i,j))==null) {
                        Vine vine = new Vine(this, Position.getPosition(i * World.tileSize, j * World.tileSize));
                        world.addItem(vine);
                    }
                }
            }
            lastAttack=new Date().getTime();
        }
    }
}
