package game.graphic.creature;

import game.controller.KeyBoardThingController;

public class Operational extends Creature{



    public Operational(String path, int width, int height){
        super(path, width, height);
        controller= new KeyBoardThingController();
        controller.setThing(this);
    }
    
}
