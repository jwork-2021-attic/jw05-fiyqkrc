package game.creature;

import asciiPanel.AsciiPanel;
import game.world.GameWorld;

public class Wall extends Thing {

    public Wall(GameWorld world) {
        super(AsciiPanel.cyan, (char) 177, world);
    }

}
