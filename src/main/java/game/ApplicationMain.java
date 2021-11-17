package game;

import asciiPanel.AsciiFont;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PFrame;
import com.pFrame.pwidget.PHeadWidget;
import game.controller.KeyBoardThingController;
import game.graphic.creature.operational.Calabash;
import game.screen.UI;
import game.world.World;

public class ApplicationMain {
    public static  void main(String[] args){
        UI ui=new UI();
        ui.createUI();

    }
}
