/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import java.util.Scanner;
import util.ClearScreen;
import util.Role;
import util.Validator;

/**
 *
 * @author yongc
 */
public class CustomerManager {
    public CustomerManager(){
    }
    
    public static boolean cusEnter(){
        do{
        System.out.println("=====================================");
        System.out.println("======== Customer Interface  ========");
        System.out.println("=====================================");
        System.out.println("====== 1) Login                ======");
        System.out.println("====== 2) Sign Up              ======");
        System.out.println("====== 3) Exit                 ======");
        System.out.println("====================================="+"\n");
        
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);
        
                // Use switch case to complete the selection operation
                switch(selection){

                    // Login
                    case 1:
                        ClearScreen.cls();
                        return Login();

                    // Manage Performance     
                    case 2:
                        ClearScreen.cls();
                        return SignUp();
                        //break;
                        
                    case 3:
                        ClearScreen.cls();
                        return false;
                    // other than 1 to 7 input 
                    default:
                        System.out.println("Invalid Range, Please enter number between 1 to 6");
                        cusEnter();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }while(true);
        
        
        //return false;
    }
    
    private static boolean Login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("========      Login       ===========");
        System.out.println("=====================================");
    
        do{


            System.out.print("Please enter username: ");
            String username = sc.nextLine();

            System.out.print("Please enter password: ");
            String password = sc.nextLine();

            Customer user = Customer.login(username, password);
            if (user != null){
                if (user.getRole() == Role.Admin){
                    return true;
                }else{
                    System.out.println("User is not Admin!");
                }
            }else{
                    System.out.println("Wrong Username/Password");
            }
                
            System.out.println("Login again? (Y)es/(N)o ");
            String opt = sc.nextLine();
            
            switch (opt){
                case "Y":
                case "y":
                    break;
                case "N":
                case "n":
                    return false;
            }
        }while(true);
    }
    
    private static boolean SignUp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("========      Sign Up     ===========");
        System.out.println("=====================================");
    
        do{


            System.out.print("Please enter username: ");
            String username = sc.nextLine();
            boolean data = Customer.signup(username);
            if (!data){
                System.out.print("Username Already Used!");
                System.out.println("Continue to Sign Up? (Y)es/(N)o ");
                String opt = sc.nextLine();

                switch (opt){
                    case "Y":
                    case "y":
                        break;
                    case "N":
                    case "n":
                        return false;
                }
                
                continue;
            }
            
            System.out.print("Please enter password: ");
            String password = sc.nextLine();
            
            System.out.print("Please enter name: ");
            String name = sc.nextLine();
            
            System.out.print("Please enter email: ");
            String email = sc.nextLine();

            boolean signup = Customer.signup(username, password, name, email);
            if (signup){
                Login();
            }
            System.out.println("Wrong Username/Password");
            System.out.println("Sign Up again? (Y)es/(N)o ");
            String opt = sc.nextLine();
            
            switch (opt){
                case "Y":
                case "y":
                    break;
                case "N":
                case "n":
                    return false;
            }
        }while(true);
    }
}
