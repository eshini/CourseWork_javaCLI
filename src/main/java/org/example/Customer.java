package org.example;
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalInterval;

    public Customer(TicketPool ticketPool, int retrievalInterval) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String ticket = ticketPool.removeTicket();
                System.out.println("Customer bought: " + ticket);
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer stopped.");
        }
    }
}