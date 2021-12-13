package game.server;

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
