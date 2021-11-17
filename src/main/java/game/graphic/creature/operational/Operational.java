package game.graphic.creature.operational;

import game.controller.AlogrithmController;
import game.controller.KeyBoardThingController;
import game.graphic.creature.Creature;

abstract public class Operational extends Creature {



    public Operational(String path, int width, int height){
        super(path, width, height);
        controller= new KeyBoardThingController();
        controller.setThing(this);
        group=2;
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

}
