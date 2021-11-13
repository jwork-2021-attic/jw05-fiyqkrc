package game;

import asciiPanel.AsciiFont;
import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pgraphic.PImage;
import com.pFrame.pwidget.PButton;
import com.pFrame.pwidget.PHeadWidget;
import com.pFrame.pwidget.PLabel;
import game.world.World;
import log.Log;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    public static int START_PAGE=0;
    public static int GAME_PAGE=1;
    public static int SETTING_PAGE=2;

    PHeadWidget ui;

    PGraphicScene world;

    PLayout startPage;
    PButton startGameButton;
    PButton settingButton;

    PLayout gamePage;

    PLayout settingPage;

    public void createUI(){
        this.ui=new PHeadWidget(null,null,new PFrame(300,200, AsciiFont.pFrame_4x4));



        this.startPage=new PLayout(null,null,3,3,false);
        this.startPage.setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");
        PLayout layout=new PLayout(startPage, Position.getPosition(2,2),2,1,true);
        startGameButton=new PButton(layout,null);
        startGameButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/startButton.png").getFile()));
        settingButton=new PButton(layout,null);
        settingButton.addBackground(PImage.getPureImage(Color.GRAY));
        settingButton.setText("Setting",1, Color.BLUE);

        this.gamePage=new PLayout(null,null,3,3,false);
        this.gamePage.setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");

        this.settingPage=new PLayout(null,null);
        PLabel label=new PLabel(null,null);
        this.settingPage.addBackground(label);
        label.setText("Welcome to play this game.Because this game is under programming,this version does not represent the final quality" ,2,Color.ORANGE);

        try {
            startGameButton.setClickFunc(this,this.getClass().getMethod("startGameButtonBeClicked"));
            settingButton.setClickFunc(this,this.getClass().getMethod("settingButtonBeClicked"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setPage(UI.START_PAGE);
        ui.startRepaintThread();
    }

    public void setWorld(PGraphicScene world){
        PGraphicView view=new PGraphicView(null,null,world);
        this.gamePage.addBackground(view);
    }

    public void setPage(int page){
        switch (page){
            case 0 -> ui.resetLayout(startPage);
            case 1 -> ui.resetLayout(gamePage);
            case 2 -> ui.resetLayout(settingPage);
            default -> Log.ErrorLog(this,"try to switch to an undefined page");
        }
    }

    public static  void main(String[] args){
        UI ui=new UI();
        ui.createUI();
        World world=new World(200,200);
        ui.setWorld(world);
    }


    public void startGameButtonBeClicked(){
        this.setPage(UI.GAME_PAGE);
    }

    public void settingButtonBeClicked(){
        this.setPage(UI.SETTING_PAGE);
    }
}


