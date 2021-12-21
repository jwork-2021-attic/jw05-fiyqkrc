package game.server.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.screen.UI;
import game.server.Message;
import game.world.World;
import log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientMain implements Runnable {
    Accepter commandListener = new Accepter();
    static ClientMain instance;
    Socket socket;
    PrintWriter pw;
    public UI ui;

    private ClientMain() {
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            pw = new PrintWriter(socket.getOutputStream());
            Log.InfoLog(this, "client connect to " + host + ":" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String string) {
        try {
            synchronized (socket.getOutputStream()) {
                pw.write(string);
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    World world;

    public void setWorld(World world) {
        this.world = world;
    }

    public static ClientMain getInstance() {
        if (instance == null) {
            instance = new ClientMain();
        }
        return instance;
    }

    public Accepter getCommandListener() {
        return commandListener;
    }

    private void analysis(JSONObject jsonObject) {
        if (Objects.equals(jsonObject.getObject(Message.messageClass, String.class), Message.FrameSync)) {
            if (world != null)
                world.frameSync(jsonObject.getObject(Message.information, JSONArray.class));
        } else if (Objects.equals(jsonObject.getObject(Message.messageClass, String.class), Message.GameInit)) {
            World world = new World(jsonObject.getObject(Message.information, JSONObject.class));
            setWorld(world);
            ui.setWorld(world);
            world.screen = ui;
            world.activeControlRole();
        } else if (Objects.equals(jsonObject.getObject(Message.messageClass, String.class), Message.StateSync)) {
            String message = Message.getLaunchStateSyncMessage(world.getCurrentState());
            sendMessage(message);
        } else if (Objects.equals(jsonObject.getObject(Message.messageClass, String.class), Message.StartStateSync)) {
            world.stateSync(jsonObject.getObject(Message.information, JSONArray.class));
        } else {
            System.out.println(jsonObject.toJSONString());
        }
    }

    @Override
    public void run() {
        Log.InfoLog(this, "client start working...");
        Thread inputListener = new Thread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 10240000);
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String jsonStr = bufferedReader.readLine();
                        JSONObject jsonObject = JSON.parseObject(jsonStr);
                        analysis(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inputListener.start();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                new Thread(() -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Message.messageClass, Message.FrameSync);
                    jsonObject.put(Message.moreArgs, Message.SubmitInput);
                    jsonObject.put(Message.information, commandListener.getMessage());
                    try {
                        synchronized (pw) {
                            pw.write(Message.JSON2MessageStr(jsonObject));
                            pw.flush();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                inputListener.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        Log.InfoLog(this, "client stop working");
    }
}
