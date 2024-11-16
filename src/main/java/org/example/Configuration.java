package org.example;

import java.io.Serializable;

public class Configuration implements Serializable {
    //total tickets per event
    public final int totalTickets;
    //Number of tickets a vendor releases to the ticket pool at once.
    public final int ticketReleaseRate;
    //Number of tickets a customer can purchase from the ticket pool at once.
    public final int customerRetrievalRate;
    //maximum ticket capacity in the ticket pool
    public final int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}

