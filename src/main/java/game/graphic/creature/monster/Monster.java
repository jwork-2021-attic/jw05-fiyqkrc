package game.graphic.creature.monster;

import game.Location;
import game.controller.AlogrithmController;
import game.controller.CreatureController;
import game.graphic.creature.Creature;

abstract public class Monster extends Creature {
    private CreatureController oldController;
    private Creature aim;


    public Monster(String path, int width, int height) {
        super(path, width, height);
        group = 1;
        speed=2;
    }

    @Override
    public void pause() {
        oldController = controller;
        controller = null;
    }

    @Override
    public void Continue() {
        controller = oldController;
    }

    @Override
    public boolean searchAim() {
        try {
            if (aim == null) {
                Location location = world.searchNearestEnemy(this, 7);
                if (location != null && world.findThing(location) instanceof Creature) {
                    aim = (Creature) world.findThing(location);
                    return true;
                } else
                    return false;
            } else {
                if (Math.abs(aim.getLocation().y() - this.getLocation().y()) < 7 && Math.abs(aim.getLocation().x() - getLocation().x()) < 7) {
                    return true;
                } else {
                    aim = null;
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            aim = null;
            return false;
        }
    }
}
