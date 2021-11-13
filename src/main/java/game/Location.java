package game;

public class Location {

    private final int x;
    private final int y;

    public Location(int x,int y){
        this.x=x;
        this.y=y;
    }

    public boolean isEqualWith(Location location){
        return (location.y==this.y)&&(location.x==this.x);
    }

}
