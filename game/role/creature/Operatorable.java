package game.role.creature;

import java.io.File;

public class Operatorable extends Creature{

    public Operatorable(File file, int width, int height) {
        super(file, width, height);
    }

    public Operatorable(String path,int width,int height){
        super(path, width, height);
    }
    
}
