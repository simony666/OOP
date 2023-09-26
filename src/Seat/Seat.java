package Seat;

import Seat.Venue;
import java.util.ArrayList;
import java.util.List;
import util.Database;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author QL
 */
public class Seat {
    
    private String SeatID;
    private Venue venue;
    private double price;
    private int status;
    private static ArrayList<Seat> seatArrayList = new ArrayList<Seat>();
    static ArrayList<Venue> venueList = Venue.getVenueArrayList();
    
    
    public Seat() {
    }

    public Seat(String SeatID, Venue venue, double price, int status) {
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

    public Venue getVenue() {
        return venue;
    }
    

    public void setVenue(Venue venue) {
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
    
    @Override
    public String toString() {
        return String.format("%-15s %-25s, %-15s, %-15s", SeatID, venue.getVenueID(), price, status);
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

        //let user choose venue
        Venue selectedVenue = null; // Initialize selectedVenue as null
        System.out.println("\nChoose Venue ID: ");
        Venue.viewAllVenue();
        do{
            System.out.print("Enter selection : ");
            InVenue = sc.nextLine().trim();
            if (InVenue.isEmpty()) {
                System.out.println("Venue ID cannot be empty. Please try again.");
            }else {
                selectedVenue = Venue.existVenue(InVenue);
                if (selectedVenue == null){
                    System.out.println("Venue ID not found. Please a valid venue");
                }
            }
        }while(selectedVenue == null);
        String findVenue = selectedVenue.getVenueID();
        String findLocation = selectedVenue.getLocation();
        int findCapacity = selectedVenue.getCapacity();

        Venue venue = new Venue(findVenue, findLocation, findCapacity);
        
        
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
       

        // Create the temporary list for store Venue
        Seat SList = new Seat(InSeat, venue, InPrice, InStatus);
        boolean addNewSeatToDB = insertSeatIntoDatabase(SList);
        if (addNewSeatToDB = true){
            System.out.println("New seat successful add in to database.");
        }
        

        // Add the artist to the ArrayList
        Seat.seatArrayList.add(SList);
        System.out.println("Seat added successfully");

        return SList; // Return the venue
    }
    
    //View all venue mothod
    public static void viewAllSeat() {
        if (seatArrayList.isEmpty()) {
            System.out.println("============:   No seat found   :============" + "\n\n");
            SeatManager.displayVenueScreen();
        } else {
            System.out.println("=============:   Seat List   :=============" + "\n");
            System.out.printf("%-15s %-15s %-15s %-15s%n", "seatID", "venueID", "price", "status");
            System.out.println("\n" + "______________________________________________________");
            for (Seat seat : seatArrayList) {
                System.out.printf("%-15s %-15s %-15s %-15s%n", seat.getSeatID(), seat.getVenue().getVenueID(), seat.getPrice(), seat.getStatus() + "\n");
            }
        }
    }
    
    //Modify seat mothod
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
            System.out.println("Seat ID: " + seatArrayList.get(index).getSeatID());
            System.out.println("Venue ID: " + seatArrayList.get(index).getVenue().getVenueID());
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
            
            
            
            
        Venue selectedVenue = null; // Initialize selectedVenue as null
        System.out.println("\nChoose Venue: ");
        Venue.viewAllVenue();
        do{
            System.out.print("Enter selection : ");
            String modifyVenue = sc.nextLine().trim();
            if (!modifyVenue.isEmpty()) {
                selectedVenue = Venue.existVenue(modifyVenue);
                if (selectedVenue == null){
                    System.out.println("Venue not found. Please a valid venue");
                }
            }
        }while(selectedVenue == null);
        String findVenue = selectedVenue.getVenueID();
        String findLocation = selectedVenue.getLocation();
        int findCapacity = selectedVenue.getCapacity();

        Venue venue = new Venue(findVenue, findLocation, findCapacity);

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
                if (newStatus < 0 || 3 > newStatus) {
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

    
    //Delete seat method
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
                boolean deleteSeatInDB = deleteSeatFromDatabase(deleSc);
                if (deleteSeatInDB = true){
                    System.out.println("Delete seat in database successful.");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a value.");
        }
    }
        
    //Find seat ID existing in database or not
    public static Seat existSeat(String targetSeatID){
        for (Seat seat : seatArrayList) {
            if (seat.getSeatID().equals(targetSeatID)) {
                return seat; // Return the Seat object when a matching SeatID is found.
            }
        }
        return null; // Return null if no matching SeatID is found.
    }
    
    // Method to retrieve seat data from the database
    public static List<Seat> getAllSeatFromDatabase() {
        List<Seat> seat = new ArrayList<>();
        seatArrayList.clear();
        ResultSet resultSet = null;

        try {

            String query = "SELECT * FROM Seat";
            resultSet = Database.runQuery(query);
            // Iterate through the result set and create Seat objects
            while (resultSet.next()) {
                String seatID = resultSet.getString("seatID");
                String venueID = resultSet.getString("venue");
                double price = resultSet.getDouble("price");
                int status = resultSet.getInt("status");

                // Create a seat object and add it to the list
                Seat newSeat = new Seat(seatID, Venue.existVenue(venueID), price, status);
                Seat.seatArrayList.add(newSeat);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }
    
    
    public static boolean insertSeatIntoDatabase(Seat newSeat) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = Database.getConnection();

            // Define the SQL query to insert a new venue
            String query = "INSERT INTO Seat (seatID, venue, price) VALUES (?, ?, ?)";

            // Create a prepared statement
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, newSeat.getSeatID());
            preparedStatement.setString(2, newSeat.getVenue().getVenueID());
            preparedStatement.setDouble(3, newSeat.getPrice());

            // Execute the INSERT query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                return true; // Insertion successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in a finally block to ensure they are always closed
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Insertion failed
    }
    
    
    //modify vseat in database
    public static boolean modifySeatInDatabase(Seat modifiedSeat) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = Database.getConnection();

            // Define the SQL query to insert a new seat
            String query = "INSERT INTO Seat (seatID, venue, price, status) VALUES (?, ?, ?, ?)";

            // Create a prepared statement
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, modifiedSeat.getSeatID());
            preparedStatement.setString(2, modifiedSeat.getVenue().getVenueID());
            preparedStatement.setDouble(3, modifiedSeat.getPrice());
            preparedStatement.setInt(4, modifiedSeat.getStatus());

            // Execute the INSERT query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                return true; 
            } else {
                connection.rollback(); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {

        }

        return false; // Insertion failed
    }
    
    
    public static boolean deleteSeatFromDatabase(String seatIDToDelete) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = Database.getConnection();

            // Define the SQL query to delete the seat by SeatID
            String query = "DELETE FROM Seat WHERE seatID = ?";

            // Create a prepared statement
            preparedStatement = connection.prepareStatement(query);

            // Set the parameter for the prepared statement (seatID)
            preparedStatement.setString(1, seatIDToDelete);

            // Execute the DELETE query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the deletion was successful
            if (rowsAffected > 0) {
                return true; // Deletion successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in a finally block to ensure they are always closed
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Deletion failed
    }
    
}

