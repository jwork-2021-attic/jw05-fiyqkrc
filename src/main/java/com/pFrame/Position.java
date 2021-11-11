package com.pFrame;

public class Position {
    private int x;
    private int y;

    private Position(int a, int b) {
        x = a;
        y = b;
        // System.out.println("hello");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEqualWith(Position p) {
        return (p.getX() == this.x) && (p.getY() == this.y);
    }

    @Override
    public String toString() {
        return "{" + String.valueOf(x) + "," + String.valueOf(y) + "}";
    }

    @Override
    public Position clone() {
        return this;
    }

    public static Position getPosition(int x, int y) {
        /*
         * if(positions[x][y]==null){ Position position=new Position(x, y);
         * positions[x][y]=position; return position; } else{ return positions[x][y]; }
         * 
         * return Position.findPosition (x, y);
         */
        return new Position(x, y);
    }
}