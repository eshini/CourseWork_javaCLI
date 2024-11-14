package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketsPerRelease;
    private final Configuration config;
    private final Scanner scanner;
    private final TransactionHistory transactionHistory;

    public Vendor(TicketPool ticketPool, Configuration config, Scanner scanner, TransactionHistory transactionHistory) {
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = config.ticketReleaseRate;
        this.config = config;
        this.scanner = scanner;
        this.transactionHistory = transactionHistory;
    }
    @Override
    public void run() {
        try {
            System.out.print("Enter your Name: ");
            String username = scanner.nextLine();
            System.out.print("Enter the price per ticket($): ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            int ticketsToAdd;
            while (true) {
                System.out.print("Enter number of tickets to add: ");
                try {
                    ticketsToAdd = scanner.nextInt();
                    scanner.nextLine(); // Clear newline character
                    if (ticketsToAdd <= 0) {
                        System.out.println("Error: Number of tickets must be a positive integer.");
                    } else if (ticketsToAdd > ticketsPerRelease) {
                        System.out.println("Error: You can only add up to " + ticketsPerRelease + " tickets at a time.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid positive integer for tickets.");
                    scanner.nextLine(); // Clear invalid input
                }
            }

            ticketPool.addTickets(ticketsToAdd, config.getMaxTicketCapacity());
            transactionHistory.addTransaction(new Transaction("Vendor", username, ticketsToAdd));

            System.out.println("Vendor " + username + " added " + ticketsToAdd + " tickets successfully!");
        } catch (Exception e) {
            System.out.println("Error occurred in Vendor: " + e.getMessage());
        }
    }
}


