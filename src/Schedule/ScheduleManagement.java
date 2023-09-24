/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;


import Artist.Artist;
import Performance.Performance;
import Performance.PerformanceManagement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;


import util.ClearScreen;
import util.Validator;

public class ScheduleManagement {
    static ArrayList<Schedule> scheduleList = Schedule.getScheduleArrayList();
    static ArrayList<Performance> pfmArrayList = Performance.getPfmArrayList();
    static ArrayList<Artist> artistArrayList = Artist.getArtistArrayList(); 
    private static int nextId = 1; // Initialize nextId

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
                if (Validator.containsSymbol(input)) {
                    System.out.println("Input contains specific symbols.");
                } else {
                    int selection = Integer.parseInt(input);
                    // Use switch case to perform the selected operation
                    switch (selection) {
                        // Add Schedule
                        case 1:
                            ClearScreen.cls();
                            try {
                                addSchedule(artistArrayList, scheduleList, pfmArrayList);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format. Please try again.");
                                // You can add additional error handling if needed
                            }
                            break;

                        // View Schedule
                        case 2:
                            ClearScreen.cls();
                            viewSchedule(scheduleList, pfmArrayList);
                            break;

                        // Modify Schedule
                        case 3:
                            ClearScreen.cls();
                            updateSchedule(scheduleList,pfmArrayList);
                            break;

                        // Remove Schedule
                        case 4:
                            ClearScreen.cls();
                            deleteSchedule(scheduleList);
                            break;

                        // Back to Admin page
                        case 5:
                            ClearScreen.cls();
                            main.mainScreen.displayAdmin();
                            break;

                        // Exit
                        case 6:
                            System.out.println("Thanks for using.");
                            System.exit(0);
                            break;

                        // Other input within the range of 1-6
                        default:
                            System.out.println("Invalid range. Please enter a number between 1 and 6.");
                            ScheduleManagement.displayScheduleScreen();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        } while (true);
    }

    // Add schedule
    public static void addSchedule(ArrayList<Artist> artistArrayList, ArrayList<Schedule> scheduleArrayList, ArrayList<Performance> pfmArrayList) throws ParseException {
            Scanner sc = new Scanner(System.in);
            String dateStr;
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            boolean isValidDate = false;
            Date currentDate = new Date(); // Get the current date

        // Check if there are any performances in the list
        if (pfmArrayList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!! No performance added, please add an artist to view. !!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            PerformanceManagement.displayPerformanceScreen();
            return;
        } else {
            // Display available artists
            PerformanceManagement.viewPerformance(artistArrayList, pfmArrayList);
        }

        // Capture user input for the artist ID
        System.out.println("");
        System.out.print("Please enter the performance ID to add the schedule: ");
        String pId = sc.nextLine();


        // Check if the artist ID contains symbols
        if (Validator.containsSymbol(pId)) {
            System.out.println("Input contains specific symbols.");
            return;
        }

        try {
            int performanceId = Integer.parseInt(pId);

            // Check whether the artist ID exists
            Performance pName = null;
            for (Performance p : pfmArrayList) {
                if (p.getId() == performanceId) {
                    pName = p;
                    break;
                }
            }

            if (pName == null) {
                System.out.println("The performance with ID " + performanceId + " does not exist.");
                return;
            }
            
            System.out.println("\n" + "===============================================================================");
            System.out.println("========================   Adding Performance Schedule  =======================");
            System.out.println("===============================================================================" + "\n");
            
            do {
                // Prompt the user to enter a date
                System.out.print("Enter a date for the performance (dd/mm/yyyy): ");
                dateStr = sc.nextLine();

                if (dateStr.trim().isEmpty() || dateStr.length() != 10) {
                    System.out.println("Invalid date format. Please try again.");
                    continue; // Skip the rest of the loop and prompt again
                }

                try {
                    Date enteredDate = sdfDate.parse(dateStr);

                    // Check if the entered date is after the current date
                    if (enteredDate.after(currentDate)) {
                        isValidDate = true; // Set the flag to true if the date is valid
                    } else {
                        System.out.println("Invalid date. Date must be after the current date. Please try again.");
                    }

                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please try again.");
                }
            } while (!isValidDate);

            String startTime = "";
            String endTime = "";
//            SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aa");
            Date d1 = null;
            Date d2 = null;
            boolean isValidTime = false;

            int hours = 0;
            int minutes = 0;

            while (true) {
                // Prompt the user to enter performance starting time
                System.out.print("Enter performance starting time (HH:mm): ");
                startTime = sc.nextLine();

                // Check if the input is empty
                if (Validator.isInputEmpty(startTime)) {
                    System.out.println("Performance starting time is required. Please try again.");
                    continue; // Prompt again if it's empty
                }

                try {
                    SimpleDateFormat sdfTime24 = new SimpleDateFormat("HH:mm");
                    d1 = sdfTime24.parse(startTime);
                    isValidTime = true; // Set the flag to true if parsing succeeds
                } catch (ParseException e) {
                    System.out.println("Invalid time format. Please try again.");
                    continue; // Prompt again if parsing fails
                }

                // Prompt the user to enter performance ending time
                System.out.print("Enter performance ending time (HH:mm): ");
                endTime = sc.nextLine();

                // Check if the input is empty
                if (Validator.isInputEmpty(endTime)) {
                    System.out.println("Performance ending time is required. Please try again.");
                    continue; // Prompt again if it's empty
                }

                try {
                    SimpleDateFormat sdfTime24 = new SimpleDateFormat("HH:mm");
                    d2 = sdfTime24.parse(endTime);
                    isValidTime = true; // Set the flag to true if parsing succeeds
                } catch (ParseException e) {
                    System.out.println("Invalid time format. Please try again.");
                    continue; // Prompt again if parsing fails
                }

                // Calculate the time difference in hours and minutes
                long diffMs = d2.getTime() - d1.getTime();

                // Handle the case where the end time is on the next day
                if (diffMs < 0) {
                    diffMs += 24 * 60 * 60 * 1000; // Add 24 hours in milliseconds
                }

                long diffSec = diffMs / 1000;
                hours = (int) (diffSec / 3600); // Total duration in hours
                int remainingSeconds = (int) (diffSec % 3600);
                minutes = remainingSeconds / 60; // Remaining seconds converted to minutes

                // Adjust for the case where the end time is before the start time
                if (hours < 0 || (hours == 0 && minutes < 0)) {
                    hours += 24;
                }

                // Check if the duration is at least 2 hours
                if (hours >= 2 && minutes >= 0) {
                    Date date; // Declare date outside the try-catch block

                    try {
                         date = sdfDate.parse(dateStr); // Initialize date inside the try block

                        // Create a Schedule object
                        Schedule schedule = new Schedule(date, startTime, endTime, hours, minutes, pName);

                        // Increment the nextId for the next schedule
                        nextId++;
                        // Add the Schedule object to the ArrayList
                        scheduleArrayList.add(schedule);

                        // Print a message to confirm that the schedule is added
                        System.out.println("Schedule added successfully!");

                        // Ask the user if they want to add another schedule
//                        System.out.print("Do you want to add another schedule? (y/n): ");
//                        String addAnother = sc.nextLine().toLowerCase();
//
//                        if (!addAnother.equals("y")) {
//                            ClearScreen.cls();
//                            break; // Exit the loop if the user doesn't want to add another schedule
//                        }
                        break;
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please try again.");
                        // You can add additional error handling if needed
                    }
                } else {
                    System.out.println("Invalid performance duration. The duration must be at least 2 hours. Please try again.");
                }
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the artist ID.");
        }
        
    }



    // view schedule
    public static void viewSchedule(ArrayList<Schedule> scheduleArrayList, ArrayList<Performance> pfmArrayList) {
        ArrayList<Schedule> scheduleList = Schedule.getScheduleArrayList();

        // check if there are any schedules in the list
        if (scheduleList.isEmpty()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("! No Schedule added, please add a schedule to view. !");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
            ScheduleManagement.displayScheduleScreen();
            return;
        }

        // Sort the scheduleList based on the date in ascending order
        Collections.sort(scheduleList, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule schedule1, Schedule schedule2) {
                // Assuming that getDate() returns a Date object
                return schedule1.getDate().compareTo(schedule2.getDate());
            }
        });

        // display the heading
        System.out.println("\n" + "========================================================================================================");
        System.out.println("=========================================   Performance Schedule  ======================================");
        System.out.println("========================================================================================================" + "\n");
        System.out.println("********************************************************************************************************");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s", "Schedule ID", "Schedule Date", "Starting Time",
                "Ending time", "Duration", "Performance Name");
        System.out.println("\n" + "********************************************************************************************************");

        int scheduleId = 1; // Initialize the schedule ID

        // retrieve schedule info from ArrayList
        for (Schedule s : scheduleList) {
            String duration = String.format("%d hrs %d min", s.getDurationHours(), s.getDurationMinutes());
            Performance p = s.getPerformance();

            // Format the date to "dd/MM/yyyy" before printing
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(s.getDate());

            System.out.printf("%-10s %15s %15s %15s %15s %20s %15s", s.getId(), formattedDate, s.getStartTime(),
                    s.getEndTime(), duration, p.getName(),"\n");
        }
    }
        
    // Update schedule
    public static void updateSchedule(ArrayList<Schedule> scheduleArrayList, ArrayList<Performance> pfmArrayList) {
        Scanner sc = new Scanner(System.in);
        String dateStr;
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValidDate = false;
        Date currentDate = new Date(); // Get the current date

        // Check if there are any schedules in the list
        if (scheduleArrayList.isEmpty()) {
            System.out.println("No schedules added. Please add a schedule to update.");
            return;
        }

        // Display available schedules
        ScheduleManagement.viewSchedule(scheduleArrayList, pfmArrayList);

        try {
            System.out.println("");
            System.out.print("Please enter the schedule ID that you want to update: ");
            int sId = Integer.parseInt(sc.nextLine());

            // Find the schedule by ID
            Schedule scheduleToUpdate = null;
            for (Schedule s : scheduleArrayList) {
                if (s.getId() == sId) {
                    scheduleToUpdate = s;
                    break;
                }
            }

            if (scheduleToUpdate == null) {
                System.out.println("The schedule with ID " + sId + " does not exist.");
                return;
            }

            System.out.println("\n===============================================");
            System.out.println("============   Updating Schedule  =============");
            System.out.println("===============================================\n");

            // Prompt the user to enter a new date
            do {
                System.out.print("Enter a new date for the schedule (dd/mm/yyyy): ");
                dateStr = sc.nextLine();

                if (dateStr.trim().isEmpty() || dateStr.length() != 10) {
                    System.out.println("Invalid date format. Please try again.");
                    continue;
                }

                try {
                    Date enteredDate = sdfDate.parse(dateStr);

                    // Check if the entered date is after the current date
                    if (enteredDate.after(currentDate)) {
                        isValidDate = true;
                        scheduleToUpdate.setDate(enteredDate); // Update the schedule's date
                    } else {
                        System.out.println("Invalid date. Date must be after the current date. Please try again.");
                    }
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please try again.");
                }
            } while (!isValidDate);


            // Prompt the user to enter a new starting time

    //            SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aa");
                Date d1 = null;
                Date d2 = null;
                boolean isValidTime = false;

                int hours = 0;
                int minutes = 0;
            String startTime;
            do {
                System.out.print("Enter a new starting time (HH:mm): ");
                startTime = sc.nextLine();

                if (Validator.isInputEmpty(startTime)) {
                    System.out.println("Starting time is required. Please try again.");
                    continue;
                }

                try {
                    SimpleDateFormat sdfTime24 = new SimpleDateFormat("HH:mm");
                    d1 = sdfTime24.parse(startTime);
                    isValidTime = true; // Set the flag to true if parsing succeeds
                } catch (ParseException e) {
                    System.out.println("Invalid time format. Please try again.");
                    continue; // Prompt again if parsing fails
                }

                // Prompt the user to enter a new ending time
                String endTime;
                do {
                    System.out.print("Enter a new ending time (HH:mm): ");
                    endTime = sc.nextLine();

                    if (Validator.isInputEmpty(endTime)) {
                        System.out.println("Ending time is required. Please try again.");
                        continue;
                    }

                    try {
                        SimpleDateFormat sdfTime24 = new SimpleDateFormat("HH:mm");
                        d2 = sdfTime24.parse(endTime);
                        isValidTime = true; // Set the flag to true if parsing succeeds
                    } catch (ParseException e) {
                        System.out.println("Invalid time format. Please try again.");
                        continue; // Prompt again if parsing fails
                    }

                    // Calculate the time difference in hours and minutes
                    long diffMs = d2.getTime() - d1.getTime();

                    // Handle the case where the end time is on the next day
                    if (diffMs < 0) {
                        diffMs += 24 * 60 * 60 * 1000; // Add 24 hours in milliseconds
                    }

                    long diffSec = diffMs / 1000;
                    hours = (int) (diffSec / 3600); // Total duration in hours
                    int remainingSeconds = (int) (diffSec % 3600);
                    minutes = remainingSeconds / 60; // Remaining seconds converted to minutes

                    // Adjust for the case where the end time is before the start time
                    if (hours < 0 || (hours == 0 && minutes < 0)) {
                        hours += 24;
                    }

                    // Check if the duration is at least 2 hours
                    if (hours >= 2 && minutes >= 0) {
                        scheduleToUpdate.setStartTime(startTime);
                        scheduleToUpdate.setEndTime(endTime);
                        scheduleToUpdate.setDurationHours(hours);
                        scheduleToUpdate.setDurationMinutes(minutes);
                        break; // Exit the loop when the schedule is updated

                    } else {
                        System.out.println("Invalid performance duration. The duration must be at least 2 hours. Please try again.");
                    }
                } while (true);
                break;
            } while (true);


            System.out.println("");
            do{
                // Display the list of available performances
                System.out.println("Available Performances:");
                for (Performance performance : pfmArrayList) {
                    System.out.println(performance.getId() + ": " + performance.getName());
                }

                // Prompt the user to choose a new performance name
                System.out.print("Enter the ID of the new performance name: ");
                String newPId = sc.nextLine();

                // Check if the selected performance ID is valid
                boolean performanceIdValid = false;
                for (Performance performance : pfmArrayList) {
                    if (String.valueOf(performance.getId()).equals(newPId)) {
                        performanceIdValid = true;
                        scheduleToUpdate.setPerformance(performance);
                        break;
                    }
                }

                if (performanceIdValid) {
                    break;
                } else {
                    System.out.println("Invalid performance ID. Please try again.");
                }              
            }while (true);

            System.out.println("Schedule updated successfully!");


        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid schedule ID.");
        }
    }

     
        
    // delete Schedule
    public static void deleteSchedule(ArrayList<Schedule> scheduleArrayList) {
       
        // capture user input of the artist Id to delete the field
        Scanner sc = new Scanner(System.in);

        try {
            ScheduleManagement.viewSchedule(scheduleArrayList, pfmArrayList);
            System.out.print("Please enter the schedule ID that you want to delete: ");
            String sId = sc.nextLine();

            // check the schedule id is it contains symbols
            if (Validator.containsSymbol(sId)) {
                System.out.println("Input contains the specific symbols.");
            } else {
                // check whether the artist ID exist
                // if no, prompt message, yes, remove it
                int index = -1;
                for (int i = 0; i < scheduleArrayList.size(); i++) {
                    Schedule s = scheduleArrayList.get(i);
                    if (s.getId() == Integer.parseInt(sId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("The schedule ID is not available. Please try it again");
                } else {
                    scheduleArrayList.remove(index);
                    System.out.println("Remove schedule's ID successfully");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }    
}

