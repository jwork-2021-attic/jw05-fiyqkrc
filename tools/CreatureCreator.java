package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.PButton;
import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pview.*;
import com.pFrame.pwidget.*;
import java.awt.Color;

import game.creature.Floor;
import game.creature.Thing;
import game.world.GameWorld;
import log.Log;

class MyWorldView extends PWorldView{
    private Pixel[][] drawBoard;
    private Pixel focusPixel;
    private Position focusPixelPosition;
    private int scale=3;

    public MyWorldView(PWidget parent, Position p, GameWorld world) {
        super(parent, p, world);
        this.drawBoard=new Pixel[10][10];
        this.focusPixel=drawBoard[0][0];
        this.focusPixelPosition=new Position(0, 0);
    }

    public Position getFocusPixelPostion(){
        return this.focusPixelPosition;
    }

    protected void setFocusPixel(Position p){
        if(p.getX()/scale<10&&p.getY()/scale<10){
            this.focusPixel=drawBoard[p.getX()/scale][p.getY()/scale];
            this.focusPixelPosition=new Position(p.getX()/scale, p.getY()/scale);
        }
    }

    public void setColor(Position p,Color color){
        int r=p.getX();
        int c=p.getY();
        drawBoard[r][c]=new Pixel(color,(char) 0xf0);
        for(int i=0;i<this.scale;i++){
            for(int j=0;j<this.scale;j++){
                this.world.put(new Thing(color, (char)0xf0, this.world), r*scale+i, c*scale+j);
            }
        }
        this.update();
    }

    public void outputImage(String path){

    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

    }

    @Override
    public void mouseClicked(MouseEvent arg0, Position pos) {
        System.out.printf("%d %d %s\n",arg0.getX(),arg0.getY(),pos);
        super.mouseClicked(arg0, pos);
        this.setFocusPixel(pos);
    }
    
}

class MyColorSelectButton extends PButton{
    MyWorldView worldView;

    public MyColorSelectButton(PWidget parent, Position p,MyWorldView worldView) {
        super(parent, p);
        this.worldView=worldView;
    }
    

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        super.mouseClicked(e, p);
        this.worldView.setColor(this.worldView.getFocusPixelPostion(), Color.ORANGE);
    }
}

class OutPutButton extends PButton{

    public OutPutButton(PWidget parent, Position p) {
        super(parent, p);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        // TODO Auto-generated method stub
        super.mouseClicked(e, p);
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
            MyColorSelectButton pButton=new MyColorSelectButton(pLayout, null,myWorldView);
        }
        OutPutButton outPutButton=new OutPutButton(pLayout, null);
        myWorldView.update();
    }
    
}
