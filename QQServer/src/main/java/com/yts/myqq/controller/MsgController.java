package com.yts.myqq.controller;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.model.Message;
import com.yts.myqq.net.MsgHandlerThread;
import com.yts.myqq.net.TCPServer;
import com.yts.myqq.util.Parser;
import com.yts.myqq.util.Protocol;

import java.io.IOException;

public class MsgController {

    //聊天消息转发
    public String sendMessage(Object data, MsgHandlerThread msgHandlerThread) throws IOException {
        Message message = Parser.getMessage(data);
        System.out.println("message： " + message);
        //找到要转发的对象
        MsgHandlerThread aimThread = TCPServer.threadMap.get(message.getReceiver());
        if (aimThread != null){
            System.out.println("threadMap。size = " + TCPServer.threadMap.size());
            aimThread.response(Protocol.retData(SystemConstant.CHAT, SystemConstant.FAILURE, message));
        }
        else {
            //对方已下线，不转发消息，返回对方离线数据
            msgHandlerThread.response(Protocol.retData(SystemConstant.OFF_LINE, SystemConstant.SUCCESS, message));
        }
        return SystemConstant.SUCCESS;
    }
}
