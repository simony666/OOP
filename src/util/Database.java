package util;

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
    
    
    public Database(){       
        String dbUrl = "jdbc:mysql://" + config.get("dbHost") + "/" + config.get("dbName");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, config.get("dbUser"), config.get("dbPass"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void closeConn(){
        try{
            if (conn != null){
                conn.close();
            }
        }catch(SQLException e){
        }
    }
    
    public static Connection getConnection() {
        return conn;
    }
    

    public static ResultSet runQuery(String sqlText){
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

    
    public static ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

    public static void setScheduleList(ArrayList<Schedule> scheduleList) {
        Database.scheduleList = scheduleList;
    }
    
    
    

    
    // Schedule
    private static void getSchedule() {
        ArrayList<Schedule> tempList = new ArrayList<>();
        String sqlText = "SELECT * FROM `Schedule`;";
        ResultSet result = runQuery(sqlText);
        try {
            while (result.next()) {
            int id = result.getInt("id");
            Date date = result.getDate("date");
            String startTime = result.getString("startTime");
            String endTime = result.getString("endTime");
            int dHours = result.getInt("durationHours");
            int dMinutes = result.getInt("durationMinutes");
            //String pName = result.getString("performanceName");
            int pId = result.getInt("performanceId");

            // Call the getArtistName method to retrieve the artist's name
            String pName = getArtistName(pId);

            // Process the retrieved data here
            Schedule tempSchedule = new Schedule(id, date, startTime, endTime, dHours, dMinutes,pName,pId);
            tempList.add(tempSchedule);
        }
          Database.scheduleList = tempList;
        } catch (SQLException ex) {
          ex.printStackTrace();
        }

    
    public static int runUpdate(String sqlText){
        int sucess = 0;

        try {
            Statement statement = conn.createStatement();
            sucess = statement.executeUpdate(sqlText);
        } catch (SQLException ex){

            ex.printStackTrace();
        }

        return sucess;
    }

    
    public static void insertSchedule(Date date, String startTime, String endTime, int durationHours, int durationMinutes, Performance pName, int pIdInt) {
        // Check for conflicting schedules
        if (hasConflictingSchedule(pIdInt, date, startTime)) {
            System.out.println("The artist already has a performance scheduled at the same date and time.");
            return; // Exit the method if there's a conflict
        }

        // Proceed to insert the new schedule
        String sql = "INSERT INTO `Schedule` (`date`, `startTime`, `endTime`, `durationHours`, `durationMinutes`, `performanceName`, `performanceId`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // Fetch the performance name based on the performance ID
        String performanceName = pName.getName();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, startTime);
            preparedStatement.setString(3, endTime);
            preparedStatement.setInt(4, durationHours);
            preparedStatement.setInt(5, durationMinutes);
            preparedStatement.setString(6, performanceName);
            preparedStatement.setInt(7, pIdInt);

            preparedStatement.executeUpdate();
            System.out.println("Schedule added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to check for conflicting schedules
    private static boolean hasConflictingSchedule(int artistId, Date date, String startTime) {
        String checkScheduleQuery = "SELECT COUNT(*) FROM Schedule WHERE performanceId = ? AND date = ? AND startTime = ?";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        try (PreparedStatement preparedStatement = conn.prepareStatement(checkScheduleQuery)) {
            preparedStatement.setInt(1, artistId);
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setString(3, startTime);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true; // Conflicting schedule found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // No conflict
    }

    
    public static String getPerformanceName(int pId) {
        // Implement logic to fetch the artist name based on artistId from your artistList
        // Return the performance name or an appropriate default value if not found
        for (Performance p : pfmList) {
            if (p.getId() == pId) {
                return p.getName();
            }
        }
        return ""; // Performance with the given ID not found, return an appropriate default value
    }
    
    private static Schedule findScheduleById(int scheduleId) {
            // Implement logic to search for the performance in your performanceList
            // Return the found Performance object or null if not found
            for (Schedule s : scheduleList) {
                if (s.getId() == scheduleId) {
                    return s; // Found the schedule by ID
                }
            }
            return null;
    }
    
    
    public static void updateSchedule(int sId, Date date, String startTime, String endTime, int durationHours, int durationMinutes, Performance pName, int pIdInt) {
        // Check if there are any schedules in the list
        if (scheduleList.isEmpty()) {
            System.out.println("No schedules added. Please add a schedule to update.");
            return;
        }

        // Find the schedule by ID
        Schedule scheduleToUpdate = null;
        for (Schedule s : scheduleList) {
            if (s.getId() == sId) {
                scheduleToUpdate = s;
                break;
            }
        }

        if (scheduleToUpdate == null) {
            System.out.println("The schedule with ID " + sId + " does not exist.");
            return;
        }

        // Proceed to update the schedule
        String sql = "UPDATE `Schedule` SET `date` = ?, `startTime` = ?, `endTime` = ?, `durationHours` = ?, `durationMinutes` = ?, `performanceName` = ?, `performanceId` = ? WHERE `id` = ?";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // Fetch the performance name based on the performance object
        String performanceName = pName.getName();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, startTime);
            preparedStatement.setString(3, endTime);
            preparedStatement.setInt(4, durationHours);
            preparedStatement.setInt(5, durationMinutes);
            preparedStatement.setString(6, performanceName);
            preparedStatement.setInt(7, pIdInt);
            preparedStatement.setInt(8, sId); // Set the ID for the WHERE clause

            preparedStatement.executeUpdate();
            System.out.println("Schedule updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update the schedule object
        scheduleToUpdate.setDate(date);
        scheduleToUpdate.setStartTime(startTime);
        scheduleToUpdate.setEndTime(endTime);
        scheduleToUpdate.setDurationHours(durationHours);
        scheduleToUpdate.setDurationMinutes(durationMinutes);
        scheduleToUpdate.setPerformanceName(performanceName);
        scheduleToUpdate.setpId(pIdInt);
    }

    
    public static void deleteSchedule(String Id) {
        String sql = "DELETE FROM `Schedule` WHERE `id` = " + Id + ";";
        runUpdate(sql);
    }

}

