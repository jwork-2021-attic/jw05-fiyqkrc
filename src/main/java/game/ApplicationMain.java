package game;

import asciiPanel.AsciiFont;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PFrame;
import com.pFrame.pwidget.PHeadWidget;
import game.graphic.creature.operational.Calabash;
import game.graphic.creature.operational.Operational;
import game.world.World;

import java.util.Objects;

public class ApplicationMain {
    public static void main(String[] args){
        World world=new World(8000, 8000);
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(600, 400, AsciiFont.pFrame_2x2));
        PGraphicView view=new PGraphicView(null, Position.getPosition(1, 2), world);
        pHeadWidget.addBackground(view);
        Calabash calabash =new Calabash(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/role/calabash0/")).getFile(), 20, 20);
        calabash.setPosition(world.getStartPosition());
        world.addOperational(calabash);

        pHeadWidget.startRepaintThread();

        calabash.attack();
    }
}
