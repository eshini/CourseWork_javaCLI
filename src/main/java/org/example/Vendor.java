package org.example;
// The Vendor class, which keeps adding tickets
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private Configuration config;

    public Vendor(TicketPool ticketPool, int ticketsPerRelease, int releaseInterval ) {
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTickets(ticketsPerRelease);
                System.out.println("Vendor added " + ticketsPerRelease + " tickets!");
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Vendor stopped.");
        }
    }
}

