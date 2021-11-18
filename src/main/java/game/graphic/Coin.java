package game.graphic;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.GameThread;
import game.graphic.creature.Creature;
import game.graphic.creature.operational.Operational;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class Coin extends Thing implements Runnable {
    double last_x;
    double last_y;
    long lastFlashTime;
    double speed;
    int coin;
    Thread thread;

    public Coin(Creature creature) {
        super(null);
        Pixel[][] pixels = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/effect/coin.png").getFile(), World.tileSize, World.tileSize).getPixels();
        this.graphic = pixels;
        this.p = creature.getPosition();
        this.width = World.tileSize;
        this.height = World.tileSize;
        this.coin = creature.getCoin();
        this.speed = 200;
    }

    @Override
    public void run() {
        lastFlashTime = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (Position.distance(getCentralPosition(), world.getOperational().getCentralPosition()) < 7*World.tileSize) {
                    double angle = Direction.calDirection(getCentralPosition(), world.getOperational().getCentralPosition());
                    long currentTime = System.currentTimeMillis();
                    double y = Math.sin(angle) * speed * (currentTime - lastFlashTime) / 1000 + last_y;
                    double x = Math.cos(angle) * speed * (currentTime - lastFlashTime) / 1000 + last_x;
                    last_y = y - (int) y;
                    last_x = x - (int) x;
                    lastFlashTime = currentTime;
                    Position nextPosition = Position.getPosition(getCentralPosition().getX() - (int) y, getCentralPosition().getY() + (int) x);
                    world.ThingMove(this, nextPosition);
                    Thing thing = world.findThing(world.getTileByLocation(nextPosition));
                    if (thing instanceof Operational) {
                        ((Operational) thing).addCoin(coin);
                        world.removeItem(this);
                        break;
                    }
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        GameThread.threadSet.remove(Thread.currentThread());
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        thread = new Thread(this);
        GameThread.threadSet.add(thread);
        thread.start();
    }
}
