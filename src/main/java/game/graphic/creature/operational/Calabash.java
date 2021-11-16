package game.graphic.creature.operational;

import com.pFrame.Position;
import game.graphic.Shoot;
import game.world.World;

import java.util.Objects;

public class Calabash extends Operational {

    public Calabash() {
        super(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/role/calabash0/")).getFile(), World.tileSize, World.tileSize);
    }

    @Override
    public void attack() {
        Shoot shoot=new Shoot(this, this.direction,group);
        double y = Math.sin(direction) * width;
        double x = Math.cos(direction) * height;
        Position startPosition=Position.getPosition(p.getX()-(int)(y),p.getY()+(int)(x));
        shoot.setPosition(startPosition);
        world.addItem(shoot);
        Thread thread = new Thread(shoot);
        thread.start();
    }

}
