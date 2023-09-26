package Seat;

import Seat.Venue;
import Seat.Seat;
import Seat.Ticket;
import java.util.ArrayList;
import java.util.Scanner;
import util.ClearScreen;
import util.Database;
import util.Validator;


/**
 *
 * @author QL
 */

public class SeatManager {

    static ArrayList<Seat> seatList = Seat.getSeatArrayList();
    static ArrayList<Venue> venueList = Venue.getVenueArrayList();
    static ArrayList<Ticket> ticketList = Ticket.getTicketArrayList();


    public static void main(String[] args){
        new Database();
        displaySeatMainScreen();
                

    }
    
    //Display Seat Management Main Screen
    public static void displaySeatMainScreen() {
        Scanner sc = new Scanner(System.in);
        
        ClearScreen.cls();
        
        while (true) {
            System.out.println("\n" + "=======================================");
            System.out.println("========   Seat Management  ===========");
            System.out.println("=======================================");
            System.out.println("======= 1) Manage Venue       =========");
            System.out.println("======= 2) Manage Seat        =========");
            System.out.println("======= 3) Manage Ticket      =========");
            System.out.println("======= 4) Back to Admin Page =========");
            System.out.println("======= 5) Exit               =========");
            System.out.println("=======================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();
            Venue.getAllVenuesFromDatabase();
            Seat.getAllSeatFromDatabase();
            Ticket.getAllTicketFromDatabase();

            // Symbol checking
            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);


                    switch (selection) {
                        case 1:
                            ClearScreen.cls();
                            displayVenueScreen();
                            break;
                        case 2:
                            ClearScreen.cls();
                            displaySeatScreen(); 
                            break;
                        case 3:
                            ClearScreen.cls();
                            displayTicketScreen();
                            break;
                        case 4:
                            ClearScreen.cls();
                            main.mainScreen.displayAdmin();
                            break;
                        case 5:
                            ClearScreen.cls();
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
            System.out.println("======= 2) View Seat               ========");
            System.out.println("======= 3) Modify Seat             ========");
            System.out.println("======= 4) Delete Seat             ========");
            System.out.println("======= 5) Back to Management Page ========");
            System.out.println("======= 6) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            Seat.addSeat();
                            break;
                        case 2:
                            Seat.viewAllSeat(); 
                            break;
                        case 3:
                            Seat.modifySeat(); 
                            break;
                        case 4:
                            Seat.deleteSeat(seatList); 
                            break;
                        case 5:
                            displaySeatMainScreen();
                            break;
                        case 6:
                            main.mainScreen.displayAdmin();
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 6");
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
            System.out.println("======= 1) Add Venue               ========");
            System.out.println("======= 2) View Venue              ========");
            System.out.println("======= 3) Modify Venue            ========");
            System.out.println("======= 4) Delete Venue            ========");
            System.out.println("======= 5) Back to Management Page ========");
            System.out.println("======= 6) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            Venue.addVenue();
                            break;
                        case 2:
                            Venue.viewAllVenue();
                            break;
                        case 3:
                            Venue.modifyVenue(); 
                            break;
                        case 4:
                            Venue.deleteVenue(venueList);
                            break;
                        case 5:
                            displaySeatMainScreen();
                            break;
                        case 6:
                            main.mainScreen.displayAdmin();
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 6");
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
            System.out.println("=============   Manage Ticket  =============");
            System.out.println("===========================================");
            System.out.println("======= 1) Add Ticket              ========");
            System.out.println("======= 2) View Ticket             ========");
            System.out.println("======= 3) Delete Venue            ========");
            System.out.println("======= 4) Back to Management Page ========");
            System.out.println("======= 5) Back to Admin Page      ========");
            System.out.println("===========================================" + "\n");

            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();

            // Symbol checking
            if (Validator.containsSymbol(input)) {
                System.out.println("Input contains specific symbols.");
            } else {
                try {
                    int selection = Integer.parseInt(input);

                    switch (selection) {
                        case 1:
                            Ticket.addTicket();
                            break;
                        case 2:
                            Ticket.viewAllTicket();
                            break;
                        case 3:
                            Ticket.deleteTicket(ticketList);
                            break;
                        case 4:
                            displaySeatMainScreen();
                            break;
                        case 5:
                            main.mainScreen.displayAdmin();
                            break;
                        default:
                            System.out.println("Invalid Range, Please enter a number between 1 to 6");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }
        }
    }
}




