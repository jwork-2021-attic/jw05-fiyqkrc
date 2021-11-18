package game.graphic.creature.monster;

import game.graphic.Direction;
import game.graphic.bullet.SpiderShoot;
import game.world.World;

import java.util.Date;

public class Spider extends Monster {
    protected long coldTime = 300;
    protected long lastAttack;

    public Spider() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Spider/").getPath(), World.tileSize, World.tileSize);
    }

    @Override
    public void attack() {
        super.attack();
        if (new Date().getTime() - lastAttack > coldTime) {
            try {
                if (aim != null) {
                    direction = Direction.calDirection(getCentralPosition(), aim.getCentralPosition());
                    SpiderShoot shoot = new SpiderShoot(this, direction);
                    world.addItem(shoot);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lastAttack = new Date().getTime();
        }
    }
}

