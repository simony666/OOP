package Seat;

/**
 *
 * @author QL
 */
public class Ticket {
    private int ticketID;
    private String seatID;
    private double ticketPrice;
    

    public Ticket(int ticketID, double ticketPrice) {
        this.ticketID = ticketID;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    
    
    
    
    
}
