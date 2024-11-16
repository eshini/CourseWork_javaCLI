package org.example;

import java.util.Scanner;

public class Main {
    private static final TicketPool ticketPool = new TicketPool();
    private static final TransactionHistory transactionHistory = new TransactionHistory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;
        boolean isSystemStarted = false;
        while (true) {
            System.out.println("...REAL TIME TICKETING SYSTEM...");
            System.out.println("\nMain Menu:");
            System.out.println("1. Start System");
            System.out.println("2. Stop System\n");

            int initialChoice = getPositiveInt(scanner, "Choose an option: ");
            switch (initialChoice) {
                case 1 -> {
                    if (!isSystemStarted) {
                        config = initializeSystemSettings(scanner);
                        isSystemStarted = true;
                        System.out.println("System has been initialized successfully!");
                        runTicketSystem(scanner, config); // Start the main system loop
                    } else {
                        System.out.println("System is already started.");
                    }
                }
                case 2 -> {
                    shutdownSystem(scanner);
                    scanner.close(); // Close the scanner only here, at the end
                    return; // Exit the program
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }
    private static void runTicketSystem(Scanner scanner, Configuration config) {
        System.out.println("\n..Welcome to the real time ticketing system...");
        while (true) {
            System.out.println("\nMenu Options:");
            System.out.println("1. Vendor Login");
            System.out.println("2. Customer Login");
            System.out.println("3. View Transaction History");
            System.out.println("4. Stop System");

            int choice = getPositiveInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1 -> vendorLogin(scanner, config);
                case 2 -> customerLogin(scanner, config);
                case 3 -> displayTransactionHistory();
                case 4 -> {
                    shutdownSystem(scanner);
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }
    private static Configuration initializeSystemSettings(Scanner scanner) {
        System.out.println("Starting the Ticket System...");
        System.out.println("You have logged in as a system Admin.Please Initialize the following details as Required.");
        // Get max ticket capacity
        int maxTicketCapacity = getPositiveInt(scanner, "Enter maximum ticket capacity for the system: ");
        int totalTickets;
        int customerRetrievalRate;
        int ticketReleaseRate;
        // Get total number of tickets, ensuring it is less than max ticket capacity
        while (true) {
            totalTickets = getPositiveInt(scanner, "Enter total number of tickets per event : ");
            if (totalTickets < maxTicketCapacity) {
                break;
            } else {
                System.out.println("Total tickets must be less than the maximum ticket capacity.");
            }
        }
        // Get ticket release rate with validations
        while (true) {
             ticketReleaseRate = getPositiveInt(scanner, "Enter ticket release rate: ");
            if (ticketReleaseRate < maxTicketCapacity) {
                break;
            } else {
                System.out.println("Ticket release rate must be less than maximum ticket capacity.");
            }
        }
        // Get customer retrieval rate with validations
        while (true) {
             customerRetrievalRate = getPositiveInt(scanner, "Enter customer retrieval rate: ");
            if (customerRetrievalRate <= ticketReleaseRate) {
                break;
            } else {
                System.out.println("Customer retrieval rate must be less than or equal to the ticket release rate.");
            }
        }
        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }
    private static int getPositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive integer.");
            } else {
                System.out.println("Invalid input.Please try again...");
                scanner.next();
            }
        }
    }
    private static void vendorLogin(Scanner scanner, Configuration config) {
        Vendor vendor = new Vendor(ticketPool, config, scanner, transactionHistory);
        Thread vendorThread = new Thread(vendor);
        vendorThread.start();
        try {
            vendorThread.join(); // Wait until vendor thread completes before returning to menu
        } catch (InterruptedException e) {
            System.out.println("An error occurred while waiting for the vendor process to complete.");
        }
    }
    private static void customerLogin(Scanner scanner, Configuration config) {
        if (ticketPool.getAvailableTickets() <= 0) {
            System.out.println("No tickets available. Please try again later.");
            return; // Return to the main menu
        }
        Customer customer = new Customer(ticketPool, config,new Scanner(System.in), transactionHistory);
        Thread customerThread = new Thread(customer);
        customerThread.start();
        try {
            customerThread.join(); // Wait until Customer thread completes before returning to menu
        } catch (InterruptedException e) {
            System.out.println("An error occurred while waiting for the vendor process to complete.");
        }
    }
    private static void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        transactionHistory.printAllTransactions();
    }
    private static void shutdownSystem(Scanner scanner) {
        System.out.println("Shutting down the Ticket System...");
        System.out.println("System stopped.");
        scanner.close(); // Close the scanner only here, at the end
    }
}
