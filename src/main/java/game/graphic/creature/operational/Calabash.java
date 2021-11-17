package game.graphic.creature.operational;

import com.pFrame.Position;
import game.Location;
import game.graphic.Direction;
import game.world.World;

import java.util.Objects;

public class Calabash extends Operational {

    public Calabash() {
        super(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/role/calabash0/")).getFile(), World.tileSize, World.tileSize);
    }

    @Override
    public void attack() {
        Location l = world.searchNearestEnemy(this, 5);
        double angle;
        if (l == null) {
            angle = this.direction;
        } else {
            Location m = this.getLocation();
            if (l.y() == m.y() && m.x() < l.x()) {
                angle = Direction.Down;
            } else if (l.y() == m.y() && m.x() > l.x()) {
                angle = Direction.Up;
            } else if (m.x() == l.x() && m.y() > l.y()) {
                angle = Direction.Left;
            } else if (m.x() == l.x() && m.y() < l.y()) {
                angle = Direction.Right;
            } else {
                angle = Math.atan(((double) l.x() - m.x()) / (m.y() - l.y()));
                if (angle > 0 && m.x() < l.x()) {
                    angle += Math.PI;
                } else if (angle < 0 && m.x() > l.x()) {
                    angle += Math.PI;
                }
            }
        }
        Shoot shoot = new Shoot(this, angle);
        Position startPosition = Position.getPosition(p.getX() + height / 2, p.getY() + width / 2);
        shoot.setPosition(startPosition);
        world.addItem(shoot);
        Thread thread = new Thread(shoot);
        thread.start();
    }

}
