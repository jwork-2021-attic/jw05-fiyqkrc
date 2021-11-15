package game;

import asciiPanel.AsciiFont;
import com.pFrame.pwidget.PFrame;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PHeadWidget;
import game.role.creature.Operational;
import game.world.World;

import java.util.Objects;

public class ApplicationMain {
    public static void main(String[] args){
        World world=new World(8000, 8000);
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(600, 400, AsciiFont.pFrame_2x2));
        PGraphicView view=new PGraphicView(null, Position.getPosition(1, 2), world);
        pHeadWidget.addBackground(view);
        Operational calabash =new Operational(Objects.requireNonNull(Operational.class.getClassLoader().getResource("image/role/calabash0/")).getFile(), 20, 20);
        calabash.setPosition(world.getStartPosition());
        world.addOperational(calabash);

        /*
        PImage startButtonBack=new PImage(null,null,ApplicationMain.class.getClassLoader().getResource("image/startButton.png").getFile());
        pHeadWidget.getLayout().setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");
        PButton startButton=new PButton(pHeadWidget,Position.getPosition(2,2));
        startButton.addBackground(startButtonBack);
        */




        pHeadWidget.startRepaintThread();
    }
}
