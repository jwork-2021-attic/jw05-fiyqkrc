package game.world;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import game.Attack;
import game.Location;
import game.controller.AlgorithmController;
import game.controller.KeyBoardController;
import game.controller.NetAlController;
import game.controller.NetKeyBoardController;
import game.graphic.Direction;
import game.graphic.StatedSavable;
import game.graphic.Thing;
import game.graphic.creature.Creature;
import game.graphic.creature.monster.Monster;
import game.graphic.creature.operational.Operational;
import game.graphic.env.Wall;
import game.graphic.interactive.GameThread;
import game.screen.UI;
import game.server.client.ClientMain;
import game.server.server.ServerMain;
import log.Log;

import java.awt.*;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class World extends PGraphicScene {
    private final Tile<Thing>[][] tiles;
    private final int tileWidth;
    private final int tileHeight;
    public static int tileSize = 20;

    public static boolean multiPlayerMode = false;
    public static boolean mainClient = false;

    public UI screen;
    public int[][] worldArray;
    private final String path;

    private final HashMap<Integer, Creature> activeCreature = new HashMap<>();

    private final ArrayList<JSONObject>[][] areas;

    private boolean isPause = false;

    int areaWidth;
    int areaHeight;
    int areaSize = 400;

    ArrayList<Operational> operationals;
    int controlRoleId;
    Operational controlRole;
    Thread daemonThread;

    ClientMain clientMain;
    Thread clientThread;
    ServerMain serverMain;


    public World(JSONObject jsonObject) {
        super(jsonObject.getObject("width", Integer.class), jsonObject.getObject("height", Integer.class));

        tileWidth = width / tileSize;
        tileHeight = height / tileSize;

        worldArray = jsonObject.getObject("worldArray", int[][].class);
        path = jsonObject.getObject("path", String.class);
        controlRoleId = jsonObject.getObject("controlRole", Integer.class);

        tiles = new Tile[tileHeight][tileWidth];
        for (int i = 0; i < tileHeight; i++)
            for (int j = 0; j < tileWidth; j++)
                tiles[i][j] = new Tile<>(new Location(i, j));

        areaHeight = this.height / areaSize + 1;
        areaWidth = this.width / areaSize + 1;
        areas = new ArrayList[areaHeight][areaWidth];

        operationals = new ArrayList<>();

        for (int i = 0; i < areaHeight; i++)
            for (int j = 0; j < areaWidth; j++) {
                areas[i][j] = new ArrayList<>();
            }

        if (multiPlayerMode && !mainClient) {

        } else {
            if (multiPlayerMode) {
                serverMain = new ServerMain();
                serverMain.start();
                clientMain = ClientMain.getInstance();
                clientMain.setWorld(this);
                clientMain.connect("127.0.0.1", 9000);
                clientThread = new Thread(clientMain);
                clientThread.start();
            }
            daemonThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.InfoLog(this, "thread for world's monster recycle start...");

                    ArrayList<Area> oldAreas = new ArrayList<>();
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            for (Operational operational : operationals) {
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
                                                areas[area.x][area.y].add(((Creature) thing).saveState());
                                                if (((Creature) thing).getController() instanceof AlgorithmController)
                                                    ((AlgorithmController) ((Creature) thing).getController()).stop();
                                                else if (((Creature) thing).getController() instanceof NetAlController) {
                                                    ((NetAlController) ((Creature) thing).getController()).stop();
                                                }
                                                removeItem(thing);

                                            } else if (thing instanceof GameThread && thing instanceof StatedSavable) {
                                                areas[area.x][area.y].add(((StatedSavable) thing).saveState());
                                                ((GameThread) thing).stop();
                                                removeItem(thing);
                                            }
                                        }
                                    }
                                }
                                for (Area area : curAreas) {
                                    ArrayList<JSONObject> added = new ArrayList<>();
                                    for (JSONObject item : areas[area.x][area.y]) {
                                        synchronized (this) {
                                            Class[] types = null;
                                            Object[] parameters = null;
                                            StatedSavable thing = (StatedSavable) Thing.class.getClassLoader().loadClass(item.getObject("class", String.class)).getDeclaredConstructor(types).newInstance(parameters);
                                            thing.resumeState(item);
                                            if (thing instanceof Thing) {
                                                if (!((Thing) thing).isBeCoverAble() && !isLocationReachable((Thing) thing, ((Thing) thing).getPosition())) {

                                                } else {
                                                    if (thing instanceof Monster) {
                                                        if (multiPlayerMode) {
                                                            ((Monster) thing).setController(new NetAlController());
                                                        } else {
                                                            ((Monster) thing).setController(new AlgorithmController());
                                                        }
                                                    }
                                                    addItem((PGraphicItem) thing);
                                                    added.add(item);
                                                }
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
                        }
                    }
                }
            });
            daemonThread.start();
        }
        loadSavedData(jsonObject);
    }


    public boolean isPause() {
        return isPause;
    }

    public Pixel[][] getWorldMap() {
        Pixel[][] pixels = Pixel.emptyPixels(worldArray.length, worldArray[0].length);
        for (int i = 0; i < worldArray.length; i++)
            for (int j = 0; j < worldArray[0].length; j++) {
                Color color = (worldArray[i][j] == 0) ? Color.GRAY : Color.BLUE;
                pixels[i][j] = Pixel.getPixel(color, (char) 0xf0);
            }
        assert pixels != null;
        pixels[controlRole.getLocation().x()][controlRole.getLocation().y()] = Pixel.getPixel(Color.RED, (char) 0xf0);
        return pixels;
    }

    public static int getTileSize() {
        return World.tileSize;
    }

    @Override
    public void setParentView(PGraphicView view) {
        super.setParentView(view);
    }

    public void setControlRole(Operational operational) {
        controlRole = operational;
    }

    public Operational getControlRole() {
        return controlRole;
    }

    public void activeControlRole() {
        if (controlRole != null) {
            if (multiPlayerMode) {
                controlRole.setController(new NetKeyBoardController());
            } else {
                controlRole.setController(new KeyBoardController());
            }
        }
        if (this.parentView != null) {
            parentView.setFocus(controlRole);
        } else {
            Log.ErrorLog(this, "please put world on a view first");
        }
        if (screen != null)
            screen.displayHealth(controlRole.getHealth(), controlRole.getHealthLimit());
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
        if (item instanceof Creature) {
            synchronized (activeCreature) {
                activeCreature.remove(item.getId());
            }
        }
        return super.removeItem(item);
    }

    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing) {
            ((Thing) item).setWorld(this);
            ((Thing) item).whenBeAddedToScene();

            if (item instanceof Creature) {
                synchronized (activeCreature) {
                    activeCreature.put(item.getId(), (Creature) item);
                }
            }

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

    public void frameSync(JSONArray jsonArray) {
        for (Object jsonObject : jsonArray) {
            try {
                JSONObject command = (JSONObject) jsonObject;
                String action = command.getObject("action", String.class);
                int id = command.getObject("id", Integer.class);
                if (activeCreature.containsKey(id)) {
                    Creature creature = activeCreature.get(id);
                    if (Objects.equals(action, "move")) {
                        double direction = command.getObject("direction", Double.class);
                        creature.move(direction);
                    } else if (Objects.equals(action, "attack")) {
                        creature.responseToEnemy();
                    } else if (Objects.equals(action, "dead")) {
                        creature.dead();
                    }
                } else {
                    Log.ErrorLog(this, "a id named" + id + " creature can not be found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addOperational(Operational operational) {
        addItem(operational);
        this.operationals.add(operational);
        if (operational.getId() == controlRoleId) {
            controlRole = operational;
        }
    }

    public ArrayList<Operational> getOperational() {
        return this.operationals;
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
        gamePause();
        new Thread(() -> {
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
                while (GameThread.threadSet.size() != 0 && !Thread.currentThread().isInterrupted()) {
                    System.out.println("Waiting for threads quit... now has " + GameThread.threadSet.size());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (screen != null) {
                screen.gameExit();
            }
        }).start();
    }

    public void gameSaveData() {
        gamePause();
        try {
            JSONObject data = new JSONObject();
            JSONArray itemData = new JSONArray();
            for (PGraphicItem item : Items) {
                if (item instanceof StatedSavable) {
                    itemData.add(((StatedSavable) item).saveState());
                }
            }
            for (ArrayList<JSONObject>[] list : areas) {
                for (ArrayList<JSONObject> list1 : list) {
                    itemData.addAll(list1);
                }
            }
            data.put("itemsData", itemData);
            data.put("idCount", PGraphicItem.getIdCount());
            data.put("width", width);
            data.put("height", height);
            data.put("worldArray", worldArray);
            data.put("path", path);
            data.put("controlRole", controlRole.getId());

            FileOutputStream stream = new FileOutputStream(path);
            stream.write(data.toJSONString().getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameContinue();
    }

    public void loadSavedData(JSONObject jsonObject) {
        JSONArray itemsData = jsonObject.getObject("itemsData", JSONArray.class);
        PGraphicItem.setIdCount(jsonObject.getObject("idCount", Integer.class));
        for (Object item : itemsData) {
            StatedSavable thing;
            try {
                thing = (StatedSavable) Thing.class.getClassLoader().loadClass(((JSONObject) item).getObject("class", String.class)).getDeclaredConstructor(null).newInstance(null);
                thing.resumeState((JSONObject) item);
                if (thing instanceof Thing) {
                    if (thing instanceof Creature || thing instanceof GameThread)
                        if (thing instanceof Operational) {
                            addOperational((Operational) thing);
                        } else
                            areas[((Thing) thing).getPosition().getX() / areaSize][((Thing) thing).getPosition().getY() / areaSize].add((JSONObject) item);
                    else
                        addItem((PGraphicItem) thing);
                }
            } catch
            (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException
                            e) {
                e.printStackTrace();
            }
        }
    }

    public void gamePause() {
        if (!isPause) {
            isPause = true;
            for (Creature creature : activeCreature.values()) {
                creature.pause();
            }
            if (controlRole != null) {
                controlRole.pause();
            }
            Log.InfoLog(this, "Game pause...");
        }
    }

    public void gameContinue() {
        if (isPause) {
            isPause = false;
            for (Creature creature : activeCreature.values()) {
                creature.Continue();
            }
            if (controlRole != null) {
                controlRole.Continue();
            }
            Log.InfoLog(this, "Game continue...");
        }
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
                            if (tiles[a][b].getThing() instanceof Creature && ((Creature) tiles[a][b].getThing()).getGroup() != creature.getGroup()) {
                                try {
                                    if (!hasWallBetweenPositions(creature.getCentralPosition(), tiles[a][b].getThing().getCentralPosition())) {
                                        return new Location(a, b);
                                    }
                                } catch (Exception ignored) {

                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean hasWallBetweenPositions(Position src, Position dest) {
        double distance = Position.distance(dest, src);
        double direction = Direction.calDirection(src, dest);
        boolean has = false;
        for (int k = 0; k < distance; k++) {
            Location location = getTileByLocation(Position.getPosition(src.getX() - (int) (k * Math.sin(direction)), src.getY() + (int) (k * Math.cos(direction))));
            if (findThing(location) instanceof Wall) {
                has = true;
                break;
            }
        }
        return has;
    }

    record Area(int x, int y) {
    }
}
