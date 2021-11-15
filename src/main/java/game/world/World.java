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
    private WorldGenerate worldGenerator;
    private int tileWidth = 32;

    Pixel[][] mazePixels;

    public World(int width, int height) {
        super(width, height);
        mazePixels = new Pixel[height][width];
        generateWorld();
        createWorld();
    }

    public Position getStartPosition() {
        return worldGenerator.getStart();
    }

    private void generateWorld() {
        boolean success = false;
        int tryTimes = 0;
        while (tryTimes <= 3 && !success) {
            try {
                worldGenerator = new WorldGenerate(this.width / (tileWidth*2), this.height /( tileWidth*2), 2000000,
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
        for (int i = 0; i < height / (tileWidth*4); i++) {
            for (int j = 0; j < width / (tileWidth*4); j++) {
                File srcpath = switch (worldArray[i][j]) {
                    case 0 -> new File(this.getClass().getClassLoader().getResource("image/wall.png").getFile());
                    case 1, 6, 5, 4, 3, 2 -> new File(this.getClass().getClassLoader().getResource("image/floor.png").getFile());
                    default -> null;
                };
                assert srcpath != null;
                for(int a=0;a<2;a++){
                    for(int b=0;b<2;b++){
                        PGraphicItem tile = new PGraphicItem(srcpath, tileWidth, tileWidth);
                        addItem(tile,Position.getPosition((2*i+a) * tileWidth, (2*j+b) * tileWidth));
                        //Pixel.pixelsAdd(this.mazePixels, tile.getPixels(), Position.getPosition((2*i+a) * tileWidth, (2*j+b) * tileWidth));
                    }
                }
            }
        }
    }
/*
    @Override
    public Pixel[][] displayOutput(Position p, int width, int height) {
        Pixel[][] pix=super.displayOutput(p, width, height);
        pix=Pixel.pixelsAdd(pix,Pixel.subPixels(mazePixels,p,width,height),Position.getPosition(0,0));
        return pix;
    }
*/
    @Override
    protected void updatePixels() {
        this.pixels = Pixel.pixelsCopy(mazePixels);
        if (this.backImage != null) {
            // TODO
        }
        for (PGraphicItem item : this.Items) {
            int w = item.getWidth();
            int h = item.getHeight();
            Pixel[][] pix = item.getPixels();
            int x = item.getPosition().getX();
            int y = item.getPosition().getY();
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if ((x + i > 0 && x + i < this.height) && (y + j > 0 && y + j < this.width)) {
                        if (pix[i][j] != null)
                            this.pixels[x + i][y + j] = pix[i][j];
                    }
                }
            }
        }
    }

    @Override
    public boolean addItem(PGraphicItem item) {
        if(item instanceof Thing)
            ((Thing) item).whenBeAddedToScene();
        return super.addItem(item);
    }
    public void addOperational(Operational operational){
        addItem(operational);
        if(this.parentView!=null){
            parentView.setItemController((ObjectUserInteractive) operational.getController());
            parentView.setFocus(operational);
        }
        else{
            Log.ErrorLog(this,"please put world on a view first");
        }
    }
}
