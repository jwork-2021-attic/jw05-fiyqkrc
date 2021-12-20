package game.server;

import com.alibaba.fastjson.JSONObject;
import org.bytedeco.opencv.presets.opencv_core;

public class Message {
    public static String messageClass="messageClass";
    public static String FrameSync="FrameSync";
    public static String StateSync="StateSync";
    public static String ErrorMessage="ErrorMessage";
    public static String GameQuit="GameQuit";
    public static String OutOfMaxClientBound="OutOfMaxClientBound";

    public static String moreArgs="moreArgs";
    public static String SubmitInput="SubmitInput";

    public static String information="Information";

    public static JSONObject getErrorMessage(String str){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(messageClass,ErrorMessage);
        jsonObject.put(information,str);
        return jsonObject;
    }

    public static JSONObject getGameQuitMessage(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(messageClass,GameQuit);
        return jsonObject;
    }

    public static String JSON2MessageStr(JSONObject jsonObject){
        return jsonObject.toJSONString()+"\n";
    }
}
