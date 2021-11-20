package game.graphic.creature;

import game.controller.CreatureController;

public interface Controllable {
    void setController(CreatureController controller);

    CreatureController getController();

    void attack();

    boolean move(double direction);

    void speak(String text);

    void dead();

    boolean isDead();

    boolean searchAim();
}
