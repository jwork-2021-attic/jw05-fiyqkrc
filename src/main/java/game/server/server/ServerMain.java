package game.server.server;

import com.alibaba.fastjson.JSONObject;
import game.server.client.ClientMain;
import log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    final private ClientSync clientSync;
    ServerSocket serverSocket;
    ExecutorService es;
    Thread server;

    public ServerMain() {
        clientSync = new ClientSync();
        es = Executors.newFixedThreadPool(100);
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        server=new Thread(()-> {
            Log.InfoLog(this,"server listener start work...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    es.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                while (!Thread.currentThread().isInterrupted()) {
                                    System.out.println("server get:"+bufferedReader.readLine());
                                    clientSocket.getOutputStream().write(ServerMain.this.toString().getBytes());
                                    clientSocket.getOutputStream().write('\n');

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.InfoLog(this,"server listener quit...");
        });
        server.start();
    }

    public void stop(){
        server.interrupt();
    }

    public static void main(String[] args){
        ServerMain serverMain=new ServerMain();
        serverMain.start();
        ClientMain.getInstance().connect("127.0.0.1",9000);
    }
}
