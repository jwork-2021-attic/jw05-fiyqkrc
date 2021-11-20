package game.world;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.Attack;
import game.graphic.*;
import game.graphic.env.CorridorFloor;
import game.graphic.env.Door;
import game.graphic.env.RoomFloor;
import game.graphic.env.Wall;
import game.graphic.interactive.GameThread;
import game.Location;
import game.controller.AlogrithmController;
import game.graphic.creature.Creature;
import game.graphic.creature.monster.*;
import game.graphic.creature.operational.Operational;
import game.graphic.interactive.Box;
import game.graphic.interactive.ExitPlace;
import game.screen.UI;
import log.Log;
import worldGenerate.WorldGenerate;
import worldGenerate.WorldGenerate.Room;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class World extends PGraphicScene implements Runnable {
    private final Tile<Thing>[][] tiles;
    private final int tileWidth;
    private final int tileHeight;
    private int[][] worldArray;
    private final int worldScale;
    private WorldGenerate worldGenerator;
    public static int tileSize = 20;
    protected static ArrayList<Room> rooms;
    public UI screen;

    protected ArrayList<Class> monster = new ArrayList<>();

    private Position startPosition;

    private ArrayList<Thing> areas[][];
    int areaWidth;
    int areaHeight;
    int areaSize = 200;

    Operational operational;
    Thread daemonThread;


    public World(int width, int height) {
        super(width, height);
        worldScale = 2;
        tileWidth = width / tileSize;
        tileHeight = height / tileSize;
        tiles = new Tile[tileHeight][tileWidth];
        for (int i = 0; i < tileHeight; i++)
            for (int j = 0; j < tileWidth; j++)
                tiles[i][j] = new Tile<Thing>(new Location(i, j));

        monster.add(Dragon.class);
        monster.add(Master.class);
        monster.add(Pangolin.class);
        monster.add(SnowMonster.class);
        monster.add(Spider.class);

        areaHeight = this.height / areaSize + 1;
        areaWidth = this.width / areaSize + 1;

        areas = new ArrayList[areaHeight][areaWidth];
        for (int i = 0; i < areaHeight; i++)
            for (int j = 0; j < areaWidth; j++) {
                areas[i][j] = new ArrayList<Thing>();
            }

        generateWorld();
        if (worldScale >= 2) {
            scaleWorld();
        }
        createWorld();
        createBox();
        createMonster();

        daemonThread = new Thread(this);
        daemonThread.start();
    }

    public Pixel[][] getWorldMap() {
        Pixel[][] pixels = Pixel.emptyPixels(worldArray.length, worldArray[0].length);
        for (int i = 0; i < worldArray.length; i++)
            for (int j = 0; j < worldArray[0].length; j++) {
                Color color = (worldArray[i][j] == 0) ? Color.GRAY : Color.BLUE;
                pixels[i][j] = Pixel.getPixel(color, (char) 0xf0);
            }
        assert pixels != null;
        pixels[operational.getLocation().x() / worldScale][operational.getLocation().y() / worldScale] = Pixel.getPixel(Color.RED, (char) 0xf0);
        return pixels;
    }

    protected void createBox() {
        Random random = new Random();
        for (Room room : rooms) {
            //generate box
            int x = random.nextInt(room.height);
            int y = random.nextInt(room.width);
            Box box = new Box();
            worldArray[x][y] = 200;
            box.setPosition(Position.getPosition((room.pos.getX() + x) * tileSize, (room.pos.getY() + y) * tileSize));
            areas[box.getPosition().getX() / areaSize][box.getPosition().getY() / areaSize].add(box);
            //addItem(box,box.getPosition());
        }
    }

    protected void createMonster() {
        Random random = new Random();

        //generate for rooms
        for (Room room : rooms) {
            for (int i = 0; i < room.width; i++)
                for (int j = 0; j < room.height; j++) {
                    if (random.nextDouble(1) > 0.75) {
                        int index = random.nextInt(monster.size());
                        worldArray[room.pos.getX() + j][room.pos.getY() + i] = 100 + index;
                        try {
                            Monster m = (Monster) monster.get(index).getDeclaredConstructor().newInstance();
                            m.setPosition(Position.getPosition((room.pos.getX() + j) * tileSize, (room.pos.getY() + i) * tileSize));
                            areas[m.getPosition().getX() / areaSize][m.getPosition().getY() / areaSize].add(m);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }

        //generate for corridor
        for (int i = 0; i < worldArray.length; i++) {
            for (int j = 0; j < worldArray[0].length; j++) {
                if (worldArray[i][j] == 1) {
                    if (random.nextDouble(1) > 0.90) {
                        int index = random.nextInt(monster.size());
                        worldArray[i][j] = 100 + index;
                        try {
                            Monster m = (Monster) monster.get(index).getDeclaredConstructor().newInstance();
                            m.setPosition(Position.getPosition((i) * tileSize, (j) * tileSize));
                            areas[m.getPosition().getX() / areaSize][m.getPosition().getY() / areaSize].add(m);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public Position getStartPosition() {
        return this.startPosition;
    }

    public int[][] scaleWorld() {
        if (worldArray != null && worldScale >= 2) {
            int width = worldArray[0].length * worldScale;
            int height = worldArray.length * worldScale;
            int[][] array = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    array[i][j] = worldArray[i / worldScale][j / worldScale];
                }
            }
            worldArray = array;
        }


        rooms = worldGenerator.getRoomsArray();
        for (Room room : rooms) {
            room.pos = Position.getPosition(room.pos.getX() * worldScale, room.pos.getY() * worldScale);
            room.height = room.height * worldScale;
            room.width = room.width * worldScale;
        }

        return worldArray;
    }

    public int getWorldScale() {
        return worldScale;
    }

    public static int getTileSize() {
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
                startPosition = Position.getPosition(worldGenerator.getStart().getX() * tileSize * worldScale, worldGenerator.getStart().getY() * tileSize * worldScale);
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
                switch (worldArray[i][j]) {
                    case 0 -> {
                        Wall wall = new Wall();
                        addItem(wall, Position.getPosition(i * tileSize, j * tileSize));
                    }
                    case 1 -> {
                        CorridorFloor corridorFloor = new CorridorFloor();
                        addItem(corridorFloor, Position.getPosition(i * tileSize, j * tileSize));
                    }
                    case 5 -> {
                        Door door = new Door();
                        addItem(door, Position.getPosition(i * tileSize, j * tileSize));

                        ExitPlace exitPlace = new ExitPlace();
                        exitPlace.setPosition(Position.getPosition(i * tileSize, j * tileSize));
                        areas[exitPlace.getPosition().getX() / areaSize][exitPlace.getPosition().getY() / areaSize].add(exitPlace);
                    }
                    case 4 -> {
                        Door door = new Door();
                        addItem(door, Position.getPosition(i * tileSize, j * tileSize));
                    }
                    case 2, 3, 6 -> {
                        RoomFloor roomFloor = new RoomFloor();
                        addItem(roomFloor, Position.getPosition(i * tileSize, j * tileSize));
                    }
                    default -> {
                    }
                }
            }
        }
    }

    @Override
    public boolean removeItem(PGraphicItem item) {
        if (item instanceof Thing) {
            if (!((Thing) item).isBeCoverAble()) {
                synchronized (item) {
                    if (((Thing) item).getTile() != null)
                        ((Thing) item).getTile().setThing(null);
                }
            }
        }
        return super.removeItem(item);
    }

    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing) {
            ((Thing) item).setWorld(this);
            ((Thing) item).whenBeAddedToScene();


            synchronized (this.tiles) {
                if (((Thing) item).isBeCoverAble() || isLocationReachable((Thing) item, ((Thing) item).getCentralPosition())) {
                    if (!((Thing) item).isBeCoverAble())
                        tiles[((Thing) item).getCentralPosition().getX() / tileSize][((Thing) item).getCentralPosition().getY() / tileSize].setThing((Thing) item);
                } else
                    return false;
            }
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
        this.operational = operational;
        if (this.parentView != null) {
            parentView.setFocus(operational);
        } else {
            Log.ErrorLog(this, "please put world on a view first");
        }
        if (screen != null)
            screen.displayHealth(operational.getHealth(), operational.getHealthLimit());
    }

    public Operational getOperational() {
        return this.operational;
    }

    public boolean isLocationReachable(Thing thing, Position position) {
        return position.getX() >= 0 && position.getX() < height && position.getY() >= 0 && position.getY() < width && (tiles[position.getX() / tileSize][position.getY() / tileSize].getThing() == null
                || tiles[position.getX() / tileSize][position.getY() / tileSize].getThing() == thing);
    }


    public boolean ThingMove(Thing thing, Position centralPosition) {
        if (thing.isBeCoverAble()) {
            if (!positionOutOfBound(centralPosition)) {
                thing.setPosition(Position.getPosition(centralPosition.getX() - thing.getHeight() / 2, centralPosition.getY() - thing.getWidth() / 2));
                return true;
            } else
                return false;
        } else {
            synchronized (this.tiles) {
                synchronized (thing) {
                    if (isLocationReachable(thing, centralPosition) && thing.getTile().getLocation() != getTileByLocation(centralPosition) && !locationOutOfBound(getTileByLocation(centralPosition))) {
                        thing.getTile().setThing(null);
                        tiles[getTileByLocation(centralPosition).x()][getTileByLocation(centralPosition).y()].setThing(thing);
                        thing.setPosition(Position.getPosition(centralPosition.getX() - thing.getHeight() / 2, centralPosition.getY() - thing.getWidth() / 2));
                        return true;
                    } else
                        return false;
                }
            }
        }
    }

    protected Location calTile(Thing thing) {
        return getTileByLocation(thing.getCentralPosition());
    }

    public Location getTileByLocation(Position position) {
        return new Location(position.getX() / tileSize, position.getY() / tileSize);
    }

    public Thing findThing(Location location) {
        if (!locationOutOfBound(location))
            return tiles[location.x()][location.y()].getThing();
        else
            return null;
    }

    public boolean locationOutOfBound(Location location) {
        return location.y() < 0 || location.x() < 0 || location.x() >= tileHeight || location.y() >= tileWidth;
    }

    public boolean positionOutOfBound(Position position) {
        return position.getY() < 0 || position.getX() < 0 || position.getX() >= height || position.getY() >= width;
    }

    public void handleAttack(Attack attack) {
        for (Location location : attack.affectedTiles) {
            if (!locationOutOfBound(location)) {
                Thing thing = tiles[location.x()][location.y()].getThing();
                if (thing != null) {
                    if (thing instanceof Creature && ((Creature) thing).getGroup() != attack.group) {
                        ((Creature) thing).deHealth(attack.attackNumber);
                    }
                }
            }
        }
    }

    public void gameFinish() {
        Thread thread = new Thread(new GameResourceRecycle());
        thread.start();
    }

    public Location searchNearestEnemy(Creature creature, int bound) {
        int x, y;
        if (creature.getTile() == null) {
            return null;
        } else {
            x = creature.getTile().getLocation().x();
            y = creature.getTile().getLocation().y();
        }
        for (int i = 1; i < bound; i++) {
            for (int a = x - i; a <= x + i; a++) {
                for (int b = y - i; b <= y + i; b++) {
                    if (!locationOutOfBound(new Location(a, b)) && ((a == x - i) || (a == x + i) || (b == y - i) || (b == y + i)) && tiles[a][b].getThing() != null) {
                        synchronized (this.tiles) {
                            if (tiles[a][b].getThing() instanceof Creature && ((Creature) tiles[a][b].getThing()).getGroup() != creature.getGroup()){
                                try{
                                    if(!hasWallBetweenPositions(creature.getCentralPosition(),tiles[a][b].getThing().getCentralPosition())){
                                        return new Location(a,b);
                                    }
                                }catch (Exception ignored){

                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean hasWallBetweenPositions(Position src,Position dest){
        double distance=Position.distance(dest, src);
        double direction=Direction.calDirection(src, dest);
        boolean has=false;
        for(int k=0;k<distance;k++){
            Location location=getTileByLocation(Position.getPosition(src.getX()-(int)(k*Math.sin(direction)), src.getY()+(int)(k*Math.cos(direction))));
            if(findThing(location) instanceof Wall){
                has=true;
                break;
            }
        }
        return has;
    }

    @Override
    public void run() {
        Log.InfoLog(this, "thread for world's monster recycle start...");

        ArrayList<Area> oldAreas = new ArrayList<>();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (this.operational != null) {
                    Position position = operational.getPosition();
                    int x = position.getX() / areaSize;
                    int y = position.getY() / areaSize;
                    ArrayList<Area> curAreas = new ArrayList<>();
                    for (int i = x - 1; i <= x + 1; i++)
                        for (int j = y - 1; j <= y + 1; j++) {
                            if (i >= 0 && i < areaHeight && j >= 0 && j < areaWidth) {
                                curAreas.add(new Area(i, j));
                            }
                        }
                    for (Area area : curAreas) {
                        for (Area area1 : oldAreas) {
                            if (area.x == area1.x && area.y == area1.y) {
                                oldAreas.remove(area1);
                                break;
                            }
                        }
                    }
                    for (Area area : oldAreas) {
                        for (int i = area.x * areaSize; i < (area.x + 1) * areaSize; i++) {
                            for (int j = area.y * areaSize; j < (area.y + 1) * areaSize; j++) {
                                Thing thing = findThing(new Location(i / tileSize, j / tileSize));
                                if (thing instanceof Creature && thing != operational) {
                                    areas[area.x][area.y].add(thing);
                                    if (((Creature) thing).getController() instanceof AlogrithmController)
                                        ((AlogrithmController) ((Creature) thing).getController()).stop();
                                    removeItem(thing);
                                } else if (thing instanceof GameThread) {
                                    areas[area.x][area.y].add(thing);
                                    ((GameThread) thing).stop();
                                    removeItem(thing);
                                }
                            }
                        }
                    }
                    for (Area area : curAreas) {
                        ArrayList<Thing> added = new ArrayList<>();
                        for (Thing thing : areas[area.x][area.y]) {
                            synchronized (this) {
                                if (!thing.isBeCoverAble() && !isLocationReachable(thing, thing.getPosition())) {
                                    //System.out.println(creature.getPosition());
                                } else {
                                    addItem(thing);
                                    added.add(thing);
                                }
                            }
                        }
                        areas[area.x][area.y].removeAll(added);
                    }
                    oldAreas = curAreas;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
                Log.ErrorLog(this, "thread failed");
                break;
            }
            //System.out.println("The number of all gameThreads at this time is " + GameThread.threadSet.size());
        }
    }

    record Area(int x, int y) {
        public Area(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class GameResourceRecycle implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                daemonThread.interrupt();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (GameThread.threadSet) {
                for (Thread thread : GameThread.threadSet) {
                    thread.interrupt();
                }
                while (GameThread.threadSet.size() != 0) {
                    System.out.println("Waiting for threads quit... now has " + GameThread.threadSet.size());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (screen != null) {
                screen.gameExit();
            }
        }
    }
}
