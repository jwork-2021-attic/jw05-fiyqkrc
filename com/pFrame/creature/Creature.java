package com.pFrame.creature;

import java.awt.Color;

import com.pFrame.world.World;

public class Creature extends Thing {

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    

}
