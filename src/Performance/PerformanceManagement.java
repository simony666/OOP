/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Performance;

/**
 *
 * @author Zy
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

import util.ClearScreen;
import util.Validator;
import util.Database;
import Artist.Artist;
import Artist.ArtistManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceManagement{
    // performance and Artist array List
    static ArrayList<Performance> pfmArrayList = getPfm(); 
    static ArrayList<Artist> artistArrayList = ArtistManagement.getArtist();
    private static List<PerformanceType> performanceTypes = new ArrayList<>();

    static {
        performanceTypes.add(new DancePerformance());
        performanceTypes.add(new MusicPerformance());
        performanceTypes.add(new PopPerformance());
    }
    
    public static void main (String[] args){
        new Database();
        displayPerformanceScreen();
    }

    
    // display performance screen
    public static void displayPerformanceScreen(){
        do{
            System.out.println("\n"+"=======================================");
            System.out.println("========  Manage Performance  =========");
            System.out.println("=======================================");
            System.out.println("======= 1) Add Performance      =======");
            System.out.println("======= 2) View Performance     =======");
            System.out.println("======= 3) Modify Performance   =======");
            System.out.println("======= 4) Remove Performance   =======");
            System.out.println("======= 5) Back to Admin Page   =======");
            System.out.println("======= 6) Exit                 =======");
            System.out.println("======================================="+"\n");

        try{    
               // capture user input
                Scanner sc = new Scanner(System.in);
                System.out.print("Please enter your selection: ");
                String input = sc.nextLine();

                // symbol checking
                if (util.Validator.containsSymbol(input)){
                    System.out.println("Input contains the specific symbols.");
                }else{
                    int selection = Integer.parseInt(input);
                    // Use switch case to complete the selection operation
                    switch(selection){

                      // Add Performance
                        case 1:
                            ClearScreen.cls();
                            addPerformance(artistArrayList,pfmArrayList);
                            break;

                        // View Performance   
                        case 2:
                            ClearScreen.cls();
                            viewPerformance(artistArrayList,pfmArrayList);
                            break;

                        // Modify Performance
                        case 3:
                            ClearScreen.cls();
                            updatePerformance(artistArrayList,pfmArrayList);
                            break;

                        // Remove Performance
                        case 4:
                            ClearScreen.cls();
                            deletePerformance(pfmArrayList);
                            break;

                        // Back to Admin page     
                        case 5:
                            ClearScreen.cls();
                            main.mainScreen.displayAdmin();
                            break;

                        // exit   
                        case 6:
                            System.out.println("Thanks for using");
                            System.exit(0);
                            break;

                        // other range between 1-6
                        default:
                            System.out.println("Invalid Range, Please enter number between 1 to 6");
                            PerformanceManagement.displayPerformanceScreen();
                    }
                }
                }catch(NumberFormatException e){
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            }while(true);
    }     
    
    
    // Add performance
    public static void addPerformance(ArrayList<Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
        // Initialize the code
        Scanner sc = new Scanner(System.in);

        // Check if there are any performances in the list
        if (artistArrayList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!! No artists have been added. Please add an artist to view performances. !!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            ArtistManagement.displayArtistScreen();
            return;
        } else {
            // Display available artists
            ArtistManagement.viewArtist();
        }

        // Capture user input for the artist ID
        System.out.println("");
        System.out.print("Please enter the artist ID for the performance: ");
        String aId = sc.nextLine();

        // Check if the artist ID contains symbols
        if (Validator.containsSymbol(aId)) {
            System.out.println("Input contains specific symbols.");
            return;
        }

        try {
            int artistId = Integer.parseInt(aId);

            // Check whether the artist ID exists
            Artist selectedArtist = null;
            for (Artist artist : artistArrayList) {
                if (artist.getId() == artistId) {
                    selectedArtist = artist;
                    break;
                }
            }

            if (selectedArtist == null) {
                System.out.println("The artist with ID " + artistId + " does not exist.");
                return;
            }

            // Capture additional performance details
            String pfmName, pfmType;
            do {
                System.out.print("Please enter the performance name: ");
                pfmName = sc.nextLine().trim();
                if (pfmName.isEmpty()) {
                    System.out.println("Performance Name cannot be empty. Please try again.");
                }
            } while (pfmName.isEmpty());

            // Prompt the user to choose a performance type
            boolean isValidType = false;
            do {
                System.out.println("");
                System.out.println("--------------------------------------------------------------");
                System.out.println("Please choose a performance type from the following options:");
                System.out.println("--------------------------------------------------------------");
                for (PerformanceType type : performanceTypes) {
                    System.out.println(type.getTypeName());
                }
                System.out.println("");
                System.out.print("Enter the selected performance type: ");
                pfmType = sc.nextLine().trim();

                if (pfmType.isEmpty()) {
                    System.out.println("Performance type cannot be empty. Please try again.");
                } else {

                    for (PerformanceType type : performanceTypes) {
                        if (type.getTypeName().equalsIgnoreCase(pfmType)) {
                            isValidType = true;
                            break;
                        }
                    }
                    if (!isValidType) {
                        System.out.println("Invalid performance type. Please select from the available options.");
                    }
                }
            } while (pfmType.isEmpty() || !isValidType);

            // Create a new Performance instance
            Performance p = new Performance(pfmName, pfmType);
            p.setArtist(selectedArtist);

            // Add the selected Artist to the performance ArrayList
            pfmArrayList.add(p);

            // Insert performance into the database
            insertPerformance(pfmName, pfmType, artistId);
            System.out.println("Performance added successfully");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the artist ID.");
        }
    }

    // view performance
    public static void viewPerformance(ArrayList<Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
        
        try {
        // Check if there are any performances in the list
        if (pfmArrayList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("! No performances added, please add a performance to view. !");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            PerformanceManagement.displayPerformanceScreen();
            return;
        }

            // Display the heading
            System.out.println("\n" + "======================================================================");
            System.out.println("========================   Performance Lists  ========================");
            System.out.println("======================================================================" + "\n");
            System.out.println("**********************************************************************");
            System.out.printf("%-15s %-20s %-20s %-20s", "Performance ID", "Performance Name", "Performance Type", "Artist Name");
            System.out.println("\n" + "**********************************************************************");


            // Retrieve performance info from the arrayList
            for (Performance p : pfmArrayList) {
                Artist artist = p.getArtist();
                System.out.printf("%s %23s %20s %20s", p.getId(), p.getName(), p.getType(), artist.getName() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            System.out.println("An error occurred while trying to view performances.");
        }
    }

    // Update performance
    public static void updatePerformance(ArrayList<Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
        Scanner sc = new Scanner(System.in);

        // Check if there are any performances in the list
        PerformanceManagement.viewPerformance(artistArrayList, pfmArrayList);

        // Capture user input for the performance ID
        System.out.print("\nPlease enter the performance ID that you want to modify: ");
        String pIdInput = sc.nextLine();

        try {
            int pId = Integer.parseInt(pIdInput);

            // Check whether the performance ID exists
            Performance selectedPfm = null;
            for (Performance p : pfmArrayList) {
                if (p.getId() == pId) {
                    selectedPfm = p;
                    break;
                }
            }

            if (selectedPfm == null) {
                System.out.println("The performance with ID " + pId + " does not exist.");
                return;
            }

            // Capture new performance name
            String pfmName;
            do {
                System.out.print("Please enter Performance name: ");
                pfmName = sc.nextLine().trim();
                if (pfmName.isEmpty()) {
                    System.out.println("Performance Name cannot be empty. Please try again.");
                }
            } while (pfmName.isEmpty());

            // Prompt the user to choose a performance type
            boolean isValidType = false;
            String pfmType = "";

            do {
                System.out.println("");
                System.out.println("--------------------------------------------------------------");
                System.out.println("Please choose a performance type from the following options:");
                System.out.println("--------------------------------------------------------------");
                for (PerformanceType type : performanceTypes) {
                    System.out.println(type.getTypeName());
                }
                System.out.print("Enter the selected performance type: ");
                pfmType = sc.nextLine().trim();

                if (pfmType.isEmpty()) {
                    System.out.println("Performance type cannot be empty. Please try again.");
                } else {
                    boolean typeExists = false;
                    for (PerformanceType type : performanceTypes) {
                        if (type.getTypeName().equalsIgnoreCase(pfmType)) {
                            typeExists = true;
                            break;
                        }
                    }
                    if (!typeExists) {
                        System.out.println("Invalid performance type. Please select from the available options.");
                    } else {
                        isValidType = true;
                    }
                }
            } while (!isValidType);

            // Capture user input for the artist ID
            int artistId = -1;
            String artistName = null; // Initialize artistName

            while (true) {
                ArtistManagement.viewArtist();
                System.out.println("");
                System.out.print("Please enter the artist ID for the performance: ");
                String aId = sc.nextLine();

                // Check if the artist ID contains symbols
                if (Validator.containsSymbol(aId)) {
                    System.out.println("Input contains specific symbols.");
                    continue; // Re-enter the artist ID
                }

                try {
                    artistId = Integer.parseInt(aId);

                    // Retrieve the artist's name based on artistId
                    Artist selectedArtist = null;
                    for (Artist artist : artistArrayList) {
                        if (artist.getId() == artistId) {
                            selectedArtist = artist;
                            artistName = selectedArtist.getName(); // Get the artist's name
                            break;
                        }
                    }

                    if (selectedArtist == null) {
                        System.out.println("The artist with ID " + artistId + " does not exist.");
                    } else {
                        // If artist ID is valid, break the loop
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for the artist ID.");
                }
            }

            // Update the selected performance details
            selectedPfm.setName(pfmName);
            selectedPfm.setType(pfmType);
            selectedPfm.setArtistId(artistId);

            // Update the performance in the database
            updatePerformance(pId, pfmName, pfmType, artistName,artistId);
            System.out.println("Performance details updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid performance ID.");
        }
    }



    // delete performance
    public static void deletePerformance(ArrayList<Performance> pfmArrayList) {
       // ArrayList<Artist> artistArrayList = Artist.getArtistArrayList();
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);

        try {
            PerformanceManagement.viewPerformance(artistArrayList, pfmArrayList);
            System.out.println("");
            System.out.print("Please enter the performance ID that you want to delete: ");
            String pId = sc.nextLine();

            // check the artist id is it contains symbols
            if (util.Validator.containsSymbol(pId)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                // check whether the artist ID exist
                // if no, prompt message, yes, remove it
                int index = -1;
                for (int i = 0; i < pfmArrayList.size(); i++) {
                    Performance p = pfmArrayList.get(i);
                    if (p.getId() == Integer.parseInt(pId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("The performance ID is not available. Please try it again");
                    
                } else {
                    pfmArrayList.remove(index);
                    deletePerformance(pId);
                    System.out.println("Remove performance's ID successfully");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }
    
    // Performance
    public static ArrayList<Performance> getPfm() {
        ArrayList<Performance> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Performance`;";
        ResultSet result = Database.runQuery(sqlText);
        try {
            while (result.next()) {
            int id = result.getInt("Id");
            String name = result.getString("performanceName");
            String type = result.getString("performanceType");
            int artistId = result.getInt("artistId"); // Get the artist's ID from the result

            // Call the getArtistName method to retrieve the artist's name
            String artistName = getArtistName(artistId);

            // Process the retrieved data here
            Performance tempPfm = new Performance(id, name, type, artistId, artistName);
            tempList.add(tempPfm);
        }
          return tempList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tempList;
    }



    
public static void insertPerformance(String performanceName, String performanceType, int artistId) {
    String sql = "INSERT INTO `Performance` (`performanceName`, `performanceType`, `artistName`, `artistId`) VALUES (?, ?, ?, ?)";

    // Fetch the artist name based on the artist ID
    String artistName = getArtistName(artistId);
    
    try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
        preparedStatement.setString(1, performanceName);
        preparedStatement.setString(2, performanceType);
        preparedStatement.setString(3, artistName);
        preparedStatement.setInt(4, artistId);

        preparedStatement.executeUpdate();
        // System.out.println("Performance added successfully. Rows affected: " + result);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static String getArtistName(int artistId) {
        // Implement logic to fetch the artist name based on artistId from your artistList
        // Return the artist name or an appropriate default value if not found
        for (Artist a : artistArrayList) {
            if (a.getId() == artistId) {
                return a.getName();
            }
        }
        return ""; // Artist with the given ID not found, return an appropriate default value
    }

    private static Performance findPerformanceById(int performanceId) {
            // Implement logic to search for the performance in your performanceList
            // Return the found Performance object or null if not found
            for (Performance p : pfmArrayList) {
                if (p.getId() == performanceId) {
                    return p; // Found the performance by ID
                }
            }
            return null;
    }
    
    public static void updatePerformance(int performanceId, String newName, String newType, String artistName, int artistId) {
        Performance performanceToUpdate = findPerformanceById(performanceId);
        // Fetch the artist name based on the artist ID
        String aName = getArtistName(artistId);
        
        if (performanceToUpdate != null) {
             String sql = "UPDATE Performance SET performanceName = ?, performanceType = ?, artistName = ? WHERE id = ?";
            
                try (
                PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newType);
                preparedStatement.setString(3, aName);
                preparedStatement.setInt(4, performanceId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    // System.out.println("Performance updated successfully. Rows affected: " + rowsUpdated);
                } else {
                    System.out.println("No performance found with ID " + performanceId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or show an error message to the user.
            }
             
        } else {
            System.out.println("No performance found with ID " + performanceId);
        }
       
    }

    public static void deletePerformance(String Id) {
        String sql = "DELETE FROM `Performance` WHERE `Id` = " + Id + ";";
        Database.runUpdate(sql);
    }
    
}