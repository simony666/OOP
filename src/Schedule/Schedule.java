/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;

import Performance.Performance;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Database;
import java.sql.PreparedStatement;


/**
 *
 * @author Zy
 */
public class Schedule {
    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private int durationHours;
    private int durationMinutes;
    
    // static field
    private static int nextId = 1;
   
    // Schedule ArrayList
    private static ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
    private static Database db = new Database("C:/Users/User/OneDrive/ruyan/TAR UC/oop/oop assignment database/assignment database.db");
    
    // constructor
    public Schedule() {
    }

    public Schedule(String date, String startTime, String endTime, int durationHours, int durationMinutes) {
        this.id = nextId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        nextId++;
    }
    
     // getter
    public int getId(){
        return id;
    }
    
    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public static ArrayList<Schedule> getScheduleArrayList() {
        return scheduleArrayList;
    }
    
    // setter
    public void setId(int id){
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    // Static method to set the entire scheduleArrayList
    public static void setScheduleArrayList(ArrayList<Schedule> scheduleList) {
        scheduleArrayList = scheduleList;
    }
    // method
//    public void setArtist(Artist.Artist artist){
//        this.artist = artist;
//    }
//
//    // Method to add a new performance and increment nextId
//    public static void addPerformance(String name, String type) {
//        Performance performance = new Performance(name,type);
//        pfmArrayList.add(performance);
//    }
   
    // Database
     public void saveToDatabase(Database database) {
        try {
            Connection connection = database.getConnection();
            String sql = "INSERT INTO schedule (date, startTime, endTime, durationHours, durationMinutes) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date);
            statement.setString(2, startTime);
            statement.setString(3, endTime);
            statement.setInt(4, durationHours);
            statement.setInt(5, durationMinutes);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Schedule> loadFromDatabase(Database db) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        try {
            Connection connection = db.getConnection();
            String sql = "SELECT * FROM schedule";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Schedule schedule = new Schedule(
                    resultSet.getString("date"),
                    resultSet.getString("startTime"),
                    resultSet.getString("endTime"),
                    resultSet.getInt("durationHours"),
                    resultSet.getInt("durationMinutes")
                );

                schedule.setId(resultSet.getInt("id"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }
}