package game.role.creature;


import com.pFrame.Position;
import game.controller.CreatureController;
import game.role.Controlable;
import game.role.Thing;

import java.io.File;

public class Creature extends Thing implements Controlable {

    protected CreatureController controller;

    protected int speed = 5;

    public Creature(File file, int width, int height) {
        super(file, width, height);
        controller=new CreatureController();
    }

    public Creature(String path, int width, int height) {
        super(path, width, height);
        controller=new CreatureController();
    }

    @Override
    public void setController(CreatureController controller) {
        this.controller = controller;
        controller.setThing(this);
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
