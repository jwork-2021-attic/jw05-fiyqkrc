package game;

import asciiPanel.AsciiFont;
import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;
import com.pFrame.pgraphic.PImage;
import com.pFrame.pwidget.PButton;
import com.pFrame.pwidget.PHeadWidget;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    public static int START_PAGE=0;
    public static int GAME_PAGE=1;
    public static int SETTING_PAGE=2;

    PHeadWidget ui;

    PLayout startPage;
    PButton startGameButton;
    PButton settingButton;

    PLayout gamePage;

    PLayout settingPage;

    public void createUI(){
        this.ui=new PHeadWidget(null,null,new PFrame(300,200, AsciiFont.pFrame_4x4));



        this.startPage=new PLayout(null,null,3,3,false);
        this.startPage.setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");
        PLayout layout=new PLayout(startPage, Position.getPosition(2,2),2,1,false);
        startGameButton=new PButton(layout,null);
        startGameButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/startButton.png").getFile()));
        settingButton=new PButton(layout,null);
        settingButton.setText("Setting",1, Color.BLUE);

        try {
            settingButton.setClickFunc(this,this.getClass().getMethod("setWorld"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        ui.resetLayout(startPage);
        ui.startRepaintThread();
    }

    public void setWorld(){
        System.out.println("test success");
    }

    public void setPage(int page){

    }

    public static  void main(String[] args){
        UI ui=new UI();
        ui.createUI();
    }
}


