package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Attack;
import game.Location;
import game.graphic.Direction;
import game.graphic.Thing;
import game.graphic.creature.Creature;
import game.world.World;

import java.util.ArrayList;

public class Pangolin extends Monster {
    public Pangolin() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Pangolin/").getPath(), World.tileSize,World.tileSize);
        speed=15;
    }

    @Override
    public void attack() {
        super.attack();

        Location l = aim.getLocation();
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

        direction=angle;

        double y = Math.sin(direction) * this.speed;
        double x = Math.cos(direction) * this.speed;
        Position nextPosition = Position.getPosition(p.getX() - (int) y, p.getY() + (int) x);
        Thing thing = world.findThing(world.getTileByLocation(nextPosition));
        if (thing != null && thing instanceof Creature && ((Creature) thing).getGroup()!=this.group) {
            ArrayList<Location> t = new ArrayList<>();
            t.add(world.getTileByLocation(nextPosition));
            world.handleAttack(new Attack(Attack.HIT, t, 10, group));
        } else if (world.positionOutOfBound(nextPosition)) {

        } else {
            this.world.ThingMove(this, nextPosition);
        }
    }
}
