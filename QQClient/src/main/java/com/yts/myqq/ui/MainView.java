package com.yts.myqq.ui;

import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.ui.ext.FriendCellRender;
import com.yts.myqq.ui.handler.FriendListHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map;

public class MainView extends JFrame implements MouseListener {

    public static MainView mainView = null;

    private DefaultListModel<User> listModel; //好友列表数据
    private JList<User> friendList;  //好友列表

    static User[] friends = new User[]{
            new User("小明", "1232697", "123456"),
            new User("小王", "1392768", "123456"),
            new User("小李", "1543273", "123456"),
            new User("小高", "1447149", "123456"),
            new User("小吴", "1287324", "123456")
    };

    public void reloadMainView(){
        System.out.println("reloadMainView");
        Iterator<Map.Entry<String, User>> iterator = UserController.friendMap.entrySet().iterator();
        while (iterator.hasNext()){
            User user = iterator.next().getValue();
            listModel.addElement(user);
        }
    }



    public MainView(){

        //JPanel mainPanel = new JPanel(null);
        //getContentPane().setLayout(new BorderLayout());

        //个人信息界面
//        JPanel userPanel = new JPanel();
//        userPanel.setLayout(null);
//        JLabel userLabel = new JLabel(User.myself.getName(), new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg")), JLabel.LEFT);
//        userLabel.setPreferredSize(new Dimension(320, 180));
//        userLabel.setFont(new Font("宋体", Font.BOLD, 22));
//        userLabel.setForeground(Color.WHITE);
//        userPanel.add(userLabel);

        //getContentPane().add(userPanel, BorderLayout.NORTH);
        //mainPanel.add(userPanel);

        //好友列表panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(null);

        JLabel userLabel = new JLabel(User.myself.getName(), new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg")), JLabel.LEFT);
        userLabel.setBounds(12, 0, 320, 100);
        userLabel.setFont(new Font("宋体", Font.BOLD, 24));
        userLabel.setForeground(Color.BLACK);
        userLabel.setBackground(Color.BLUE);
        listPanel.add(userLabel);

//        JLabel bannerLable = new JLabel("好友列表", Label.CENTER);
//        bannerLable.setBounds(12, 101, 320, 10);
//        bannerLable.setBackground(Color.WHITE);
//        listPanel.add(bannerLable);

        JScrollPane listScroll = new JScrollPane();
        listScroll.setBounds(12, 120, 320, 500);
        //listScroll.setPreferredSize(new Dimension(320, 500));
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
        friendList.addMouseListener(new FriendListHandler(this.friendList));
        // 设置窗体
        getContentPane().add(listPanel);
        //mainPanel.add(listPanel);
        //getContentPane().add(mainPanel);

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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            System.out.println("点了两次！");
            User user = friendList.getSelectedValue();
            if (user != null){
                System.out.println("user值为： " + user);
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
