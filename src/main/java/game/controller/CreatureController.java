package game.controller;

import game.graphic.Controllable;

public class CreatureController{
    protected Controllable controllable;

    public CreatureController(){

    }

    public void setThing(Controllable controllable){
        this.controllable = controllable;
    }
}
