package com.yts.myqq.ui.handler;

import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.User;
import com.yts.myqq.ui.LoginView;
import com.yts.myqq.ui.MainView;

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
        //获取账号密码
        String account = (String) loginView.getAccount().getSelectedItem();
        String password = new String(loginView.getPassword().getPassword());

        if (!"".equals(account) || !"".equals(password)){
            User.myself = new User(account, password);
            System.out.println("user " + User.myself);
            //加锁，如果登录成功后，保证先绘制好友列表界面，再动态获取列表数据并更新界面
            //synchronized (MainView.class){
                if (userController.login(User.myself)){
                    loginView.dispose();
                    //登录成功返回主界面
                    //获取好友列表数据
                    userController.initFriendList(User.myself);

                    //创建主界面
                    MainView.mainView = new MainView();

                }else {
                    JOptionPane.showMessageDialog(loginView, "用户名或密码错误!");
                }
            //}
        }
        else{
            JOptionPane.showMessageDialog(loginView, "用户名或密码为空!");
        }

    }
}
