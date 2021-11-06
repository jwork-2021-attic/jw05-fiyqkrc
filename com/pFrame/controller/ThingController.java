package com.pFrame.controller;

import java.awt.event.KeyEvent;

import com.pFrame.creature.Thing;

public abstract class ThingController {
    protected Thing thing;

    public ThingController(){

    }

    public abstract void respondToUserInput(KeyEvent event);

    public void setThing(Thing thing){
        this.thing=thing;
    }
}
