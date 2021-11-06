package com.pFrame.creature;

import com.pFrame.world.World;

import asciiPanel.AsciiPanel;

public class Wall extends Thing {

    public Wall(World world) {
        super(AsciiPanel.cyan, (char) 177, world);
    }

}
