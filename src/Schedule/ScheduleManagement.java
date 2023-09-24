/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ScheduleManagement {
    static ArrayList<Schedule> scheduleList = Schedule.getScheduleArrayList();

    // Display Schedule Screen
    public static void displayScheduleScreen() {
        do {
            System.out.println("\n=======================================");
            System.out.println("========  Manage Schedule  ============");
            System.out.println("=======================================");
            System.out.println("======= 1) Add Schedule         =======");
            System.out.println("======= 2) View Schedule        =======");
            System.out.println("======= 3) Modify Schedule      =======");
            System.out.println("======= 4) Remove Schedule      =======");
            System.out.println("======= 5) Back to Admin Page   =======");
            System.out.println("======= 6) Exit                 =======");
            System.out.println("=======================================\n");

            try {
                // Capture user input
                Scanner sc = new Scanner(System.in);
                System.out.print("Please enter your selection: ");
                String input = sc.nextLine();

                // Symbol checking
                if (util.SymbolValidator.containsSymbol(input)) {
                    System.out.println("Input contains specific symbols.");
                } else {
                    int selection = Integer.parseInt(input);
                    // Use switch case to perform the selected operation
                    switch (selection) {
                        // Add Schedule
                        case 1:
                            addSchedule(scheduleList);
                            break;

                        // View Schedule
                        case 2:
                            viewSchedule(scheduleList);
                            break;

                        // Modify Schedule
                        case 3:
                            //updateSchedule(scheduleList);
                            break;

                        // Remove Schedule
                        case 4:
                            //deleteSchedule(scheduleList);
                            break;

                        // Back to Admin page
                        case 5:
                            main.mainScreen.displayAdmin();
                            break;

                        // Exit
                        case 6:
                            System.out.println("Thanks for using.");
                            System.exit(0);
                            break;

                        // Other input within the range of 1-6
                        default:
                            System.out.println("Invalid range. Please enter a number between 1 to 6.");
                            ScheduleManagement.displayScheduleScreen();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        } while (true);
    }

    // Add schedule
    public static void addSchedule(ArrayList<Schedule> scheduleArrayList){
    Scanner sc = new Scanner(System.in);
    String dateStr;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    boolean isValidDate = false;

    do {
        // Prompt the user to enter a date
        System.out.print("Enter a date for the performance (dd/mm/yyyy): ");

        // Read the input using the next() method
        dateStr = sc.nextLine();

        if (dateStr.trim().isEmpty() || dateStr.length() != 10) {
            System.out.println("Invalid date format. Please try again.");
            continue; // Skip the rest of the loop and prompt again
        }

        try {
            Date date = sdfDate.parse(dateStr);
            // change date format
            sdfDate.format(date);
            isValidDate = true; // Set the flag to true if parsing succeeds
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        }
    } while (!isValidDate);


    
    // Set time
    String startTime;
    String endTime;
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aa");
    Date d1 = null;
    Date d2 = null;
    boolean isValidTime = false;

    do {
        // Prompt the user to enter performance times
        System.out.print("Enter performance starting time (hh:mm AM /PM): ");
        startTime = sc.nextLine();

        System.out.print("Enter performance ending time (hh:mm aa AM /PM): ");
        endTime = sc.nextLine();

        if (startTime.trim().isEmpty() || endTime.trim().isEmpty()) {
            System.out.println("Both start and end times are required. Please try again.");
            continue; // Skip the rest of the loop and prompt again
        }

        try {
            d1 = sdfTime.parse(startTime);
            d2 = sdfTime.parse(endTime);
            isValidTime = true; // Set the flag to true if parsing succeeds
        } catch (ParseException e) {
            System.out.println("Invalid time format. Please try again.");
        }
    } while (!isValidTime);


    //System.out.println("Performance starting time: " + sdfTime.format(d1));
    //System.out.println("Performance ending time: " + sdfTime.format(d2));

    // Calculate the time difference in hours and minutes
    long diffMs = d2.getTime() - d1.getTime();
    long diffSec = diffMs / 1000;
    int hours = (int) (diffSec / 3600); // Total duration in hours
    int remainingSeconds = (int) (diffSec % 3600);
    int minutes = remainingSeconds / 60; // Remaining seconds converted to minutes

    if (hours == 0 && minutes == 0) {
        System.out.println("Invalid performance duration. The duration cannot be 0 minutes. Please try again.");
        // Allow the user to reinput the time
        do {
            System.out.print("Enter performance starting time (hh:mm AM /PM): ");
            startTime = sc.nextLine();

            System.out.print("Enter performance ending time (hh:mm aa AM /PM): ");
            endTime = sc.nextLine();

            try {
                d1 = sdfTime.parse(startTime);
                d2 = sdfTime.parse(endTime);
                isValidTime = true; // Set the flag to true if parsing succeeds
            } catch (ParseException e) {
                System.out.println("Invalid time format. Please try again.");
            }
        } while (!isValidTime);
        // Update the total duration
        diffMs = d2.getTime() - d1.getTime();
        diffSec = diffMs / 1000;
        hours = (int) (diffSec / 3600); // Total duration in hours
        remainingSeconds = (int) (diffSec % 3600);
        minutes = remainingSeconds / 60; // Remaining seconds converted to minutes

    }

        // System.out.println("The performance duration is " + hours + " hours and " +minutes + " minutes.");
   
        // Create a Schedule object
    Schedule schedule = new Schedule(dateStr, startTime, endTime, hours, minutes);

    // Add the Schedule object to the ArrayList
    scheduleArrayList.add(schedule);

    // Print a message to confirm that the schedule is added
    System.out.println("Schedule added successfully!");
    
    }

    // view schedule
    public static void viewSchedule(ArrayList<Schedule> scheduleArrayList) {
        ArrayList<Schedule> scheduleList = Schedule.getScheduleArrayList();

        // check if there are any artists in the list
        if (scheduleList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("! No Schedule added, please add schedule to view. !");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            ScheduleManagement.displayScheduleScreen();
            return;
        }

        // display the heading
        System.out.println("\n" + "========================================================================");
        System.out.println("========================   Performance Schedule  =======================");
        System.out.println("========================================================================" + "\n");
        System.out.println("****************************************************************************");
        System.out.printf("%-15s %-15s %-15s %-15s %-25s", "Schedule ID", "Schedule Date", "Starting Time", 
                "Ending time","duration");
        System.out.println("\n" + "****************************************************************************");
        // retrieve artist info from arrayList
        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule s = scheduleList.get(i);
            String duration = String.format("%d hrs %d min", s.getDurationHours(), s.getDurationMinutes());
            System.out.printf("%-10s %15s %15s %15s %15s %25s", s.getId(), s.getDate(), s.getStartTime(),
                    s.getEndTime(), duration, "\n");

        }
    }
}

