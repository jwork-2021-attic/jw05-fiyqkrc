package game.graphic.creature.operational;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.Attack;
import game.Location;
import game.graphic.Thing;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.util.ArrayList;

public class Shoot extends Thing implements Runnable {
    private final Calabash calabash;
    protected double angle;
    protected int group;
    protected int speed;
    protected double direction;

    double last_x;
    double last_y;

    public Shoot(Calabash calabash, double angle) {
        super(null);
        this.calabash = calabash;
        direction = angle;
        beCoverAble = true;
        Pixel[][] pixels = GraphicItemGenerator.generateItem(Shoot.class.getClassLoader().getResource("image/shoot/shoot.png").getFile(), World.tileSize / 4, World.tileSize / 4).getPixels();
        this.width = World.tileSize/4;
        this.height = World.tileSize/4;
        graphic = pixels;
        this.speed = 6;
        this.group = calabash.getGroup();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.world != null) {
                    double y = Math.sin(direction) * this.speed+last_y;
                    double x = Math.cos(direction) * this.speed+last_x;

                    last_x=x-(int)x;
                    last_y=y-(int)y;

                    Position nextPosition = Position.getPosition(p.getX() - (int) y, p.getY() + (int) x);
                    Position nextCentral = Position.getPosition(nextPosition.getX()+height/2,nextPosition.getY()+width/2);
                    Thing thing = world.findThing(world.getTileByLocation(nextCentral));
                    if (thing != null && thing != this.calabash) {
                        ArrayList<Location> t = new ArrayList<>();
                        t.add(world.getTileByLocation(nextCentral));
                        world.handleAttack(new Attack(Attack.HIT, t, calabash.getAttack(), group));
                        world.removeItem(this);
                        break;
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
