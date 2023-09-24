package Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Database;
<<<<<<< Updated upstream
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
=======
>>>>>>> Stashed changes

/**
 *
 * @author Zy
 */
public class Artist extends util.Person {
    
    // non-static field
    private int id;
<<<<<<< Updated upstream
    private Database db;
=======
    private static Database db = new Database("C:/Users/User/OneDrive/ruyan/TAR UC/oop/oop assignment database/assignment database.db");
>>>>>>> Stashed changes
//    private String name;
//    private int age;
//    private Role role;
    
    // static field
    private static int nextId = 1;
    
    // Shared ArrayList for storing artists
    private static ArrayList<Artist> artistArrayList = new ArrayList<Artist>();
    
    // no arg constructor
    public Artist(){
        getFromDatabase();
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
<<<<<<< Updated upstream
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
    
=======
    artistArrayList = artistList; // Assuming artistArrayList is a class variable

//    Connection conn = null;
//    ResultSet result = null;
//
//    try {
//        // Establish a database connection
//        conn = db.getConnection(); // Assuming db is an instance of your database utility class
//
//        // Create a SQL statement
//        stmt = conn.createStatement();
//
//        // Execute a SQL query
//        String sqlQuery = "SELECT * FROM your_table_name"; // Replace 'your_table_name' with the actual table name
//        result = stmt.executeQuery(sqlQuery);
//
//        
//    } catch (SQLException ex) {
//        ex.printStackTrace(); // Handle the exception appropriately, e.g., logging or error handling
//    } finally {
//        // Close resources in reverse order of their creation
//        try {
//            if (result != null) {
//                result.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (conn != null) {
//                conn.close();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace(); // Handle any exceptions that occur while closing resources
//        }
//    }
}
    private void getFromDatabase(){
        // connect to database
        // get data from databse (select statement)
        // save data into arrayList
//        while (result.next()) {
//            // Retrieve data from the result set
//            int id = result.getInt("Id"); // Assuming Id is an integer column
//            String name = result.getString("Name"); // Assuming Name is a string column
//
//            // Create an Artist object or process the data as needed
//            Artist artist = new Artist(id, name); // Replace with your Artist class constructor
//
//            // Add the artist to your ArrayList
//            artistList.add(artist);
//        }
    }
>>>>>>> Stashed changes
}
