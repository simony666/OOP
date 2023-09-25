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
import javax.lang.model.util.Types;

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
        String dbUrl = "jdbc:mysql://" + config.get("dbHost") + "/" + config.get("dbName");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, config.get("dbUser"), config.get("dbPass"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
    
    private static ResultSet runQuery(String sqlText){
        ResultSet resultSet = null;

        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlText);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return resultSet;
    }
    
    private static int runUpdate(String sqlText){
        int sucess = 0;

        try {
            Statement statement = conn.createStatement();
            sucess = statement.executeUpdate(sqlText);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return sucess;
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
   public static void getArtist() {
    ArrayList<Artist> tempList = new ArrayList<>();
    String sqlText = "SELECT * FROM `Artist`;";
    ResultSet result = runQuery(sqlText);
    try {
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String bandName = result.getString("bandName");

            // Process the retrieved data here
            Artist tempArtist = new Artist(id, name, bandName);
            tempList.add(tempArtist);
        }

        Database.artistList = tempList;
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    
    
    public static boolean insertArtist(String name, String bandName) {
        if (artistNameExists(name)) {
            System.out.println("Artist with the same name already exists in the database.");
            return false; // Don't insert if the name already exists
        }

        String sql = "INSERT INTO `Artist` (`name`, `bandName`) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            // Conditionally set bandName to null if it's empty
            if (bandName.isEmpty()) {
                preparedStatement.setNull(2, java.sql.Types.VARCHAR); // Set bandName to NULL
            } else {
                preparedStatement.setString(2, bandName); // Set bandName to the provided value
            }

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                //System.out.println("Artist added successfully");
                return true;
            } else {
                //System.out.println("Failed to add artist");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean artistNameExists(String name) {
        String query = "SELECT COUNT(*) FROM `Artist` WHERE `name` = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void updateArtist(int Id, String newName, String newBandName) {
        if (artistNameExists(newName)) {
            System.out.println("Artist with the same name already exists in the database.");
            return; // Exit the method if the name already exists
        }

        if (!Validator.containsOnlyAlphabetic(newName)) {
            System.out.println("Artist Name must contain only letters. Please try again.");
            return; // Exit the method if the name contains non-alphabetic characters
        }

        String sql = "UPDATE `Artist` SET `name` = \"" + newName + "\", `bandName` = \"" + newBandName + 
                "\" WHERE `Id` = " + Id + ";";

        runUpdate(sql);
    }

    public static void deleteArtist(String Id) {
        String sql = "DELETE FROM `Artist` WHERE `Id` = " + Id + ";";
        runUpdate(sql);
    }
    
    
    // Performance
    private static void getPfm() {
        ArrayList<Performance> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Performance`;";
        ResultSet result = runQuery(sqlText);
        try {
            while (result.next()) {
            int id = result.getInt("Id");
            String name = result.getString("performanceName");
            String type = result.getString("performanceType");
            int artistId = result.getInt("artistId"); // Get the artist's ID from the result

            // Call the getArtistName method to retrieve the artist's name
            String artistName = getArtistName(artistId);

            // Process the retrieved data here
            Performance tempPfm = new Performance(id, name, type, artistId, artistName);
            tempList.add(tempPfm);
        }
          Database.pfmList = tempList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    
public static void insertPerformance(String performanceName, String performanceType, int artistId) {
    String sql = "INSERT INTO `Performance` (`performanceName`, `performanceType`, `artistName`, `artistId`) VALUES (?, ?, ?, ?)";

    // Fetch the artist name based on the artist ID
    String artistName = getArtistName(artistId);
    
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setString(1, performanceName);
        preparedStatement.setString(2, performanceType);
        preparedStatement.setString(3, artistName);
        preparedStatement.setInt(4, artistId);

        preparedStatement.executeUpdate();
        // System.out.println("Performance added successfully. Rows affected: " + result);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static String getArtistName(int artistId) {
        // Implement logic to fetch the artist name based on artistId from your artistList
        // Return the artist name or an appropriate default value if not found
        for (Artist a : artistList) {
            if (a.getId() == artistId) {
                return a.getName();
            }
        }
        return ""; // Artist with the given ID not found, return an appropriate default value
    }

    private static Performance findPerformanceById(int performanceId) {
            // Implement logic to search for the performance in your performanceList
            // Return the found Performance object or null if not found
            for (Performance p : pfmList) {
                if (p.getId() == performanceId) {
                    return p; // Found the performance by ID
                }
            }
            return null;
    }
    
    public static void updatePerformance(int performanceId, String newName, String newType, String artistName, int artistId) {
        Performance performanceToUpdate = findPerformanceById(performanceId);
        // Fetch the artist name based on the artist ID
        String aName = getArtistName(artistId);
        
        if (performanceToUpdate != null) {
             String sql = "UPDATE Performance SET performanceName = ?, performanceType = ?, artistName = ? WHERE id = ?";
            
                try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newType);
                preparedStatement.setString(3, aName);
                preparedStatement.setInt(4, performanceId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    // System.out.println("Performance updated successfully. Rows affected: " + rowsUpdated);
                } else {
                    System.out.println("No performance found with ID " + performanceId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or show an error message to the user.
            }
             
        } else {
            System.out.println("No performance found with ID " + performanceId);
        }
       
    }

    public static void deletePerformance(String Id) {
        String sql = "DELETE FROM `Performance` WHERE `Id` = " + Id + ";";
        runUpdate(sql);
    }

}
