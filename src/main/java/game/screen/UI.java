package game.screen;

import asciiPanel.AsciiFont;
import com.pFrame.pwidget.PFrame;
import com.pFrame.pwidget.PLayout;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.PImage;
import com.pFrame.pwidget.PButton;
import com.pFrame.pwidget.PHeadWidget;
import com.pFrame.pwidget.PLabel;
import com.pFrame.pwidget.PWidget;
import game.graphic.creature.operational.Calabash;
import game.world.World;
import log.Log;

import java.awt.*;

public class UI {
    public static int START_PAGE=0;
    public static int GAME_PAGE=1;
    public static int SETTING_PAGE=2;

    public PHeadWidget ui;

    public PGraphicScene world;

    public PLayout startPage;
    public PButton startGameButton;
    public PButton settingButton;

    public PLabel coinValueLabel;
    public PButton MapButton;
    public MessageLabel messageLabel;


    public PLayout gamePage;

    public PLayout settingPage;

    public void createUI(){
        this.ui=new PHeadWidget(null,null,new PFrame(600,400, AsciiFont.pFrame_2x2));

        this.startPage=new PLayout(null,null,3,3,false);
        this.startPage.setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");
        PLayout layout=new PLayout(startPage, Position.getPosition(2,2),2,1,true);
        startGameButton=new PButton(layout,null);
        startGameButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/startButton.png").getFile()));
        settingButton=new PButton(layout,null);
        settingButton.addBackground(PImage.getPureImage(Color.GRAY));
        settingButton.setText("Setting",1, Color.BLUE);

        this.gamePage=new PLayout(null,null,3,3,false);
        this.gamePage.setRCNumStyle(3,3,"1x,2x,1x","1x,2x,1x");

        PLayout rightUpPanel=new PLayout(gamePage,Position.getPosition(1,3),4,2);
        rightUpPanel.setColumnLayout("15,3x");
        PLayout leftUpPanelLayout=new PLayout(gamePage,Position.getPosition(1,1),4,4,true);

        MapButton=new PButton(leftUpPanelLayout,null);
        MapButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/icons/17-12.png").getFile()));

        messageLabel=new MessageLabel(gamePage,Position.getPosition(1,2));


        PWidget coinImage =new PImage(rightUpPanel,Position.getPosition(1,1),UI.class.getClassLoader().getResource("image/coin.png").getFile());
        coinValueLabel=new PLabel(rightUpPanel,Position.getPosition(1,2));
        coinValueLabel.setText("x0",2,Color.WHITE);


        this.settingPage=new PLayout(null,null);
        PLabel label=new PLabel(null,null);
        this.settingPage.addBackground(label);
        label.setText("Welcome to play this game.Because this game is under programming,this version does not represent the final quality" ,2,Color.ORANGE);

        try {
            startGameButton.setClickFunc(this,this.getClass().getMethod("startGameButtonBeClicked"));
            settingButton.setClickFunc(this,this.getClass().getMethod("settingButtonBeClicked"));
            MapButton.setClickFunc(this,this.getClass().getMethod("MapButtonBeClicked"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setPage(UI.START_PAGE);
        ui.startRepaintThread();
    }

    public void setWorld(World world){
        PGraphicView view=new PGraphicView(null,null,world);
        view.setViewPosition(Position.getPosition(0,0));
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



    public void setCoinValue(int n){
        this.coinValueLabel.setText("x"+ n,2,Color.WHITE);
    }


    public void startGameButtonBeClicked(){
        this.setPage(UI.GAME_PAGE);
        World world=new World(20000,20000);
        this.setWorld(world);
        this.sendMessage("Game start now!");
        Calabash calabash=new Calabash();
        calabash.setPosition(world.getStartPosition());
        world.addOperational(calabash);
        world.getParentView().setFocus(calabash);
    }

    public void settingButtonBeClicked(){
        this.setPage(UI.SETTING_PAGE);
    }

    public void MapButtonBeClicked(){
        Log.InfoLog(this,"this method:MapButtonBeClicked need finish");
    }

    public void sendMessage(String str){
        this.messageLabel.sendMessage(str,3000);
    }
}


