package game.world;

import java.io.File;

import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import worldGenerate.WorldGenerate;

public class World extends PGraphicScene{
    private int[][] worldArray;
    private WorldGenerate worldGenerator;
    private int tileWidth=10;

    public World(int width, int height) {
        super(width, height);
        generateWorld();
        createWorld();
    }

    public Position getStartPosition(){
        return worldGenerator.getStart();
    }

    private void generateWorld(){
        boolean success=false;
        int tryTimes=0;
        while(tryTimes<=3&&success==false){
            try{
                worldGenerator=new WorldGenerate(this.width/tileWidth, this.height/tileWidth, 2000000, 
                    20, 2, 
                    20, 2
                    );
                worldArray=worldGenerator.generate();
                success=true;
            }catch(Exception e){
                e.printStackTrace();
                tryTimes++;
            }
        }
    }

    private void createWorld(){
        for(int i=0;i<height/tileWidth;i++){
            for(int j=0;j<width/tileWidth;j++){
                File srcpath = switch (worldArray[i][j]) {
                    case 0 -> new File(this.getClass().getClassLoader().getResource("image/wall.png").getFile());
                    case 1, 6, 5, 4, 3, 2 -> new File(this.getClass().getClassLoader().getResource("image/floor.png").getFile());
                    default -> null;
                };
                PGraphicItem tile=new PGraphicItem(srcpath,tileWidth,tileWidth);
                tile.setPosition(Position.getPosition(i*tileWidth, j*tileWidth));
                this.addItem(tile);
            }
        }
    }
    
}
