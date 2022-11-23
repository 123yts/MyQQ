package com.yts.myqq.ui.ext;

import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;

import javax.swing.*;
import java.awt.*;

public class MessageCellRender extends DefaultListCellRenderer {

    private User friend;

    public MessageCellRender(User user){
        super();
        this.friend = user;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Message message = (Message)value;
        JLabel messageLabel;
        //JPanel messagePanel;
        if (User.myself.getAccount().equals(message.getSender())){
            //String content = message.getContent();
            //String strMsg = "<html><body>" + content + "<br>" + "拼接一段换行文字" + "<body></html>";
            messageLabel = new JLabel("我" + ": " + message.getContent(), JLabel.RIGHT);
            //messagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        }else{
            messageLabel = new JLabel(friend.getName() + ": " + message.getContent(), JLabel.LEFT);
            //messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        }
        //            messageLabel.setPreferredSize(new Dimension());

        messageLabel.setPreferredSize(new Dimension(100, 30));
        messageLabel.setFont(new Font("宋体", Font.BOLD, 24));
//        messagePanel.add(messageLabel);
//        messagePanel.setSize(200,  40);
//        messagePanel.setVisible(true);
        return messageLabel;
    }
}
