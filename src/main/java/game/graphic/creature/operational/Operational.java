package game.graphic.creature.operational;

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
        this.parentScene.getParentView().freeKeyMouseListener();
    }

    @Override
    public void Continue() {
        this.parentScene.getParentView().addMouseWheelListener(((KeyBoardThingController)controller));
        this.parentScene.getParentView().addMouseListener((KeyBoardThingController)controller);
        this.parentScene.getParentView().addKeyListener((KeyBoardThingController)controller);
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        controller= new KeyBoardThingController();
        controller.setThing(this);
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
