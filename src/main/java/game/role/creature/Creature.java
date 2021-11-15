package game.role.creature;


import com.pFrame.Position;
import game.controller.CreatureController;
import game.role.Controllable;
import game.role.Thing;

public class Creature extends Thing implements Controllable {

    protected CreatureController controller;

    protected int speed = 5;

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
        if(world==null||world.isLocationReachable(this,Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x)))
            this.setPosition(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
    }

}
