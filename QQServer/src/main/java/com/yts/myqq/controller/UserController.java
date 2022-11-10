package com.yts.myqq.controller;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.dao.UserDao;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;
import com.yts.myqq.net.MsgHandlerThread;
import com.yts.myqq.net.TCPServer;
import com.yts.myqq.util.Parser;
import com.yts.myqq.util.Protocol;

import java.io.IOException;
import java.util.List;

public class UserController {

    //用户登录
    public String login(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        User user = Parser.getUser(data);
        UserDao dao = new UserDao();
        System.out.println("user: " + user);
        //判断登陆是否成功，成功就将该线程加入到threadMap中去
        if ((user = dao.getUser(user.getAccount(), user.getPassword())) != null){
            //返回完整user数据，客户端存入User.self
            msgHandlerThread.response(Protocol.retData(SystemConstant.LOGIN, SystemConstant.SUCCESS, user));

        }
        else{
            //查询失败
            msgHandlerThread.response(Protocol.retData(SystemConstant.LOGIN, SystemConstant.FAILURE, "用户名或密码错误！"));
        }
        return SystemConstant.FAILURE;
    }

    //用户请求用户好友列表数据
    public String getFriendList(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        User user = Parser.getUser(data);
        //判断是否
        if (!TCPServer.threadMap.containsKey(user.getAccount())){
            TCPServer.threadMap.put(user.getAccount(), msgHandlerThread);
        }
        UserDao dao = new UserDao();
        System.out.println("user: " + user);
        String response = Protocol.retData(SystemConstant.FRIEND_LIST, SystemConstant.SUCCESS, dao.getUserList(user.getAccount()));
        msgHandlerThread.response(response);
        return response;
    }

    //用户下线后，通知其好友我已下线
    public void notifyFriendsForOffLine(){
        //TODO
    }

    //用户下线
    public String disconnect(Object data) throws IOException {
        Message message = Parser.getMessage(data);
        System.out.println("message： " + message);
        //找到其它所有好友，通知他们我下线了，并将thread从map中删除
        //Todo
        TCPServer.threadMap.remove(message.getSender());
        return SystemConstant.DISCONNECT;
    }

}
