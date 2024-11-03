package org.example;

import java.util.LinkedList;
import java.util.Queue;

// A class that acts as a 'ticket box' for storing and giving out tickets
public class TicketPool {
    private final Queue<String> tickets = new LinkedList<>();

    // Add a certain number of tickets to the pool
    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            tickets.add("Ticket#" + (tickets.size() + 1));
        }
        notifyAll(); // Tell everyone waiting for tickets that they are available
    }

    // Remove one ticket from the pool
    public synchronized String removeTicket() {
        while (tickets.isEmpty()) { // If no tickets, wait
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error: " + e.getMessage());
            }
        }
        return tickets.poll(); // Take out a ticket
    }
}


