package com.yts.myqq.uibackup;

import com.yts.myqq.uibackup.handler.LoginHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame{

    JLabel accountLabel;
    JTextField account;
    JLabel passwordLabel;
    JPasswordField password;
    JButton loginBtn;
    JButton registerBtn;
    LoginHandler loginHandler = new LoginHandler(this);

    public JTextField getAccount(){
        return this.account;
    }

    public JPasswordField getPassword(){
        return this.password;
    }


    public LoginView(){
        super("登录界面");
        //JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3,1));

        accountLabel = new JLabel("账号");
        accountLabel.setPreferredSize(new Dimension(50, 30));
        account = new JTextField();
        account.setPreferredSize(new Dimension(150, 30));

        passwordLabel = new JLabel("密码");
        passwordLabel.setPreferredSize(new Dimension(50, 30));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(150, 30));

        loginBtn = new JButton("登录");
        registerBtn = new JButton("注册");

        loginBtn.addActionListener(loginHandler);
        registerBtn.addActionListener(e -> new RegisterView());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(accountLabel);
        topPanel.add(account);

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.add(passwordLabel);
        middlePanel.add(password);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(loginBtn);
        bottomPanel.add(registerBtn);

        contentPane.add(topPanel);
        contentPane.add(middlePanel);
        contentPane.add(bottomPanel);

        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new LoginView();
    }

}
