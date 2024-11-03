package org.example;

public class Main {
    public static void main(String[] args) {

        try {
            // Load or configure the system
            Configuration config = Configuration.getConfigurationFromUser();

            // Initialize ticket pool
            TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Create and start vendor threads
            for (int i = 0; i < 2; i++) { // Assuming 2 vendors for simplicity
                Thread vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
                vendorThread.start();
            }

            // Create and start customer threads
            for (int i = 0; i < 5; i++) { // Assuming 5 customers for simplicity
                Thread customerThread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()), "Customer-" + i);
                customerThread.start();
            }

        } catch (Exception e) {
            System.err.println("Error starting the system: " + e.getMessage());
        }
    }
}