package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Location;
import game.graphic.Direction;
import game.world.World;

import java.util.Date;

public class Spider extends Monster {
    protected long coldTime = 3000;
    protected long lastAttack;

    public Spider() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Spider/").getPath(), World.tileSize, World.tileSize);

    }


    @Override
    public void attack() {
        if (new Date().getTime() - lastAttack > coldTime) {
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
            for(int i=0;i<3;i++) {
                SpiderShoot shoot = new SpiderShoot(this, angle);
                Position startPosition = Position.getPosition(p.getX() + height / 2, p.getY() + width / 2);
                shoot.setPosition(startPosition);
                world.addItem(shoot);
                Thread thread = new Thread(shoot);
                thread.start();
            }
            lastAttack = new Date().getTime();
        }
    }
}

