package game.world;

import game.role.Thing;

public class Tile<T extends Thing> {

    private T thing;
    private int xPos;
    private int yPos;

    public T getThing() {
        return thing;
    }

    public void setThing(T thing) {
        this.thing = thing;
        this.thing.setTile(this);
    }
}
