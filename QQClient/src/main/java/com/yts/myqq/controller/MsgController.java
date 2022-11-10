package com.yts.myqq.controller;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.util.Parser;
import com.yts.myqq.util.Protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MsgController {

    //与每个好友的聊天信息，String: 好友账号， List：消息列表
    public static Map<String, List<Message>> messageMap = new HashMap();

    //存储与账户为friendAccount的好友的聊天信息
    public static void addMessage(String friendAccount, Message message){
        if (messageMap.containsKey(friendAccount)){
            List messageList = messageMap.get(friendAccount);
            messageList.add(message);
        }else{
            //初次聊天，创建列表，插入数据
            List<Message> messageList = new ArrayList<>();
            messageList.add(message);
            messageMap.put(friendAccount, messageList);
        }
    }

    TCPClient connection = TCPClient.getConnection();

    public void sendMessage(Message message){
        String request = Protocol.request(SystemConstant.CHAT, message);
        connection.send(request);
    }

    public Message receiveMessage(String response){
        return Parser.getRetMessage(response);
    }


}
