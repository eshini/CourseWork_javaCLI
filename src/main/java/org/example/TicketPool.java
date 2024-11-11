package org.example;

import java.util.LinkedList;
import java.util.Queue;

// TicketPool acts as a 'ticket box' for storing and managing tickets
public class TicketPool {
    private final Queue<String> tickets = new LinkedList<>();

    public synchronized void addTickets(int count, int maxCapacity) {
        if (tickets.size() + count > maxCapacity) {
            System.out.println("Max capacity reached, cannot add more tickets.");
            return;
        }

        for (int i = 0; i < count; i++) {
            tickets.add("Ticket#" + (tickets.size() + 1));
        }
        notifyAll();
    }

    public synchronized String removeTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error: " + e.getMessage());
                return null;
            }
        }
        return tickets.poll();
    }

    public synchronized int getAvailableTickets() {
        return tickets.size();
    }
}



