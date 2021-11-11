package game.role;

import java.io.File;

import com.pFrame.pgraphic.PGraphicItem;

public class Thing extends PGraphicItem{

    public Thing(File file, int width, int height) {
        super(file, width, height);
    }
    
    public Thing(String path,int width,int height){
        super(path, width, height);
    }
}
