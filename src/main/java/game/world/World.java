package game.world;

import java.io.File;
import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.Random;

import com.pFrame.pwidget.ObjectUserInteractive;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import game.Attack;
import game.Location;
import game.controller.AlogrithmController;
import game.graphic.Thing;
import game.graphic.creature.Creature;
import game.graphic.creature.monster.Monster;
import game.graphic.creature.monster.Pangolin;
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
                tiles[i][j]=new Tile<Thing>(new Location(i,j));

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
                        if(random.nextInt(10)>6){
                            Monster monster=new Pangolin();
                            monster.setPosition(Position.getPosition(i*tileSize,j*tileSize));
                            addItem(monster);
                            AlogrithmController alogrithmController=new AlogrithmController(monster);
                        }
                    }
                    default -> {
                    }
                }
            }
        }
    }

    @Override
    public synchronized boolean removeItem(PGraphicItem item) {
        if(item instanceof Thing){
            if(!((Thing) item).isBeCoverAble()){
                if(((Thing) item).getTile()!=null)
                    ((Thing) item).getTile().setThing(null);
            }
        }
        return super.removeItem(item);
    }

    @Override
    public boolean addItem(PGraphicItem item) {
        if (item instanceof Thing) {
            ((Thing) item).whenBeAddedToScene();
            ((Thing) item).setWorld(this);

            if(((Thing) item).isBeCoverAble() || isLocationReachable((Thing)item, item.getPosition())){
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

    public boolean isLocationReachable(Thing thing,Position position){
        position=Position.getPosition(position.getX()+thing.getHeight()/2,position.getY()+thing.getWidth()/2);
        return position.getX() >= 0 && position.getX() < height && position.getY() >= 0 && position.getY() < width && (tiles[position.getX() / tileSize][position.getY() / tileSize].getThing() == null
                || tiles[position.getX() / tileSize][position.getY() / tileSize].getThing()==thing);
    }


    public boolean ThingMove(Thing thing,Position position){
        if(thing.isBeCoverAble()) {
            if(!positionOutOfBound(position))
            {
                thing.setPosition(position);
                return true;
            }
            else
                return false;
        }
        else if(isLocationReachable(thing,position)){
            if (thing.getTile().getLocation() != getTileByLocation(position)) {
                thing.getTile().setThing(null);
                tiles[getTileByLocation(position).x()][getTileByLocation(position).y()].setThing(thing);
            }
            thing.setPosition(position);
            return true;
        }
        else{
            //Log.WarningLog(thing,"Move to position "+position+" failed");
            return false;
        }
    }

    protected Location calTile(Thing thing){
        return getTileByLocation(thing.getPosition());
    }

    public Location getTileByLocation(Position position){
        return new Location((position.getX()+tileSize/2)/tileSize,(position.getY()+tileSize/2)/tileSize);
    }

    public Thing findThing(Location location){
        if(!locationOutOfBound(location))
            return tiles[location.x()][location.y()].getThing();
        else
            return null;
    }

    public boolean locationOutOfBound(Location location){
        return location.y()<0||location.x()<0||location.x()>=tileHeight||location.y()>=tileWidth;
    }

    public boolean positionOutOfBound(Position position){
        return position.getY()<0|| position.getX()<0||position.getX()>=height||position.getY()>=width;
    }

    public void handleAttack(Attack attack){
        for(Location location:attack.affectedTiles){
            if(!locationOutOfBound(location)) {
                Thing thing = tiles[location.x()][location.y()].getThing();
                if (thing != null) {
                    if(thing instanceof Creature && ((Creature) thing).getGroup()!=attack.group){
                        ((Creature)thing).deHealth(attack.attackNumber);
                    }
                }
            }
        }
    }

    public Location searchNearestEnemy(Creature creature,int bound){
        int x,y;
        if(creature.getTile()==null){
           return null;
        }
        else
        {
            x=creature.getTile().getLocation().x();
            y=creature.getTile().getLocation().y();
        }
        for(int i=1;i<bound;i++){
            for(int a=x-i;a<=x+i;a++){
                for(int b=y-i;b<=y+i;b++){
                    if(!locationOutOfBound(new Location(a,b)) && ((a==x-i)||(a==x+i)||(b==y-i)||(b==y+i))&& tiles[a][b].getThing()!=null){
                        if(tiles[a][b].getThing() instanceof Creature && ((Creature) tiles[a][b].getThing()).getGroup()!=creature.getGroup())
                            return new Location(a,b);
                    }
                }
            }
        }
        return null;
    }
}
