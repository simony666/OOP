package Artist;

import util.ClearScreen;
import util.Validator;
import util.Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;


/**
 *
 * @author Zy
 */
public class ArtistManagement {
    
   
    // display artist screen
    public static void displayArtistScreen() {
        ArrayList<Artist> artistList = Artist.getArtistArrayList(); // Retrieve the artist list
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "=====================================");
            System.out.println("========   Manage Artist  ===========");
            System.out.println("=====================================");
            System.out.println("======= 1) Add Artist         =======");
            System.out.println("======= 2) View Artist        =======");
            System.out.println("======= 3) Modify Artist      =======");
            System.out.println("======= 4) Remove Artist      =======");
            System.out.println("======= 5) Back to Admin Page =======");
            System.out.println("======= 6) Exit               =======");
            System.out.println("=====================================" + "\n");

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
                            ClearScreen.cls();
                            ArtistManagement.addArtist();
                            break;
                        case 2:
                            ClearScreen.cls();
                            ArtistManagement.viewArtist(); 
                            break;
                        case 3:
                            ClearScreen.cls();
                            updateArtist(artistList); // Pass the artistList
                            break;
                        case 4:
                            ClearScreen.cls();
                            deleteArtist(artistList); // Pass the artistList
                            break;
                        case 5:
                             ClearScreen.cls();
                             main.mainScreen.displayAdmin();
                            break;
                        case 6:
                            System.out.println("Thanks for using");
                            System.exit(0);
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


    // add artist
    private static Artist addArtist() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("============   Add Artist  =============");
        System.out.println("========================================\n");

        String artistName;
        do {
            System.out.print("Please enter the Artist's Name: ");
            artistName = sc.nextLine().trim();
            if (artistName.isEmpty()) {
                System.out.println("Artist Name cannot be empty. Please try again.");
            } else if (!Validator.containsOnlyAlphabetic(artistName)) {
                System.out.println("Artist Name must contain only letters. Please try again.");
                artistName = ""; // Reset artistName to trigger the loop again
            } else if (artistNameExists(artistName)) {
                System.out.println("Artist with the same name already exists in the database.");
                return null; // Return null to indicate an error
            }
        } while (artistName.isEmpty());

        String bandName = ""; // Initialize bandName to an empty string

        try {
            System.out.print("Please enter the Band name (or leave it empty for no band): ");
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                bandName = input; // Update the bandName if input is not empty
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Band name cannot be empty.")) {
                System.out.println("Invalid band name input. Band name cannot be empty.");
                return null; // Return null to indicate an error
            }
        }

        // Create the artist
        Artist artist = new Artist(artistName, bandName);

        // Insert the artist into the database and check if insertion was successful

        boolean insertionSuccessful = insertArtist(artist.getName(), artist.getBandName());

        if (insertionSuccessful) {
            // Add the artist to the ArrayList only if insertion was successful
            Artist.getArtistArrayList().add(artist);
            System.out.println("Artist added successfully");
        } else {
            System.out.println("Failed to add artist.");
        }

        return artist; // Return the created artist
    }


    // view artist
    public static void viewArtist() {
        // Call the method to retrieve data from the database
        ArrayList<Artist> artistList = getArtist();

        if (artistList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("! No artist added, please add an artist to view. !");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            ArtistManagement.displayArtistScreen();

        } else {
            System.out.println("\n" + "==============================================================");
            System.out.println("======================   Artist Lists  =======================");
            System.out.println("==============================================================" + "\n");
            System.out.println("***************************************************************");
            System.out.printf("%-15s %-20s %-15s", "Artist ID", "Artist Name", "Band Name");
            System.out.println("\n" + "***************************************************************");

            for (Artist a : artistList) {
                System.out.printf("%s %20s %20s", a.getId(), a.getName(), a.getBandName()+ "\n");
            }
        }
    }



    // delete artist
    public static void deleteArtist(ArrayList<Artist> array) {
        // Call the method to retrieve data from the database
        ArrayList<Artist> artistList = getArtist();
        
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);
        

        // display available artist
        ArtistManagement.viewArtist();
        try {
            System.out.print("Please enter the artist ID that you want to delete: ");
            String aId = sc.nextLine();

            // check the artist id is it contains symbols
            if (Validator.containsSymbol(aId)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                // check whether the artist ID exist
                // if no, prompt message, yes, remove it
                int index = -1;
                for (int i = 0; i < artistList.size(); i++) {
                    Artist a = artistList.get(i);
                    if (a.getId() == Integer.parseInt(aId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("The artist ID is not available. Please try it again");
                } else {
                    artistList.remove(index);
                    deleteArtist(aId);
                    System.out.println("Remove artist's ID successfully");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }

    // Update artist
    public static void updateArtist(ArrayList<Artist> array) {
        // Call the method to retrieve data from the database
        
        ArrayList<Artist> artistList = getArtist();
        
        Scanner sc = new Scanner(System.in);

        // Display available artists
        ArtistManagement.viewArtist();

        // Capture user input for the artist ID
        System.out.print("Please enter the artist ID that you want to modify: ");
        String aIdInput = sc.nextLine();

        try {
            int aId = Integer.parseInt(aIdInput);
            //System.out.println(String.valueOf(aId));

            // Find the artist with the given ID
            Artist selectedArtist = null;
                   
            for (Artist artist : artistList) {
                if (artist.getId() == aId) {
                    selectedArtist = artist;
                    break;
                }
            }
            
            if (selectedArtist == null) {
                System.out.println("The artist with ID " + aId + " does not exist.");
                return; // Exit the method if the artist doesn't exist
            }

            String artistName;
            do {
                System.out.print("Please enter the Artist's Name: ");
                artistName = sc.nextLine().trim();
                if (artistName.isEmpty()) {
                    System.out.println("Artist Name cannot be empty. Please try again.");
                } else if (!Validator.containsOnlyAlphabetic(artistName)) {
                    System.out.println("Artist Name must contain only letters. Please try again.");
                    artistName = ""; // Reset artistName to trigger the loop again
                } else if (artistNameExists(artistName)) {
                    System.out.println("Artist with the same name already exists in the database.");
                    return; // Exit the method if the artist name already exists
                }
            } while (artistName.isEmpty());

            // Update artist details
            selectedArtist.setName(artistName);

            String bandName = null; // Initialize bandName as null

            // Capture new band name
            System.out.print("Please enter Band name (or leave it empty for no band): ");
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                bandName = input; // Update bandName if input is not empty
            }

            selectedArtist.setBandName(bandName);

            // Corrected method call
            updateArtist(aId, artistName, bandName);

            System.out.println("Artist details updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid artist ID.");
        }
    }
    
    public static ArrayList<Artist> getArtist() {
        ArrayList<Artist> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Artist`;";
        ResultSet result = Database.runQuery(sqlText);
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String bandName = result.getString("bandName");

                // Process the retrieved data here
                Artist tempArtist = new Artist(id, name, bandName);
                tempList.add(tempArtist);
            }

            return tempList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tempList;
    }

    
    
    public static boolean insertArtist(String name, String bandName) {
        if (artistNameExists(name)) {
            System.out.println("Artist with the same name already exists in the database.");
            return false; // Don't insert if the name already exists
        }

        String sql = "INSERT INTO `Artist` (`name`, `bandName`) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);

            // Conditionally set bandName to null if it's empty
            if (bandName.isEmpty()) {
                preparedStatement.setNull(2, java.sql.Types.VARCHAR); // Set bandName to NULL
            } else {
                preparedStatement.setString(2, bandName); // Set bandName to the provided value
            }

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0; //System.out.println("Artist added successfully");
            //System.out.println("Failed to add artist");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean artistNameExists(String name) {
        String query = "SELECT COUNT(*) FROM `Artist` WHERE `name` = \"" + name + "\"";
        try {
            ResultSet resultSet = Database.runQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void updateArtist(int Id, String newName, String newBandName) {
        if (artistNameExists(newName)) {
            System.out.println("Artist with the same name already exists in the database.");
            return; // Exit the method if the name already exists
        }

        if (!Validator.containsOnlyAlphabetic(newName)) {
            System.out.println("Artist Name must contain only letters. Please try again.");
            return; // Exit the method if the name contains non-alphabetic characters
        }

        String sql = "UPDATE `Artist` SET `name` = \"" + newName + "\", `bandName` = \"" + newBandName + 
                "\" WHERE `Id` = " + Id + ";";

        Database.runUpdate(sql);
    }
    
    public static void deleteArtist(String Id) {
        String sql = "DELETE FROM `Artist` WHERE `Id` = " + Id + ";";
        Database.runUpdate(sql);
    }
    
    public static String getArtistName(int artistId) {
        // Implement logic to fetch the artist name based on artistId from your artistList
        // Return the artist name or an appropriate default value if not found
        for (Artist a : getArtist()) {
            if (a.getId() == artistId) {
                return a.getName();
            }
        }
        return ""; // Artist with the given ID not found, return an appropriate default value
    }


}