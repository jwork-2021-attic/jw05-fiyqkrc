package game;

import com.pFrame.PFrame;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PHeadWidget;

import asciiPanel.AsciiFont;
import game.world.World;

public class ApplicationMain {
    public static void main(String[] args){
        World world=new World(800, 800);
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(300, 200, AsciiFont.pFrame_4x4));
        PGraphicView view=new PGraphicView(null, null, world);
        pHeadWidget.addBackground(view);
        pHeadWidget.startRepaintThread();
    }
}
