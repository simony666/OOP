package main;

import Artist.ArtistManagement;
import util.SymbolValidator;
import java.util.Scanner;

/**
 *
 * @author Zy
 */
public class mainScreen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
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

            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);

                // Use switch case to complete the selection operation
                switch(selection){
                    // Admin login
                    case 1:
                         displayAdmin();
                         break;

                    // customer login      
                    case 2:
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
        System.out.println("====== 3) Manage Customer Seat ======");
        System.out.println("====== 4) Manage Payment       ======");
        System.out.println("====== 5) Manage Customer      ======");
        System.out.println("====== 6) Back                 ======");
        System.out.println("====================================="+"\n");
    
        try{
            // capture user input
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                
                int selection = Integer.parseInt(input);
        
                // Use switch case to complete the selection operation
                switch(selection){

                    // Manage Artist
                    case 1:
                         ArtistManagement.displayArtistScreen();
                         break;

                    // Manage Performance     
                    case 2:
                        System.out.println("Manage Performance");
                        break;

                    // Manage Customer Seat
                    case 3:
                         System.out.println("Manage Customer Seat");
                         break;

                    // Manage Payment
                    case 4:
                         System.out.println("Manage Payment");
                         break;

                    // Manage Customer     
                    case 5:
                         System.out.println("Manage Customer");
                         break;

                   // Back to main menu    
                    case 6:
                        displayMainScreen();
                        break;

                    // other than 1 to 3 input 
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
