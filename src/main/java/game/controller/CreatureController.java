package game.controller;

import game.graphic.Controllable;

abstract public class CreatureController{
    protected Controllable controllable;
    public boolean stop=false;

    public CreatureController(){

    }

    public void setThing(Controllable controllable){
        this.controllable = controllable;
    }
}
