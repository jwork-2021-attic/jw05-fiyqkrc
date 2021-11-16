package game.graphic.creature.monster;

import game.controller.AlogrithmController;
import game.controller.CreatureController;
import game.graphic.creature.Creature;

abstract public class Monster extends Creature{
    private CreatureController oldController;

    public Monster(String path, int width, int height) {
        super(path, width, height);
    }

    @Override
    public void pause() {
        oldController=controller;
        controller=null;
    }

    @Override
    public void Continue() {
        controller=oldController;
    }
}
