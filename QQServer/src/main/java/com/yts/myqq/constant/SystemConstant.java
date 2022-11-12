package com.yts.myqq.constant;

public class SystemConstant {
    public static final String MSG_TYPE = "MSG_TYPE";  //消息类型请求头（4种）

    public static final String LOGIN = "LOGIN";  //1、登录请求
    public static final String REGISTER = "REGISTER";  //2、注册请求
    public static final String CHAT = "CHAT";   //3、聊天消息
    public static final String ADD_FRIEND = "ADD_FRIEND";   //4、添加好友
    public static final String DISCONNECT = "DISCONNECT";  //5、关闭socket连接

    public static final String DATA = "DATA";   //请求数据

    //===================================================
    public static final String RET_CODE = "RET_CODE";   //响应数据请求头
    public static final String SUCCESS = "200";   //1、成功
    public static final String FAILURE = "404";   //2、失败


    public static final String RET_TYPE = "RET_TYPE";   //响应数据类型请求头
    public static final String FRIEND_LIST = "FRIEND_LIST";   //1、用户列表 2、聊天信息（同上 CHAT ）
    public static final String ON_LINE = "ON_LINE";   //3、在线
    public static final String OFF_LINE = "OFF_LINE";   //4、离线

    public static final String RET_DATA = "RET_DATA";   //响应数据


}
