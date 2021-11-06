package com.pFrame;

public class Position {
    private int x;
    private int y;

    public Position(int a, int b) {
        x = a;
        y = b;
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
        return "{"+String.valueOf(x)+","+String.valueOf(y)+"}";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Position(this.x, this.y);
    }
}