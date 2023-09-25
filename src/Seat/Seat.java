package Seat;

import Seat.Venue;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Database;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;



/**
 *
 * @author User
 */
public class Seat {

    static void existSeat(String InSeatID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private String SeatID;
    private String venue;
    private double price;
    private int status;
    private static ArrayList<Seat> seatArrayList = new ArrayList<Seat>();
    
    
    public Seat() {
    }

    public Seat(String SeatID, String venue, double price, int status) {
        this.SeatID = SeatID;
        this.venue = venue;
        this.price = price;
        this.status = status;
    }
    
    

    
    public String getSeatID() {
        return SeatID;
    }

    public void setSeatID(String SeatID) {
        this.SeatID = SeatID;
    }

    public String getVenue() {
        return venue;
    }
    

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }    

    public static ArrayList<Seat> getSeatArrayList() {
        return seatArrayList;
    }

    public static void setSeatArrayList(ArrayList<Seat> seatArrayList) {
        Seat.seatArrayList = seatArrayList;
    }
    
    
    
    //Add seat method
    public static Seat addSeat() {
        Scanner sc = new Scanner(System.in);
        String InSeat;
        String InVenue;
        double InPrice;
        int InStatus = 1; //set status is default (1 for available, 2 for Unavailable)
        
        //let user input the seat ID and verify
        do {
            boolean idExist = false;
            System.out.print("Please enter Seat ID: ");
            InSeat = sc.nextLine().trim();
            if (InSeat.isEmpty()) {
                System.out.println("Seat ID cannot be empty. Please try again.");
                InSeat = null;
            }else 
                // Check if the Seat ID already exists
                for (Seat seat : seatArrayList) {
                    if (seat.getSeatID().equals(InSeat)) {
                        idExist = true;
                        break;
                    }
                }
            if (idExist) {
                System.out.println("Seat ID already exists. Please choose a different Seat ID.");
                InSeat = null; // Set InVenue to null to indicate an error
            }
        }while (InSeat == null);

        
        //let let user input the venue and verify
        do {
            System.out.print("Please enter Venue (Venue ID): ");
            InVenue = sc.nextLine().trim();
            if (InVenue.isEmpty()) {
            System.out.println("Location cannot be empty. Please try again.");
            }
        } while (InVenue.isEmpty());

        
        //let user input the price and verify
        try {
            System.out.print("Please enter Seat Price: ");
            InPrice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            if (InPrice <= 0 ) {
                throw new IllegalArgumentException("Invalid seat price");
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Invalid seat price")) {
                System.out.println("Invalid seat price input. Please try again");
                return null; // Return null to indicate an error
            }
            
            InPrice = 0;
        } catch (InputMismatchException ex) {
            System.out.println("Invalid input. Please enter a value.");
            sc.nextLine();
            InPrice = -1;
            return null; // Return null to indicate an error
        }
        
        //let user input the status and verify
//        try {
//            System.out.print("Please enter status(1 for available, 2 for unavaialable): ");
//            InStatus = sc.nextInt();
//            sc.nextLine(); // Consume the newline character
//
//            if (InStatus != 1 || InStatus != 2) {
//                throw new IllegalArgumentException("Invalid status(1 for available, 2 for unavaialable)");
//            }
//        } catch (IllegalArgumentException e) {
//            if (e.getMessage().equals("Invalid status")) {
//                System.out.println("Invalid status input. Please try again");
//                return null; // Return null to indicate an error
//            }
//            
//            InStatus = 0;
//        } catch (InputMismatchException ex) {
//            System.out.println("Invalid input. Please enter a value.");
//            sc.nextLine();
//            InStatus = -1;
//            return null; // Return null to indicate an error
//        }

        // Create the temporary list for store Venue
        Seat SList = new Seat(InSeat, InVenue, InPrice, InStatus);

        // Add the artist to the ArrayList
        Seat.seatArrayList.add(SList);
        System.out.println("Seat added successfully");

        return SList; // Return the venue
    }
        
        
    
    //View all venue
    public static void viewAllSeat() {
        if (seatArrayList.isEmpty()) {
            System.out.println("============:   No seat found   :============" + "\n\n");
            SeatManager.displayVenueScreen();
        } else {
            for (Seat seat : seatArrayList) {
                System.out.println("=============:   Seat Lsit   :=============" +"\n");
                System.out.printf("%-15s, %-15s, %-15s, %-15s", "seatID", "venue", "price", "status");
                System.out.println("\n"+"______________________________________________________");
                for (int i = 0; i < seatArrayList.size(); i++) {
                    Seat seatView = seatArrayList.get(i);
            System.out.printf("%-15s %-25s %-15s %-15s", seatView.getSeatID(), seatView.getVenue(), seatView.getPrice(), seatView.getStatus() + "\n");
        }      
            }
        }
    }
    
    public static void modifySeat(){
        Scanner sc = new Scanner(System.in);

        viewAllSeat();

        // Prompt the user to enter the Seat ID to modify
        System.out.print("\n" + "Please enter the Seat ID to modify: ");
        String modifySc = sc.nextLine().trim();

        // Find the index of the seat with the matching Seat ID
        int index = -1;
        for (int i = 0; i < seatArrayList.size(); i++) {
            Seat seat = seatArrayList.get(i);
            if (seat.getSeatID().equals(modifySc)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Seat with ID '" + modifySc + "' not found.");
        } else {
            // Seat found, allow modifications
            System.out.println("Current Venue Details:");
            System.out.println("Venue ID: " + seatArrayList.get(index).getVenue());
            System.out.println("Location: " + seatArrayList.get(index).getPrice());
            System.out.println("Capacity: " + seatArrayList.get(index).getStatus());

            // Prompt for new details
            System.out.print("Enter new Seat ID (press Enter to keep current): ");
            String newSeatID = sc.nextLine().trim();
        
            // Check if a seat with the new Seat ID already exists (excluding the current venue)
            for (int i = 0; i < seatArrayList.size(); i++) {
                Seat seat = seatArrayList.get(i);
                if (seat.getSeatID().equals(newSeatID) && !newSeatID.equals(modifySc)) {
                    System.out.println("Seat with ID '" + newSeatID + "' already exists. Please choose a different Seat ID.");
                    return; // Return early if the new Seat ID already exists
                }
            }

            // If the user wants to change the Seat ID, update it
            if (!newSeatID.isEmpty()) {
                seatArrayList.get(index).setSeatID(newSeatID);
            }

            System.out.print("Enter new Venue (press Enter to keep current): ");
            String newVenue = sc.nextLine().trim();
            if (!newVenue.isEmpty()) {
                seatArrayList.get(index).setVenue(newVenue);
            }

            System.out.print("Enter new price (press Enter to keep current): ");
            String newPriceStr = sc.nextLine().trim();
            if (!newPriceStr.isEmpty()) {
                try {
                    int newPrice = Integer.parseInt(newPriceStr);
                    if (newPrice > 0) {
                        seatArrayList.get(index).setPrice(newPrice);
                    } else {
                        System.out.println("Invalid price input. Price must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for price.");
                }
            }
            
            System.out.print("Enter new status <1 || 2> (press Enter to keep current): ");
            String newStatusStr = sc.nextLine().trim();
            if (!newStatusStr.isEmpty()) {
                try {
                    int newStatus = Integer.parseInt(newStatusStr);
                    if (newStatus > 0 || 3 < newStatus) {
                        seatArrayList.get(index).setStatus(newStatus);
                        System.out.println("Seat details modified successfully.");
                    } else {
                        System.out.println("Invalid Seat input. Price must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for status.");
                }
            }
        }   
    }

    
    
    public static void deleteSeat(ArrayList<Seat> seatArrayList) {
        Scanner sc = new Scanner(System.in);

        viewAllSeat();

        try {
            System.out.print("Please enter the Seat ID that you want to delete: ");
            String deleSc = sc.nextLine();
            int index = -1;
            for (int i = 0; i < seatArrayList.size(); i++) {
                Seat dele = seatArrayList.get(i);
                if (dele.getSeatID().equals(deleSc)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("The seat ID is not exits. Please try it again");
            } else {
                seatArrayList.remove(index);
                System.out.println("Remove seat ID successfully");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a value.");
        }
    }
    
    
    public static Seat existSeat(String SeatID){
        for (Seat seat : seatArrayList) {
            if (seat.getSeatID().equals(SeatID)) {
            
                return seat; // Return the Seat object when a matching SeatID is found.
            }
        }
        return null; // Return null if no matching SeatID is found.
    }
    
    
    public static double getSeatPrice(String SeatID, double Price){
        double price;
        for (int i = 0; i < seatArrayList.size(); i++){
            Seat seat = seatArrayList.get(i);
            price = seat.getPrice();
                 
            return price; // Return the Seat object when a matching SeatID is found.
            }
        return 0;
    }
}
