package com.pFrame.controller;

import java.awt.event.KeyEvent;

public abstract class AlogrithmThingController extends ThingController{

    public AlogrithmThingController(){
        
    }

    public abstract void respondToUserInput(KeyEvent event);
    
}
