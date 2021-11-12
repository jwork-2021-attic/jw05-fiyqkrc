package game.role.creature.calabash;

import game.role.creature.Operational;

import java.io.File;

public class Calabash extends Operational {
    public Calabash(File file, int width, int height) {
        super(file, width, height);
    }

    public Calabash(String path, int width, int height) {
        super(path, width, height);
    }
}
