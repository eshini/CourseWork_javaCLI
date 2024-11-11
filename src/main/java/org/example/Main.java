package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Main class to manage the ticket system
public class Main {
    private static final TicketPool ticketPool = new TicketPool();
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = adminLogin(scanner);

        System.out.println("Starting the Ticket System...");
        executorService = Executors.newCachedThreadPool();

        while (true) {
            System.out.println("\nMenu Options:");
            System.out.println("1. Vendor Login");
            System.out.println("2. Customer Login");
            System.out.println("3. View Transaction History");
            System.out.println("4. Stop System");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> vendorLogin(scanner, config);
                case 2 -> customerLogin(scanner, config);
                case 3 -> displayTransactionHistory();
                case 4 -> {
                    shutdownSystem();
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }
    private static Configuration adminLogin(Scanner scanner) {
        System.out.println("You're Logged In as an Admin User.please Initialize System Settings to continue..");

        int totalTickets = getPositiveInt(scanner, "Enter total number of tickets: ");
        int ticketReleaseRate = getPositiveInt(scanner, "Enter ticket release rate (tickets per second): ");
        int customerRetrievalRate = getPositiveInt(scanner, "Enter customer ticket-buying speed (milliseconds): ");
        int maxTicketCapacity = getPositiveInt(scanner, "Enter maximum ticket capacity: ");

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }
    private static int getPositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear invalid input
            }
        }
        return value;
    }
    private static void vendorLogin(Scanner scanner, Configuration config) {
        int ticketsPerRelease= config.ticketReleaseRate;
        Vendor vendor = new Vendor(ticketPool, ticketsPerRelease, scanner, config);
        executorService.execute(vendor);
    }

    private static void customerLogin(Scanner scanner, Configuration config) {
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        Scanner scannercustomer = new Scanner(System.in);
        Customer customer = new Customer(ticketPool, customerRetrievalRate, scannercustomer);
        executorService.execute(customer);
    }
    private static void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : Customer.transactionHistory) {
            System.out.println(transaction);
        }
    }
    private static void shutdownSystem() {
        System.out.println("Shutting down the Ticket System...");
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        System.out.println("System stopped.");
    }
}
