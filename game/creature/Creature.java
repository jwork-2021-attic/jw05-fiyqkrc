package game.creature;

import java.awt.Color;

import game.world.GameWorld;

public class Creature extends Thing {

    Creature(Color color, char glyph, GameWorld world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    

}
