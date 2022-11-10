package com.yts.myqq.ui.handler;

import com.yts.myqq.model.User;
import com.yts.myqq.ui.ChatView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FriendListHandler implements MouseListener {

    private JList<User> friendList;  //好友列表

    public FriendListHandler(JList<User> friendList){
        this.friendList = friendList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            System.out.println("点了两次！");
            User user = friendList.getSelectedValue();
            if (user != null){
                System.out.println("user值为： " + user);
                //加载对应的聊天界面, 并放入Map中
                ChatView.chatViewMap.put(user.getAccount(), new ChatView(user));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
