package game.controller;

import java.awt.event.KeyEvent;

import game.creature.Direction;
import game.creature.Thing;

public class KeyBoardThingController extends ThingController{

    @Override
    public void respondToUserInput(KeyEvent key) {
        Thing aim=this.thing;
        switch(key.getKeyChar()){
            case 'w':aim.move(Direction.Up, 1);break;
            case 'a':aim.move(Direction.Left, 1);break;
            case 'd':aim.move(Direction.Right,1);break;
            case 's':aim.move(Direction.Down, 1);break;
            default: System.out.println("Undefined keycode:"+String.valueOf(key.getKeyChar()));break;
        }
    }

    public KeyBoardThingController(){
        super();
    }
    
}
