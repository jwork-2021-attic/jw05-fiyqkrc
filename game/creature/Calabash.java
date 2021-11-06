package game.creature;

import java.awt.Color;

import game.world.GameWorld;

public class Calabash extends Creature implements Comparable<Calabash> {

    private int rank;

    public Calabash(Color color, int rank, GameWorld world) {
        super(color, (char) 0xf0, world);
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    @Override
    public int compareTo(Calabash o) {
        return Integer.valueOf(this.rank).compareTo(Integer.valueOf(o.rank));
    }

    public void swap(Calabash another) {
        int x = this.getX();
        int y = this.getY();
        this.moveTo(another.getX(), another.getY());
        another.moveTo(x, y);
    }
}
