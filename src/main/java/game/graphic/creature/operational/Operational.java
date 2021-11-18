package game.graphic.creature.operational;

import com.pFrame.pwidget.ObjectUserInteractive;
import game.controller.KeyBoardThingController;
import game.graphic.creature.Creature;

abstract public class Operational extends Creature {

    public Operational(String path, int width, int height){
        super(path, width, height);
        group=2;
        speed=speed*2;
    }

    @Override
    public void pause() {
        this.world.addKeyListener('w',null);
        this.world.addMouseListener(null);
        this.world.addKeyListener('a',null);
        this.world.addKeyListener('s',null);
        this.world.addKeyListener('d',null);
    }

    @Override
    public void Continue() {
        this.world.addKeyListener('w', (ObjectUserInteractive) this.controller);
        this.world.addMouseListener((ObjectUserInteractive) this.controller);
        this.world.addKeyListener('a', (ObjectUserInteractive) this.controller);
        this.world.addKeyListener('s', (ObjectUserInteractive) this.controller);
        this.world.addKeyListener('d', (ObjectUserInteractive) this.controller);
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        controller= new KeyBoardThingController();
        controller.setThing(this);
        Continue();
    }

    @Override
    public void deHealth(double i) {
        super.deHealth(i);
        if(world.screen!=null)
        {
            world.screen.displayHealth(health,healthLimit);
        }
        if(isDead())
            dead();
    }

    @Override
    public void addCoin(int n) {
        super.addCoin(n);
        if(world.screen!=null){
            world.screen.setCoinValue(coin);
        }

    }

    @Override
    public void dead() {
        super.dead();
        world.gameFinish();
    }
}
