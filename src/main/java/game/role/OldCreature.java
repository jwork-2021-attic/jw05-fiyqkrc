package game.role;

import java.awt.Color;

import game.world.GameWorld;

public class OldCreature extends OldThing {

    OldCreature(Color color, char glyph, GameWorld world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    

}
