package game.server.server;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSync {
    private ExecutorService es;

    public ClientSync(){
        es = (ExecutorService) Executors.newFixedThreadPool(8);
    }

    public void handleMessage(JSONObject message){
        synchronized (es){
        }
    }
}
