package game.controller;

import game.role.Controlable;

public class CreatureController{
    protected Controlable controlable;

    public CreatureController(){

    }

    public void setThing(Controlable controlable){
        this.controlable=controlable;
    }
}
