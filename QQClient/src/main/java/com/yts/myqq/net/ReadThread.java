package com.yts.myqq.net;

import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.controller.MsgController;
import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;
import com.yts.myqq.ui.ChatView;
import com.yts.myqq.ui.MainView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ReadThread implements Runnable{

    private Socket tcpConnection;
    private BufferedReader br;

    public ReadThread(Socket tcpConnection, BufferedReader br){
        this.tcpConnection = tcpConnection;
        this.br = br;
    }

    @Override
    public void run() {
        System.out.println("读线程已开启！");
        MsgController msgController = new MsgController();
        try {
            while (true){
                String response = br.readLine();
                read(response);
                System.out.println("客户端收到 response: " + response);

            }
        } catch (IOException e) {
            System.out.println("客户端：数据读取异常");
            e.printStackTrace();
        }
    }

    public void read(String response){
        MsgController msgController = new MsgController();

        UserController userController = new UserController();

        Message message;

        Map map = JSONObject.parseObject(response);
        //返回数据对应业务类型
        String type = (String) map.get(SystemConstant.MSG_TYPE);
        //Object data = map.get(SystemConstant.RET_DATA);
        switch (type){
            //存储聊天信息
            case SystemConstant.CHAT:
                    message = msgController.receiveMessage(response);
                    //存入对应的消息list中
                    MsgController.addMessage(message.getSender(), message);
                    ChatView chatView = null;
                    if ((chatView = ChatView.chatViewMap.get(message.getSender())) != null){
                        chatView.reloadCharView();
                    }
                    break;

            //存储好友列表
            case SystemConstant.FRIEND_LIST:
                    userController.getFriendList(response);
                    System.out.println("存放用户列表数据");
                    //动态刷新好友列表界面
                    if (MainView.mainView != null){

                        MainView.mainView.reloadMainView();
                    }
                    break;
            //提示好友不在线
            case SystemConstant.OFF_LINE:
                message = msgController.receiveMessage(response);
                JOptionPane.showMessageDialog(ChatView.chatViewMap.get(message.getReceiver()), "对方不在线!");
                break;
            case SystemConstant.ADD_FRIEND:
                message = msgController.receiveMessage(response);
                if (message.getSender().equals(User.myself.getAccount())){
                    //收到回复
                    String retCode = (String) JSONObject.parseObject(response).get(SystemConstant.RET_CODE);
                    if(retCode.equals(SystemConstant.SUCCESS)){
                        JOptionPane.showMessageDialog(MainView.mainView, message.getContent());
                    }else {
                        JOptionPane.showMessageDialog(MainView.mainView, message.getContent());
                    }
                }else{
                    //收到请求
                    int option = JOptionPane.showConfirmDialog(MainView.mainView,message.getSender() + "请求添加你为好友！是否通过？",
                            "确认通过吗？", JOptionPane.YES_NO_OPTION);
                    //同意好友请求
                    if (option == JOptionPane.YES_OPTION){
                        message.setContent(SystemConstant.SUCCESS);
                        userController.responseAddFriend(message);
                    }
                    //不同意
                    else {
                        message.setContent(SystemConstant.FAILURE);
                        userController.responseAddFriend(message);
                    }
                }



        }

    }
}
