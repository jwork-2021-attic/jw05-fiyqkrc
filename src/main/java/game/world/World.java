package game.world;

import java.io.File;

import com.pFrame.ObjectUserInteractive;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.role.Thing;
import game.role.creature.Creature;
import game.role.creature.Operational;
import log.Log;
import worldGenerate.WorldGenerate;

public class World extends PGraphicScene {
    private Tile<Thing>[][] tiles;
    private int tileWidth;
    private int tileHeight;
    private int[][] worldArray;
    private int worldScale;
    private WorldGenerate worldGenerator;
    private int tileSize;

    private Position startPosition;


    public World(int width, int height) {
        super(width, height);
        worldScale=2;
        tileSize =20;
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

    public int getTileSize(){
        return this.tileSize;
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
        for (int i = 0; i < tileHeight; i++) {
            for (int j = 0; j < tileWidth; j++) {
                File srcpath=null;
                switch (worldArray[i][j]) {
                    case 0 -> {
                        srcpath=new File(this.getClass().getClassLoader().getResource("image/wall.png").getFile());
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        tiles[i][j].setThing(thing);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    case 1, 6, 5, 4, 3, 2 -> {
                        srcpath=new File(this.getClass().getClassLoader().getResource("image/floor.png").getFile());
                        Thing thing = new Thing(srcpath, tileSize, tileSize);
                        addItem(thing, Position.getPosition(i  * tileSize, j * tileSize));
                    }
                    default -> {
                    }
                };
            }
        }
    }


    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing) {
            ((Thing) item).whenBeAddedToScene();
            ((Thing) item).setWorld(this);
        }
        return super.addItem(item);
    }

    public void addOperational(Operational operational) {
        addItem(operational);
        if (this.parentView != null) {
            parentView.setItemController((ObjectUserInteractive) operational.getController());
            parentView.setFocus(operational);
        } else {
            Log.ErrorLog(this, "please put world on a view first");
        }
    }

    public boolean isLocationReachable(Creature creature,Position position){
        if(tiles[position.getX()/tileSize][position.getY()/tileSize].getThing()==null)
            return true;
        else
            return false;
    }
}
