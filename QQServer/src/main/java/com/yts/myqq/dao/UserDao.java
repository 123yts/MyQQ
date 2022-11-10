package com.yts.myqq.dao;

import com.yts.myqq.model.User;
import com.yts.myqq.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public User getUser(String account, String password){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "select * from user where account = ? and password = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, account);
            pst.setString(2, password);
            resultSet = pst.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getString("name"),
                        resultSet.getString("account"), resultSet.getString("password"));
            }
        } catch (SQLException throwables) {
            System.out.println("查询失败！");
            throwables.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection, pst, resultSet);
        }
        return null;
    }

    public List<User> getUserList(String account){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "SELECT u.name, u.account, u.password FROM USER u INNER JOIN friend f ON u.account = f.friend AND f.`me` = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, account);
            resultSet = pst.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()){
                userList.add(new User(resultSet.getString("u.name"), resultSet.getString("u.account"),
                        resultSet.getString("u.password")));
            }
            return userList;

        } catch (SQLException throwables) {
            System.out.println("查询失败！");
            throwables.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection, pst, resultSet);
        }
        return null;
    }

    public boolean insertUser(User user){
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "insert into user values (?, ?, ?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getAccount());
            pst.setString(2, user.getName());
            pst.setString(3, user.getPassword());
            return pst.executeUpdate() > 0 ? true : false;

        } catch (SQLException throwables) {
            System.out.println("查询失败！");
            throwables.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection, pst, null);
        }
        return false;
    }

}
