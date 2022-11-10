package com.yts.myqq.uibackup.handler;

import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.User;
import com.yts.myqq.net.TCPClient;
import com.yts.myqq.uibackup.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginHandler implements ActionListener {

    private LoginView loginView;

    public LoginHandler(LoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserController userController = new UserController();
        String account = loginView.getAccount().getText().trim();
        String password = new String(loginView.getPassword().getPassword());

        if (!"".equals(account) || !"".equals(password)){
            User user = new User(account, password);
            System.out.println("user " + user);
            if (userController.login(user)){
                //登录成功返回主界面
                //new MainView();
                System.out.println("登录成功返回主界面");
                //开启循环读线程
                TCPClient.getConnection().startReadThread();
            }else {
                JOptionPane.showMessageDialog(loginView, "用户名或密码错误!");
            }
        }
        else{
            JOptionPane.showMessageDialog(loginView, "用户名或密码为空!");
        }
    }
}
