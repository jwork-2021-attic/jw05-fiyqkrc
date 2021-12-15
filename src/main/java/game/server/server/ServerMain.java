package game.server.server;

import game.server.Accepter;
import game.server.HandlerPool;
import game.server.Listener;

public class ServerMain {
    final public Accepter accepter;
    final private Listener listener;
    final private HandlerPool handlerPool;

    public ServerMain(){
        accepter=new Accepter();
        listener=new Listener();
        handlerPool=new HandlerPool();
    }

    public void start(){
        accepter.start();
    }
}
