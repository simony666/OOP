package Seat;

import Seat.Venue;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Database;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;



/**
 *
 * @author User
 */
public class Seat {
    private String SeatID;
    private String venue;
    private double price;
    private int status;
//    private Database db = new Database("C:/Users/User/Documents/Study/Y1 S3/JAVA/seatDatabase.db");
    private String[][] seatArray = new String[3][];
    
    
    public Seat() {
    }
    
    public Seat(String SeatID, String venue, double price, int status) {
        this.SeatID = SeatID;
        this.venue = venue;
        this.price = price;
        this.status = status;
    }

    
        public String getSeatID() {
        return SeatID;
    }

    public void setSeatID(String SeatID) {
        this.SeatID = SeatID;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String[][] getSeatArray() {
        return seatArray;
    }

    public void setSeatArray(String[][] seatArray) {
        this.seatArray = seatArray;
    }
  
    
    //Connect to sqLite database
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/User/Documents/Study/Y1 S3/JAVA/seatDatabase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.getMessage();
        } 
        return conn;
    }
    
    
    //Select seat data from database
    public void selectSeatFromDatabase(){
        String sql = "SELECT seatID, price, venue, status FROM seat";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            
            // Initialize ArrayLists for each category
            List<String[]> rList = new ArrayList<>();
            List<String[]> vList = new ArrayList<>();
            List<String[]> aList = new ArrayList<>();
            
            // loop through the result set
            while (rs.next()) {
                String seatID = rs.getString("seatID");
                double price = rs.getDouble("price");
                String venue = rs.getString("venue");
                int status = rs.getInt("status");
                
                // Create an array to hold the data for each seat
                String[] seatData = {seatID, String.valueOf(price), venue, String.valueOf(status)};
                
                // Categorize the seat data based on the first alphabet of SeatID
                char firstChar = seatID.charAt(0);
                switch (firstChar) {
                    case 'R':
                        rList.add(seatData);
                        System.out.println(rList);
                        break;
                    case 'V':
                        vList.add(seatData);
                        System.out.println(vList);
                        break;
                    case 'A':
                        aList.add(seatData);
                        System.out.println(aList);
                        break;
                    default:
                        break;
                }
            }
            
            // Convert the ArrayLists to 2D arrays
            seatArray = rList.toArray(new String[0][]);
            seatArray = vList.toArray(new String[1][]);
            seatArray = aList.toArray(new String[2][]);
           
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //Add seat into arrayList
    private void addToSeatArray(Seat newSeat) {
        // Determine the category based on the first character of seatID
        char firstChar = newSeat.getSeatID().charAt(0);
        int categoryIndex = -1;

        switch (firstChar) {
            case 'R':
                categoryIndex = 0;
                break;
            case 'V':
                categoryIndex = 1;
                break;
            case 'A':
                categoryIndex = 2;
                break;
            default:
                break;
        }

        if (categoryIndex != -1) {
            // Calculate the new size of the array
            int newSize = seatArray[categoryIndex] != null ? seatArray[categoryIndex].length + 1 : 1;

            // Create a new array with the updated size
            String[][] newArray = new String[3][];
            for (int i = 0; i < seatArray.length; i++) {
                if (i == categoryIndex) {
                    newArray[i] = new String[newSize];
                    if (seatArray[i] != null) {
                        // Copy existing data to the new array
                        System.arraycopy(seatArray[i], 0, newArray[i], 0, seatArray[i].length);
                    }
                    // Add the new seat data to the new array
                    newArray[i][newSize - 1] = newSeat.getSeatID() + "," + newSeat.getPrice() + "," +
                            newSeat.getVenue() + "," + newSeat.getStatus();
                } else {
                    newArray[i] = seatArray[i];
                }
            }

            // Update the seatArray reference with the new array
            seatArray = newArray;
        }
    }
    
    //Save Seat to database
    private void saveSeatToDatabase(Seat newSeat) {
        String sql = "INSERT INTO seat (seatID, price, venue, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newSeat.getSeatID());
            pstmt.setDouble(2, newSeat.getPrice());
            pstmt.setString(3, newSeat.getVenue());
            pstmt.setInt(4, newSeat.getStatus());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving seat data to the database: " + e.getMessage());
        }
    }

   
    
    public void addSeat() {
        selectSeatFromDatabase(); // Fetch existing seat data first

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Seat ID: ");
            String seatID = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();

            scanner.nextLine(); // Consume the newline character left by nextDouble()

            System.out.print("Enter Venue: ");
            String venue = scanner.nextLine();

            System.out.print("Enter Status: ");
            int status = scanner.nextInt();

            // Create a new seat object with user-entered data
            Seat newSeat = new Seat(seatID, venue, price, status);

            // Add the new seat data to the seatArray
            addToSeatArray(newSeat);

            // Save the new seat data to the database
            saveSeatToDatabase(newSeat);

            System.out.println("Seat added successfully.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
    
    
    
    
//        public String addSeat(String seat){
//        
//            Connection conn = db.getConnection();   
//            String sqlText = "select * from Data;";
//            ResultSet result;
//            try {
//                result = db.runSql(sqlText);
//                while (result.next()) {
//                //assume table : id, name,price,catergoty
//                    int id = result.getInt("ID");
//                String name = result.getString("name");
//            }
//        } catch (SQLException ex) {
//            //Log Error
//        }
//            return null;
//        }
    
    
//     Method to read data from the database and set it in the seatArray
//    public void loadSeatsFromDatabase() {
//        Connection conn = db.getConnection();
//        String sqlText = "SELECT * FROM seat;";
//        ResultSet result;
//        
//        try {
//            result = db.runSql(sqlText);
//            while (result.next()) {
//                String seatID = result.getString("seatID");
//                double price = result.getDouble("price");
//                String seatID = String.format("%c%03d", name.charAt(0), id); // Assuming name contains "r", "v", "a" as the seat type
//                int typeIndex = getTypeIndex(name.charAt(0)); // Get the index for the seat type
//                addSeatToArray(typeIndex, seatID);
//            }
//        } catch (SQLException ex) {
//            // Handle the exception (log or throw it)
//            ex.printStackTrace();
//        }
//    }
    
//    private int getTypeIndex(char seatType) {
//        switch (seatType) {
//            case 'r':
//                return 0; // Regular seats
//            case 'v':
//                return 1; // VIP seats
//            case 'a':
//                return 2; // Accessible seats
//            default:
//                return 3; // Default (for unrecognized types)
//
//        }
//        
//    }
    
//    private void addSeatToArray(int typeIndex, String seatID) {
//        String[] seatsOfType = seatArray[typeIndex];
//        String[] newSeatsOfType = new String[seatsOfType.length + 1];
//        System.arraycopy(seatsOfType, 0, newSeatsOfType, 0, seatsOfType.length);
//        newSeatsOfType[seatsOfType.length] = seatID;
//        seatArray[typeIndex] = newSeatsOfType;
//    }

    
    
//    public String addSeat(String seat){
//        
//        Connection conn = db.getConnection();
//        String sqlText = "select * from Data;";
//        ResultSet result;
//        try {
//            result = db.runSql(sqlText);
//            while (result.next()) {
//                //assume table : id, name,price,catergoty
//                int id = result.getInt("ID");
//                String name = result.getString("name");
//            }
//        } catch (SQLException ex) {
//            //Log Error
//        }
//        return null;
//    }
    
    
        // Method to add a seat to the database
//    public void addSeat(String seatID) throws SQLException {
//        Connection conn = db.getConnection();
//        String sqlText = "INSERT INTO Seat (seatID) VALUES (?)";
//        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlText)) {
//            preparedStatement.setString(1, seatID);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    // Method to modify a seat in the database
//    public void modifySeat(String oldSeatID, String newSeatID) throws SQLException {
//        Connection conn = db.getConnection();
//        String sqlText = "UPDATE Seat SET seatID = ? WHERE seatID = ?";
//        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlText)) {
//            preparedStatement.setString(1, newSeatID);
//            preparedStatement.setString(2, oldSeatID);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    // Method to remove a seat from the database
//    public void removeSeat(String seatID) throws SQLException {
//        Connection conn = db.getConnection();
//        String sqlText = "DELETE FROM Seat WHERE seatID = ?";
//        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlText)) {
//            preparedStatement.setString(1, seatID);
//            preparedStatement.executeUpdate();
//        }
//    }


}
