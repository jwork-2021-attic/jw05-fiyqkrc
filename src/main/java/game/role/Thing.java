package game.role;

import com.pFrame.pgraphic.PGraphicItem;
import game.world.Tile;

import java.io.File;

public class Thing extends PGraphicItem{
    protected Tile<? extends Thing> tile;

    public Thing(File file, int width, int height) {
        super(file, width, height);
    }
    
    public Thing(String path,int width,int height){
        super(path, width, height);
    }

    public void setTile(Tile<? extends Thing> tile){
        this.tile=tile;
    }

    public void whenBeAddedToScene(){

    }
}
