package org.example;
import java.io.Serializable;

// A class that saves important numbers for the ticket system to work
public class Configuration implements Serializable {
    private int totalTickets; // Total tickets in the system
    private int ticketReleaseRate; // How many tickets added each time
    private int customerRetrievalRate; // How fast tickets are bought
    private int maxTicketCapacity; // Maximum number of tickets allowed

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Basic getters and setters
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}

