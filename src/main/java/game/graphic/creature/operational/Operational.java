package game.graphic.creature.operational;

import com.pFrame.Position;
import com.pFrame.pwidget.PFrameKeyListener;
import game.controller.CreatureController;
import game.graphic.creature.Creature;
import game.world.World;

import java.util.Random;

abstract public class Operational extends Creature {

    public Operational(String path, int width, int height) {
        super(path, width, height);
        group = 2;
        speed = speed * 2;
    }

    @Override
    public void pause() {
        if (controller != null && controller instanceof PFrameKeyListener) {
            this.world.freeKeyListener('w', (PFrameKeyListener) this.controller);
            this.world.freeKeyListener('a', (PFrameKeyListener) this.controller);
            this.world.freeKeyListener('s', (PFrameKeyListener) this.controller);
            this.world.freeKeyListener('d', (PFrameKeyListener) this.controller);
            this.world.freeKeyListener('j', (PFrameKeyListener) this.controller);
        }
    }

    @Override
    public void Continue() {
        if (this.controller != null && controller instanceof PFrameKeyListener) {
            this.world.addKeyListener('w', (PFrameKeyListener) this.controller);
            this.world.addKeyListener('a', (PFrameKeyListener) this.controller);
            this.world.addKeyListener('s', (PFrameKeyListener) this.controller);
            this.world.addKeyListener('d', (PFrameKeyListener) this.controller);
            this.world.addKeyListener('j', (PFrameKeyListener) this.controller);
        }
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        Continue();
    }

    @Override
    public void deHealth(double i) {
        super.deHealth(i);
        if (world != null) {
            if (world.screen != null && world.getControlRole() == this) {
                world.screen.displayHealth(health, healthLimit);
            }
            if (isDead())
                dead();
        }
    }

    @Override
    public void addCoin(int n) {
        super.addCoin(n);
        if (world.screen != null && world.getControlRole() == this) {
            world.screen.setCoinValue(coin);
        }

    }

    @Override
    public void dead() {
        if (!World.multiPlayerMode) {
            super.dead();
            if (world.getControlRole() == this)
                world.gameFinish();
        } else {
            if (World.mainClient) {
                this.deHealth(-healthLimit / 2);
                while (true) {
                    int x = new Random().nextInt(world.getWidth());
                    int y = new Random().nextInt(world.getHeight());
                    if (world.isLocationReachable(this, Position.getPosition(y, x)) && world.ThingMove(this, Position.getPosition(y, x))) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void setController(CreatureController controller) {
        super.setController(controller);
        Continue();
    }
}
