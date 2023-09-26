package Seat;

import java.util.ArrayList;
import java.util.Scanner;
import Seat.Seat;

/**
 *
 * @author QL
 */
public class Ticket {
    private int ticketID;
    private static int nextID = 1;
    private Seat seat;
    private Venue venue;
    static ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>();
    
    public Ticket(){
    }
    

    public Ticket(Seat seat, Venue venue) {
        this.ticketID = nextID;
        nextID++;
        this.seat = seat;
        this.venue = venue;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    

    public static ArrayList<Ticket> getTicketArrayList() {
        return ticketArrayList;
    }

    public static void setTicketArrayList(ArrayList<Ticket> ticketArrayList) {
        Ticket.ticketArrayList = ticketArrayList;
    }

    

    public static boolean addTicket(Seat seat, Venue venue){
        //Yong Choy Mun
        Ticket ticket = new Ticket(seat, venue);
        Ticket.ticketArrayList.add(ticket);
        return true;
    }
    
    //Add Ticket Method
    public static Ticket addTicket(){
        Scanner sc = new Scanner(System.in);
        String InSeatID;
        double InTicketPrice = 0;
        double outPrice = 0;
        
        
        //let user choose seat
        Seat selectedSeat = null; // Initialize selectedVenue as null
        System.out.println("\nChoose Seat ID: ");
        Seat.viewAllSeat();
        do{
            System.out.print("Enter selection : ");
            InSeatID = sc.nextLine().trim();
            if (InSeatID.isEmpty()) {
                System.out.println("Seat ID cannot be empty. Please try again.");
            }else {
                selectedSeat = Seat.existSeat(InSeatID);
                if (selectedSeat == null){
                    System.out.println("Seat ID not found. Please a valid venue");
                }else{
                    if (selectedSeat.getStatus() == 2) { //check status if status is 2 cannot process
                    System.out.println("Seat is unavailable. Cannot create a ticket for this seat.");
                    selectedSeat = null; // Reset selectedSeat to null
                    }else {
                        boolean seatAlreadyHasTicket = false;
                    
                    // Check if the selected seat already has a ticket
                    for (Ticket ticket : ticketArrayList) {
                        if (ticket.getSeat().getSeatID().equals(InSeatID)) {
                            seatAlreadyHasTicket = true;
                            break;
                        }
                    }
                    if (seatAlreadyHasTicket) {
                        System.out.println("This seat already has a ticket. Cannot create another ticket for the same seat.");
                        selectedSeat = null; // Reset selectedSeat to null
                    }
                }
            }
        }
        }while(selectedSeat == null);
        String findSeatID = selectedSeat.getSeatID();
        String findVenueID = selectedSeat.getVenue().getVenueID();
        double findPrice = selectedSeat.getPrice();
        int findStatus = selectedSeat.getStatus();

        Seat seat = new Seat(findSeatID, Venue.existVenue(findVenueID), findPrice, findStatus);
        
//        do{
//            System.out.print("Please enter Seat ID: ");
//            InSeatID = sc.nextLine().trim();
//            boolean idExist = false;
//            if (InSeatID.isEmpty()){
//                System.out.println("Seat ID cannot be empty. Please try Again.");
//                InSeatID = null;                
//            }else{
//                // Check if the Seat ID already exists
//                for (Ticket ticket : ticketArrayList) {
//                    if (ticket.getSeatID().equals(InSeatID)) {
//                        idExist = true;
//                        break;
//                    }
//                }
//                if (idExist){
//                    System.out.println("Seat ID already exists in ticket. Please choose a different Seat ID.");
//                    InSeatID = null;
//                }else{
//                    // Check if the seat ID not exits
//                    Seat foundSeat = Seat.existSeat(InSeatID);
//                    if (foundSeat == null){
//                        System.out.println("Seat ID does not found in array. Please try again.");
//                        InSeatID = null;
//                    }else{
//                        boolean checkedStatus = Seat.checkStatus(InSeatID);
//                        if (checkedStatus == true){
//                            outPrice = Seat.getSeatPrice(InSeatID, InTicketPrice);
//                            }else{
//                                InSeatID = null;
//                                System.out.println("Status for this seat is unavailable. Please choose anthoer seat");
//                            }
//                        }
//                    }
//            }
//        }while (InSeatID == null);
        
        
        // Create the temporary list for store Venue
        Ticket TList = new Ticket(Seat.existSeat(findSeatID), Venue.existVenue(findVenueID));

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
        System.out.println("=============:   Ticket List   :=============" + "\n");
        System.out.printf("%-15s %-15s %-15s %-15s%n", "TicketID", "SeatID", "VenueID", "Ticket Price");
        System.out.println("\n_________________________________________________________");
        for (Ticket ticket : ticketArrayList) {
            System.out.printf("%-15s %-15s %-15s %-15s%n", ticket.getTicketID(), ticket.getSeat().getSeatID(), ticket.getSeat().getVenue().getVenueID(), ticket.getSeat().getPrice());
        }
    }
}
    
//    public static void viewAllTicket() {
//        if (ticketArrayList.isEmpty()) {
//            System.out.println("============:   No ticket found   :============" + "\n\n");
//            SeatManager.displayTicketScreen();
//        } else {
//            for (Ticket ticket : ticketArrayList) {
//                System.out.println("=============:   Ticket Lsit   :=============" +"\n");
//                System.out.printf("%-15s %-15s %-15s %-15s", "TicketID", "SeatID", "VenueID", "Ticket Price");
//                System.out.println("\n"+"_________________________________________________________");
//                for (int i = 0; i < ticketArrayList.size(); i++) {
//                    Ticket ticketView = ticketArrayList.get(i);
//            System.out.printf("%-15s %-15s %-15s %-15s", ticket.getTicketID(), ticket.getSeat().getSeatID(), ticket.getSeat().getVenue().getVenueID(), ticket.getSeat().getPrice());
//        }      
//            }
//        }
//    }
    

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
    
    
//    public static Ticket existTicket(int targetTicketID){
//    for (Ticket ticket : ticketArrayList) {
//        if (ticket.getSeatID().equals(targetTicketID)) {
//            return ticket; // Return the Seat object when a matching SeatID is found.
//        }
//    }
//    return null; // Return null if no matching SeatID is found.
//    }
}
