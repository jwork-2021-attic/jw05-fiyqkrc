package game.server.client;

import com.alibaba.fastjson.JSONObject;
import game.server.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMain implements Runnable{
    Accepter commandListener=new Accepter() ;
    static ClientMain instance;
    Socket socket;

    private ClientMain() {
    }

    public void connect(String host,int port){
        try {
            socket=new Socket(host,port);
            new Thread(instance).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientMain getInstance() {
        if (instance == null) {
            instance = new ClientMain();
        }
        return instance;
    }

    public Accepter getCommandListener(){
        return commandListener;
    }

    @Override
    public void run() {
        Thread inputListener=new Thread(()->{
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!Thread.currentThread().isInterrupted()){
                    String jsonStr=bufferedReader.readLine();
                    System.out.println("client get:"+jsonStr);
                    //JSONObject jsonObject= JSON.parseObject(jsonStr);
                    //todo
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        inputListener.start();

        while(!Thread.currentThread().isInterrupted()){
            try{
                new Thread(() -> {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Message.messageClass,Message.FrameSync);
                    jsonObject.put(Message.moreArgs,Message.SubmitInput);
                    jsonObject.put(Message.information,commandListener.getMessage());
                    try {
                        socket.getOutputStream().write(Message.JSON2MessageBytes(jsonObject));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                Thread.sleep(20);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                inputListener.interrupt();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
