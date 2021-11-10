package worldGenerate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.pFrame.PFrame;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PHeadWidget;

import asciiPanel.AsciiFont;
import mazeGenerator.MazeGenerator;

public class WorldGenerate {
    private int width;
    private int height;
    private int max_rooms_generate_trys;
    private int room_max_height;
    private int room_min_height;
    private int room_max_width;
    private int room_min_width;

    private int[][] world;
    private ArrayList<Room> roomsArray;

    public WorldGenerate(int width, int height, int max_rooms_generate_trys, int room_max_width, int room_min_width,
            int room_max_height, int room_min_height) {
        this.width = width;
        this.height = height;
        this.max_rooms_generate_trys = max_rooms_generate_trys;
        this.room_max_height = room_max_height;
        this.room_max_width = room_max_width;
        this.room_min_height = room_min_height;
        this.room_min_width = room_min_width;

        roomsArray = new ArrayList<>();
        this.world = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                world[i][j] = 0;
            }
        }
    }

    public int[][] generate() {
        this.rooms_generate();
        this.maze_generate();
        return this.world;
    }

    private void rooms_generate() {
        Random random = new Random(82734994);
        // 进行至多max_rooms_generate_trys次的room生成尝试
        for (int i = 0; i < this.max_rooms_generate_trys; i++) {

            // 生成随机大小和位置的rooms
            Position pos = Position.getPosition(random.nextInt(this.height), random.nextInt(this.width));
            int random_width = random.nextInt(this.room_min_width, this.room_max_width);
            int random_height = random.nextInt(this.room_min_height, this.room_max_height);
            // 对生成的room合法性检查
            boolean vaild = true;
            for (int a = 0; a < random_height; a++) {
                for (int b = 0; b < random_width; b++) {
                    if (isOutBound(Position.getPosition(pos.getX() + a, pos.getY() + b))) {
                        vaild = false;
                        break;
                    } else if (!isEmptyOrOutBound(Position.getPosition(pos.getX() + a, pos.getY() + b))
                            || !isEmptyOrOutBound(Position.getPosition(pos.getX() + a - 1, pos.getY() + b))
                            || !isEmptyOrOutBound(Position.getPosition(pos.getX() + a + 1, pos.getY() + b))
                            || !isEmptyOrOutBound(Position.getPosition(pos.getX() + a, pos.getY() + b - 1))
                            || !isEmptyOrOutBound(Position.getPosition(pos.getX() + a, pos.getY() + b + 1))) {
                        vaild = false;
                        break;
                    }
                }
                if (!vaild) {
                    break;
                }
            }

            // 如果room没有越界或者和其它room重合，加入roomsArray;
            if (vaild) {
                for (int a = 0; a < random_height; a++) {
                    for (int b = 0; b < random_width; b++) {
                        world[pos.getX() + a][pos.getY() + b] = 1;
                    }
                }
                roomsArray.add(new Room(pos, random_width, random_height));
            }
        }
    }

    private void maze_generate() {
        
    }

    ArrayList<Position> emptyPositionAround(Position p){
        ArrayList<Position> list=new ArrayList<>();
        if(isEmptyOrOutBound(leftPosition(p)))
            list.add(leftPosition(p));
        if(isEmptyOrOutBound(upPosition(p)))
            list.add(upPosition(p));
        if(isEmptyOrOutBound(downPosition(p)))
            list.add(downPosition(p));
        if(isEmptyOrOutBound(rightPosition(p)))
            list.add(rightPosition(p));
        return list;
    }

    boolean isEmpty(Position p){
        if(isOutBound(p))
            return false;
        else
            return (this.world[p.getX()][p.getY()]==0);
    }

    void tagOnePixel(Position p,int x){
        world[p.getX()][p.getY()]=x;
        System.out.println(p);
    }

    boolean isEmptyAround(Position p){
        return isEmptyOrOutBound(upPosition(p))&&isEmptyOrOutBound(downPosition(p))&&isEmptyOrOutBound(leftPosition(p))&&isEmptyOrOutBound(rightPosition(p));
    }

    Position upPosition(Position p){
        return Position.getPosition(p.getX()-1, p.getY());
    }

    Position downPosition(Position p){
        return Position.getPosition(p.getX()+1, p.getY());
    }

    Position leftPosition(Position p){
        return Position.getPosition(p.getX(), p.getY()-1);
    }

    Position rightPosition(Position p){
        return Position.getPosition(p.getX(), p.getY()+1);
    }

    Position upPosition(Position p,int x){
        return Position.getPosition(p.getX()-x, p.getY());
    }

    Position downPosition(Position p,int x){
        return Position.getPosition(p.getX()+x, p.getY());
    }

    Position leftPosition(Position p,int x){
        return Position.getPosition(p.getX(), p.getY()-x);
    }

    Position rightPosition(Position p,int x){
        return Position.getPosition(p.getX(), p.getY()+x);
    }

    boolean isEmptyOrOutBound(Position p) {
        if (isOutBound(p)) {
            return true;
        } else {
            return (this.world[p.getX()][p.getY()] == 0);
        }
    }

    boolean isOutBound(Position p) {
        return (p.getX() >= this.height) || (p.getX() < 0) || (p.getY() >= this.width) || (p.getY() < 0);
    }

    class Room {
        Position pos;
        int width;
        int height;

        Room(Position pos, int width, int height) {
            this.pos = pos;
            this.width = width;
            this.height = height;
        }
    }

    PGraphicItem toPGraphicItem(int[][] world) {
        if (world == null)
            return null;
        else {
            int width = world[0].length;
            int height = world.length;
            Pixel[][] pixels = Pixel.emptyPixels(width, height);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    switch (world[i][j]) {
                    case 0:
                        pixels[i][j] = Pixel.getPixel(Color.BLACK, (char) 0xf0);
                        break;
                    case 1:
                        pixels[i][j] = Pixel.getPixel(Color.CYAN, (char) 0xf0);
                        break;
                    case 2:
                        pixels[i][j] = Pixel.getPixel(Color.GRAY, (char) 0xf0);
                        break;
                    default:
                        break;
                    }
                }
            }
            return new PGraphicItem(pixels);
        }
    }

    public static void main(String[] args) {
        WorldGenerate generate = new WorldGenerate(200, 200, 20000, 20, 10, 20, 10);
        PGraphicItem item = generate.toPGraphicItem(generate.generate());
        PHeadWidget pHeadWidget = new PHeadWidget(null, null, new PFrame(300, 250, AsciiFont.pFrame_4x4));
        PGraphicScene scene = new PGraphicScene(250, 250);
        scene.addItem(item, Position.getPosition(0, 0));
        PGraphicView view = new PGraphicView(pHeadWidget, null, scene);
        view.setViewPosition(Position.getPosition(0, 0));
        pHeadWidget.startRepaintThread();
    }
}
