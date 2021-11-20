package game.graphic.bullet;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.graphic.creature.operational.Calabash;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;


public class NormalBullet extends Bullet {
    public static Pixel[][] image;

    static {
        image = GraphicItemGenerator.generateItem(NormalBullet.class.getClassLoader().getResource("image/shoot/shoot.png").getFile(), World.tileSize / 4, World.tileSize / 4).getPixels();
    }

    public NormalBullet(Calabash calabash, double angle) {
        super(calabash, angle);
        Pixel[][] pixels = image;
        this.width = World.tileSize / 4;
        this.height = World.tileSize / 4;
        graphic = pixels;
        this.speed = 120;
        this.p = Position.getPosition(parent.getCentralPosition().getX() - height / 2, parent.getCentralPosition().getY() - width / 2);
    }
}
