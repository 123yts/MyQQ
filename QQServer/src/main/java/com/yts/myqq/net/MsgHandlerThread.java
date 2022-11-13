package com.yts.myqq.net;

import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.controller.MsgController;
import com.yts.myqq.controller.UserController;
import com.yts.myqq.util.Protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MsgHandlerThread implements Runnable{

    Socket tcpConnection;

    InputStream in = null;
    OutputStream out = null;
    InputStreamReader isr = null;
    BufferedReader br = null;

    public MsgHandlerThread(Socket tcpConnection){
        this.tcpConnection = tcpConnection;
    }

    @Override
    public void run() {
        try {
            in = tcpConnection.getInputStream();
            out = tcpConnection.getOutputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);

            while (true){
                //客户端请求报文
                String request = br.readLine();
                System.out.println("request: " + request);

                //响应报文
                String response = response(request, this);

                //客户端是否要断开连接
                if (SystemConstant.DISCONNECT.equals(response)){
                    break;
                }

            }
            System.out.println("断开与客户端IP:" + tcpConnection.getInetAddress() + "，端口号："+ tcpConnection.getPort() + "的连接");

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
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
                if (this.tcpConnection != null){
                    tcpConnection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void response(String response) throws IOException {
        out.write((response + "\n").getBytes(StandardCharsets.UTF_8));
        System.out.println("response: " + response);
    }

    public String response(String response, MsgHandlerThread msgHandlerThread) throws IOException {
        Map<String, Object> map = JSONObject.parseObject(response);
        String msgType = (String) map.get(SystemConstant.MSG_TYPE);
        Object data = map.get(SystemConstant.DATA);

        MsgController msgController = new MsgController();
        UserController userController = new UserController();

        switch (msgType){
                //处理登陆
            case SystemConstant.LOGIN:
                return userController.login(data, msgHandlerThread);
                //处理注册
            case SystemConstant.REGISTER:
                return userController.register(data, msgHandlerThread);
                //转发聊天信息
            case SystemConstant.CHAT:
                return msgController.sendMessage(data, msgHandlerThread);
                //关闭socket(用户下线）
            case SystemConstant.DISCONNECT:
                return userController.disconnect(data);
                //获取好友列表
            case SystemConstant.FRIEND_LIST:
                return userController.getFriendList(data, msgHandlerThread);
                //添加好友信息
            case SystemConstant.ADD_FRIEND:
                return userController.addFriend(data, msgHandlerThread);
            default:
                return Protocol.retData(null, SystemConstant.FAILURE, "请求类型异常！");
        }
    }
}
