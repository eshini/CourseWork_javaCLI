package org.example;

import java.util.Scanner;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    public final int ticketsPerRelease;
    private final Scanner scanner;
    private final Configuration config;
    private String username;
    private String email;

    public Vendor(TicketPool ticketPool, int ticketsPerRelease,  Scanner scanner, Configuration config) {
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.scanner = scanner;
        this.config = config;
    }

    @Override
    public void run() {
        vendorLogin();
    }

    // Handles the vendor login and ticket-adding process
    private void vendorLogin() {
        System.out.print("Enter Vendor Username: ");
        this.username = scanner.nextLine();
        System.out.print("Enter Vendor Email: ");
        this.email = scanner.nextLine();

        System.out.print("Enter number of tickets to add: ");
        int ticketsToAdd = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        ticketPool.addTickets(ticketsToAdd, config.getMaxTicketCapacity());
        Customer.transactionHistory.add(new Transaction("Vendor", username, ticketsToAdd));

        System.out.println("Vendor " + username + " added " + ticketsToAdd + " tickets.");
    }
}


