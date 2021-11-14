package game.role.creature;

import game.controller.KeyBoardThingController;

import java.io.File;

public class Operational extends Creature{

    public Operational(File file, int width, int height) {
        super(file, width, height);
        controller= new KeyBoardThingController();
    }

    public Operational(String path, int width, int height){
        super(path, width, height);
        controller= new KeyBoardThingController();
    }
    
}
