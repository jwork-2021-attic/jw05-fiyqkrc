package tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

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
        this.focusPixelPosition=Position.getPosition(0, 0);
    }

    public Position getFocusPixelPostion(){
        return this.focusPixelPosition;
    }

    protected void setFocusPixel(Position p){
        if(p.getX()/scale<10&&p.getY()/scale<10){
            this.focusPixel=drawBoard[p.getX()/scale][p.getY()/scale];
            this.focusPixelPosition=Position.getPosition(p.getX()/scale, p.getY()/scale);
        }
    }

    public void setColor(Position p,Color color){
        int r=p.getX();
        int c=p.getY();
        drawBoard[r][c]=Pixel.getPixel(color,(char) 0xf0);
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
                    pixels[i][j]=Pixel.getPixel(this.color,(char) 0xf0);
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
        Log.setLogLevel(Log.Error);
        Log.setTerminalOutPut(true);
        Log.setOutPath("log.txt");
        PHeadWidget pHeadWidget=new PHeadWidget(null, null, new PFrame(600, 400, AsciiFont.pFrame_2x2));
        pHeadWidget.getLayout().setRCNumStyle(1, 1, "", "");
        pHeadWidget.getLayout().setInset(false);

        PGraphicScene scene=new PGraphicScene(500, 1000);
        MyWorldView myWorldView=new MyWorldView(null, null, scene);
        PGraphicItem item=GraphicItemGenerator.generateItem("/home/fiyqkrc/Icon-120.png", 40, 40);
        scene.addItem(item);
        //myWorldView.setFocus(item);

        PImage image=new PImage(null,null,new File("/home/fiyqkrc/output/2-3.png"));

        pHeadWidget.addBackground(image);

        PLayout layout=new PLayout(pHeadWidget, null,5,5,true);

        for(int i=0;i<25;i++){
            PButton pButton=new PButton(layout, null);
            pButton.setText("Hello World,nice to meet you!", 1, Color.RED);
        }

        //myWorldView.update();
        pHeadWidget.startRepaintThread();
    }
    
}
