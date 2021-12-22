package game;

import game.screen.UI;

import java.util.Objects;

public class ApplicationMain {
    public static void main(String[] args) {
        Config.loadConfig("config.json");
        UI ui = new UI();
        ui.createUI();
    }
}
