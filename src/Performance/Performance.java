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

public class Performance {
    // Performance ArrayList
    private static ArrayList<Performance> pfmArrayList = new ArrayList<Performance>();

    // Non-static fields
    private int id;
    private String name;
    private String type;
    private Artist artist;

    // Static field
    //private static int nextId = 1;

    // Constructors
    public Performance() {
        //this.id = nextId++;
    }

    public Performance(String name, String type) {
        this();
        this.name = name;
        this.type = type;
    }

    public Performance(int id, String name, String type) {
        this();
        this.id = id;
        this.name = name;
        this.type = type;
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
}
