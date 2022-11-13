package com.yts.myqq.ui;

import com.yts.myqq.controller.UserController;
import com.yts.myqq.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JDialog implements ActionListener {

    JLabel nameLable = new JLabel("昵称", Label.RIGHT);
    JTextField nameField = new JTextField();
    JLabel passwordLable1 = new JLabel("密码", Label.RIGHT);
    JPasswordField passwordField1 = new JPasswordField();
    JLabel passwordLable2 = new JLabel("确认密码", Label.RIGHT);
    JPasswordField passwordField2 = new JPasswordField();
    JButton submitBtn = new JButton("确认");
    JButton cancelBtn = new JButton("取消");

    public RegisterView(LoginView loginView){
        super(loginView, "欢迎注册", true);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,20));
        panel.setBackground(new Color(106, 206, 231));
        nameLable.setPreferredSize(new Dimension(100, 40));
        nameLable.setForeground(Color.WHITE);
        nameLable.setFont(new Font("正楷", Font.BOLD, 18));
        nameField.setPreferredSize(new Dimension(200, 40));
        passwordLable1.setPreferredSize(new Dimension(100, 40));
        passwordLable1.setForeground(Color.WHITE);
        passwordLable1.setFont(new Font("正楷", Font.BOLD, 18));
        passwordField1.setPreferredSize(new Dimension(200, 40));
        passwordLable2.setPreferredSize(new Dimension(100, 40));
        passwordLable2.setForeground(Color.WHITE);
        passwordLable2.setFont(new Font("正楷", Font.BOLD, 18));
        passwordField2.setPreferredSize(new Dimension(200, 40));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 0));
        btnPanel.setBackground(new Color(106, 206, 231));
        submitBtn.setPreferredSize(new Dimension(80, 40));
        submitBtn.setFont(new Font("正楷", Font.BOLD, 14));
        cancelBtn.setPreferredSize(new Dimension(80, 40));
        cancelBtn.setFont(new Font("正楷", Font.BOLD, 14));
        submitBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(submitBtn);
        btnPanel.add(cancelBtn);

        panel.add(nameLable);
        panel.add(nameField);
        panel.add(passwordLable1);
        panel.add(passwordField1);
        panel.add(passwordLable2);
        panel.add(passwordField2);
        panel.add(btnPanel);

        //主窗体设置
        Container contentPane = getContentPane();
        contentPane.add(panel);
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("images/qq.jpg")).getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterView(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn){
            String name = nameField.getText();
            String password1 = new String(passwordField1.getPassword());
            String password2 = new String(passwordField2.getPassword());
            if (name == null || "".equals(name)){
                JOptionPane.showMessageDialog(this, "昵称不能为空！");
                return;
            }
            if (password1 == null || "".equals(password1) || password2 == null || "".equals(password2)){
                JOptionPane.showMessageDialog(this, "密码不能为空！");
                return;
            }
            if (!password1.equals(password2)){
                JOptionPane.showMessageDialog(this, "两次密码不一致！");
                return;
            }
            System.out.println("name: " + name + ", password1: " + password1 + ", password2: " + password2);
            User user = new User(name, null, password1);
            UserController userController = new UserController();
            //注册
            if ((user = userController.register(user))!=null){
                JOptionPane.showMessageDialog(this, "注册成功！你的QQ号是：" + user.getAccount());
                this.dispose();
            }
        }else{
            this.dispose();
        }
    }
}
