package game.world;

import java.io.File;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.role.Thing;
import worldGenerate.WorldGenerate;

public class World extends PGraphicScene {
    private int[][] worldArray;
    private WorldGenerate worldGenerator;
    private int tileWidth = 10;

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
                worldGenerator = new WorldGenerate(this.width / tileWidth, this.height / tileWidth, 2000000,
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
                assert srcpath != null;
                PGraphicItem tile = new PGraphicItem(srcpath, tileWidth, tileWidth);
                Pixel.pixelsAdd(this.mazePixels, tile.getPixels(), Position.getPosition(i * tileWidth, j * tileWidth));
            }
        }
    }

    @Override
    protected void updatePixels() {
        this.pixels = Pixel.pixelsCopy(this.mazePixels);
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
}
