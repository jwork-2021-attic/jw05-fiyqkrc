package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Location;
import game.world.World;

import java.util.Date;

public class SnowMonster extends Monster{
    public long coldTime=10000;
    public long lastAttack;

    public SnowMonster(){
        super(Pangolin.class.getClassLoader().getResource("image/monster/SnowMonster/").getPath(), World.tileSize,World.tileSize);
        health=400;
        attack=20;
        speed=2;
        resistance=0.1;
    }

    @Override
    public void attack() {
        if(new Date().getTime()-lastAttack>coldTime){
            int x=this.world.getTileByLocation(getCentralPosition()).x();
            int y=this.world.getTileByLocation(getCentralPosition()).y();
            for(int i=x-1;i<=x+1;i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (!getWorld().locationOutOfBound(new Location(i, j))) {
                        IceAttack iceAttack = new IceAttack(this, Position.getPosition(i * World.tileSize, j * World.tileSize));
                        world.addItem(iceAttack);
                    }
                }
            }
            lastAttack=new Date().getTime();
        }
    }
}
