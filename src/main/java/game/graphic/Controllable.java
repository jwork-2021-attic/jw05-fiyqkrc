package game.graphic;

import game.controller.CreatureController;

public interface Controllable {
    void setController(CreatureController controller);

    CreatureController getController();

    void attack();

    void move(double direction);

    void speak(String text);
}
