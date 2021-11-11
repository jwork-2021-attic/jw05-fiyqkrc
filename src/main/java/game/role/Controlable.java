package game.role;

import game.controller.CreatureController;

public interface Controlable {
    public void setController(CreatureController controller);

    public CreatureController getController();

    public void attack();

    public void move(double direction);
}
