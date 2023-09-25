package Seat;

/**
 *
 * @author ql
 */
public class Venue {
    private String venueID;
    private String location;
    private int capacity;

    public Venue(String venueID, String location, int capacity) {
        this.venueID = venueID;
        this.location = location;
        this.capacity = capacity;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    
    
}
