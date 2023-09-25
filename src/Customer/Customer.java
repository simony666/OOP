package Customer;

import util.User;
import util.Role;

/**
 *
 * @author yongc
 */
public class Customer extends User{
    
    
    
    public Customer(String username, String email, String name, Role role) {
        super(username,email,name,role);
    }
    
    public static Customer login(String username, String password){
        User user = User.login(username,password);
        if (user == null){
            return null;
        }else{
            return new Customer(user.getUsername(),user.getEmail(),user.getName(),user.getRole());
        }
    }
    
    
}
