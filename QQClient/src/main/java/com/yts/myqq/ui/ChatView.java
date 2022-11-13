package com.yts.myqq.ui;

import com.yts.myqq.controller.MsgController;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.ui.ext.MessageCellRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatView extends JFrame{


    public static Map<String, ChatView> chatViewMap = new HashMap<>();


    private User friend;    //好友账号
    private DefaultListModel<Message> listModel; //消息列表数据


    public void reloadChatView() {
        System.out.println("执行reloadChatView方法");
        //DefaultListModel<Message> listModel = new DefaultListModel<>();
        List<Message> list;
        if ((list = MsgController.messageMap.get(friend.getAccount())) != null){
            listModel.clear();
            list.forEach(message -> listModel.addElement(message));
        }

    }

    public ChatView(User friend){
        super("聊天界面");

        this.friend = friend;

        Container contentPane = getContentPane();
        //标题panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(51, 153, 255));
        JLabel titleLabel = new JLabel(friend.getName() + "(" + friend.getAccount() + ")", JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(800, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 22));
        //titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        //消息列表panel
        JPanel listPanel = new JPanel(null);
        JScrollPane listScroll = new JScrollPane();
        listModel = new DefaultListModel<>();
        JList<Message> messageJList = new JList<>(listModel);
        messageJList.setCellRenderer(new MessageCellRender());

        //初始化消息列表
        reloadChatView();

        listScroll.setBounds(22,0,850, 500);
        listScroll.setViewportView(messageJList);
        listPanel.add(listScroll);

        //发送消息文本框panel
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        //发送消息文本框
        JTextArea textArea = new JTextArea("", 5, 40);
        textArea.setLineWrap(true); //自动换行
        textArea.setForeground(Color.RED); //背景颜色
        textArea.setFont(new Font("华文宋体", Font.BOLD, 15));
        textPanel.add(textArea);
        //发送按钮
        JButton sendBtn = new JButton("发送");
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //存入消息列表
                String text = textArea.getText();
                if (text == null || "".equals(text)) return;
                System.out.println(text);
                Message message = new Message(User.myself.getAccount(), friend.getAccount(), text, false);
                //发送未读消息给服务器
                MsgController msgController = new MsgController();
                msgController.sendMessage(message);
                //自己发的消息设置为已读
                message.setReaded(true);
                MsgController.addMessage(friend.getAccount(), message);
                //置空
                textArea.setText("");
                MsgController.messageMap.get(friend.getAccount()).forEach(item -> System.out.println(item));
                //设置list数据
                reloadChatView();

            }
        });
        textPanel.add(sendBtn);

        //总体绘制
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(listPanel, BorderLayout.CENTER);
        contentPane.add(textPanel, BorderLayout.SOUTH);

        //主窗体设置
        contentPane.setBackground(new Color(51, 153, 255));
        setSize(900, 600);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //关闭窗口时调用回调函数，释放socket资源
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //关闭socket
                ChatView.chatViewMap.remove(friend.getAccount());
                ChatView.this.dispose();
            }
        });
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChatView(new User("我爱学Java", "123456", "666666"));
    }

}
