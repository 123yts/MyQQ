package com.yts.myqq.ui.ext;

import com.yts.myqq.controller.MsgController;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FriendCellRender extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        //好友
        User user = (User)value;
        JPanel friendPanel = new JPanel();
        friendPanel.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg"));
        icon.setImage(icon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        JLabel friendInfoLabel = new JLabel( user.getName() + "", icon, JLabel.LEFT);
        friendInfoLabel.setFont(new Font("正楷", Font.BOLD, 18));
        friendInfoLabel.setPreferredSize(new Dimension(200, 75)); //300

        JLabel msgCountLabel = new JLabel();
        int totalCount = 0;
        msgCountLabel.setPreferredSize(new Dimension(100,75));

        msgCountLabel.setFont(new Font("正楷", Font.BOLD, 16));
        msgCountLabel.setForeground(Color.red);

        if (MsgController.notReadMsgCountMap.get(user.getAccount()) != null){
            totalCount = MsgController.notReadMsgCountMap.get(user.getAccount());
        }
        if (totalCount > 0){
            msgCountLabel.setText(totalCount + "条未读");
        }
        friendPanel.add(friendInfoLabel);
        friendPanel.add(msgCountLabel);

        return friendPanel;
    }

    public int getNoReadMsgCount(User user){
        List<Message> msgList;
        if ((msgList = MsgController.messageMap.get(user.getAccount()))!=null){
            int totalCount = 0;
            for (int i = 0; i < msgList.size(); i++) {
                if (!msgList.get(i).isReaded()) {
                    totalCount++;
                }
            }
            return totalCount;
        }
        return 0;
    }


}
