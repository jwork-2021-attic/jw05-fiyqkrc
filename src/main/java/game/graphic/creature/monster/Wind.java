package game.graphic.creature.monster;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.Attack;
import game.Location;
import game.graphic.Thing;
import game.graphic.creature.Creature;
import game.graphic.creature.operational.Shoot;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.util.ArrayList;

public class Wind extends Thing implements Runnable{
    private final Dragon dragon;
    protected double angle;
    protected int group;
    protected int speed;
    protected double direction;

    public Wind(Dragon dragon, double angle) {
        super(null);
        this.dragon = dragon;
        direction = angle;
        beCoverAble = true;
        Pixel[][] pixels = GraphicItemGenerator.generateItem(Shoot.class.getClassLoader().getResource("image/shoot/wind.png").getFile(), World.tileSize , World.tileSize ).getPixels();
        this.width = World.tileSize;
        this.height = World.tileSize;
        graphic = pixels;
        this.speed = 6;
        this.group = dragon.getGroup();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.world != null) {
                    double y = Math.sin(direction) * this.speed;
                    double x = Math.cos(direction) * this.speed;
                    Position nextPosition = Position.getPosition(p.getX() - (int) y, p.getY() + (int) x);
                    Thing thing = world.findThing(world.getTileByLocation(nextPosition));
                    if (thing != null && thing != this.dragon) {
                        if(thing instanceof Creature && ((Creature) thing).getGroup()==this.dragon.getGroup())
                        {
                            this.world.ThingMove(this, nextPosition);
                            Thread.sleep(30);
                        }
                        else {
                            ArrayList<Location> t = new ArrayList<>();
                            t.add(world.getTileByLocation(nextPosition));
                            world.handleAttack(new Attack(Attack.HIT, t, 10, group));
                            world.removeItem(this);
                            break;
                        }
                    } else if (world.positionOutOfBound(nextPosition)) {
                        world.removeItem(this);
                        break;
                    } else {
                        this.world.ThingMove(this, nextPosition);
                        Thread.sleep(30);
                    }
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
