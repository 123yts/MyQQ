package com.yts.myqq.ui;


import com.yts.myqq.net.TCPClient;
import com.yts.myqq.ui.handler.LoginHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class LoginView extends JFrame {

    //账号
    JComboBox<Object> account;
    //密码
    JPasswordField password;

    public JComboBox<Object> getAccount() {
        return account;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public LoginView() {
        Container contentPane = getContentPane();
        BorderLayout border_Layout = new BorderLayout();
        JPanel panel_north = CreateNorthPanel();
        JPanel panel_center = CrateCenterPanel();
        JPanel panel_west = CreateWestPanel();
        JPanel panel_south = CreateSouthPanel();
        JPanel panel_east = CreateEastPanel();

        contentPane.setLayout(border_Layout);
        //1.1 创建并加入顶部面板
        contentPane.add(panel_north, BorderLayout.PAGE_START);
        //1.2 创建并加入中部面板
        contentPane.add(panel_center, BorderLayout.CENTER);
        //1.3 创建并加入左侧面板
        contentPane.add(panel_west, BorderLayout.LINE_START);
        //1.4 创建并加入底部面板
        contentPane.add(panel_south, BorderLayout.PAGE_END);
        //1.5 创建并加入右侧面板
        contentPane.add(panel_east, BorderLayout.LINE_END);

        //2 根据QQ登陆界面效果，进行布局分布
        setSize(426, 300);                //设置窗口尺寸
        //setLocation(497, 242);
        setLocationRelativeTo(null);      //设置窗口在屏幕显示位置、
        setUndecorated(true);             //设置JFrame窗口边框不显示
        setResizable(false);              //禁止改变窗口大小
        //关闭窗口时调用回调函数，释放socket资源
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                TCPClient.getConnection().close();
                System.exit(0);
            }
        });
        setVisible(true);                   //设置窗口可见
    }

    public JPanel CreateNorthPanel() {
        // 创建一个JPanel顶部面板
        JPanel panel = new JPanel();
        // 取消面板内默认布局
        panel.setLayout(null);
        // 设置顶部面板尺寸
        panel.setPreferredSize(new Dimension(0, 140));
        // 1.1、向顶部面板添加背景图片
        ImageIcon image = new ImageIcon(this.getClass().getClassLoader().getResource("images/back.jpg"));
        JLabel backGround = new JLabel(image);
        // 设置背景图片的位置及尺寸
        backGround.setBounds(0, 0, 426, image.getIconHeight());
        panel.add(backGround);
        // 1.2、在顶部JPanel面板右上角添加一个退出按钮
        JButton exitBtn = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/close_normal.jpg")));
        exitBtn.addActionListener(event -> this.dispose());
        exitBtn.setBounds(403, 0, 26, 26);
        // 设置鼠标移动到退出按钮时更改图片
        exitBtn.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/close_hover.jpg")));
        // 取消按钮边框效果
        exitBtn.setBorderPainted(false);
        panel.add(exitBtn);
        // 为退出按钮注册监听器，用来关闭窗口
        exitBtn.addActionListener(event -> this.dispose());
        return panel;
    }

    //左侧面板区域
    public JPanel CreateWestPanel() {
        //左侧面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(130, 0));
        ImageIcon image = new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg"));
        JLabel backGround = new JLabel(image);
        backGround.setBounds(0, 0, 120, 110);
        panel.add(backGround);
        return panel;
    }

    //中部面板区域
    public JPanel CrateCenterPanel() {
        //创建一个JPanel中部面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //1、 创建一个JcomboBox下拉框组件，并初始化QQ账号
        String str[] = {"111", "222", "333", "444"};
        //JComboBox account=new JComboBox(str);
        account = new JComboBox<>(str);
        panel.add(account);
        //account.setBorder(null);
        //设置下拉框可编辑
        account.setEditable(true);
        account.setBounds(0, 15, 175, 30);
        //设置下拉框内容字体
        account.setFont(new Font("Calibri", 0, 13));
        //2、 创建一个JPasswordField密码框组件
        password = new JPasswordField();
        //设置密码框面板为FlowLayout流式布局
        password.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        password.setBounds(0, 44, 175, 30);
        password.setPreferredSize(new Dimension(185, 25));
        panel.add(password);
        //3、 创建ImageIcon小键盘组件，并加入到密码框组件中
        ImageIcon image = new ImageIcon(this.getClass().getClassLoader().getResource("images/keyboard.jpg"));
        JButton jbu = new JButton(image);
        jbu.setPreferredSize(new Dimension(22, 20));
        jbu.setBorderPainted(false);
        //默认先填上密码
        password.setText("123456");
        password.add(jbu);
        //4、 创建两个JCheckBox多选框组件
        JCheckBox jch1 = new JCheckBox("记住密码");
        //设置选中时不显示边框
        jch1.setFocusPainted(false);
        jch1.setFont(new Font("宋体", 0, 13));
        jch1.setBounds(0, 85, 80, 20);
        panel.add(jch1);
        JCheckBox jch2 = new JCheckBox("自动登录");
        jch2.setFocusPainted(false);
        jch2.setFont(new Font("宋体", 0, 12));
        jch2.setBounds(100, 85, 80, 20);
        panel.add(jch2);
        return panel;
    }

    //右侧面板区域
    public JPanel CreateEastPanel() {
        //创建一个Jpanel右侧面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(100, 0));
        //创建两个JLabel标签组件
        JLabel regeist = new JLabel("注册账号");
        regeist.setForeground(new Color(100, 149, 238));
        regeist.setBounds(0, 13, 60, 30);
        regeist.setFont(new Font("宋体", 0, 12));
        //创建一个JPanel右侧面板
        JLabel regetpwd = new JLabel("找回密码");
        regetpwd.setForeground(new Color(100, 149, 238));
        regetpwd.setBounds(0, 43, 60, 30);
        regetpwd.setFont(new Font("宋体", 0, 12));
        panel.add(regetpwd);
        panel.add(regeist);
        return panel;
    }

    //底部面版区域
    public JPanel CreateSouthPanel() {
        //创建一个JPanel底部面板
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 51));
        panel.setLayout(null);
        //1、  创建左下角多人登录图标组件
        JButton jble = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/single_normal.jpg")));
        jble.setPreferredSize(new Dimension(40, 40));
        jble.setFocusPainted(false);
        jble.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/single_down.jpg")));
        jble.setBorderPainted(false);
        //设置不显示按钮区域
        jble.setContentAreaFilled(false);
        jble.setBounds(0, 10, 40, 40);
        jble.setToolTipText("多账号登录");
        //2、 创建底部中间登陆图标组件
        URL url = this.getClass().getClassLoader().getResource("images/login_normal.jpg");
        ImageIcon image = new ImageIcon(url);
        JButton jb = new JButton("登         录", image);
        //为登陆按钮设置监听器
        LoginHandler loginHandler = new LoginHandler(this);
        jb.addActionListener(loginHandler);
        jb.setFont(new Font("宋体", 0, 13));//Font宋体
        jb.setBounds(130, 0, 175, 40);
        //将文字放在图片中间
        jb.setHorizontalTextPosition(SwingConstants.CENTER);
        jb.setFocusPainted(false);
        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/login_hover.jpg")));
        //3、 创建右下角二维码登录图标组件
        JButton jbri = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/right_normal.jpg")));
        jbri.setBounds(380, 10, 40, 40);
        jbri.setFocusPainted(false);
        jbri.setBorderPainted(false);
        jbri.setContentAreaFilled(false);
        jbri.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/right_hover.jpg")));
        jbri.setToolTipText("二维码登录");
        //将底部3个组件添加到底部JPanel面板中
        panel.add(jble);
        panel.add(jb);
        panel.add(jbri);
        return panel;
    }

    public static void main(String[] args) {
        new LoginView();
    }

}
