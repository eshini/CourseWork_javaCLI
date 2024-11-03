package org.example;
public class Customer implements Runnable {
        private TicketPool ticketPool;
        private int retrievalRate;

        public Customer(TicketPool ticketPool, int retrievalRate) {
            this.ticketPool = ticketPool;
            this.retrievalRate = retrievalRate;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    for (int i = 0; i < retrievalRate; i++) {
                        String ticket = ticketPool.removeTicket();
                        if (ticket != null) {
                            System.out.println(Thread.currentThread().getName() + " purchased " + ticket);
                        } else {
                            System.out.println("No tickets available.");
                        }
                    }
                    Thread.sleep(1000); // Wait for the next retrieval attempt
                } catch (InterruptedException e) {
                    System.err.println("Customer thread interrupted.");
                    break;
                }
            }
        }
}
