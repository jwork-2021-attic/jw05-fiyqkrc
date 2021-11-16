package game;

public record Location(int x, int y) {

    public boolean isEqualWith(Location location) {
        return (location.y == this.y) && (location.x == this.x);
    }

}
