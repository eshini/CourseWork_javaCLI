package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Constructors, getters, and setters
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Load settings from a JSON file
    public static Configuration loadFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }

    // Save settings to a JSON file
    public void saveToFile(String filename) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        }
    }
    public static Configuration getConfigurationFromUser() {
        Scanner scanner = new Scanner(System.in);
        int totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity;

        // Prompt and validate user input
        System.out.print("Enter total number of tickets: ");
        totalTickets = scanner.nextInt();
        System.out.print("Enter ticket release rate: ");
        ticketReleaseRate = scanner.nextInt();
        System.out.print("Enter customer retrieval rate: ");
        customerRetrievalRate = scanner.nextInt();
        System.out.print("Enter maximum ticket capacity: ");
        maxTicketCapacity = scanner.nextInt();

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
}
