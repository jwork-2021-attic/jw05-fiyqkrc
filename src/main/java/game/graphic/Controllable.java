package game.graphic;

import com.pFrame.pwidget.ObjectUserInteractive;
import game.controller.CreatureController;

import java.util.ArrayList;

public interface Controllable {
    void setController(CreatureController controller);

    CreatureController getController();

    void attack();

    boolean move(double direction);

    void speak(String text);

    void dead();

    boolean isDead();

    boolean searchAim();

    public ArrayList<ObjectUserInteractive> getAroundInteractive();
}
