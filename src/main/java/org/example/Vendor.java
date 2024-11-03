package org.example;
public class Vendor implements Runnable {
        private TicketPool ticketPool;
        private int ticketReleaseRate;

        public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
            this.ticketPool = ticketPool;
            this.ticketReleaseRate = ticketReleaseRate;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    ticketPool.addTickets(ticketReleaseRate);
                    System.out.println("Vendor added " + ticketReleaseRate + " tickets.");
                    Thread.sleep(2000); // Wait for the next release
                } catch (InterruptedException e) {
                    System.err.println("Vendor thread interrupted.");
                    break;
                }
            }
        }
}

