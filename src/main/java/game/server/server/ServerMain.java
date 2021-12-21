package game.server.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.Config;
import game.graphic.creature.operational.Calabash;
import game.server.Message;
import game.server.client.ClientMain;
import game.world.GameArchiveGenerator;
import log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static int maxClientNum = 4;
    final private ClientSync clientSync;
    ServerSocket serverSocket;
    int currentClient;
    Socket firstSocket;
    ExecutorService es;
    Thread server;
    CopyOnWriteArraySet<Socket> sockets;
    private final JSONArray messageArray = new JSONArray();

    public ServerMain() {
        clientSync = new ClientSync();
        sockets = new CopyOnWriteArraySet<>();
        es = Executors.newFixedThreadPool(100);
        currentClient = 0;
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread frameSyncThread;

    JSONObject worldData;

    public void start() {

        //generate world
        GameArchiveGenerator gameArchiveGenerator = new GameArchiveGenerator(2000, 2000, Config.DataPath + "/server/game.json", 2);
        gameArchiveGenerator.generateWorldData();

        worldData = gameArchiveGenerator.getWorldData();

        //create && start frame sync thread
        frameSyncThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Message.messageClass, Message.FrameSync);
                    jsonObject.put(Message.moreArgs, Message.SubmitInput);
                    String message;
                    synchronized (messageArray) {
                        jsonObject.put(Message.information, messageArray);
                        message = Message.JSON2MessageStr(jsonObject);
                        messageArray.clear();
                    }
                    for (Socket socket : sockets) {
                        new Thread(() -> {
                            try {
                                synchronized (socket.getOutputStream()) {
                                    socket.getOutputStream().write(message.getBytes());
                                    socket.getOutputStream().flush();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        frameSyncThread.start();
        HashMap<Socket, Calabash> SocketCalabashMap = new HashMap<>();

        //start server accept main thread
        server = new Thread(() -> {
            Log.InfoLog(this, "server listener start work...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (currentClient >= maxClientNum) {
                        new PrintWriter(clientSocket.getOutputStream()).write(Message.JSON2MessageStr(Message.getErrorMessage(Message.OutOfMaxClientBound)));
                        clientSocket.close();
                    } else {
                        currentClient++;
                        sockets.add(clientSocket);
                        if (firstSocket == null)
                            firstSocket = clientSocket;

                        //submit a NIO handle thread to executors
                        es.submit(new Runnable() {
                            private final Socket socket = clientSocket;

                            PrintWriter pw = new PrintWriter(socket.getOutputStream());

                            private void closeConnection() {
                                try {
                                    sendMessage(Message.JSON2MessageStr(Message.getGameQuitMessage()));
                                    socket.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    sockets.remove(socket);
                                    Thread.currentThread().interrupt();
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

                            private void handleMessage(String jsonStr) {
                                //System.out.println(jsonStr);
                                try {

                                    JSONObject jsonObject = JSON.parseObject(jsonStr);
                                    String messageClass = jsonObject.getObject(Message.messageClass, String.class);
                                    if (Objects.equals(messageClass, Message.ErrorMessage)) {
                                        Log.ErrorLog(this, jsonObject.getObject(Message.information, String.class));
                                    } else if (Objects.equals(messageClass, Message.FrameSync)) {
                                        synchronized (messageArray) {
                                            messageArray.addAll(jsonObject.getObject(Message.information, JSONArray.class));
                                        }
                                    } else if (Objects.equals(messageClass, Message.StateSync)) {

                                    } else if (Objects.equals(messageClass, Message.GameQuit)) {

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Thread.currentThread().interrupt();
                                }
                            }

                            @Override
                            public void run() {
                                try {

                                    //create calabash and send to client for init
                                    Calabash calabash = new Calabash();
                                    SocketCalabashMap.put(socket, calabash);
                                    worldData.getObject("itemsData", JSONArray.class).add(calabash.saveState());
                                    worldData.put("controlRole", calabash.getId());
                                    System.out.println(worldData.toJSONString().length());
                                    sendMessage(Message.getWorldInitCommand(worldData));

                                    //start input listener && handle thread
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 10240000);
                                    while (!Thread.currentThread().isInterrupted()) {
                                        handleMessage(bufferedReader.readLine());
                                    }
                                    System.out.println("client socket quit");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    closeConnection();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.InfoLog(this, "server listener quit...");
        });
        server.start();
    }

    public void stop() {
        try {
            serverSocket.close();
            server.interrupt();
            frameSyncThread.interrupt();
            es.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.InfoLog(this, "server stop...");
    }

    public static void main(String[] args) throws InterruptedException {
        ServerMain serverMain = new ServerMain();
        serverMain.start();
        ClientMain.getInstance().connect("127.0.0.1", 9000);
        Thread.sleep(2000);
        serverMain.stop();
    }
}
