package game.graphic.creature;

import game.controller.CreatureController;

public interface Controllable {
    void setController(CreatureController controller);

    CreatureController getController();

    void responseToEnemy();

    boolean move(double direction);

    int getId();

    void speak(String text);

    void dead();

    boolean isDead();

    Creature searchAim();
}
