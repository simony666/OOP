/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;


import util.Database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import Performance.Performance;


/**
 *
 * @author Zy
 */


public class Schedule {
    private int id;
    private Date date; // Change from String to Date
    private String startTime;
    private String endTime;
    private int durationHours;
    private int durationMinutes;
    private Performance performance;

    // static field
    private static int nextId = 1;

    // Schedule ArrayList
    private static ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
    //private static Database db = new Database("C:/Users/User/OneDrive/ruyan/TAR UC/oop/oop assignment database/assignment database.db");

    // constructor
    public Schedule() {
    }

    public Schedule(Date date, String startTime, String endTime, int durationHours, int durationMinutes, Performance performance) {
        this.id = nextId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        this.performance = performance;
        nextId++; // Increment nextId for the next schedule
    }

    // getter
    public int getId() {
        return id;
    }

    public Date getDate() {
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
    
    public Performance getPerformance(){
        return performance;
    }

    public static ArrayList<Schedule> getScheduleArrayList() {
        return scheduleArrayList;
    }
    
    public static int getNextId() {
        return nextId;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
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
    
    public static void setNextId(int id) {
        nextId = id;
    }
    
    public void setPerformance(Performance performance){
        this.performance = performance;
    }
    
    // Static method to set the entire scheduleArrayList
    public static void setScheduleArrayList(ArrayList<Schedule> scheduleList) {
        scheduleArrayList = scheduleList;
    }
}