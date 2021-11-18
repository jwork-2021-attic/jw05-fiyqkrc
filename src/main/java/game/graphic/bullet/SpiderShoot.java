package game.graphic.bullet;


import com.pFrame.Pixel;
import com.pFrame.Position;
import game.graphic.creature.monster.Spider;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

public class SpiderShoot extends Bullet {
    public SpiderShoot(Spider spider, double angle) {
        super(spider,angle);
        Pixel[][] pixels = GraphicItemGenerator.generateItem(NormalBullet.class.getClassLoader().getResource("image/shoot/spider.png").getFile(), World.tileSize / 4, World.tileSize / 4).getPixels();
        this.width = World.tileSize/4;
        this.height = World.tileSize/4;
        graphic = pixels;
        this.speed = 200;
        this.p= Position.getPosition(creature.getCentralPosition().getX()-height/2, creature.getCentralPosition().getY()-width/2);
    }
}
