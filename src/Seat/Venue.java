package Seat;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * @author QL
 */
public class Venue {
    private String venueID;
    private String location;
    private int capacity;
    private static ArrayList<Venue> venueArrayList = new ArrayList<>();
    
    
    public Venue(){
    }

    public Venue(String venueID, String location, int capacity) {
        this.venueID = venueID;
        this.location = location;
        this.capacity = capacity;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static ArrayList<Venue> getVenueArrayList() {
        return venueArrayList;
    }

    public static void setVenueArrayList(ArrayList<Venue> venueArrayList) {
        Venue.venueArrayList = venueArrayList;
    }
    
    @Override
    public String toString() {
        return String.format("%-15s %-25s, %-15", venueID, location, capacity);
    }
    
    
    
    //Add Venue method
    public static Venue addVenue() {
        Scanner sc = new Scanner(System.in);
        String InVenue;
        String InLocation;
        int InCapacity;
        
        //let user input the venueID and verify
        do {
            boolean idExist = false;
            System.out.print("Please enter Venue ID: ");
            InVenue = sc.nextLine().trim();
            if (InVenue.isEmpty()) {
                System.out.println("Venue ID cannot be empty. Please try again.");
                InVenue = null;
            }else 
                // Check if the Venue ID already exists
                for (Venue venue : venueArrayList) {
                    if (venue.getVenueID().equals(InVenue)) {
                        idExist = true;
                        break;
                    }
                }
            if (idExist) {
                System.out.println("Venue ID already exists. Please choose a different Venue ID.");
                InVenue = null; // Set InVenue to null to indicate an error
            }
        }while (InVenue == null);

        //let let user input the location and verify
        do {
            System.out.print("Please enter location: ");
            InLocation = sc.nextLine().trim();
            if (InLocation.isEmpty()) {
            System.out.println("Location cannot be empty. Please try again.");
            }
        } while (InLocation.isEmpty());

        //let user input the capacity and verify
            try {
                System.out.print("Please enter capacity: ");
                InCapacity = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                if (InCapacity < 0 ) {
                    throw new IllegalArgumentException("Invalid capacity");
                }
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals("Invalid capacity")) {
                    System.out.println("Invalid capacity input. Please try again");
                    return null; // Return null to indicate an error
                }
            
                InCapacity = 0;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a value.");
                sc.nextLine();
                InCapacity = -1;
                return null; // Return null to indicate an error
            }


        // Create the temporary list for store Venue
        Venue VList = new Venue(InVenue, InLocation, InCapacity);

        // Add the artist to the ArrayList
        Venue.venueArrayList.add(VList);
        System.out.println("Venue added successfully");

        return VList; // Return the venue
    }
    
    //View all venue
    public static void viewAllVenue() {
        if (venueArrayList.isEmpty()) {
            System.out.println("============:   No venues found   :============" + "\n\n");
        } else {
            System.out.println("=============:   Venue List   :=============");
            System.out.printf("%-15s %-25s %-15s%n", "venueID", "location", "capacity");
            System.out.println("______________________________________________________");
            for (Venue venue : venueArrayList) {
                System.out.printf("%-15s %-25s %-15s%n", venue.getVenueID(), venue.getLocation(), venue.getCapacity() + "\n");
            }
        }
    }
    
    //Modify venue method
    public static void modifyVenue() {
        Scanner sc = new Scanner(System.in);

        viewAllVenue();

        // Prompt the user to enter the Venue ID to modify
        System.out.print("\n" + "Please enter the Venue ID to modify: ");
        String modifySc = sc.nextLine().trim();

        // Find the index of the venue with the matching Venue ID
        int index = -1;
        for (int i = 0; i < venueArrayList.size(); i++) {
            Venue venue = venueArrayList.get(i);
            if (venue.getVenueID().equals(modifySc)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Venue with ID '" + modifySc + "' not found.");
        } else {
            // Venue found, allow modifications
            System.out.println("Current Venue Details:");
            System.out.println("Venue ID: " + venueArrayList.get(index).getVenueID());
            System.out.println("Location: " + venueArrayList.get(index).getLocation());
            System.out.println("Capacity: " + venueArrayList.get(index).getCapacity());

            // Prompt for new details
            System.out.print("Enter new Venue ID (press Enter to keep current): ");
            String newVenueID = sc.nextLine().trim();
        
            // Check if a venue with the new Venue ID already exists (excluding the current venue)
            for (int i = 0; i < venueArrayList.size(); i++) {
                Venue venue = venueArrayList.get(i);
                if (venue.getVenueID().equals(newVenueID) && !newVenueID.equals(modifySc)) {
                    System.out.println("Venue with ID '" + newVenueID + "' already exists. Please choose a different Venue ID.");
                    return; // Return early if the new Venue ID already exists
                }
            }

            // If the user wants to change the Venue ID, update it
            if (!newVenueID.isEmpty()) {
                venueArrayList.get(index).setVenueID(newVenueID);
            }

            System.out.print("Enter new Location (press Enter to keep current): ");
            String newLocation = sc.nextLine().trim();
            if (!newLocation.isEmpty()) {
                venueArrayList.get(index).setLocation(newLocation);
            }

            System.out.print("Enter new Capacity (press Enter to keep current): ");
            String newCapacityStr = sc.nextLine().trim();
            if (!newCapacityStr.isEmpty()) {
                try {
                    int newCapacity = Integer.parseInt(newCapacityStr);
                    if (newCapacity > 0) {
                        venueArrayList.get(index).setCapacity(newCapacity);
                        System.out.println("Venue details modified successfully.");
                    } else {
                        System.out.println("Invalid capacity input. Capacity must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for capacity.");
                }
            }
        }   
    }
    
    
    //Delete venue method
    public static void deleteVenue(ArrayList<Venue> venueArrayList) {
        Scanner sc = new Scanner(System.in);

        viewAllVenue();

        try {
            System.out.print("Please enter the Venue ID that you want to delete: ");
            String deleSc = sc.nextLine();
            int index = -1;
            for (int i = 0; i < venueArrayList.size(); i++) {
                Venue dele = venueArrayList.get(i);
                if (dele.getVenueID().equals(deleSc)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("The venue ID is not exits. Please try it again");
            } else {
                venueArrayList.remove(index);
                System.out.println("Remove venue ID successfully");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a value.");
        }
    }
    
    public static Venue existVenue(String targetVenueID){
        String findVenue = null;
        String findLocation = null;
        int findCapacity = -1;
        for (int i =0; i < venueArrayList.size(); i++) {
            Venue venue = venueArrayList.get(i);
            if (venue.getVenueID().equals(targetVenueID)) {
                findVenue = venue.getVenueID();
                findLocation = venue.getLocation();
                findCapacity = venue.getCapacity();
                return venue;
            }
        }
            return null;
        }
    
}
