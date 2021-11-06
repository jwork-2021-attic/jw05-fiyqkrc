package com.pFrame.creature;

import java.awt.Color;

import com.pFrame.world.World;

public class Floor extends Thing {

    public Floor(World world) {
        super(Color.gray, (char) 250, world);
    }

}
