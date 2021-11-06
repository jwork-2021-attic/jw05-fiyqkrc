package com.pFrame.creature;

import java.awt.Color;

import com.pFrame.controller.ThingController;
import com.pFrame.widget.Position;
import com.pFrame.world.Tile;
import com.pFrame.world.World;

public class Thing {

    protected World world;
    protected ThingController controller;

    public World getWorld(){
        return this.world;
    }

    public void setController(ThingController controller){
        this.controller=controller;
        this.controller.setThing(this);
    }

    public ThingController getController(){
        return this.controller;
    }

    public Tile<? extends Thing> tile;

    public Position getPosition(){
        return new Position(this.tile.getxPos(), this.tile.getyPos());
    }

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    public Thing(Color color, char glyph, World world) {
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

    public boolean move(Direction d,int distance){
        int x=this.getX();
        int y=this.getY();
        if(d==Direction.Up && world.positionValid(new Position(this.getX()-distance, this.getY()))){
            this.world.put(this, this.getX()-distance, this.getY());
            this.world.put(new Floor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Right && world.positionValid(new Position(this.getX(), this.getY()+distance))){
            this.world.put(this,this.getX(),this.getY()+distance);
            this.world.put(new Floor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Down && world.positionValid(new Position(this.getX()+distance, this.getY()))){
            this.world.put(this,this.getX()+distance,this.getY());
            this.world.put(new Floor(this.world),x,y);
            return true;
        }
        else if(d==Direction.Left && world.positionValid(new Position(this.getX(), this.getY()-distance))){
            this.world.put(this,this.getX(),this.getY()-distance);
            this.world.put(new Floor(this.world),x,y);
            return true;
        }
        else{
            return false;
        }
        
    }

}
