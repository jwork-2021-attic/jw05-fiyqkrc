package game.graphic.bullet;

import com.pFrame.Pixel;
import com.pFrame.Position;
import game.graphic.creature.monster.Dragon;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class Wind extends Bullet{
    public Wind(Dragon dragon, double angle) {
        super(dragon,angle);
        Pixel[][] pixels = GraphicItemGenerator.generateItem(NormalBullet.class.getClassLoader().getResource("image/shoot/wind.png").getFile(), World.tileSize , World.tileSize ).getPixels();
        this.width = World.tileSize;
        this.height = World.tileSize;
        graphic = pixels;
        speed=60;
        this.p= Position.getPosition(creature.getCentralPosition().getX()-height/2, creature.getCentralPosition().getY()-width/2);
    }
}
