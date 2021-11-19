package game.graphic.env;

import com.pFrame.Pixel;
import game.graphic.Thing;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.io.File;
import java.util.Random;

public class Wall extends Thing {
    static public File[] WallPaths = {
            new File(Wall.class.getClassLoader().getResource("image/source/1-18.png").getFile()),
            new File(Wall.class.getClassLoader().getResource("image/source/1-15.png").getFile()),
            new File(Wall.class.getClassLoader().getResource("image/source/1-23.png").getFile())
    };
    static public Pixel[][][] AllPixels;

    static {
        AllPixels=new Pixel[WallPaths.length][][];
        AllPixels[0]= GraphicItemGenerator.generateItem(WallPaths[0], World.tileSize,World.tileSize).getPixels();
        AllPixels[1]= GraphicItemGenerator.generateItem(WallPaths[1], World.tileSize,World.tileSize).getPixels();
        AllPixels[2]= GraphicItemGenerator.generateItem(WallPaths[2], World.tileSize,World.tileSize).getPixels();

    }

    public Wall(){
        super(null);
        Random random=new Random();
        graphic=AllPixels[random.nextInt(WallPaths.length)];
        height=World.tileSize;
        width=World.tileSize;
        beCoverAble=false;
    }

}
