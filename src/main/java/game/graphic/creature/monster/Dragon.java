package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Location;
import game.graphic.Direction;
import game.world.World;

import java.util.Date;

public class Dragon extends Monster {
    protected long codeTime = 3000;
    protected long lastAttack;

    public Dragon() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Dragon/").getPath(), World.tileSize, World.tileSize);
        speed = 2;
    }

    @Override
    public void attack() {
        super.attack();
        if (new Date().getTime() - lastAttack > this.codeTime) {
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

            Wind wind = new Wind(this, angle);
            Position startPosition = Position.getPosition(p.getX() + height / 2, p.getY() + width / 2);
            wind.setPosition(startPosition);
            world.addItem(wind);
            Thread thread = new Thread(wind);
            thread.start();

            lastAttack = new Date().getTime();
        }
    }
}
