package com.yts.myqq.uibackup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JListTest extends JFrame implements MouseListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JList<String> list;
    private DefaultListModel<String> listModel;  //波罗渔夫的添加项

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JListTest frame = new JListTest();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JListTest() {
        setTitle("JList测试");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 306, 300);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 12, 223, 195);
        contentPane.add(scrollPane);

        // //波罗渔夫的添加项
        listModel =new DefaultListModel<String>();
        list = new JList<String>(listModel);

        scrollPane.setViewportView(list);

        textField = new JTextField();
        textField.setBounds(35, 234, 74, 17);
        contentPane.add(textField);
        textField.setColumns(10);


	 //波罗渔夫的添加项/
        JButton btnNew = new JButton("更新");
        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModel.addElement(textField.getText());
                textField.setText("");
            }
        });
        //
        btnNew.setBounds(121, 231, 64, 23);
        contentPane.add(btnNew);

        /// //波罗渔夫的添加项//
        JButton btnDel = new JButton("删除");
        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModel.removeElement(list.getSelectedValue());

            }
        });

        btnDel.setBounds(197, 231, 64, 23);
        contentPane.add(btnDel);

        JLabel lblName = new JLabel("输入姓名：");
        lblName.setBounds(38, 219, 74, 13);
        contentPane.add(lblName);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){

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
