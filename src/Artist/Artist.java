package Artist;

import java.util.ArrayList;
import util.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zy
 */
public class Artist extends util.Person {
    
    // non-static field
    private int id;
    private Database db;
//    private String name;
//    private int age;
//    private Role role;
    
    // static field
    private static int nextId = 1;
    
    // Shared ArrayList for storing artists
    private static ArrayList<Artist> artistArrayList = new ArrayList<Artist>();
    
    // no arg constructor
    public Artist(){
    };
    
    // Constructor with 4 parameters
    public Artist(String name, int age){
        super(name,age);
        this.id = nextId;
//        this.name = name;
//        this.age = age;
//        this.role = role;
        // Increment nextId for the next artist
        nextId++;
    };  
    
    // getter / Accessors for id, name, age, and role
    public int getId(){
        return id;
    }
    
    
//    public String getName(){
//        return name;
//    }
//    
//    public int getAge(){
//        
//        return age;
//    }
//    
//    public Role getRole(){
//        return role;
//    }
    
    public static int getNextId(){
        return nextId;
    }
    
    public static ArrayList<Artist> getArtistArrayList() {
        return artistArrayList;
    }
    
    
    // setter / Mutuator for id, name, age, and role
    public void setId(int id){
        this.id = id;
    }
    
//    public void setName(String name){
//        this.name = name;
//    }
//    
//    public void setAge(int age){
//         this.age = age;
//    }
   
    public static void setArtistArrayList(ArrayList<Artist> artistList) {
        artistArrayList = artistList;
        
        Connection conn = db.getConnection();
        ResultSet result;
        try {
            result = db.runSql("select * from database");
        } catch (SQLException ex) {
            //connection fail
        }
        
        while (result.next()){
            //assume table got id,name,pass
            int id = result.getInt('ID');
            String name = result.getString('Name');
        }
        
    }
    
}
