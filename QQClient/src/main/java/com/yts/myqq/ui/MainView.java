package com.yts.myqq.ui;

import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.controller.MsgController;
import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.ui.ext.FriendCellRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;

public class MainView extends JFrame implements MouseListener {

    public static MainView mainView = null;

    private DefaultListModel<User> listModel; //好友列表数据
    private JList<User> friendList;  //好友列表
    private JPopupMenu popupMenu;    //右键菜单

    public void reloadMainView(){
        System.out.println("reloadMainView");
        Iterator<Map.Entry<String, User>> iterator = UserController.friendMap.entrySet().iterator();
        listModel.clear();
        while (iterator.hasNext()){
            User user = iterator.next().getValue();
            listModel.addElement(user);
        }
    }



    public MainView(){

        //好友列表panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBackground(new Color(131, 123, 205));

        //1、个人信息
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg"));
        icon.setImage(icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        JLabel userLabel = new JLabel(User.myself.getName(), icon, JLabel.LEFT);
        userLabel.setBounds(12, 0, 320, 80);
        userLabel.setFont(new Font("正楷", Font.BOLD, 24));
        userLabel.setForeground(Color.WHITE);
        //userLabel.setBackground(Color.BLUE);
        listPanel.add(userLabel);

        //2、添加好友
        JLabel textLabel = new JLabel("好友列表");
        textLabel.setBounds(12, 85,60,40);
        textLabel.setFont(new Font("黑体", Font.BOLD, 14));
        listPanel.add(textLabel);

        //添加好友输入框
        JTextField textField = new JTextField(); //空白文本框,让后面文本框失去焦点，显示提示信息
        listPanel.add(textField);
        JTextField searchField = new JTextField("输入QQ号添加好友");
        searchField.setBounds(90, 90, 160, 30);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //得到焦点，清空提示文字
                if(searchField.getText().equals("输入QQ号添加好友")){
                    searchField.setText(""); //清空提示信息
                    searchField.setForeground(Color.black); //设置用户输入文字为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点，显示提示文字
                if(searchField.getText().equals("")){
                    searchField.setForeground(Color.gray); //设置用户输入文字为黑色
                    searchField.setText("输入QQ号添加好友"); //显示提示信息
                }
            }
        });
        listPanel.add(searchField);

        //添加按钮
        JButton addBtn = new JButton("添加");
        addBtn.setBounds(260, 90, 60, 30);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addBtn == e.getSource()){
                    String text = searchField.getText();
                    if (text == "" || text.length() != 3){
                        JOptionPane.showMessageDialog(MainView.this, "请输入正确的QQ号码!");
                        return;
                    }
                    if (text.charAt(0) == '0'){
                        JOptionPane.showMessageDialog(MainView.this, "请输入正确的QQ号码!");
                        return;
                    }
                    for (int i = 0; i < text.length(); i++) {
                        if (!Character.isDigit(text.charAt(i))){
                            JOptionPane.showMessageDialog(MainView.this, "请输入正确的QQ号码!");
                            return;
                        }
                    }
                    //已经是好友了
                    if (UserController.friendMap.containsKey(text)){
                        JOptionPane.showMessageDialog(MainView.this, "该用户已经是你的好友了!");
                        return;
                    }
                    //send添加好友请求
                    UserController userController = new UserController();
                    userController.addFriend(text);
                }
            }
        });
        listPanel.add(addBtn);

        //3、好友列表

        //添加右键菜单
        popupMenu = new JPopupMenu();

        JMenuItem sendMsgMenuItem = new JMenuItem("发送消息");
        sendMsgMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("发送消息！");
                User user = friendList.getSelectedValue();
                loadChatView(user);
            }
        });

        JMenuItem deleteFriendMenuItem = new JMenuItem("删除好友");
        deleteFriendMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("删除好友!");
                UserController userController = new UserController();

                User user = friendList.getSelectedValue();
                if (user != null){
                    System.out.println("user值为： " + user);
                    //收到请求
                    int option = JOptionPane.showConfirmDialog(MainView.mainView,"删除好友"+user.getName(),
                            "确认删除吗？", JOptionPane.YES_NO_OPTION);
                    //发送请求
                    if (option == JOptionPane.YES_OPTION){
                        userController.deleteFriend(user.getAccount());
                    }
                }
            }
        });


        popupMenu.add(sendMsgMenuItem);
        popupMenu.add(deleteFriendMenuItem);

        JScrollPane listScroll = new JScrollPane();
        listScroll.setBounds(12, 130, 320, 500);
        listPanel.add(listScroll);



        listModel = new DefaultListModel<>();
        //好友列表
        friendList = new JList<>(listModel);
        //添加元素渲染（显示每个好友）
        friendList.setCellRenderer(new FriendCellRender());
        //初始化用户列表
        System.out.println("mainView开始绘制----");
        reloadMainView();

        listScroll.setViewportView(friendList);
        //给好友列表设置监听器
        friendList.addMouseListener(this);
        // 设置窗体
        getContentPane().add(listPanel);

        setTitle("QQ主界面");
        //关闭窗口时调用回调函数，释放socket资源
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //关闭socket
                TCPClient.getConnection().close();
                System.exit(0);
            }
        });
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg")).getImage());
        setSize(350, 700);
        setResizable(false);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() - getWidth();
        setLocation(x, 0);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainView();
    }

    //加载聊天界面
    public void loadChatView(User user){
        if (user != null){
            System.out.println("user值为： " + user);
            //加载对应的聊天界面, 并放入Map中
            if (ChatView.chatViewMap.get(user.getAccount()) == null){
                ChatView.chatViewMap.put(user.getAccount(), new ChatView(user));
                //清空对应的未读消息
                //是否已初始化
                if (MsgController.notReadMsgCountMap.get(user.getAccount())==null) return;
                //已初始化且未读数量大于0条则清空
                if (MsgController.notReadMsgCountMap.get(user.getAccount()) > 0){
                    MsgController.clearNotReadMsg(user.getAccount());
                    MainView.mainView.reloadMainView();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            System.out.println("点了两次！");
            User user = friendList.getSelectedValue();
            loadChatView(user);
        }else {
            //右键点击，展示菜单
            if (e.getButton() == MouseEvent.BUTTON3){
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
                //为右键鼠标点击处的listItem设置选中状态
                int row = friendList.locationToIndex(e.getPoint());
                friendList.setSelectedIndex(row);
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
