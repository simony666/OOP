package util;

import Artist.Artist;
import Performance.Performance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author yongc
 */
public class Database {
    private static Connection conn;
    private Config config = new Config();
    
    public static ArrayList<Artist> artistList = new ArrayList<>();
    public static ArrayList<Performance> pfmList = new ArrayList<>();
    
    public Database(){ 
        String database = config.get("database");
        if (database.equals("mysql")){
            //mysql
            
            String dbUrl = "jdbc:mysql://" + config.get("dbHost") + "/" + config.get("dbName");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(dbUrl, config.get("dbUser"), config.get("dbPass"));
            } catch (ClassNotFoundException | SQLException e) {
            }
        }else if (database.equals("sqlite")){
            //sqlite
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + config.get("sqliteFilePath"));
            } catch (ClassNotFoundException | SQLException e) {
            }
        }
        
        getArtist();
        getPfm();
    }
    
    private void closeConn(){
        try{
            if (conn != null){
                conn.close();
            }
        }catch(SQLException e){
        }
    }
    
    private static Connection getConnection() {
        return conn;
    }
    
    private static ResultSet runSql(String sqlText){
        ResultSet resultSet = null;

        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlText);
        } catch (SQLException ex){
        }

        return resultSet;
    }

    public static ArrayList<Artist> getArtistList() {
        return artistList;
    }

    public static void setArtistList(ArrayList<Artist> artistList) {
        Database.artistList = artistList;
    }

    public static ArrayList<Performance> getPfmArrayList() {
        return pfmList;
    }

    public static void setPfmArrayList(ArrayList<Performance> pfmArrayList) {
        Database.pfmList = pfmArrayList;
    }
    
    // Artist
    public static void getArtist(){
        ArrayList<Artist> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Artist`;";
        ResultSet result = runSql(sqlText);
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                
                // Process the retrieved data here
                Artist tempArtist = new Artist(name,age);
                tempList.add(tempArtist);
            }
            
            Database.artistList = tempList;
        } catch (SQLException ex) {
            
        }
    }
    
public static void insertArtist(String name, String bandName) {
    String sql = "INSERT INTO `Artist` (`name`, `bandName`) VALUES (\""+name+"\", \""+bandName+"\");";
    int result = runUpdate(sql);
    System.out.println(String.valueOf(result));
}

public static void updateArtist(int Id, String newName, String newBandName) {
    String sql = "UPDATE `Artist` SET `name` = \"" + newName + "\", `bandName` = \"" + newBandName + 
            "\" WHERE `Id` = " + Id + ";";
    
    runUpdate(sql);
}

public static void deleteArtist(String Id) {
    String sql = "DELETE FROM `Artist` WHERE `Id` = " + Id + ";";
    
    runUpdate(sql);
}

// Performance
    private static void getPfm(){
        ArrayList<Performance> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Performance`;";
        ResultSet result = runSql(sqlText);
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String type = result.getString("type");
                
                // Process the retrieved data here
                Performance tempPfm = new Performance(name,type);
                tempList.add(tempPfm);
            }
            
            Database.pfmList = tempList;
        } catch (SQLException ex) {
            
        }
    }
}
