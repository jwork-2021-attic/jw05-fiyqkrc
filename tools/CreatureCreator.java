package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.*;
import com.pFrame.pwidget.*;

import asciiPanel.AsciiFont;

import java.awt.Color;

import imageTransFormer.GraphicItemGenerator;
import log.Log;

class MyWorldView extends PGraphicView{
    private Pixel[][] drawBoard;
    private Pixel focusPixel;
    private Position focusPixelPosition;
    private int scale=1;

    public MyWorldView(PWidget parent, Position p, PGraphicScene world) {
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
                //this.world.put(new Thing(color, (char)0xf0, this.world), r*scale+i, c*scale+j);
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
    Color color;

    public MyColorSelectButton(PWidget parent, Position p,MyWorldView worldView,Color color) {
        super(parent, p);
        this.worldView=worldView;
        this.color=color;
    }
    

    @Override
    public void mouseClicked(MouseEvent e, Position p) {
        super.mouseClicked(e, p);
        this.worldView.setColor(this.worldView.getFocusPixelPostion(), this.color);
    }

    @Override
    public Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            Pixel[][] pixels = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];
            for(int i=0;i<this.getWidgetHeight();i++){
                for(int j=0;j<this.getWidgetWidth();j++){
                    pixels[i][j]=new Pixel(this.color,(char) 0xf0);
                }
            }
            return pixels;
        }
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
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(60, 40, AsciiFont.pFrame_8x8));
        pHeadWidget.getLayout().setRCNumStyle(1, 2, "", "2x,1x");
        pHeadWidget.getLayout().setInset(false);

        PGraphicScene scene=new PGraphicScene(100, 100);
        MyWorldView myWorldView=new MyWorldView(pHeadWidget, null, scene);
        PGraphicItem item=GraphicItemGenerator.generateItem("/home/fiyqkrc/Icon-120.png", 10, 10);
        scene.addItem(item);
        myWorldView.setFocus(item);

        PLayout pLayout=new PLayout(pHeadWidget,null,3,1,false);
        pLayout.setRowLayout("3x,2x,2x");

        PLayout pixelContainer=new PLayout(pLayout, null,4,4);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                PButton button=new PButton(pixelContainer, null);
            }
        }

        PLayout colorContainer=new PLayout(pLayout, null,3,4,false);
        MyColorSelectButton pButton=new MyColorSelectButton(colorContainer, null,myWorldView,Color.CYAN);
        MyColorSelectButton pButton1=new MyColorSelectButton(colorContainer, null,myWorldView,Color.BLUE);
        MyColorSelectButton pButton2=new MyColorSelectButton(colorContainer, null,myWorldView,Color.WHITE);
        MyColorSelectButton pButton3=new MyColorSelectButton(colorContainer, null,myWorldView,Color.DARK_GRAY);
        MyColorSelectButton pButton4=new MyColorSelectButton(colorContainer, null,myWorldView,Color.YELLOW);
        MyColorSelectButton pButton5=new MyColorSelectButton(colorContainer, null,myWorldView,Color.GREEN);
        MyColorSelectButton pButton6=new MyColorSelectButton(colorContainer, null,myWorldView,Color.RED);
        MyColorSelectButton pButton7=new MyColorSelectButton(colorContainer, null,myWorldView,Color.MAGENTA);
        MyColorSelectButton pButton8=new MyColorSelectButton(colorContainer, null,myWorldView,Color.ORANGE);
        MyColorSelectButton pButton9=new MyColorSelectButton(colorContainer, null,myWorldView,Color.PINK);

        OutPutButton outPutButton=new OutPutButton(pLayout, null);
        myWorldView.update();
    }
    
}
