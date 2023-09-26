package Payment;

import Seat.Seat;
import Seat.Ticket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ql
 */
public class Invoice {
    private int invoiceID;
    private static int nextID = 1;
    private Seat seat;
    private Ticket ticket;
    private static ArrayList<Invoice> InvoiceArrayList = new ArrayList<Invoice>();

    public Invoice(Seat seat, Ticket ticket) {
        this.invoiceID = nextID;
        nextID++;
        this.seat = seat;
        this.ticket = ticket;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public static ArrayList<Invoice> getInvoiceArrayList() {
        return InvoiceArrayList;
    }

    public static void setInvoiceArrayList(ArrayList<Invoice> InvoiceArrayList) {
        Invoice.InvoiceArrayList = InvoiceArrayList;
    }
    
    
    public static void makePayment(){
        Scanner sc = new Scanner(System.in);
        int InTicketID;
        double InTicketPrice = 0;
        double outPrice = 0;
        Seat selectedTicket = null;
        
        Ticket.viewAllTicket();
        System.out.print("Please enter Ticket Id that you want to make payment: ");
        int InTicket = sc.nextInt();
        sc.nextLine();
        Ticket foundTicket = Ticket.existTicket(InTicket);
        if (foundTicket == null){
            System.out.println("Ticket Id not exist. Please try again");
        }else{
            System.out.print("Enter your password: ");
            //if (varify)
            //Here, you can implement the logic to collect payment.
            // For simplicity, let's assume the payment is successful and mark the ticket as paid.
            
            
            System.out.println("Thank for your purchase");
            //change the related seatID's status to 2
            foundTicket.getSeat().setStatus(2);
            Invoice invoice = generateInvoice(foundTicket);
            
        }
        
    }
    
    public static Invoice generateInvoice(Ticket ticket) {
    Seat seat = ticket.getSeat();
    Invoice invoice = new Invoice(seat, ticket);

    // Add the invoice to the ArrayList
    InvoiceArrayList.add(invoice);

    return invoice;
    }
    
    public static void viewAllInvoice() {
        if (InvoiceArrayList.isEmpty()) {
            System.out.println("============:   No invoice found   :============" + "\n\n");
            
        } else {
            System.out.println("=============:   Invoice List   :=============" + "\n");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "InvoiceID", "TicketID", "SeatID", "VenueID", "price");
            System.out.println("\n_________________________________________________________");
            for (Invoice invoice : InvoiceArrayList) {
                    System.out.printf("%-10s %-15s %-15s %-15s%n",
                    invoice.getInvoiceID(),
                    invoice.getTicket().getTicketID(),
                    invoice.getSeat().getSeatID(),
                    invoice.getTicket().getSeat().getVenue().getVenueID(),
                    invoice.getSeat().getPrice());
            }
        }
    }
    
    public static void deleteInvoice(ArrayList<Invoice> invoiceArrayList, int invoiceID) {
        Scanner sc = new Scanner(System.in);
        
        viewAllInvoice();
    
        try{
            System.out.println("Please enter the Invoice ID that you want to delete: ");
            int deleSc = sc.nextInt();
            int index = -1;
            for (int i =0; i < InvoiceArrayList.size(); i++){
                Invoice dele = InvoiceArrayList.get(i);
                if (dele.getInvoiceID() == deleSc){
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("The invoice ID is not exits. Please try it again");
            } else {
                // Get the associated seat and change its status to 1 (available)
                Seat associatedSeat = invoiceArrayList.get(index).getSeat();
                associatedSeat.setStatus(1);
                //delete releted invoice
                InvoiceArrayList.remove(index);
                System.out.println("Remove invoice ID successfully");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a value.");
        }
    }
        

}
     
        

    
    







            
            
            
            
       
        

    
    
    
    
    

