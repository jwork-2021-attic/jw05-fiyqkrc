package game.world;

import java.io.File;
import java.util.Random;

import com.pFrame.pwidget.ObjectUserInteractive;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.graphic.Thing;
import game.graphic.creature.operational.Operational;
import log.Log;
import worldGenerate.WorldGenerate;

public class World extends PGraphicScene {
    private final Tile<Thing>[][] tiles;
    private final int tileWidth;
    private final int tileHeight;
    private int[][] worldArray;
    private final int worldScale;
    private WorldGenerate worldGenerator;
    public static int tileSize=20;

    private Position startPosition;


    public World(int width, int height) {
        super(width, height);
        worldScale=2;
        tileWidth=width/tileSize;
        tileHeight=height/tileSize;
        tiles=new Tile[tileHeight][tileWidth];
        for(int i=0;i<tileHeight;i++)
            for(int j=0;j<tileWidth;j++)
                tiles[i][j]=new Tile<Thing>();

        generateWorld();
        if(worldScale>=2){
            scaleWorld();
        }
        createWorld();
    }
    public Position getStartPosition()
    {
        return this.startPosition;
    }

    public int[][] scaleWorld(){
        if(worldArray!=null&&worldScale>=2) {
            int width = worldArray[0].length*worldScale;
            int height=worldArray.length*worldScale;
            int[][] array=new int[height][width];
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    array[i][j]=worldArray[i/worldScale][j/worldScale];
                }
            }
            worldArray=array;
            return worldArray;
        }
        return worldArray;
    }

    public int getWorldScale(){
        return worldScale;
    }

    public static int getTileSize(){
        return World.tileSize;
    }



    private void generateWorld() {
        boolean success = false;
        int tryTimes = 0;
        while (tryTimes <= 3 && !success) {
            try {
                worldGenerator = new WorldGenerate(this.width / (tileSize * worldScale), this.height / (tileSize * worldScale), 2000000,
                        20, 2,
                        20, 2
                );
                worldArray = worldGenerator.generate();
                startPosition=Position.getPosition(worldGenerator.getStart().getX()*tileSize*worldScale,worldGenerator.getStart().getY()*tileSize*worldScale);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
                tryTimes++;
            }
        }
    }

    private void createWorld() {
        Random random=new Random();
        File[] WallPaths={
                new File(this.getClass().getClassLoader().getResource("image/source/1-18.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/1-15.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/1-23.png").getFile())
        };
        File[] CorridorPaths={
                new File(this.getClass().getClassLoader().getResource("image/source/1-45.png").getFile())
        };
        File[] RoomPath={
                new File(this.getClass().getClassLoader().getResource("image/source/3-28.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-29.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-30.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-31.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-32.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-33.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-34.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-35.png").getFile()),
                new File(this.getClass().getClassLoader().getResource("image/source/3-36.png").getFile())
        };
        File[] DoorPath={
                new File(this.getClass().getClassLoader().getResource("image/source/0-39.png").getFile())
        };
        for (int i = 0; i < tileHeight; i++) {
            for (int j = 0; j < tileWidth; j++) {
                File srcpath=null;
                switch (worldArray[i][j]) {
                    case 0 -> {
                        srcpath=WallPaths[random.nextInt(WallPaths.length)];
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        thing.setBeCoverAble(false);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    case 1 -> {
                        srcpath=CorridorPaths[random.nextInt(CorridorPaths.length)];
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    case 6, 5, 4 ->{
                        srcpath=DoorPath[random.nextInt(DoorPath.length)];
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    case 2,3 -> {
                        srcpath=RoomPath[random.nextInt(RoomPath.length)];
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    default -> {
                    }
                }
            }
        }
    }


    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing) {
            ((Thing) item).whenBeAddedToScene();
            ((Thing) item).setWorld(this);

            if(((Thing) item).isBeCoverAble() || isLocationReachable(item.getPosition())){
                if(!((Thing) item).isBeCoverAble())
                    tiles[item.getPosition().getX()/tileSize][item.getPosition().getY()/tileSize].setThing((Thing)item);
            }
            else
                return false;
        }
        return super.addItem(item);
    }

    @Override
    public boolean addItem(PGraphicItem item, Position p) {
        item.setPosition(p);
        return addItem(item);
    }

    public void addOperational(Operational operational) {
        addItem(operational);
        if (this.parentView != null) {
            parentView.getKeyMouseListener((ObjectUserInteractive) operational.getController());
            parentView.setFocus(operational);
        } else {
            Log.ErrorLog(this, "please put world on a view first");
        }
    }

    public boolean isLocationReachable(Position position){
        return position.getX() >= 0 && position.getX() < height && position.getY() >= 0 && position.getY() < width && tiles[position.getX() / tileSize][position.getY() / tileSize].getThing() == null;
    }
}
