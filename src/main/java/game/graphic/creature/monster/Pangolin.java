package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Attack;
import game.Location;
import game.graphic.Direction;
import game.graphic.Thing;
import game.graphic.effect.Swoon;
import game.world.World;

import java.util.ArrayList;
import java.util.Date;

public class Pangolin extends Monster {
    protected long lastAttack;
    protected long codeTime = 5000;

    protected double last_y;
    protected double last_x;


    public Pangolin() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Pangolin/").getPath(), World.tileSize, World.tileSize);
        speed=3;
        resistance=0.5;
        attack=40;
        health=80;
        speedLimit=speed;
        healthLimit=health;
        resistanceLimit=resistance;
        attackLimit=attack;
    }

    @Override
    public void attack() {
        super.attack();
        if (new Date().getTime() - lastAttack > this.codeTime) {
            try {
                if (aim != null) {
                    direction = Direction.calDirection(getCentralPosition(), aim.getCentralPosition());
                    double y = Math.sin(direction) * this.speed * 2;
                    double x = Math.cos(direction) * this.speed * 2;

                    last_x = x - (int) x;
                    last_y = y - (int) y;

                    Position nextPosition = Position.getPosition(p.getX() - (int) y, p.getY() + (int) x);
                    Position nextCentral = Position.getPosition(nextPosition.getX() + height / 2, nextPosition.getY() + width / 2);
                    Thing thing = world.findThing(world.getTileByLocation(nextCentral));
                    if (thing != null && thing != this) {
                        ArrayList<Location> t = new ArrayList<>();
                        t.add(world.getTileByLocation(nextCentral));
                        world.handleAttack(new Attack(Attack.HIT, t, this.getAttack(), group));
                        lastAttack = System.currentTimeMillis();
                        Swoon swoon = new Swoon(this.p);
                        world.addItem(swoon);
                    } else if (world.positionOutOfBound(nextCentral)) {
                    } else {
                        this.world.ThingMove(this, nextCentral);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
