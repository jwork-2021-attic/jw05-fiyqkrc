package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.PFrame;
import com.pFrame.Position;
import com.pFrame.pview.*;
import com.pFrame.pwidget.*;

import game.creature.Floor;
import game.world.GameWorld;

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
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(10, 10));
        GameWorld world=new GameWorld(10, 10);
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++)
                world.put(new Floor(world),i,j);
        }
        MyWorldView myWorldView=new MyWorldView(pHeadWidget, null, world );
    }
    
}
