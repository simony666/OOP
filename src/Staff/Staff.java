/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Staff;

import util.Role;
import util.User;

/**
 *
 * @author yongc
 */
public class Staff extends User{
    
    public Staff(String username, String email, String name, Role role) {
        super(username,email,name,role);
    }
    
    public static Staff login(String username, String password){
        User user = User.login(username,password);
        if (user == null){
            return null;
        }else{
            return new Staff(user.getUsername(),user.getEmail(),user.getName(),user.getRole());
        }
    }
}
