package com.yts.myqq.ui.ext;

import com.yts.myqq.model.User;

import javax.swing.*;
import java.awt.*;

public class FriendCellRender extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        //好友
        User user = (User)value;
        //JPanel friendPanel = new JPanel();
        //friendPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg"));
        icon.setImage(icon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        JLabel label = new JLabel( user.getName() + "", icon, JLabel.LEFT);
        label.setPreferredSize(new Dimension(300, 75));

        //friendPanel.add(label);
//        setText("值为"+value);
//        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg"));
//        icon.setImage(icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
//        setIcon(icon);

        //label.addMouseListener(this);
//        if (isSelected){
//            System.out.println("被选中了，number为： " + value);
//        }
//        if (cellHasFocus){
//            System.out.println("focus: " + value);
//        }

        return label;//super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }


}
