package game.graphic;

import game.controller.CreatureController;

public interface Controllable {
    public void setController(CreatureController controller);

    public CreatureController getController();

    public void attack();

    public void move(double direction);

    public void speak(String text);
}
