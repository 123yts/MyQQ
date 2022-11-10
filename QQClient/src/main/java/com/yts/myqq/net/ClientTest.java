package com.yts.myqq.net;

import com.yts.myqq.controller.MsgController;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;

import java.util.Scanner;

public class ClientTest {
    public ClientTest(){
        Scanner scanner = new Scanner(System.in);
        MsgController msgController = new MsgController();
        //TCPClient connection = TCPClient.getConnection();
        while (true){
            System.out.print("请输入发送的QQ号： ");
            String number = scanner.nextLine();
            System.out.print("请输入发送的消息： ");
            String content = scanner.nextLine();
            Message message = new Message(User.myself.getAccount(), number, content);
            msgController.sendMessage(message);
            //235668
        }
    }
}
