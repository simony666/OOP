/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Staff;

import Customer.Customer;
import java.util.ArrayList;
import java.util.Scanner;
import util.ClearScreen;
import util.Database;
import util.Role;
import util.User;
import util.Validator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author yongc
 */
public class StaffManager {
    
    private static ArrayList<User> userArray = getUserList();
    
    public StaffManager(){
    }
    
    public static boolean staffEnter(){
        do{
        System.out.println("=====================================");
        System.out.println("======== Admin Interface  ===========");
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
        
                switch(selection){

                    case 1:
                        ClearScreen.cls();
                        return Login();

                    case 2:
                        ClearScreen.cls();
                        return SignUp();
                        
                    case 3:
                        ClearScreen.cls();
                        return false;

                    default:
                        System.out.println("Invalid Range, Please enter number between 1 to 6");
                        staffEnter();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }while(true);
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

            Staff user = Staff.login(username, password);
            if (user != null){
                if (user.getRole() == Role.Admin){
                    return true;
                }else{
                    System.out.println("User is not Admin!");
                }
            }else{
                    System.out.println("Wrong Username/Password");
            }
                
            do{
                System.out.println("Login again? (Y)es/(N)o ");
                String opt = sc.nextLine();

                switch (opt.toLowerCase()){
                    case "y":
                        Login();
                    case "n":
                        return false;
                    default:
                        System.out.println("Invalid option. Please enter 'Y' or 'N'.");
                }
            }while(true);
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
            boolean data = Staff.signup(username);
            if (!data){
                System.out.println("Username Already Used!");
                System.out.println("Continue to Sign Up? (Y)es/(N)o ");
                String opt = sc.nextLine();

                switch (opt.toLowerCase()){
                    case "y":
                        return SignUp();
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

            boolean signup = Staff.signup(username, password, name, email);
            if (signup){
                Login();
            }
            System.out.println("Wrong Username/Password");
                        
            do{
                System.out.println("Login again? (Y)es/(N)o ");
                String opt = sc.nextLine();

                switch (opt.toLowerCase()){
                    case "y":
                        return Login();
                    case "n":
                        return false;
                    default:
                        System.out.println("Invalid option. Please enter 'Y' or 'N'.");
                }
            }while(true);
        }while(true);
    }
    
    public static void manageCus(){
        do{
        System.out.println("=====================================");
        System.out.println("======== Customer Management ========");
        System.out.println("=====================================");
        System.out.println("====== 1) Get Data                ====");
        System.out.println("====== 2) List Users              ====");
        System.out.println("====== 3) Find User               ====");
        System.out.println("====== 4) Modify User             ====");
        System.out.println("====== 5) Back to Main Menu       ====");
        System.out.println("=====================================\n");
        
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);
        
                switch(selection){

                    case 1:
                        ClearScreen.cls();
                        getData();
                        break;
                    case 2:
                        ClearScreen.cls();
                        listUser();
                        break;
                        
                    case 3:
                        ClearScreen.cls();
                        findUser();
                    case 4:
                        ClearScreen.cls();
                        modUser();
                    case 5:
                        return;
                        
                    default:
                        System.out.println("Invalid Range, Please enter number between 1 to 5");
                        manageCus();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }while(true);
    }
    
    private static void modUser() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("========    Modify User    ==========");
        System.out.println("=====================================");

        System.out.print("Please enter the username to modify: ");
        String username = sc.nextLine();

        User foundUser = null;

        for (User user : userArray) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                foundUser = user;
                break;
            }
        }

        if (foundUser != null) {
            modifyUser(foundUser);
        } else {
            System.out.println("User not found.");
        }
    }
    
    private static void getData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("========      Get Data     ==========");
        System.out.println("=====================================");

        do {
            System.out.print("Please enter the username: ");
            String username = sc.nextLine();

            User foundUser = null;

            for (User user : userArray) {
                if (user.getUsername().equals(username)) {
                    foundUser = user;
                    break;
                }
            }

            if (foundUser != null) {
                System.out.println("User Info:");
                System.out.println("Username: " + foundUser.getUsername());
                System.out.println("Name: " + foundUser.getName());
                System.out.println("Email: " + foundUser.getEmail());
                System.out.println("Role: " + foundUser.getRole());

                System.out.print("(M)odify User, (S)earch another User or (B)ack to Main Menu");
                String opt = sc.nextLine();

                switch (opt.toLowerCase()) {
                    case "m":
                        modifyUser(foundUser);
                        break;
                    case "s":
                        getData();
                        break;
                    case "b":
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 'M','S'or'B'.");
                }
            } else {
                System.out.println("User not found.");
                System.out.print("(S)earch another User or (B)ack to Main Menu: ");
                String opt = sc.nextLine();

                switch (opt.toLowerCase()) {
                    case "s":
                        getData();
                        break;
                    case "b":
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 'S' or 'B'.");
                }
            }
        } while (true);
    }
    
    private static void modifyUser(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("========    Modify User    ==========");
        System.out.println("=====================================");
        System.out.println("User Info:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
        System.out.println("=====================================");

        do {
            System.out.println("What would you like to modify?");
            System.out.println("1) Password");
            System.out.println("2) Name");
            System.out.println("3) Role");
            System.out.println("4) Email");
            System.out.println("5) Cancel");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    user.setPassword(newPassword);
                    break;
                case "2":
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    user.setName(newName);
                    break;
                case "3":
                    System.out.print("Enter new role (Admin, Artist, Customer): ");
                    String newRoleStr = sc.nextLine();
                    Role newRole = Role.Customer;
                    switch (newRoleStr.toLowerCase()) {
                        case "admin":
                            newRole = Role.Admin;
                            break;
                        case "artist":
                            newRole = Role.Artist;
                            break;
                        case "customer":
                            newRole = Role.Customer;
                            break;
                        default:
                            System.out.println("Invalid role. Role set to Customer.");
                    }
                    user.setRole(newRole);
                    break;
                case "4":
                    System.out.print("Enter new email: ");
                    String newEmail = sc.nextLine();
                    user.setEmail(newEmail);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            String sqlText = "update `User` set " +
                             "`password` = '" + user.getPassword() + "', " +
                             "`name` = '" + user.getName() + "', " +
                             "`role` = '" + user.getRole() + "', " +
                             "`email` = '" + user.getEmail() + "' " +
                             "where `username` = '" + user.getUsername() + "';";

            int updateResult = Database.runUpdate(sqlText);

            if (updateResult > 1) {
                System.err.println("Multiple users changed!");
            } else if (updateResult == 0) {
                System.err.println("Update unsuccessful.");
            } else {
                System.out.println("Update successful.");
                userArray = getUserList();
                return;
            }
        } while (true);
    }

    private static void listUser() {
        Scanner sc = new Scanner(System.in);
        int pageSize = 20;
        int currentPage = 1;

        do {
            System.out.println("=====================================");
            System.out.println("========     List Users     =========");
            System.out.println("=====================================");

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, userArray.size());

            System.out.println(String.format("| %-15s | %-25s | %-30s | %-15s |", "Username", "Name", "Email", "Role"));
            System.out.println("| --------------- | ------------------------- | ---------------------------------- | --------------- |");

            for (int i = startIndex; i < endIndex; i++) {
                User user = userArray.get(i);
                System.out.println(String.format("| %-15s | %-25s | %-30s | %-15s |", 
                    user.getUsername(), user.getName(), user.getEmail(), user.getRole()));
            }

            int totalPages = (int) Math.ceil((double) userArray.size() / pageSize);
            System.out.println("=====================================");
            System.out.println("Page: " + currentPage + "/" + totalPages);
            System.out.println("=====================================");

            System.out.print("(P)revious page | (N)ext page | (Q)uit: ");
            String choice = sc.nextLine();

            switch (choice.toLowerCase()) {
                case "p":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You are already on the first page.");
                    }
                    break;
                case "n":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("You are already on the last page.");
                    }
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 'P' for previous, 'N' for next, or 'Q' to quit.");
            }
        } while (true);
    }

    private static void findUser() {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("=====================================");
            System.out.println("========    Find User     ===========");
            System.out.println("=====================================");

            String searchTerm;
            do {
                System.out.print("Please enter a name or username (at least 3 characters): ");
                searchTerm = sc.nextLine().trim();
            } while (searchTerm.length() < 3);

            System.out.println(String.format("| %-15s | %-25s | %-30s | %-15s |", "Username", "Name", "Email", "Role"));
            System.out.println("| --------------- | ------------------------- | ---------------------------------- | --------------- |");

            boolean found = false;
            for (User user : userArray) {
                if (user.getUsername().toLowerCase().contains(searchTerm.toLowerCase()) || 
                    user.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println(String.format("| %-15s | %-25s | %-30s | %-15s |", 
                        user.getUsername(), user.getName(), user.getEmail(), user.getRole()));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching records found.");
            }

            System.out.print("(S)earch next keyword | (E)xit to Main Menu: ");
            String choice = sc.nextLine();

            switch (choice.toLowerCase()) {
                case "s":
                    break;
                case "e":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 'S' to search with a new keyword or 'E' to exit to the Main Menu.");
            }
        } while (true);
    }
    
    private static ArrayList<User> getUserList(){
        ArrayList<User> tempList = new ArrayList<>();
        
        String sqlText = "select * from `User`";
        ResultSet result = Database.runQuery(sqlText);
        try {
            while(result.next()){
                String username = result.getString("username");
                String name = result.getString("name");
                String email = result.getString("email");
                String rolestr = result.getString("role");
                
                Role role = Role.Customer;
                switch(rolestr){
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
                
                User u = new User(username,email,name,role);
                tempList.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return tempList;
    }
}