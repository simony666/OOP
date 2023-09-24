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

public class PerformanceManagement{
    // performance and Artist array List
    //static ArrayList<Performance> array = new ArrayList<Performance>();
    static ArrayList<Performance> pfmArrayList = Performance.getPfmArrayList(); 
    static ArrayList<Artist.Artist> artistArrayList = Artist.Artist.getArtistArrayList(); 
    
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
                if (util.SymbolValidator.containsSymbol(input)){
                    System.out.println("Input contains the specific symbols.");
                }else{
                    int selection = Integer.parseInt(input);
                    // Use switch case to complete the selection operation
                    switch(selection){

                      // Add Performance
                        case 1:
//                          System.out.println("Add performance");
                            addPerformance(artistArrayList,pfmArrayList);
                            break;

                        // View Performance   
                        case 2:
                            viewPerformance(artistArrayList,pfmArrayList);
                            break;

                        // Modify Performance
                        case 3:
                            updatePerformance(artistArrayList,pfmArrayList);
                            break;

                        // Remove Performance
                        case 4:
                            deletePerformance(pfmArrayList);
                            break;

                        // Back to Admin page     
                        case 5:
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
    
    
    // add performance
    public static void addPerformance(ArrayList<Artist.Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
        // initial the code
        Scanner sc = new Scanner(System.in);

        
        // Check if there are any performances in the list
        if (artistArrayList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!! No artist added, please add an artist to view. !!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            Artist.ArtistManagement.displayArtistScreen();
            return;
        }else{
            // Display available artists
            Artist.ArtistManagement.viewArtist();
        }
        // Capture user input for the artist ID
        System.out.print("Please enter the artist ID for the performance: ");
        String aId = sc.nextLine();

        // Check if the artist ID contains symbols
        if (util.SymbolValidator.containsSymbol(aId)) {
            System.out.println("Input contains specific symbols.");
            return;
        }

        try {
            int artistId = Integer.parseInt(aId);

            // Check whether the artist ID exists
            Artist.Artist selectedArtist = null;
            for (Artist.Artist artist : artistArrayList) {
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
            // perforance name
            String pfmName,pfmType;
            do {
                System.out.print("Please enter Performance name: ");
                pfmName = sc.nextLine().trim();
                if (pfmName.isEmpty()) {
                    System.out.println("Performance Name cannot be empty. Please try again.");
                }
            } while (pfmName.isEmpty());

            do {
                System.out.print("Please enter Performance type: ");
                pfmType = sc.nextLine().trim();
                if (pfmType.isEmpty()) {
                    System.out.println("Performance type cannot be empty. Please try again.");
                }
            } while (pfmType.isEmpty());
            
            
            // Create a new Performance instance
            Performance p = new Performance(pfmName,pfmType);
            p.setArtist(selectedArtist);

            // Add the selected Artist to performance Array List
            pfmArrayList.add(p);
            System.out.println("Performance added successfully");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the artist ID.");
        }
    }

    // view performance
    public static void viewPerformance(ArrayList<Artist.Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
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
        System.out.printf("%-15s %-18s %-18s %-20s", "Performance ID", "Performance Name",  "Performance Type","Artist Name");
        System.out.println("\n" + "**********************************************************************");

        // Retrieve performance info from the arrayList
        for (Performance p : pfmArrayList) {
            Artist.Artist artist = p.getArtist();
            System.out.printf("%-15s %-18s %-18s %-20s", p.getId(), p.getName(), p.getType(), artist.getName() + "\n");
        }
    }

     // Update performance
    public static void updatePerformance(ArrayList<Artist.Artist> artistArrayList, ArrayList<Performance> pfmArrayList) {
        Scanner sc = new Scanner(System.in);

        // Check if there are any performances in the list
        PerformanceManagement.viewPerformance(artistArrayList, pfmArrayList);
        
        // Capture user input for the performance ID
        System.out.print("\nPlease enter the performance ID that you want to modify: ");
        String pIdInput = sc.nextLine();

        try {
            int pId = Integer.parseInt(pIdInput);

            // Check whether the artist ID exists
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

            // Capture new artist name
            String pfmName;

            try {
                // sc.nextLine(); // Consume the newline character
                do {
                System.out.print("Please enter Performance name: ");
                pfmName = sc.nextLine().trim();
                if (pfmName.isEmpty()) {
                    System.out.println("Performance Name cannot be empty. Please try again.");
                }
            } while (pfmName.isEmpty());
                

                // Update artist details
                selectedPfm.setName(pfmName);
                System.out.println("Performance details updated successfully!");
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a numeric value for age.");
                sc.nextLine(); // Clear the input buffer
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid artist ID.");
        }
    }

    // delete performance
    public static void deletePerformance(ArrayList<Performance> pfmArrayList) {
       // ArrayList<Artist> artistArrayList = Artist.getArtistArrayList();
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);

        try {
            PerformanceManagement.viewPerformance(artistArrayList, pfmArrayList);
            System.out.print("Please enter the performance ID that you want to delete: ");
            String pId = sc.nextLine();

            // check the artist id is it contains symbols
            if (util.SymbolValidator.containsSymbol(pId)) {
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
                    System.out.println("Remove performance's ID successfully");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }
    
}