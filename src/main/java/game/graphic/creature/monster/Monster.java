package game.graphic.creature.monster;

import game.Location;
import game.controller.AlogrithmController;
import game.controller.CreatureController;
import game.graphic.creature.Creature;
import game.graphic.effect.Dialog;

import java.lang.reflect.InvocationTargetException;

abstract public class Monster extends Creature {
    private CreatureController oldController;
    protected Creature aim;

    public Monster(String path, int width, int height) {
        super(path, width, height);
        group = 1;
        speed=2;
    }

    @Override
    public void pause() {
        oldController = controller;
        if(controller instanceof AlogrithmController)
            ((AlogrithmController) controller).stop();
    }

    @Override
    public void Continue() {
        try {
            controller =(CreatureController) oldController.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean searchAim() {
        try {
            if (aim == null) {
                Location location = world.searchNearestEnemy(this, 7);
                if (location != null && world.findThing(location) instanceof Creature) {
                    aim = (Creature) world.findThing(location);
                    Dialog dialog=new Dialog("Ya!!!",this.getPosition());
                    world.addItem(dialog);
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

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        controller=new AlogrithmController();
        controller.setThing(this);
    }
}
