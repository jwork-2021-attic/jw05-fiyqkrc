package game.graphic.creature.operational;

import game.Location;
import game.graphic.Direction;
import game.graphic.bullet.NormalBullet;
import game.world.World;

import java.util.Objects;

public class Calabash extends Operational {

    public Calabash() {
        super(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/role/calabash0/")).getFile(), World.tileSize, World.tileSize);
        health=500;
        healthLimit=500;
    }

    @Override
    public void attack() {
        Location l = world.searchNearestEnemy(this, 5);
        double angle;
        if (l == null) {
            angle = this.direction;
        } else {
            angle=Direction.calDirection(getCentralPosition(),l.getCentralPosition());
        }
        NormalBullet normalBullet = new NormalBullet(this, angle);
        world.addItem(normalBullet);

    }

}
