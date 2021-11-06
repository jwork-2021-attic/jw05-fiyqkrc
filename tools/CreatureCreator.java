package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.PFrame;
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
        Log.setTerminalOutPut(false);
        Log.setOutPath("log.txt");
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(20, 20));
        GameWorld world=new GameWorld(20, 20);
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++)
                world.put(new Floor(world),i,j);
        }
        MyWorldView myWorldView=new MyWorldView(pHeadWidget, null, world );
        myWorldView.update();
    }
    
}
