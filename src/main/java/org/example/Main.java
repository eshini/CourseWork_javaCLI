package org.example;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Main {
    // Main class to manage the ticket system
        private static final TicketPool ticketPool = new TicketPool();
        private static ExecutorService executorService;

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Configuration config = initializeConfig(scanner);

            System.out.println("Starting the Ticket System...");
            executorService = Executors.newCachedThreadPool();

            executorService.execute(new Vendor(ticketPool, config.getTicketReleaseRate(), 1000));
            executorService.execute(new Customer(ticketPool, config.getCustomerRetrievalRate()));

            String command;
            while (true) {
                System.out.println("Type 'stop' to end the system:");
                command = scanner.nextLine();
                if ("stop".equalsIgnoreCase(command)) {
                    shutdownSystem();
                    break;
                }
            }
            scanner.close();
        }
        // Collect settings for the ticket system from the user
        private static Configuration initializeConfig(Scanner scanner) {
            System.out.print("Enter total number of tickets: ");
            int totalTickets = scanner.nextInt();

            System.out.print("Enter ticket release rate (tickets per second): ");
            int ticketReleaseRate = scanner.nextInt();

            System.out.print("Enter customer ticket-buying speed (milliseconds): ");
            int customerRetrievalRate = scanner.nextInt();

            System.out.print("Enter maximum ticket capacity: ");
            int maxTicketCapacity = scanner.nextInt();

            return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        }

        // Stop all ticket sellers and buyers and end the program
        private static void shutdownSystem() {
            System.out.println("Shutting down the Ticket System...");
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdownNow();
            }
            System.out.println("System stopped.");
        }
}