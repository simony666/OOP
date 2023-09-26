/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author yongc
 */
public class User extends Person{
    private String username;
    private String password;
    private String email;
    private String name;
    private int id;
    private Role role;
    
    public User(){
    }

    public User(String username, String email, String name, Role role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
    }
    
    //first call
    public static boolean signup(String username){
        return getUsername(username);
    }
    
    public static boolean signup(String username, String pass,String name,String email){
        Role role = Role.Customer;
        String sqlText= "insert into `User` (`username`,`password`,`name`,`role`,`email`) values (\""+ username +"\",\""+pass+"\",\""+name+"\",\""+role+"\",\""+email+"\");";
        int result = Database.runUpdate(sqlText);
        
        return result == 1;
    }
    
    public static User login(String username, String password){
        String sqlText = "select * from `User` where `username`=\""+username+"\";";
        ResultSet result = Database.runQuery(sqlText);
        
        try {
            while(result.next()){
                String pass = result.getString("password");
                if (password.equals(pass)){
                    String rolestr = result.getString("role");
                    Role role = Role.Customer;
                    switch (rolestr){
                        case "Admin":
                            role = Role.Admin;
                            break;
                        case "Artist":
                            role = Role.Artist;
                            break;
                        case "Customer":
                            role = Role.Customer;
                            break;
                    }
                    
                    String user = result.getString("username");
                    String email = result.getString("email");
                    String name = result.getString("name");
                    return new User(user, email, name, role);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private static boolean getUsername(String username){
        String sqlText= "select COUNT(*) from `User` where username = \""+ username +"\"";
        ResultSet result = Database.runQuery(sqlText);
        try {
            if (result.next()) {
                int count = result.getInt(1);
                return count == 0; // If count is 0, the username doesn't exist; otherwise, it exists.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("5");
        return false;
    }
    
    public boolean updateUsername(String newUsername){
        if (getUsername(newUsername)){
            return false;
        }
        
        String sqlText = "update `User` set `username` = \""+newUsername+"\" where `username` = \""+this.username+"\"; ";
        int result = Database.runUpdate(sqlText);
        if (result==1){
            return true;
        }else if (result >1){
            System.err.println("Error: Multiple Username Changed!!!");
            return false;
        }else{
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
