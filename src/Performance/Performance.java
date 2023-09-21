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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Performance {
    // Performance ArrayList
    private static ArrayList<Performance> pfmArrayList = new ArrayList<Performance>();

    // Non-static fields
    private int id;
    private String name;
    private String type;
    private Artist artist;
    private Database db = new Database('ip',user,pass,database);

    // Static field
    private static int nextId = 1;

    // Constructors
    public Performance() {
        this.id = nextId++;
    }

    public Performance(String name, String type) {
        this();
        this.name = name;
        this.type = type;
    }

    // Getters
    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

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

    
    // method:
    // Static method to set the entire pfmArrayList
    public static void setPfmArrayList(ArrayList<Performance> pfmList) {
        pfmArrayList = pfmList;
        
        Connection conn = db.getConnection();
        ResultSet result;
        try {
            result = db.runSql("select * from database");
        } catch (SQLException ex) {
            //connection fail
        }
        
        while (result.next()){
            //assume table got id,name,pass
            String name = result.getString('Name');
            String name = result.getString('Type');
        }
    }
    
    public void setArtist(Artist artist){
        this.artist = artist;
    }

    // Method to add a new performance and increment nextId
    public static void addPerformance(String name, String type) {
        Performance performance = new Performance(name,type);
        pfmArrayList.add(performance);
    }
}
