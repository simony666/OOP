package Artist;

import util.SymbolValidator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 *
 * @author Zy
 */


/**
 *
 * @author Zy
 */
public class ArtistManagement {
    // create array object to store artist data
   static ArrayList<Artist> array = new ArrayList<Artist>();
   
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

    String artistName;
    do {
        System.out.print("Please enter Artist Name: ");
        artistName = sc.nextLine().trim();
        if (artistName.isEmpty()) {
            System.out.println("Artist Name cannot be empty. Please try again.");
        }
    } while (artistName.isEmpty());

    int artistAge;

    try {
        System.out.print("Please enter Artist age: ");
        artistAge = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        if (artistAge <= 0 || artistAge >= 100) {
            throw new IllegalArgumentException("Invalid age");
        }
    } catch (IllegalArgumentException e) {
        if (e.getMessage().equals("Invalid age")) {
            System.out.println("Invalid age input. Age must be between 1 and 99.");
            return null; // Return null to indicate an error
        }
        artistAge = 0;
    } catch (InputMismatchException ex) {
        System.out.println("Invalid input. Please enter a numeric value for age.");
        sc.nextLine();
        return null; // Return null to indicate an error
    }

    // Create the artist
    Artist artist = new Artist(artistName, artistAge);

    // Add the artist to the ArrayList
    Artist.getArtistArrayList().add(artist);
    System.out.println("Artist added successfully");

    return artist; // Return the created artist
}


    // view artist
    public static void viewArtist() {
    ArrayList<Artist> artistArrayList = Artist.getArtistArrayList();
    
    // check if there are any artists in the list
    if (artistArrayList.isEmpty()) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("! No artist added, please add an artist to view. !");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
        ArtistManagement.displayArtistScreen();
        return;
    }

    // display the heading
    System.out.println("\n" + "==============================================================");
    System.out.println("======================   Artist Lists  =======================");
    System.out.println("==============================================================" + "\n");
    System.out.println("***************************************************************");
    System.out.printf("%-15s %-20s %-15s", "Artist ID", "Artist Name", "Artist Age");
    System.out.println("\n" + "***************************************************************");

    // retrieve artist info from arrayList
    for (int i = 0; i < artistArrayList.size(); i++) {
        Artist a = artistArrayList.get(i);
        System.out.printf("%s %18s %20s", a.getId(), a.getName(), a.getAge() + "\n");
    }
}


    // delete artist
    public static void deleteArtist(ArrayList<Artist> array) {
       // ArrayList<Artist> artistArrayList = Artist.getArtistArrayList();
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);
        

        // display available artist
        ArtistManagement.viewArtist();
        try {
            System.out.print("Please enter the artist ID that you want to delete: ");
            String aId = sc.nextLine();

            // check the artist id is it contains symbols
            if (SymbolValidator.containsSymbol(aId)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                // check whether the artist ID exist
                // if no, prompt message, yes, remove it
                int index = -1;
                for (int i = 0; i < array.size(); i++) {
                    Artist a = array.get(i);
                    if (a.getId() == Integer.parseInt(aId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("The artist ID is not available. Please try it again");
                } else {
                    array.remove(index);
                    System.out.println("Remove artist's ID successfully");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }

    // Update artist
    public static void updateArtist(ArrayList<Artist> array) {
        Scanner sc = new Scanner(System.in);

        // display available artist
        ArtistManagement.viewArtist();
        
        // Capture user input for the artist ID
        System.out.print("Please enter the artist ID that you want to modify: ");
        String aIdInput = sc.nextLine();

        try {
            int aId = Integer.parseInt(aIdInput);

            // Check whether the artist ID exists
            Artist selectedArtist = null;
            for (Artist artist : array) {
                if (artist.getId() == aId) {
                    selectedArtist = artist;
                    break;
                }
            }

            if (selectedArtist == null) {
                System.out.println("The artist with ID " + aId + " does not exist.");
                return;
            }

            // Capture new artist name
            System.out.print("Please enter new Artist Name: ");
            String aName = sc.nextLine();

            int aAge;

            try {
                // Capture new artist age
                System.out.print("Please enter new Artist age: ");
                aAge = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                // Validation for age constraints
                if (aAge <= 0 || aAge >= 100) {
                    System.out.println("Invalid age input. Age must be between 1 and 99.");
                    return; // Exit the method without updating the artist
                }

                // Update artist details
                selectedArtist.setName(aName);
                selectedArtist.setAge(aAge);

                System.out.println("Artist details updated successfully!");
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a numeric value for age.");
                sc.nextLine(); // Clear the input buffer
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid artist ID.");
        }
    }

}
