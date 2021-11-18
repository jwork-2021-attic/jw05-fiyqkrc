package game.graphic.creature.monster;

import game.graphic.Direction;
import game.graphic.bullet.Wind;
import game.world.World;

import java.util.Date;

public class Dragon extends Monster {
    protected long codeTime = 3000;
    protected long lastAttack;

    public Dragon() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Dragon/").getPath(), World.tileSize, World.tileSize);
        speed = 2;
    }

    @Override
    public void attack() {
        super.attack();
        if (new Date().getTime() - lastAttack > this.codeTime) {
            try {
                if (aim != null) {
                    direction = Direction.calDirection(getCentralPosition(), aim.getCentralPosition());
                    Wind wind = new Wind(this, direction);
                    world.addItem(wind);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lastAttack = new Date().getTime();
        }
    }
}