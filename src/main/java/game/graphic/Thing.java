package game.graphic;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import game.Location;
import game.world.Tile;
import game.world.World;

import java.io.File;

public class Thing extends PGraphicItem{
    protected Tile<? extends Thing> tile;
    protected boolean beCoverAble;
    protected World world;

    public World getWorld()
    {
        return this.world;
    }

    public void setWorld(World world){
        this.world=world;
    }

    public Position getCentralPosition(){
        if(this.width!=0&&this.height!=0){
            return Position.getPosition(p.getX()+height/2,p.getY()+width/2);
        }
        else
            return p;
    }

    public Location getLocation(){
        if(this.getTile()!=null){
            return this.tile.getLocation();
        }
        else
            return new Location((p.getX()+height/2)/height,(p.getY()+width/2)/width);
    }

    public boolean isBeCoverAble(){
        return beCoverAble;
    }

    public void setBeCoverAble(boolean coverAble){
        beCoverAble=coverAble;
    }

    public Thing(File file, int width, int height) {
        super(file, width, height);
        beCoverAble=true;
    }
    
    public Thing(String path,int width,int height){
        super(path, width, height);
        beCoverAble=true;
    }

    public Thing(Pixel[][] pixels){
        super(pixels);
        beCoverAble=true;
    }

    public void setTile(Tile<? extends Thing> tile){
        this.tile=tile;
    }

    public Tile<? extends Thing> getTile(){
        return this.tile;
    }

    public void whenBeAddedToScene(){

    }
}
