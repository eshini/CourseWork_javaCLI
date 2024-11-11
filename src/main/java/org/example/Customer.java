package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalInterval;
    public static List<Transaction> transactionHistory = new ArrayList<>();
    private final Scanner scanner;
    private String username;
    private String email;

    public Customer(TicketPool ticketPool, int retrievalInterval, Scanner scanner) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        customerLogin();
    }

    // Handles the customer login and ticket purchasing process
    private void customerLogin() {
        System.out.print("Enter Customer Username: ");
        this.username = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        this.email = scanner.nextLine();

        if (ticketPool.getAvailableTickets() == 0) {
            System.out.println("No tickets available. Please try again later.");
            return;
        }

        System.out.println("Tickets available: " + ticketPool.getAvailableTickets());
        System.out.print("Enter number of tickets to purchase: ");
        int ticketsToPurchase = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (ticketsToPurchase > ticketPool.getAvailableTickets()) {
            System.out.println("Not enough tickets available. Try a smaller number.");
            return;
        }

        // Purchase tickets
        for (int i = 0; i < ticketsToPurchase; i++) {
            String ticket = ticketPool.removeTicket();
            System.out.println("Customer " + username + " bought: " + ticket);
        }
        transactionHistory.add(new Transaction("Customer", username, ticketsToPurchase));

        // E-bill simulation
        System.out.println("Purchase completed! E-bill generated:");
        System.out.println("Customer: " + username);
        System.out.println("Tickets Purchased: " + ticketsToPurchase);
        System.out.println("Total Price: $" + (ticketsToPurchase * 10)); // Assuming a ticket price of $10
        System.out.println("Purchase Order ID: " + System.currentTimeMillis());
    }
}
