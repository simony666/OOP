package main;

import Artist.ArtistManagement;
import Performance.PerformanceManagement;
import Schedule.ScheduleManagement;
import util.Validator;
import java.util.Scanner;
import util.ClearScreen;
import util.Database;

/**
 *
 * @author Zy
 */
public class mainScreen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         Database database = new Database();
        ClearScreen.cls();
        //ArtistManagement.displayArtistScreen();
        displayMainScreen();
        
    }
    
    public static void displayMainScreen(){

    do{
        // Main login screen
        System.out.println("===========================");
        System.out.println("===== Login Interface =====");
        System.out.println("===========================");
        System.out.println("==== 1) Admin Login    ====");
        System.out.println("==== 2) Customer Login ====");
        System.out.println("==== 3) Exit           ====");
        System.out.println("==========================="+"\n");
    
        
        try{
            // capture user input
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);

                // Use switch case to complete the selection operation
                switch(selection){
                    // Admin login
                    case 1:
                         ClearScreen.cls();
                         displayAdmin();
                         break;

                    // customer login      
                    case 2:
                        ClearScreen.cls();
                        System.out.println("Welcome ???");
                        break;
                    
                    // exit 
                    case 3:    
                        System.out.println("Thanks for using");
                        System.exit(0);
                        break;
                        
                    // other than 1 to 3 input 
                    default:
                        System.out.println("Invalid Range, Please enter number between 1 to 3");
                        displayMainScreen();
                }
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid Input. Please enter a numeric value.");
        }
    }while(true);
}
    
public static void displayAdmin(){
        
    do{
         // Main login screen
        System.out.println("=====================================");
        System.out.println("======== Admin Interface  ===========");
        System.out.println("=====================================");
        System.out.println("====== 1) Manage Artist        ======");
        System.out.println("====== 2) Manage Performance   ======");
        System.out.println("====== 3) Manage Schedule      ======");
        System.out.println("====== 4) Manage Customer Seat ======");
        System.out.println("====== 5) Manage Payment       ======");
        System.out.println("====== 6) Manage Customer      ======");
        System.out.println("====== 7) Back                 ======");
        System.out.println("====================================="+"\n");
    
        try{
            // capture user input
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);
        
                // Use switch case to complete the selection operation
                switch(selection){

                    // Manage Artist
                    case 1:
                        ClearScreen.cls();
                         ArtistManagement.displayArtistScreen();
                         break;

                    // Manage Performance     
                    case 2:
                        ClearScreen.cls();
                        PerformanceManagement.displayPerformanceScreen();
                        break;

                    // Manage schedule
                    case 3:
                        ClearScreen.cls();
                        ScheduleManagement.displayScheduleScreen();
                        break;
                        
                    // Manage Customer Seat
                    case 4:
                         ClearScreen.cls();
                         System.out.println("Manage Customer Seat");
                         break;

                    // Manage Payment
                    case 5:
                        ClearScreen.cls();
                         System.out.println("Manage Payment");
                         break;

                    // Manage Customer     
                    case 6:
                        ClearScreen.cls();
                         System.out.println("Manage Customer");
                         break;

                   // Back to main menu    
                    case 7:
                        ClearScreen.cls();
                        displayMainScreen();
                        break;

                    // other than 1 to 7 input 
                    default:
                        System.out.println("Invalid Range, Please enter number between 1 to 6");
                        displayAdmin();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }while(true);        
    }
}
