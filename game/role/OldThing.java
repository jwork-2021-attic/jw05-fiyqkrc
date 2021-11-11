package game.role;

import java.awt.Color;

import com.pFrame.Position;

import game.controller.CreatureController;
import game.world.GameWorld;
import game.world.Tile;

public class OldThing {

    protected GameWorld world;
    protected CreatureController controller;

    public GameWorld getWorld(){
        return this.world;
    }

    public void setController(CreatureController controller){
        this.controller=controller;
        //this.controller.setThing(this);
    }

    public CreatureController getController(){
        return this.controller;
    }

    public Tile<? extends OldThing> tile;

    public Position getPosition(){
        return Position.getPosition(this.tile.getxPos(), this.tile.getyPos());
    }

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends OldThing> tile) {
        this.tile = tile;
    }

    public OldThing(Color color, char glyph, GameWorld world) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
    }

    private final Color color;

    public Color getColor() {
        return this.color;
    }

    private final char glyph;

    public char getGlyph() {
        return this.glyph;
    }
/*
    public boolean move(Direction d,int distance){
        int x=this.getX();
        int y=this.getY();
        if(d==Direction.Up && world.positionValid(Position.getPosition(this.getX()-distance, this.getY()))){
            this.world.put(this, this.getX()-distance, this.getY());
            this.world.put(new OldFloor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Right && world.positionValid(Position.getPosition(this.getX(), this.getY()+distance))){
            this.world.put(this,this.getX(),this.getY()+distance);
            this.world.put(new OldFloor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Down && world.positionValid(Position.getPosition(this.getX()+distance, this.getY()))){
            this.world.put(this,this.getX()+distance,this.getY());
            this.world.put(new OldFloor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Left && world.positionValid(Position.getPosition(this.getX(), this.getY()-distance))){
            this.world.put(this,this.getX(),this.getY()-distance);
            this.world.put(new OldFloor(this.world),x,y);
            return true;
        }
        else{
            return false;
        }
        
    }
*/
}
