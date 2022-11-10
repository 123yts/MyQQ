package com.yts.myqq.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {

    //存放每一个在线用户的thread（key：QQ号码， value：thread）
    public static Map<String , MsgHandlerThread> threadMap = new HashMap<>();

    private TCPServer(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10087);
            System.out.println("服务器启动-------");
            while (true){
                Socket tcpConnection = serverSocket.accept();
                new Thread(new MsgHandlerThread(tcpConnection)).start();
                System.out.println("收到客户端 " + tcpConnection.getInetAddress() + ":" + tcpConnection.getPort() + " 的连接");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new TCPServer();
    }
}
