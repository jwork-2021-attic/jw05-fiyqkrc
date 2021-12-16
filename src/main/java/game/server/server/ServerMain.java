package game.server.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import game.server.Message;
import game.server.client.ClientMain;
import game.world.World;
import log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static int maxClientNum = 4;
    final private ClientSync clientSync;
    ServerSocket serverSocket;
    int currentClient;
    ExecutorService es;
    Thread server;
    CopyOnWriteArraySet<Socket> sockets;

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

    public void start() {
        server = new Thread(() -> {
            Log.InfoLog(this, "server listener start work...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (currentClient >= maxClientNum) {
                        clientSocket.getOutputStream().write(Message.JSON2MessageBytes(Message.getErrorMessage(Message.OutOfMaxClientBound)));
                        clientSocket.close();
                    } else {
                        currentClient++;
                        sockets.add(clientSocket);
                        es.submit(new Runnable() {
                            private final Socket socket = clientSocket;

                            private void closeConnection() {
                                try {
                                    socket.getOutputStream().write(Message.JSON2MessageBytes(Message.getGameQuitMessage()));
                                    socket.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    sockets.remove(socket);
                                    Thread.currentThread().interrupt();
                                }
                            }

                            private void handleMessage(String jsonStr) {
                                JSONObject jsonObject = JSON.parseObject(jsonStr);
                                String messageClass = jsonObject.getObject(Message.messageClass, String.class);
                                if (Objects.equals(messageClass, Message.ErrorMessage)) {
                                    Log.ErrorLog(this, jsonObject.getObject(Message.information, String.class));
                                } else if (Objects.equals(messageClass, Message.FrameSync)) {

                                } else if (Objects.equals(messageClass, Message.StateSync)) {

                                } else if (Objects.equals(messageClass, Message.GameQuit)) {

                                }
                                System.out.println(jsonStr);
                            }

                            @Override
                            public void run() {
                                try {
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    while (!Thread.currentThread().isInterrupted()) {
                                        handleMessage(bufferedReader.readLine());
                                    }
                                    System.out.println("client socket quit");
                                } catch (IOException e) {
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
        server.interrupt();
        es.shutdownNow();
    }

    public static void main(String[] args) throws InterruptedException {
        ServerMain serverMain = new ServerMain();
        serverMain.start();
        ClientMain.getInstance().connect("127.0.0.1", 9000);
        Thread.sleep(1000);
        serverMain.stop();
    }
}
