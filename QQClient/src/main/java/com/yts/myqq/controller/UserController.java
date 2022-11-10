package com.yts.myqq.controller;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.util.Parser;
import com.yts.myqq.util.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController {

    //好友map
    public static Map<String, User> friendMap = new HashMap<>();

    TCPClient connection = TCPClient.getConnection();

    //登录
    public boolean login(User user){
        String response = connection.sendAndReceive(Protocol.request(SystemConstant.LOGIN, user));

        //登录成功，将信息记录到User.myself中
        if ((User.myself = Parser.getRetLogin(response)) != null) {
            return true;
        }
        return false;
    }

    //注册
    public boolean register(User user){
        String response = connection.sendAndReceive(Protocol.request(SystemConstant.REGISTER, user));
        return Parser.getRetRegister(response);
    }

    public void initFriendList(User user){
        String response = connection.sendAndReceive(Protocol.request(SystemConstant.FRIEND_LIST, user));
        List<User> userList = Parser.getRetFriendList(response);
        //存入好友map里面
        if (userList != null){
            userList.forEach(item -> friendMap.put(item.getAccount(), item));
        }
        else friendMap.clear(); //空就清空好友列表数据
        //开启循环读线程
        connection.startReadThread();
    }

    public void getFriendList(String response){
        //获取所有用户列表
        List<User> userList = Parser.getRetFriendList(response);
        //存入好友map里面
        if (userList != null){
            userList.forEach(item -> friendMap.put(item.getAccount(), item));
        }else friendMap.clear(); //空就清空好友列表数据
    }


}
