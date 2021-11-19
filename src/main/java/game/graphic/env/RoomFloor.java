package game.graphic.env;

import com.pFrame.Pixel;
import game.graphic.Thing;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.io.File;
import java.util.Random;

public class RoomFloor extends Thing {
    static public File[] FloorPaths = {
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-28.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-29.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-30.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-31.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-32.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-33.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-34.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-35.png").getFile()),
            new File(RoomFloor.class.getClassLoader().getResource("image/source/3-36.png").getFile())
    };
    static public Pixel[][][] AllPixels;

    static {
        AllPixels=new Pixel[FloorPaths.length][][];
        AllPixels[0]= GraphicItemGenerator.generateItem(FloorPaths[0], World.tileSize,World.tileSize).getPixels();
        AllPixels[1]= GraphicItemGenerator.generateItem(FloorPaths[1], World.tileSize,World.tileSize).getPixels();
        AllPixels[2]= GraphicItemGenerator.generateItem(FloorPaths[2], World.tileSize,World.tileSize).getPixels();
        AllPixels[3]= GraphicItemGenerator.generateItem(FloorPaths[3], World.tileSize,World.tileSize).getPixels();
        AllPixels[4]= GraphicItemGenerator.generateItem(FloorPaths[4], World.tileSize,World.tileSize).getPixels();
        AllPixels[5]= GraphicItemGenerator.generateItem(FloorPaths[5], World.tileSize,World.tileSize).getPixels();
        AllPixels[6]= GraphicItemGenerator.generateItem(FloorPaths[6], World.tileSize,World.tileSize).getPixels();
        AllPixels[7]= GraphicItemGenerator.generateItem(FloorPaths[7], World.tileSize,World.tileSize).getPixels();
        AllPixels[8]= GraphicItemGenerator.generateItem(FloorPaths[8], World.tileSize,World.tileSize).getPixels();
    }

    public RoomFloor(){
        super(null);
        Random random=new Random();
        graphic=AllPixels[random.nextInt(FloorPaths.length)];
        height=World.tileSize;
        width=World.tileSize;
    }
}
