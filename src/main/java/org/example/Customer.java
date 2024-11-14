package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final Configuration config;
    private final Scanner scanner;
    private final TransactionHistory transactionHistory;

    public Customer(TicketPool ticketPool,Configuration config, Scanner scanner, TransactionHistory transactionHistory) {
        this.ticketPool = ticketPool;
        this.retrievalRate = config.customerRetrievalRate;
        this.scanner = scanner;
        this.config = config;
        this.transactionHistory = transactionHistory;
    }

    @Override
    public void run() {
        try {
            System.out.print("Enter Customer Name: ");
            String username = scanner.nextLine();

            int ticketsToBuy;
            while (true) {
                int availableTickets = ticketPool.getAvailableTickets();
                System.out.println("Available tickets: " + availableTickets);
                System.out.print("Enter number of tickets to buy: ");
                try {
                    ticketsToBuy = scanner.nextInt();
                    scanner.nextLine(); // Clear the newline character
                    // Validate the number of tickets
                    if (ticketsToBuy <= 0) {
                        System.out.println("Error: Number of tickets must be a positive integer.");
                    } else if (ticketsToBuy > retrievalRate) {
                        System.out.println("Error: You can only buy up to " + retrievalRate + " tickets at a time.");
                    } else if (ticketsToBuy > availableTickets) {
                        System.out.println("Error: Not enough tickets available. Please try a lower amount.");
                    }
                    else {
                        ticketPool.removeTicket(ticketsToBuy);
                        transactionHistory.addTransaction(new Transaction("Customer", username, ticketsToBuy));

                        System.out.println("Customer " + username + " bought" + ticketsToBuy + " tickets successfully!");
                        break; // exit the loop

                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid positive integer for tickets.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred in Customer: " + e.getMessage());
        }
    }
}

