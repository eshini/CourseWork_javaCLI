package org.example;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
        private List<String> tickets = Collections.synchronizedList(new LinkedList<>());
        private int maxCapacity;

        public TicketPool(int maxCapacity) {
            this.maxCapacity = maxCapacity;
        }

        // Synchronized method for adding tickets
        public synchronized void addTickets(int number) {
            for (int i = 0; i < number && tickets.size() < maxCapacity; i++) {
                tickets.add("Ticket " + (tickets.size() + 1));
            }
        }

        // Synchronized method for removing tickets
        public synchronized String removeTicket() {
            if (!tickets.isEmpty()) {
                return tickets.remove(0);
            }
            return null;
        }

        public int getTicketCount() {
            return tickets.size();
        }

}
