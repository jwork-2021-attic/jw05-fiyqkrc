package game;

import asciiPanel.AsciiFont;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PFrame;
import com.pFrame.pwidget.PHeadWidget;
import game.graphic.creature.operational.Calabash;
import game.world.World;

public class ApplicationMain {
    public static void main(String[] args){
        World world=new World(800, 800);
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(600, 400, AsciiFont.pFrame_2x2));
        PGraphicView view=new PGraphicView(null, Position.getPosition(1, 2), world);
        pHeadWidget.addBackground(view);
        Calabash calabash =new Calabash();
        calabash.setPosition(world.getStartPosition());
        world.addOperational(calabash);

        pHeadWidget.startRepaintThread();

        calabash.attack();
    }
}
