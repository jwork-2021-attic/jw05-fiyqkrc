package game.graphic.bullet;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.graphic.creature.operational.Calabash;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class NormalBullet extends Bullet {
    public NormalBullet(Calabash calabash, double angle) {
        super(calabash,angle);
        Pixel[][] pixels = GraphicItemGenerator.generateItem(NormalBullet.class.getClassLoader().getResource("image/shoot/shoot.png").getFile(), World.tileSize / 4, World.tileSize / 4).getPixels();
        this.width = World.tileSize / 4;
        this.height = World.tileSize / 4;
        graphic = pixels;
        this.speed = 120;
        this.p= Position.getPosition(creature.getCentralPosition().getX()-height/2, creature.getCentralPosition().getY()-width/2);
    }
}
