package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.PButton;
import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;
import com.pFrame.pview.*;
import com.pFrame.pwidget.*;

import game.creature.Floor;
import game.world.GameWorld;
import log.Log;

class MyWorldView extends PWorldView{

    public MyWorldView(PWidget parent, Position p, GameWorld world) {
        super(parent, p, world);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    @Override
    public void mouseClicked(MouseEvent arg0, Position pos) {
        System.out.printf("%d %d %s\n",arg0.getX(),arg0.getY(),pos);
        super.mouseClicked(arg0, pos);
    }
    
}

public class CreatureCreator {
    public static void main(String[] args){
        Log.setTerminalOutPut(true);
        Log.setOutPath("log.txt");
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(200, 100));
        pHeadWidget.getLayout().setRCNumStyle(1, 2, "", "1x,40");
        GameWorld world=new GameWorld(200, 200);
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++)
                world.put(new Floor(world),i,j);
        }
        MyWorldView myWorldView=new MyWorldView(pHeadWidget, null, world );
        PLayout pLayout=new PLayout(pHeadWidget,null,6,2);
        pLayout.setRowLayout("1x,1x,1x,1x,1x,2x");
        for(int i=0;i<10;i++){
            PButton pButton=new PButton(pLayout, null);
        }
        myWorldView.update();
    }
    
}
