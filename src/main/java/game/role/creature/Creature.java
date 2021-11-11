package game.role.creature;

import java.io.File;

import com.pFrame.Pixel;
import com.pFrame.Position;

import game.controller.CreatureController;
import game.role.Controlable;
import game.role.Thing;

public class Creature extends Thing implements Controlable {

    protected CreatureController controller;

    protected int speed = 10;

    public Creature(File file, int width, int height) {
        super(file, width, height);
    }

    public Creature(String path, int width, int height) {
        super(path, width, height);
    }

    @Override
    public void setController(CreatureController controller) {
        this.controller = controller;

    }

    @Override
    public CreatureController getController() {
        return this.controller;
    }

    @Override
    public void attack() {

    }

    @Override
    public void move(double direction) {
        double y = Math.sin(direction) * speed;
        double x = Math.cos(direction) * speed;
        this.setPosition(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
    }

    

}
