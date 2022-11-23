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
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    public String register(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        User user = Parser.getUser(data);
        UserDao dao = new UserDao();
        System.out.println("user: " + user);
        //随机生成QQ号
        Random random = new Random();
        random.setSeed(new Date().getTime());
        while (true){
            String account = "" + (random.nextInt(900) + 100);
            //QQ号没被占用，创建用户
            if (!dao.exitUser(account)){
                user.setAccount(account);
                break;
            }
        }
        //数据插入数据
        if (dao.insertUser(user)){
            msgHandlerThread.response(Protocol.retData(SystemConstant.REGISTER, SystemConstant.SUCCESS, user));
        }
        else msgHandlerThread.response(Protocol.retData(SystemConstant.REGISTER, SystemConstant.FAILURE, null));
        return SystemConstant.REGISTER;
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

    //添加好友
    public String addFriend(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        Message message = Parser.getMessage(data);
        //请求方发送的消息
        if (TCPServer.threadMap.get(message.getSender()) == msgHandlerThread){
            //数据库查找是否有该用户
            UserDao userDao = new UserDao();
            if (!userDao.exitUser(message.getReceiver())){
                message.setContent("该用户不存在");
                msgHandlerThread.response(Protocol.retData(SystemConstant.ADD_FRIEND, SystemConstant.FAILURE, message));
                return SystemConstant.FAILURE;
            }
            //是否已经是好友了
            if (userDao.getFriendShip(message.getSender(), message.getReceiver()) || userDao.getFriendShip(message.getReceiver(), message.getSender())){
                message.setContent(message.getReceiver() + "已经是你的好友了!");
                msgHandlerThread.response(Protocol.retData(SystemConstant.ADD_FRIEND, SystemConstant.FAILURE, message));
                return SystemConstant.FAILURE;
            }
            //转发请求
            MsgHandlerThread aimThread = TCPServer.threadMap.get(message.getReceiver());
            if (aimThread != null){
                aimThread.response(Protocol.retData(SystemConstant.ADD_FRIEND, SystemConstant.SUCCESS, message));
            }
            else{
                //通知对方已下线
                message.setContent(message.getReceiver() + "已下线！");
                msgHandlerThread.response(Protocol.retData(SystemConstant.ADD_FRIEND, SystemConstant.FAILURE, message));
            }
        }
        //接收方发来回复
        else{
            MsgHandlerThread aimThread = TCPServer.threadMap.get(message.getSender());
            UserDao userDao = new UserDao();
            if (message.getContent().equals(SystemConstant.SUCCESS)){
                //数据库添加好友关系
                userDao.addFriendShip(message.getSender(), message.getReceiver());
                userDao.addFriendShip(message.getReceiver(), message.getSender());
                //刷新receiver方的好友列表
                msgHandlerThread.response(Protocol.retData(SystemConstant.FRIEND_LIST, SystemConstant.SUCCESS, userDao.getUserList(message.getReceiver())));
                message.setContent(message.getReceiver() + "通过了你的好友申请!");
            }else {
                message.setContent(message.getReceiver() + "拒绝了你的好友申请!");
            }
            //请求好友方未下线，就向请求方返回通知
            if (aimThread != null){
                //发送消息
                aimThread.response(Protocol.retData(SystemConstant.ADD_FRIEND, SystemConstant.SUCCESS, message));
                //重新加载好友列表
                aimThread.response(Protocol.retData(SystemConstant.FRIEND_LIST, SystemConstant.SUCCESS, userDao.getUserList(message.getSender())));
            }
        }

        return SystemConstant.ADD_FRIEND;
    }

    //删除好友
    public String deleteFriend(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        Message message = Parser.getMessage(data);
        UserDao userDao = new UserDao();
        //删除成功！
        if(userDao.deleteFriendShip(message.getSender(), message.getReceiver()) &&
                userDao.deleteFriendShip(message.getReceiver(), message.getSender())){
            //返回删除成功消息
            msgHandlerThread.response(Protocol.retData(SystemConstant.DELETE_FRIEND, SystemConstant.SUCCESS, message));
            //重新加载好友列表
            msgHandlerThread.response(Protocol.retData(SystemConstant.FRIEND_LIST, SystemConstant.SUCCESS, userDao.getUserList(message.getSender())));
            //好友在线的话，同时更新好友的主界面
            MsgHandlerThread aimthread = TCPServer.threadMap.get(message.getReceiver());
            if (aimthread != null){
                aimthread.response(Protocol.retData(SystemConstant.FRIEND_LIST, SystemConstant.SUCCESS, userDao.getUserList(message.getReceiver())));
            }
        }else{
            //返回失败信息
            msgHandlerThread.response(Protocol.retData(SystemConstant.DELETE_FRIEND, SystemConstant.FAILURE, message));
        }

        return SystemConstant.DELETE_FRIEND;
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
        //TODO
        TCPServer.threadMap.remove(message.getSender());
        return SystemConstant.DISCONNECT;
    }

}
