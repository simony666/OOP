package Seat;

import java.util.Scanner;
import util.SymbolValidator;


/**
 *
 * @author User
 */

public class SeatManager {

//    public static void main(String[] args){
//        Seat seat = new Seat();
//        seat.selectAll();
//    }
    
    //Display Seat Management Main Screen
    public static void displaySeatMainScreen() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "=======================================");
            System.out.println("========   Seat Management  ===========");
            System.out.println("=======================================");
            System.out.println("======= 1) Manage Seat         ========");
            System.out.println("======= 2) Manage Vanue        ========");
            System.out.println("======= 3) Manage Ticket      =========");
            System.out.println("======= 4) Back to Admin Page =========");
            System.out.println("======= 5) Exit               =========");
            System.out.println("=======================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            displaySeatScreen();
                            break;
                        case 2:
                            displayVenueScreen(); 
                            break;
                        case 3:
                            displaySeatScreen();
                            break;
                        case 4:
                            main.mainScreen.displayAdmin();
                            break;
                        case 5:
                            System.out.println("Thanks for using");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 5");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }
    }
    
    public static void displaySeatScreen() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "===========================================");
            System.out.println("=============   Manage Seat  ==============");
            System.out.println("===========================================");
            System.out.println("======= 1) Add Seat                ========");
            System.out.println("======= 2) Modify Seat             ========");
            System.out.println("======= 3) Delete Seat             ========");
            System.out.println("======= 4) Back to Management Page ========");
            System.out.println("======= 5) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            ArtistManagement.addArtist();
                            break;
                        case 2:
                            ArtistManagement.viewArtist(); 
                            break;
                        case 3:
                            updateArtist(artistList); // Pass the artistList
                            break;
                        case 4:
                            deleteArtist(artistList); // Pass the artistList
                            break;
                        case 5:
                            main.mainScreen.displayAdmin();
                            break;
                        case 6:
                            System.out.println("Thanks for using");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 5");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }
    }
    
        public static void displayVenueScreen() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "===========================================");
            System.out.println("=============   Manage Venue  =============");
            System.out.println("===========================================");
            System.out.println("======= 1) Add Venue                =======");
            System.out.println("======= 2) Modify Venue             =======");
            System.out.println("======= 3) Delete Venue             =======");
            System.out.println("======= 4) Back to Management Page ========");
            System.out.println("======= 5) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            ArtistManagement.addArtist();
                            break;
                        case 2:
                            ArtistManagement.viewArtist(); 
                            break;
                        case 3:
                            updateArtist(artistList); // Pass the artistList
                            break;
                        case 4:
                            deleteArtist(artistList); // Pass the artistList
                            break;
                        case 5:
                            main.mainScreen.displayAdmin();
                            break;
                        case 6:
                            System.out.println("Thanks for using");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 5");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }
    }
        
    public static void displayTicketScreen() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "===========================================");
            System.out.println("============   Manage Ticket  =============");
            System.out.println("===========================================");
            System.out.println("======= 1) Add Ticket                =======");
            System.out.println("======= 2) Modify Ticket             =======");
            System.out.println("======= 3) Delete Ticket             =======");
            System.out.println("======= 4) Back to Management Page ========");
            System.out.println("======= 5) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (SymbolValidator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            ArtistManagement.addArtist();
                            break;
                        case 2:
                            ArtistManagement.viewArtist(); 
                            break;
                        case 3:
                            updateArtist(artistList); // Pass the artistList
                            break;
                        case 4:
                            deleteArtist(artistList); // Pass the artistList
                            break;
                        case 5:
                            main.mainScreen.displayAdmin();
                            break;
                        case 6:
                            System.out.println("Thanks for using");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 5");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }
    }    
}




