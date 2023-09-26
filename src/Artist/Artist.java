package Artist;

import java.util.ArrayList;
import util.Person;

import util.Database;
/**
 *
 * @author Zy
 */
public class Artist extends Person {
    
    // non-static field
    private int id;
    private String bandName;
    
    // static field
    // private static int nextId = 1;
    
    
    // Shared ArrayList for storing artists
    private static ArrayList<Artist> artistArrayList = new ArrayList<Artist>();
    
    // no arg constructor
    public Artist(){
        //getFromDatabase();
        
    };
    
    public Artist(String name, String bandName){
        super(name);
        this.bandName = bandName;
    }
    
    // Constructor with 2 parameters
    public Artist(int id, String name, String bandName){
        super(name);
        this.id = id;
        // this.id = nextId;
        this.bandName = bandName;
//        this.age = age;
//        this.role = role;
        // Increment nextId for the next artist
        //nextId++;
    };  

    public String getBandName() {
        return bandName;
    }

    
    // getter 
    public int getId(){
        return id;
    }
    
  
    
//    public static int getNextId(){
//        return nextId;
//    }
    
    public static ArrayList<Artist> getArtistArrayList() {
        return artistArrayList;
    }
    
    
    // setter / Mutuator for id, name, age, and role
//    public void setId(int id){
//        this.id = id;
//    }

    public void setBandName(String bandName) {
            this.bandName = bandName;
        }

    public static void setArtistArrayList(ArrayList<Artist> artistList) {
        artistArrayList = artistList; // Assuming artistArrayList is a class variable
    }
    

}