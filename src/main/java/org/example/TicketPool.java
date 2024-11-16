package org.example;

import java.util.LinkedList;
import java.util.Queue;

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

    public synchronized void removeTicket(int ticketsToBuy) {
        if (tickets.isEmpty()) {
                System.out.println("no tickets available.please try again later.");
                return;
        }
        for (int i = 0; i < ticketsToBuy; i++) {
            tickets.poll(); // Removes a single ticket from the front of the queue
        }
    }

    public synchronized int getAvailableTickets() {
        return tickets.size();
    }
}
