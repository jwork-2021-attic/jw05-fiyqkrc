package game;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Config {
    public int WindowWidth;
    public int WindowHeight;

    public int SceneWidth;
    public int SceneHeight;
    public int worldScale;
    public int tileSize;

    public double monsterOnGolang;
    public double monsterOnRoom;


    public int ScreenFlash;

    public boolean AsciiPanelAccelerate;

    public int LogOutPutLevel;
    public boolean LogTerminalOutput;
    public String LogPath;

    private JSONObject creatureInfos;
    public HashMap<String,String[]> gameEnvInfos;
    public String ExitNodeSource;

    public CreatureProperty getCreatureInfo(String name){
        return null;
    }



    static public void loadConfig(String path){
        try{
            FileInputStream stream=new FileInputStream(new File(path));
            byte[] bytes=stream.readAllBytes();
            JSONObject object=(JSONObject) JSONObject.parse(bytes);
            System.out.println(object.get("Calabash"));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void main(String[] args){
        loadConfig(Config.class.getClassLoader().getResource("config.json").getFile());
    }

}


