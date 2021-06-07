/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import web.DbListener;
import static web.DbListener.getConnection;

/**
 *
 * @author Fabio
 */
public class User {
    private String login;
    private String name;
    private String role;
    
    public static String getCreateStatement(){
        return "create table if not exists users("
                + "login varchar(50) unique not null,"
                + "name varchar(200) not null,"
                + "role varchar(20) not null,"
                + "password_hash long not null"
                + ")";
    }
    
    public static ArrayList<User> getUsers() throws Exception{
        ArrayList<User> list = new ArrayList<>();
        
        Connection con = DbListener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        while(rs.next()){
            String login = rs.getString("login");
            String name = rs.getString("name");
            String role = rs.getString("role");
            list.add(new User(login, name, role));
        }
        stmt.close();
        con.close();
        
        return list;
    }
    
    
    public static User getUser(String login, String password) throws Exception{
        User user = null;
        
        Connection con = DbListener.getConnection();
        String sql = "select * from users where login=? AND password_hash=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setLong(2, password.hashCode());
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            String name = rs.getString("name");
            String role = rs.getString("role");
            user = new User(login, name, role);
        }
        stmt.close();
        con.close();
        
        return user;
    }
    
    
    public static void insertUser(String login, String name, String role, String password) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "insert into users(login, name, role, password_hash) "
                + "VALUES(?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setString(2, name);
        stmt.setString(3, role);
        stmt.setLong(4, password.hashCode());
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void deleteUser(String login) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "delete from users where login = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void changePassword(String login, String password) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "update users set password_hash = ? where login = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setLong(1, password.hashCode());
        stmt.setString(2, login);
        stmt.execute();
        stmt.close();
        con.close();
    }

    public User(String login, String name, String role) {
        this.login = login;
        this.name = name;
        this.role = role;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
