package game.world;

import java.io.File;

import com.pFrame.ObjectUserInteractive;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.role.Thing;
import game.role.creature.Operational;
import log.Log;
import worldGenerate.WorldGenerate;

public class World extends PGraphicScene {
    private int[][] worldArray;
    private int worldScale;
    private WorldGenerate worldGenerator;
    private int tileWidth;


    public World(int width, int height) {
        super(width, height);
        worldScale=2;
        tileWidth=20;
        generateWorld();
        if(worldScale>=2){
            scaleWorld();
        }
        createWorld();
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

    public int getTileWidth(){
        return this.tileWidth;
    }

    public Position getStartPosition() {
        return worldGenerator.getStart();
    }

    private void generateWorld() {
        boolean success = false;
        int tryTimes = 0;
        while (tryTimes <= 3 && !success) {
            try {
                worldGenerator = new WorldGenerate(this.width / (tileWidth * worldScale), this.height / (tileWidth * worldScale), 2000000,
                        20, 2,
                        20, 2
                );
                worldArray = worldGenerator.generate();
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
                tryTimes++;
            }
        }
    }

    private void createWorld() {
        for (int i = 0; i < height / tileWidth; i++) {
            for (int j = 0; j < width / tileWidth; j++) {
                File srcpath = switch (worldArray[i][j]) {
                    case 0 -> new File(this.getClass().getClassLoader().getResource("image/wall.png").getFile());
                    case 1, 6, 5, 4, 3, 2 -> new File(this.getClass().getClassLoader().getResource("image/floor.png").getFile());
                    default -> null;
                };
                PGraphicItem tile = new PGraphicItem(srcpath, tileWidth, tileWidth);
                addItem(tile, Position.getPosition(i  * tileWidth, j * tileWidth));
            }
        }
    }


    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing)
            ((Thing) item).whenBeAddedToScene();
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
}
