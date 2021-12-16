package game.screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.*;
import game.Config;
import game.world.World;
import game.world.GameArchiveGenerator;
import log.Log;

import java.awt.*;
import java.io.*;

public class UI {
    public static int START_PAGE = 0;
    public static int GAME_PAGE = 1;
    public static int SETTING_PAGE = 2;

    public PHeadWidget ui;
    public PLayout startPage;
    public PButton startGameButton;
    public PButton settingButton;
    public PButton loadSavedDataButton;

    public PLabel coinValueLabel;
    public PButton PauseButton;
    public PButton MapButton;
    public PButton QuitButton;
    public PButton ScreenShotButton;
    public PButton RecordButton;

    public MessageLabel messageLabel;

    public HealthBar healthBar;


    public RecordablePage gamePage;

    public PLayout settingPage;

    public void createUI() {
        this.ui = new PHeadWidget(null, null);

        this.startPage = new PLayout(null, null, 3, 3, false);
        this.startPage.setRCNumStyle(3, 3, "2x,1x,2x", "2x,1x,2x");
        PLayout layout = new PLayout(startPage, Position.getPosition(2, 2), 3, 1, true);
        startGameButton = new PButton(layout, null);
        startGameButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/startButton.png").getFile()));
        loadSavedDataButton = new PButton(layout, null);
        loadSavedDataButton.addBackground(PImage.getPureImage(Color.GRAY));
        loadSavedDataButton.setText("Continue", 1, Color.BLUE);
        settingButton = new PButton(layout, null);
        settingButton.addBackground(PImage.getPureImage(Color.GRAY));
        settingButton.setText("Setting", 1, Color.BLUE);

        this.gamePage = new RecordablePage(null, null, 3, 3, false);
        this.gamePage.setRCNumStyle(3, 3, "1x,4x,1x", "1x,4x,1x");

        PLayout rightUpPanel = new PLayout(gamePage, Position.getPosition(1, 3), 4, 2);
        rightUpPanel.setColumnLayout("15,3x");
        PLayout leftUpPanelLayout = new PLayout(gamePage, Position.getPosition(1, 1), 2, 3, true);

        QuitButton = new PButton(leftUpPanelLayout, null);
        QuitButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/quit.png").getFile()));

        PauseButton = new PButton(leftUpPanelLayout, null);
        PauseButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/pause.png").getFile()));

        ScreenShotButton = new PButton(leftUpPanelLayout, null);
        ScreenShotButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/screenshot.png").getFile()));

        RecordButton = new PButton(leftUpPanelLayout, null);
        RecordButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/record.png").getFile()));

        MapButton = new PButton(leftUpPanelLayout, null);
        MapButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/map.png").getFile()));

        messageLabel = new MessageLabel(gamePage, Position.getPosition(1, 2));

        healthBar = new HealthBar(gamePage, Position.getPosition(3, 2));


        PWidget coinImage = new PImage(rightUpPanel, Position.getPosition(1, 1), UI.class.getClassLoader().getResource("image/coin.png").getFile());
        coinValueLabel = new PLabel(rightUpPanel, Position.getPosition(1, 2));
        coinValueLabel.setText("x0", 2, Color.WHITE);


        this.settingPage = new PLayout(null, null);

        PLabel label = new PLabel(null, null);
        this.settingPage.addBackground(label);
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("about.txt");
            byte[] bytes = inputStream.readAllBytes();
            StringBuilder string = new StringBuilder();
            for (byte b : bytes) {
                string.append((char) b);
            }
            label.setText(string.toString(), 1, Color.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            startGameButton.setClickFunc(this, this.getClass().getMethod("startGameButtonBeClicked"));
            loadSavedDataButton.setClickFunc(this, this.getClass().getMethod("loadSavedDataButtonBeClicked"));
            settingButton.setClickFunc(this, this.getClass().getMethod("settingButtonBeClicked"));
            MapButton.setClickFunc(this, this.getClass().getMethod("MapButtonBeClicked"));
            PauseButton.setClickFunc(this, this.getClass().getMethod("PauseButtonClicked"));
            QuitButton.setClickFunc(this, this.getClass().getMethod("QuitButtonClicked"));
            ScreenShotButton.setClickFunc(this, this.getClass().getMethod("ScreenShotButtonClicked"));
            RecordButton.setClickFunc(this, this.getClass().getMethod("RecordButtonClicked"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setPage(UI.START_PAGE);
        ui.startRepaintThread();
    }

    public void setWorld(World world) {
        PGraphicView view = new PGraphicView(null, null, world);
        view.setViewPosition(Position.getPosition(0, 0));
        this.gamePage.addBackground(view);
    }


    public void setPage(int page) {
        switch (page) {
            case 0 -> ui.resetLayout(startPage);
            case 1 -> ui.resetLayout(gamePage);
            case 2 -> ui.resetLayout(settingPage);
            default -> Log.ErrorLog(this, "try to switch to an undefined page");
        }
    }

    public void gameExit() {
        if (gamePage.isRecording())
            gamePage.finishRecord();
        gameWorld = null;
        this.gamePage.addBackground(null);
        setPage(UI.START_PAGE);
    }


    public void setCoinValue(int n) {
        this.coinValueLabel.setText("x" + n, 2, Color.WHITE);
    }

    public World gameWorld;

    public void RecordButtonClicked() {
        if (gamePage.isRecording())
            gamePage.finishRecord();
        else
            gamePage.startRecord();
    }

    public void ScreenShotButtonClicked() {
        gamePage.getScreenShot();
    }

    public void QuitButtonClicked() {

        new Thread(() -> {
            gameWorld.gamePause();
            int option = PDialog.Dialog("Do you hope to save your game?", "Yes,I want to continue when next time I open this game.", "No,I will start a new game later.");
            if (option == 1) {
                gameWorld.gameSaveData();
                sendMessage("This module waits implementation...");
                gameWorld.gameContinue();
                gameWorld.gameFinish();
            } else {
                gameWorld.gameFinish();
            }
        }).start();
    }

    public void startGameButtonBeClicked() {
        GameArchiveGenerator gameArchiveGenerator = new GameArchiveGenerator(2000, 2000, Config.DataPath + "/saved.json", 2);
        gameArchiveGenerator.generateWorldData();
        loadSavedDataButtonBeClicked();
    }

    public void loadSavedDataButtonBeClicked() {
        File file = new File(Config.DataPath + "/saved.json");
        if (file.exists()) {
            FileInputStream stream;
            try {
                stream = new FileInputStream(file);
                String jsonString = new String(stream.readAllBytes());
                JSONObject jsonObject = JSON.parseObject(jsonString);
                this.setPage(UI.GAME_PAGE);
                gameWorld = new World(jsonObject);
                this.setWorld(gameWorld);
                this.sendMessage("game continue");
                gameWorld.screen = this;
                gameWorld.activeControlRole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void PauseButtonClicked() {
        if (gameWorld.isPause()) {
            PauseButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/pause.png").getFile()));
            gameWorld.gameContinue();
        } else {
            PauseButton.addBackground(new PImage(null, null, UI.class.getClassLoader().getResource("image/continue.png").getFile()));
            gameWorld.gamePause();
        }
    }


    public void settingButtonBeClicked() {
        this.setPage(UI.SETTING_PAGE);
    }

    public PGraphicView mapView;


    public void MapButtonBeClicked() {
        if (mapView == null) {
            PGraphicScene world = new PGraphicScene(400, 400);
            mapView = new PGraphicView(gamePage, Position.getPosition(2, 2), world);
            Pixel[][] pixels = gameWorld.getWorldMap();
            int scale = Math.max(1, mapView.getWidgetHeight() / pixels.length);
            pixels = Pixel.pixelsScaleLarger(pixels, scale);
            PGraphicItem item = new PGraphicItem(pixels);
            world.addItem(item);
            mapView.setViewPosition(Position.getPosition(0, 0));
            sendMessage("Map may can not display whole,\nthis is the map around your role");
        } else {
            gamePage.removeWidget(mapView);
            mapView = null;
        }
    }

    public void sendMessage(String str) {
        this.messageLabel.sendMessage(str, 3000);
    }

    public void displayHealth(double health, double healthLimit) {
        healthBar.display(health, healthLimit);
    }
}


