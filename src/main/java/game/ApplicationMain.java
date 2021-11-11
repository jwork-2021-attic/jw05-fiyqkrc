package game;

import com.pFrame.PFrame;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PHeadWidget;

import asciiPanel.AsciiFont;
import game.controller.KeyBoardThingController;
import game.role.creature.Operational;
import game.world.World;

import java.util.Objects;

public class ApplicationMain {
    public static void main(String[] args){
        World world=new World(200, 200);
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(300, 200, AsciiFont.pFrame_4x4));
        PGraphicView view=new PGraphicView(null, Position.getPosition(1, 2), world);
        pHeadWidget.addBackground(view);
        Operational calabash =new Operational(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/icons/0-0.png")).getFile(), 10, 10);
        KeyBoardThingController controller=new KeyBoardThingController();
        controller.setThing(calabash);
        calabash.setPosition(world.getStartPosition());
        view.setFocus(calabash);
        view.setItemController(controller);
        world.addItem(calabash);
        pHeadWidget.startRepaintThread();
    }
}
