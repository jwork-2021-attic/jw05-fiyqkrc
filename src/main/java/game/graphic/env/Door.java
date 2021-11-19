package game.graphic.env;

import com.pFrame.Pixel;
import game.graphic.Thing;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.io.File;
import java.util.Random;

public class Door extends Thing {
    static public File[] DoorPaths = {
            new File(Door.class.getClassLoader().getResource("image/source/0-39.png").getFile())
    };
    static public Pixel[][][] AllPixels;

    static {
        AllPixels=new Pixel[DoorPaths.length][][];
        AllPixels[0]= GraphicItemGenerator.generateItem(DoorPaths[0], World.tileSize,World.tileSize).getPixels();
    }

    public Door(){
        super(null);
        Random random=new Random();
        graphic=AllPixels[random.nextInt(DoorPaths.length)];
        height=World.tileSize;
        width=World.tileSize;
    }
}
