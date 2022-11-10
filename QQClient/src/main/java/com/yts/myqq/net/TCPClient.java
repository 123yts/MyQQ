package com.yts.myqq.net;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;
import com.yts.myqq.util.Protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    static TCPClient connection = new TCPClient();

    public static TCPClient getConnection(){
        return connection;
    }

    private Socket tcpConnection;
    private InputStream in = null;
    private OutputStream out = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;

    private TCPClient(){
        try {
            tcpConnection = new Socket("127.0.0.1", 10087);
            System.out.println("成功与服务器建立连接-------");
            in = this.tcpConnection.getInputStream();
            out = this.tcpConnection.getOutputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务器发送请求，用于登录成功之后
    public void send(String request){
        try {
            out.write((request + "\n").getBytes(StandardCharsets.UTF_8));
            System.out.println("request: " + request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用于登录和注册使用
    public String sendAndReceive(String request){
        try {
            out.write((request + "\n").getBytes(StandardCharsets.UTF_8));
            System.out.println("request: " + request);
            String response = br.readLine();
            System.out.println("response: " + response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //登录成功后开启读线程
    public void startReadThread(){
        //开启读线程
        new Thread(new ReadThread(tcpConnection, br)).start();
    }


    public void close(){
        try {
            //向服务器发送断开请求
            send(Protocol.request(SystemConstant.DISCONNECT, new Message(User.myself.getAccount(), "server", SystemConstant.DISCONNECT)));
            if (br != null){
                br.close();
            }
            if (isr != null){
                isr.close();
            }
            if (in != null){
                in.close();
            }
            if (out != null){
                out.close();
            }
            if (tcpConnection != null){
                tcpConnection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
