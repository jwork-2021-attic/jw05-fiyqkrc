package game.role;

import asciiPanel.AsciiPanel;
import game.world.GameWorld;

public class OldWall extends OldThing {

    public OldWall(GameWorld world) {
        super(AsciiPanel.cyan, (char) 0xf0, world);
    }

}
