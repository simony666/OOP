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

public class Schedule {
    private int id;
    private Date date;
    private String startTime;
    private String endTime;
    private int durationHours;
    private int durationMinutes;
    private Performance performance;
    private String performanceName; // Changed variable name from "pName" to "performanceName"
    private int pId;

    private static ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();

    public Schedule() {
    }

    public Schedule(Date date, String startTime, String endTime, int durationHours, int durationMinutes, Performance pName, int pId) {
        this.date =  new java.sql.Date(date.getTime());
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        this.performance = pName; // Assign the Performance object directly
        this.pId = pId;
    }


    public Schedule(int id, Date date, String startTime, String endTime, int durationHours, int durationMinutes, String performanceName, int pId) { // Changed parameter name to "performanceName"
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        this.performanceName = performanceName; // Updated assignment
        this.pId = pId;
        this.performance = getPfmById(pId); // Use getPfmById method to retrieve the Performance object
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

    public String getPerformanceName() {
        return performanceName;
    }

    public int getpId() {
        return pId;
    }

    public static ArrayList<Performance> getPfmArrayList() {
        return pfmArrayList;
    }
    
    
//    public static int getNextId() {
//        return nextId;
//    }

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
    
//    public static void setNextId(int id) {
//        nextId = id;
//    }
    
    public void setPerformance(Performance performance){
        this.performance = performance;
    }
    
    // Static method to set the entire scheduleArrayList
    public static void setScheduleArrayList(ArrayList<Schedule> scheduleList) {
        scheduleArrayList = scheduleList;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public static void setPfmArrayList(ArrayList<Performance> pfmArrayList) {
        Schedule.pfmArrayList = pfmArrayList;
    }
    
    
    
    public Performance getPfmById(int pId) {
        // Implement logic to fetch an Artist object from the Artist list using artistId
        // Return the Artist object or null if not found
        for (Performance p : pfmArrayList) {
            if (p.getId() == pId) {
                return p; // Return the Artist object
            }
        }
        // If artist with the given ID is not found, return null
        return null;
    }
}