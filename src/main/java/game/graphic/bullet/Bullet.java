package game.graphic.bullet;

import com.pFrame.Position;
import game.Attack;
import game.GameThread;
import game.Location;
import game.graphic.Thing;
import game.graphic.creature.Creature;

import java.util.ArrayList;

public class Bullet extends Thing implements Runnable {
    protected final Creature creature;
    protected int group;
    protected int speed;
    protected double direction;

    protected double last_x;
    protected double last_y;
    protected long lastFlashPosition;
    protected Thread thread;

    public Bullet(Creature creature, double angle) {
        super(null);
        this.creature = creature;
        direction = angle;
        beCoverAble = true;
        this.group = creature.getGroup();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (this.world != null) {
                    long currentTime = System.currentTimeMillis();
                    double y = Math.sin(direction) * this.speed * (double) (currentTime - lastFlashPosition) / 1000 + last_y;
                    double x = Math.cos(direction) * this.speed * (double) (currentTime - lastFlashPosition) / 1000 + last_x;
                    lastFlashPosition = currentTime;

                    last_x = x - (int) x;
                    last_y = y - (int) y;

                    Position nextPosition = Position.getPosition(p.getX() - (int) y, p.getY() + (int) x);
                    Position nextCentral = Position.getPosition(nextPosition.getX() + height / 2, nextPosition.getY() + width / 2);
                    Thing thing = world.findThing(world.getTileByLocation(nextCentral));
                    if (thing != null && thing != this.creature) {
                        ArrayList<Location> t = new ArrayList<>();
                        t.add(world.getTileByLocation(nextCentral));
                        world.handleAttack(new Attack(Attack.HIT, t, creature.getAttack(), group));
                        world.removeItem(this);
                        break;
                    } else if (world.positionOutOfBound(nextCentral)) {
                        world.removeItem(this);
                        break;
                    } else {
                        this.world.ThingMove(this, nextCentral);
                        Thread.sleep(20);
                    }
                } else {
                    Thread.sleep(100);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GameThread.threadSet.remove(Thread.currentThread());
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        thread=new Thread(this);
        this.lastFlashPosition=System.currentTimeMillis();
        GameThread.threadSet.add(thread);
        thread.start();
    }
}

