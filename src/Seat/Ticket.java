package Seat;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author QL
 */
public class Ticket {
    private int ticketID;
    private static int nextID = 1;
    private String seatID;
    private double ticketPrice;
    static ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>();
    

    public Ticket(String seatID, double ticketPrice) {
        this.ticketID = nextID;
        nextID++;
        this.seatID = seatID;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public static ArrayList<Ticket> getTicketArrayList() {
        return ticketArrayList;
    }

    public static void setTicketArrayList(ArrayList<Ticket> ticketArrayList) {
        Ticket.ticketArrayList = ticketArrayList;
    }

    

    
    //Add Ticket Method
    public static Ticket addTicket(){
        Scanner sc = new Scanner(System.in);
        String InSeatID;
        double InTicketPrice = 0;
        double outPrice = 0;
        
        do{
            System.out.print("Please enter Seat ID: ");
            InSeatID = sc.nextLine().trim();
            boolean idExist = false;
            if (InSeatID.isEmpty()){
                System.out.println("Seat ID cannot be empty. Please try Again.");
                InSeatID = null;                
            }else{
                // Check if the Seat ID already exists
                for (Ticket ticket : ticketArrayList) {
                    if (ticket.getSeatID().equals(InSeatID)) {
                        idExist = true;
                        break;
                    }
                }
                if (idExist){
                    System.out.println("Seat ID already exists in ticket. Please choose a different Seat ID.");
                    InSeatID = null;
                }else{
                    // Check if the seat ID not exits
                    Seat foundSeat = Seat.existSeat(InSeatID);
                    if (foundSeat == null){
                        System.out.println("Seat ID does not found in array. Please try again.");
                        InSeatID = null;
                    }else{
                        boolean checkedStatus = Seat.checkStatus(InSeatID);
                        if (checkedStatus == true){
                            outPrice = Seat.getSeatPrice(InSeatID, InTicketPrice);
                            }else{
                                InSeatID = null;
                                System.out.println("Status for this seat is unavailable. Please choose anthoer seat");
                            }
                        }
                    }
            }
        }while (InSeatID == null);
        
        
        // Create the temporary list for store Venue
        Ticket TList = new Ticket(InSeatID, outPrice);

        // Add the artist to the ArrayList
        Ticket.ticketArrayList.add(TList);
        System.out.println("Ticket added successfully");

        return TList; // Return the venue 
        
    }
    
    //view Ticket method
    public static void viewAllTicket() {
        if (ticketArrayList.isEmpty()) {
            System.out.println("============:   No ticket found   :============" + "\n\n");
            SeatManager.displayTicketScreen();
        } else {
            for (Ticket ticket : ticketArrayList) {
                System.out.println("=============:   Seat Lsit   :=============" +"\n");
                System.out.printf("%-15s, %-15s, %-15s", "ticketID", "SeatID", "ticketPrice");
                System.out.println("\n"+"______________________________________________________");
                for (int i = 0; i < ticketArrayList.size(); i++) {
                    Ticket ticketView = ticketArrayList.get(i);
            System.out.printf("%-15s %-25s %-15s", ticketView.getTicketID(), ticketView.getSeatID(), ticketView.getTicketPrice() + "\n");
        }      
            }
        }
    }
    

    //Delete ticket Method
    public static void deleteTicket(ArrayList<Ticket> ticketArrayList) {
        Scanner sc = new Scanner(System.in);

        viewAllTicket();

        try {
            System.out.print("Please enter the Ticket ID that you want to delete: ");
            int deleSc = sc.nextInt();
            int index = -1;
            for (int i = 0; i < ticketArrayList.size(); i++) {
                Ticket dele = ticketArrayList.get(i);
                if (dele.getTicketID() == deleSc) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("The ticket ID is not exits. Please try it again");
            } else {
                ticketArrayList.remove(index);
                System.out.println("Remove ticket ID successfully");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a value.");
        }
    }
}
