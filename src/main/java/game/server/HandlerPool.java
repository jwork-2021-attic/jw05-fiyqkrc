package game.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerPool {
    private ExecutorService es;

    public HandlerPool(){
        es = (ExecutorService) Executors.newFixedThreadPool(8);
    }

    public void handleMessage(Message message){
        synchronized (es){
        }
    }
}
