package game.creature;

import java.awt.Color;

import game.world.GameWorld;

public class Floor extends Thing {

    public Floor(GameWorld world) {
        super(Color.BLACK, (char) 0 , world);
    }

}
