package util;

import Artist.Artist;
import Performance.Performance;

import java.sql.PreparedStatement;
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
    
    private static ArrayList<Artist> artistList = new ArrayList<>();
    private static ArrayList<Performance> pfmList = new ArrayList<>();
    
    public Database(){       
        String dbUrl = "jdbc:mysql://" + config.get("dbHost") + "/" + config.get("dbName");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, config.get("dbUser"), config.get("dbPass"));
        } catch (ClassNotFoundException | SQLException e) {
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
    
    private static void getArtist(){
        ArrayList<Artist> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Artist`;";
        ResultSet result = runSql(sqlText);
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String bandName = result.getString("bandName");
                
                // Process the retrieved data here
                Artist tempArtist = new Artist(name,bandName);
                tempList.add(tempArtist);
            }
            
            Database.artistList = tempList;
        } catch (SQLException ex) {
            
        }
    }
    
public static void insertArtist(String name, String bandName) {
    String sql = "INSERT INTO Artist (name, bandName) VALUES (?, ?)";
    
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, bandName);
        preparedStatement.executeUpdate();
        System.out.println("Artist added successfully");
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error: Failed to insert artist into the database.");
    }
}


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
