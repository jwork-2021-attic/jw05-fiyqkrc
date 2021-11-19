package game.graphic.interactive;

import com.pFrame.Position;
import game.graphic.Thing;
import game.graphic.interactive.buff.Buff;
import game.graphic.creature.Creature;
import game.graphic.creature.operational.Operational;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.util.Random;

public class Box extends Thing implements Runnable,GameThread{
    protected Thread thread;
    public Box() {
        super(null);
        graphic = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/box/1.png").getFile(), World.tileSize, World.tileSize).getPixels();
        width = World.tileSize;
        height = World.tileSize;
        beCoverAble = true;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (world != null) {
                    Thing thing = world.findThing(getLocation());
                    if (thing instanceof Operational) {
                        graphic = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/box/2.png").getFile(), World.tileSize, World.tileSize).getPixels();
                        Random random=new Random();
                        Class<? extends Buff> buffClass=Buff.buffs.get(random.nextInt(Buff.buffs.size()));
                        Buff buff=buffClass.getDeclaredConstructor().newInstance();
                        buff.creature= (Creature) thing;
                        buff.setPosition(Position.getPosition(getPosition().getX()+ random.nextInt(World.tileSize), getPosition().getY()+World.tileSize));
                        world.addItem(buff);
                        break;
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
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
        thread = new Thread(this);
        thread.start();
        GameThread.threadSet.add(thread);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
