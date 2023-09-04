package Artist;

import util.SymbolValidator;
import util.Role;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 *
 * @author Zy
 */
public class ArtistManagement {
    // create array object to store artist data
   static ArrayList<Artist> array = new ArrayList<Artist>();
   
    // display artist screen  
    public static void displayArtistScreen(){
        do{
            System.out.println("\n"+"=====================================");
            System.out.println("========   Manage Artist  ===========");
            System.out.println("=====================================");
            System.out.println("======= 1) Add Artist         =======");
            System.out.println("======= 2) View Artist        =======");
            System.out.println("======= 3) Modify Artist      =======");
            System.out.println("======= 4) Remove Artist      =======");
            System.out.println("======= 5) Back to Admin Page =======");
            System.out.println("======= 6) Exit               =======");
            System.out.println("====================================="+"\n");
    
        try{    
           // capture user input
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter your selection: ");
            String input = sc.nextLine();
        
            // symbol checking
            if (SymbolValidator.containsSymbol(input)){
            System.out.println("Input contains the specific symbols.");
            }else{
                int selection = Integer.parseInt(input);
                // Use switch case to complete the selection operation
                switch(selection){

                  // Add Artist
                    case 1:
                      // System.out.println("Add artist");
                        addArtist(array);
                        break;

                    // View Artist   
                    case 2:
                        viewArtist(array);
                        break;

                    // Modify Artist
                    case 3:
                        updateArtist(array);
                        break;

                    // Remove Artist
                    case 4:
                        deleteArtist(array);
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
                        displayArtistScreen();
                }
            }
            }catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }while(true);
    }        
    
    // add artist
   public static void addArtist(ArrayList<Artist> array) {
    Scanner sc = new Scanner(System.in);

    String artistName;
    do {
        System.out.print("Please enter Artist Name: ");
        artistName = sc.nextLine().trim(); // Trim whitespace from the input
        if (artistName.isEmpty()) {
            System.out.println("Artist Name cannot be empty. Please try again.");
        }
    } while (artistName.isEmpty());

    int artistAge;

    try {
        System.out.print("Please enter Artist age: ");
        artistAge = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        
        // Validation for age constraints
        if (artistAge <= 0 || artistAge >= 100) {
            throw new IllegalArgumentException("Invalid age");
        }

    } catch (IllegalArgumentException e) {
        if (e.getMessage().equals("Invalid age")) {
            System.out.println("Invalid age input. Age must be between 1 and 99.");
            return; // Exit the method without adding the artist
        }
        artistAge = 0; // Assign a default value to artistAge to prevent compilation error
    } catch (InputMismatchException ex) {
        System.out.println("Invalid input. Please enter a numeric value for age.");
        sc.nextLine(); // Clear the input buffer
        return; // Exit the method without adding the artist
    }

    Artist a = new Artist(artistName, artistAge, Role.Artist);

    array.add(a);

    // Prompt the successfully added message
    System.out.println("Artist added successfully");
}


    // view artist
    public static void viewArtist(ArrayList<Artist> array){
        // check is there any value in arrayList
        if(array.isEmpty()){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("! No artist added, please add an artist to view. !");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+"\n");
            return;
        }
        
        // display the heading
        System.out.println("\n"+"==============================================================");
        System.out.println("======================   Artist Lists  =======================");
        System.out.println("==============================================================" + "\n");
        System.out.println("***************************************************************");
        System.out.printf("%-15s %-20s %-15s %-10s","Artist ID","Artist Name","Artist Age", "Role" );
        System.out.println("\n"+"***************************************************************");
        
        // retrive artist info from arrayList
        for(int i = 0; i < array.size(); i++){
            Artist a = array.get(i);
            System.out.printf("%s %17s %19d %19s", a.getId(),a.getName(),a.getAge(),a.getRole() + "\n");
        }
        
    }

    // delete artist
    public static void deleteArtist(ArrayList<Artist> array) {
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);

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

    // update artist
    public static void updateArtist(ArrayList<Artist> array) {
    Scanner sc = new Scanner(System.in);

    System.out.print("Please enter the artist ID that you want to modify: ");
    String aId = sc.nextLine();

    System.out.print("Please enter new Artist Name: ");
    String aName = sc.nextLine();

    int aAge;

        try {
            System.out.print("Please enter new Artist age: ");
            aAge = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            // Validation for age constraints
            if (aAge <= 0 || aAge >= 100) {
                System.out.println("Invalid age input. Age must be between 1 and 99.");
                return; // Exit the method without updating the artist
            }

            // to check whether the artist ID contains symbols
            if (SymbolValidator.containsSymbol(aId) || SymbolValidator.containsSymbol(Integer.toString(aAge))) {
                System.out.println("Input contains the specific symbols.");
            } else {
                // check whether the id exists
                int index = -1;
                for (int i = 0; i < array.size(); i++) {
                    Artist artist = array.get(i);
                    if (artist.getId() == Integer.parseInt(aId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("No artist found with the given ID.");
                    return;
                }

                Artist a = array.get(index);
                a.setName(aName);
                a.setAge(aAge);

                System.out.println("Artist details updated successfully!");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Invalid input. Please enter a numeric value for age.");
            sc.nextLine(); // Clear the input buffer
            return; // Exit the method without updating the artist
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid artist ID.");
            return; // Exit the method without updating the artist
        }
    }
}
