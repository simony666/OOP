/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import Payment.Invoice;
import Seat.Seat;
import Seat.Ticket;
import Seat.Venue;
import Staff.StaffManager;
import java.util.ArrayList;
import java.util.Scanner;
import util.ClearScreen;
import util.Database;
import util.Role;
import util.Validator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import util.User;

/**
 *
 * @author yongc
 */
public class CustomerManager {
    
    private static ArrayList<Customer> cusArray = getCustomerList();
    private static ArrayList<User> userArray = StaffManager.getUserList();
    
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
                main.mainScreen.setUsername(user.getUsername());
                return true;
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
                return Login();
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
    
    private static ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> tempList = new ArrayList<>();
        
        String sqlText = "select * from `User` where `role` = \"Customer\";";
        ResultSet result = Database.runQuery(sqlText);
        try {
            while(result.next()){
                String username = result.getString("username");
                String name = result.getString("name");
                String email = result.getString("email");
                Role role = Role.Customer;
                
                Customer c = new Customer(username,email,name,role);
                tempList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return tempList;
    }
    
    public static void viewAvailableSeats() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("======== View Available Seats =======");
        System.out.println("=====================================");

        // Get the list of venues
        //ArrayList<Venue> venues = (ArrayList<Venue>) Venue.getAllVenuesFromDatabase();
        Venue.getAllVenuesFromDatabase();
        ArrayList<Venue> venues = Venue.getVenueArrayList();

        // Display available venues
        System.out.println("Available Venues:");
        for (int i = 0; i < venues.size(); i++) {
            Venue venue = venues.get(i);
            System.out.println(i + 1 + ") " + venue.getLocation() + " (Capacity: " + venue.getCapacity() + ")");
        }

        // Ask the user to select a venue
        System.out.print("Select a venue (1-" + venues.size() + "): ");
        int venueSelection = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (venueSelection < 1 || venueSelection > venues.size()) {
            System.out.println("Invalid selection. Please enter a valid venue number.");
            return;
        }

        Venue selectedVenue = venues.get(venueSelection - 1);

        // Get the list of seats for the selected venue
        Seat.getAllSeatFromDatabase();
        ArrayList<Seat> seats = Seat.getSeatArrayList();

        // Display available seats for the selected venue
        System.out.println("Available Seats in " + selectedVenue.getLocation() + ":");
        System.out.println("=====================================");
        System.out.printf("%-12s %-10s %-10s\n", "Seat Number", "Price", "Status");

        for (Seat seat : seats) {
            if (seat.getVenue().getVenueID() == selectedVenue.getVenueID() && seat.getStatus() == 1) {
                System.out.printf("%-12s $%-10s %-10s\n",
                        seat.getSeatID(), seat.getPrice(), "Avaiable");
            }
        }
        
        System.out.print("(B)ack to Main Menu: ");
        String opt = sc.nextLine();

        switch (opt.toLowerCase()) {
            case "b":
                return;
            default:
                System.out.println("Invalid option. Please enter 'S' or 'B'.");
        }
    }
    
    public static void buyTicket(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("======== View Available Seats =======");
        System.out.println("=====================================");

        // Get the list of venues
        //ArrayList<Venue> venues = (ArrayList<Venue>) Venue.getAllVenuesFromDatabase();
        Venue.getAllVenuesFromDatabase();
        ArrayList<Venue> venues = Venue.getVenueArrayList();

        // Display available venues
        System.out.println("Available Venues:");
        for (int i = 0; i < venues.size(); i++) {
            Venue venue = venues.get(i);
            System.out.println(i + 1 + ") " + venue.getLocation() + " (Capacity: " + venue.getCapacity() + ")");
        }

        // Ask the user to select a venue
        System.out.print("Select a venue (1-" + venues.size() + "): ");
        int venueSelection = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (venueSelection < 1 || venueSelection > venues.size()) {
            System.out.println("Invalid selection. Please enter a valid venue number.");
            return;
        }

        Venue selectedVenue = venues.get(venueSelection - 1);

        // Get the list of seats for the selected venue
        Seat.getAllSeatFromDatabase();
        ArrayList<Seat> seats = Seat.getSeatArrayList();

        // Display available seats for the selected venue
        System.out.println("Available Seats in " + selectedVenue.getLocation() + ":");
        System.out.println("=====================================");
        System.out.println("Seat Number\tPrice\tStatus");

        for (Seat seat : seats) {
            if (seat.getVenue().getVenueID().equals(selectedVenue.getVenueID()) && seat.getStatus()== 1) {
                System.out.println(seat.getSeatID() + "\t\t$" + seat.getPrice() + "\t" + seat.getStatus());
            }
        }

        // Ask the user to select a seat
        System.out.print("Select a seat by entering its number: ");
        String seatNumber = sc.nextLine();
        //sc.nextLine(); // Consume newline

        // Find the selected seat
        Seat selectedSeat = null;
        for (Seat seat : seats) {
            if (seat.getSeatID().equals(String.valueOf(seatNumber))) {
                selectedSeat = seat;
                break;
            }
        }

        if (selectedSeat == null || !(selectedSeat.getStatus() == 1)) {
            System.out.println("Invalid seat selection. Please select an available seat.");
            return;
        }

        // Book the selected seat
        Ticket ticket = Ticket.addTicket(selectedSeat, selectedVenue);
        
        Invoice inv = Invoice.makePayment(ticket);
        
        
        if (inv != null) {
            System.out.println("Seat booked successfully!");
            
            System.out.println("=============:   Invoice   :=============" + "\n");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "InvoiceID", "TicketID", "SeatID", "VenueID", "price");
            System.out.println("\n_______________________________________________________________________________");
            System.out.printf("%-15s %-15s %-15s %-15s%n",
                    inv.getInvoiceID(),
                    inv.getTicket().getTicketID(),
                    inv.getSeat().getSeatID(),
                    inv.getTicket().getSeat().getVenue().getVenueID(),
                    inv.getSeat().getPrice());
            System.out.println("Pay At: " + new Date().toString());

            // Add the ticket information to the SQL table
            String sqlText = "INSERT INTO Ticket (seat, venue) VALUES (\""+selectedSeat.getSeatID()+"\", \""+selectedVenue.getVenueID()+"\")";
            Database.runUpdate(sqlText);
        } else {
            System.out.println("Failed to book the seat. Please try again later.");
        }
    }
    
    public static void viewPurchasedTickets(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("==== View Purchased Tickets ========");
        System.out.println("=====================================");

        ArrayList<Ticket> purchasedTickets = getPurchasedTickets();

        if (purchasedTickets.isEmpty()) {
            System.out.println("No purchased tickets found for the username: " + username);
        } else {
            System.out.println("Purchased Tickets:");
            System.out.println("=======================================================");
            System.out.printf("%-10s %-15s %-10s\n", "Ticket ID", "Seat", "Venue");
            System.out.println("=======================================================");

            for (Ticket ticket : purchasedTickets) {
                System.out.printf("%-10s %-15s %-10s\n",
                        ticket.getTicketID(),
                        ticket.getSeat().getSeatID(),
                        ticket.getVenue().getLocation());
            }
        }

        System.out.println("Press Enter to return to the main menu...");
        sc.nextLine();
    }

    private static ArrayList<Ticket> getPurchasedTickets() {
        ArrayList<Ticket> purchasedTickets = new ArrayList<>();

        String sqlText = "SELECT * FROM Ticket;";
        ResultSet result = Database.runQuery(sqlText);

        try {
            while (result.next()) {
                String seat = result.getString("seat");
                String venue = result.getString("venue");

                // Create Ticket object and add it to the list
                Ticket ticket = new Ticket(Seat.existSeat(seat), Venue.existVenue(venue));
                purchasedTickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchasedTickets;
    }

    public static void modifyAccount(String username) {
        User customer = null;
        for (User cus : userArray) {
            if (cus.getUsername().equals(username)) {
                customer = cus;
                break;
            }
        }
        
        
        Scanner sc = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("========  Modify Account  ===========");
        System.out.println("=====================================");
        System.out.println("Account Info:");
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("=====================================");
        
        boolean p = false;
        boolean n = false;
        boolean e = false;
        do {
            System.out.println("What would you like to modify?");
            System.out.println("1) Password");
            System.out.println("2) Name");
            System.out.println("3) Email");
            System.out.println("4) Save and Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    customer.setPassword(newPassword);
                    p = true;
                    break;
                case "2":
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    customer.setName(newName);
                    n = true;
                    break;
                case "3":
                    System.out.print("Enter new email: ");
                    String newEmail = sc.nextLine();
                    customer.setEmail(newEmail);
                    e = true;
                    break;
                case "4":
                    // Save the updated customer information to the database
                    updateCustomer(customer,p,n,e);
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (true);
    }

    private static void updateCustomer(User user,boolean p,boolean n, boolean e) {
        String sqlText;
        if (p){
            sqlText = "update `User` set `password` = '" + user.getPassword() + "' " +"where `username` = '" + user.getUsername() + "';";
            Database.runUpdate(sqlText);
        }
        if (n){
            sqlText = "update `User` set `name` = '" + user.getName() + "' " +"where `username` = '" + user.getUsername() + "';";
            Database.runUpdate(sqlText);
        }
        
        if (e){
            sqlText = "update `User` set `email` = '" + user.getEmail() + "' " +"where `username` = '" + user.getUsername() + "';";
            Database.runUpdate(sqlText);
        }

            System.out.println("Update successful.");
            cusArray = getCustomerList();
    }

}
