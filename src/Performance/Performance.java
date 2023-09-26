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
import util.Database;
import Artist.Artist;
import Artist.ArtistManagement;

public class Performance {
    // Performance ArrayList
    private static ArrayList<Artist> artistList = ArtistManagement.getArtist();
    private static ArrayList<Performance> pfmArrayList = PerformanceManagement.getPfm();

    // Non-static fields
    private int id;
    private String name;
    private String type;
    private Artist artist;
    private int artistId;
    private String artistName;

  
    public static ArrayList<Artist> getArtistList() {
        return artistList;
        //this.id = nextId++;
    }

    public static void setArtistList(ArrayList<Artist> artistList) {
        Performance.artistList = artistList;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    // Static field
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    //private static int nextId = 1;
    // Constructors
    public Performance() {
    }

    public Performance(String name, String type) {
        this();
        this.name = name;
        this.type = type;
        
    }

    public Performance(int id, String name, String type, int artistId, String artistName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.artistId = artistId;
        this.artistName = artistName;
        this.artist = getArtistById(artistId); // Use getArtistById to retrieve the Artist object
    }


    
    // Getters
    public int getId() {
        return id;
    }

//    public static int getNextId() {
//        return nextId;
//    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static ArrayList<Performance> getPfmArrayList() {
        return pfmArrayList;
    }
    
    public Artist getArtist(){
        return artist;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }
    
    public void setArtist(Artist artist){
        this.artist = artist;
    }

    
    // method:
    // Static method to set the entire pfmArrayList
    public void setPfmArrayList(ArrayList<Performance> pfmList) {
        pfmArrayList = pfmList;
        
    }
    
    
    // Method to add a new performance and increment nextId
    public static void addPerformance(String name, String type) {
        Performance performance = new Performance(name,type);
        pfmArrayList.add(performance);
    }
    
    public Artist getArtistById(int artistId) {
        // Implement logic to fetch an Artist object from the Artist list using artistId
        // Return the Artist object or null if not found
        for (Artist a : artistList) {
            if (a.getId() == artistId) {
                return a; // Return the Artist object
            }
        }
        // If artist with the given ID is not found, return null
        return null;
    }
    
    public static String getPerformanceName(int pId) {
        // Implement logic to fetch the artist name based on artistId from your artistList
        // Return the performance name or an appropriate default value if not found
        for (Performance p : getPfmArrayList()) {
            if (p.getId() == pId) {
                return p.getName();
            }
        }
        return ""; // Performance with the given ID not found, return an appropriate default value
    }
}
