package game.graphic.env;

import com.pFrame.Pixel;
import game.graphic.Thing;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.io.File;
import java.util.Random;

public class CorridorFloor extends Thing {
    static public File[] FloorPaths = {
            new File(CorridorFloor.class.getClassLoader().getResource("image/source/1-45.png").getFile())
    };
    static public Pixel[][][] AllPixels;

    static {
        AllPixels=new Pixel[FloorPaths.length][][];
        AllPixels[0]= GraphicItemGenerator.generateItem(FloorPaths[0], World.tileSize,World.tileSize).getPixels();
    }

    public CorridorFloor(){
        super(null);
        Random random=new Random();
        graphic=AllPixels[random.nextInt(FloorPaths.length)];
        height=World.tileSize;
        width=World.tileSize;
    }
}
