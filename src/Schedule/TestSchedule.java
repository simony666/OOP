/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;

import java.util.ArrayList;

/**
 *
 * @author Zy
 */
public class TestSchedule {
    public static void main(String[] args) {
         // Create an instance of ScheduleManagement
        ScheduleManagement scheduleManager = new ScheduleManagement();

        // Create an ArrayList to pass to the addSchedule method
        ArrayList<Schedule> scheduleList = new ArrayList<>();

        // Call the addSchedule method
        //scheduleManager.addSchedule(scheduleList);
        
 System.out.println("\n" + "========================================================================");
        System.out.println("========================   Performance Schedule  =======================");
        System.out.println("========================================================================" + "\n");
        System.out.println("*************************************************************************");
        System.out.printf("%-15s %-15s %-15s %-15s %-25s", "Schedule ID", "Schedule Date", "Starting Time", 
                "Ending time","duration");
        System.out.println("\n" + "*************************************************************************");

    }
}
