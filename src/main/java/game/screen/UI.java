package game.screen;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.*;
import game.graphic.creature.operational.Calabash;
import game.world.World;
import log.Log;

import java.awt.*;
import java.io.InputStream;

public class UI {
    public static int START_PAGE=0;
    public static int GAME_PAGE=1;
    public static int SETTING_PAGE=2;

    public PHeadWidget ui;
    public PLayout startPage;
    public PButton startGameButton;
    public PButton settingButton;

    public PLabel coinValueLabel;
    public PButton PauseButton;
    public PButton MapButton;
    public PButton QuitButton;
    public MessageLabel messageLabel;

    public HealthBar healthBar;


    public PLayout gamePage;

    public PLayout settingPage;

    public void createUI(){
        this.ui=new PHeadWidget(null,null);

        this.startPage=new PLayout(null,null,3,3,false);
        this.startPage.setRCNumStyle(3,3,"2x,1x,2x","2x,1x,2x");
        PLayout layout=new PLayout(startPage, Position.getPosition(2,2),2,1,true);
        startGameButton=new PButton(layout,null);
        startGameButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/startButton.png").getFile()));
        settingButton=new PButton(layout,null);
        settingButton.addBackground(PImage.getPureImage(Color.GRAY));
        settingButton.setText("Setting",1, Color.BLUE);

        this.gamePage=new PLayout(null,null,3,3,false);
        this.gamePage.setRCNumStyle(3,3,"1x,4x,1x","1x,4x,1x");

        PLayout rightUpPanel=new PLayout(gamePage,Position.getPosition(1,3),4,2);
        rightUpPanel.setColumnLayout("15,3x");
        PLayout leftUpPanelLayout=new PLayout(gamePage,Position.getPosition(1,1),2,3,true);

        QuitButton=new PButton(leftUpPanelLayout,null);
        QuitButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/quit.png").getFile()));

        PauseButton=new PButton(leftUpPanelLayout,null);
        PauseButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/pause.png").getFile()));



        MapButton=new PButton(leftUpPanelLayout,null);
        MapButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/map.png").getFile()));

        messageLabel=new MessageLabel(gamePage,Position.getPosition(1,2));

        healthBar=new HealthBar(gamePage,Position.getPosition(3,2));


        PWidget coinImage =new PImage(rightUpPanel,Position.getPosition(1,1),UI.class.getClassLoader().getResource("image/coin.png").getFile());
        coinValueLabel=new PLabel(rightUpPanel,Position.getPosition(1,2));
        coinValueLabel.setText("x0",2,Color.WHITE);


        this.settingPage=new PLayout(null,null);

        PLabel label=new PLabel(null,null);
        this.settingPage.addBackground(label);
        try{
            InputStream inputStream=  this.getClass().getClassLoader().getResourceAsStream("about.txt");
            byte[] bytes=inputStream.readAllBytes();
            StringBuilder string= new StringBuilder();
            for(byte b:bytes){
                string.append((char) b);
            }
            label.setText(string.toString(),1,Color.YELLOW);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            startGameButton.setClickFunc(this,this.getClass().getMethod("startGameButtonBeClicked"));
            settingButton.setClickFunc(this,this.getClass().getMethod("settingButtonBeClicked"));
            MapButton.setClickFunc(this,this.getClass().getMethod("MapButtonBeClicked"));
            PauseButton.setClickFunc(this,this.getClass().getMethod("PauseButtonClicked"));
            QuitButton.setClickFunc(this,this.getClass().getMethod("QuitButtonClicked"));
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

    public void gameExit() {
        gameWorld = null;
        this.gamePage.addBackground(null);
        setPage(UI.START_PAGE);
    }



    public void setCoinValue(int n){
        this.coinValueLabel.setText("x"+ n,2,Color.WHITE);
    }

    public World gameWorld;

    public void QuitButtonClicked(){
        gameWorld.gameFinish();
    }

    public void startGameButtonBeClicked(){
        this.setPage(UI.GAME_PAGE);
        gameWorld=new World(2000,2000);
        this.setWorld(gameWorld);
        this.sendMessage("Game start now!");
        gameWorld.screen=this;
        Calabash calabash=new Calabash();
        calabash.setPosition(gameWorld.getStartPosition());
        gameWorld.addOperational(calabash);
        gameWorld.getParentView().setFocus(calabash);
    }

    public void PauseButtonClicked(){
        if(gameWorld.isPause()){
            PauseButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/pause.png").getFile()));
            gameWorld.gameContinue();
        }
        else{
            PauseButton.addBackground(new PImage(null,null,UI.class.getClassLoader().getResource("image/continue.png").getFile()));
            gameWorld.gamePause();
        }
    }


    public void settingButtonBeClicked(){
        this.setPage(UI.SETTING_PAGE);
    }

    public PGraphicView mapView;


    public void MapButtonBeClicked() {
        if (mapView == null) {
            PGraphicScene world = new PGraphicScene(400, 400);
            mapView = new PGraphicView(gamePage, Position.getPosition(2, 2), world);
            Pixel[][] pixels=gameWorld.getWorldMap();
            int scale=Math.max(1,mapView.getWidgetHeight()/pixels.length);
            pixels=Pixel.pixelsScaleLarger(pixels,scale);
            PGraphicItem item = new PGraphicItem(pixels);
            world.addItem(item);
            mapView.setViewPosition(Position.getPosition(0,0));
            sendMessage("Map may can not display whole,\nthis is the map around your role");
        }
        else{
            gamePage.removeWidget(mapView);
            mapView=null;
        }
    }

    public void sendMessage(String str){
        this.messageLabel.sendMessage(str,3000);
    }

    public void displayHealth(double health,double healthLimit){
        healthBar.display(health,healthLimit);
    }
}


