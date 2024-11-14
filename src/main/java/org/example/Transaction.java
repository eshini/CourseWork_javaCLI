package org.example;

public class Transaction {
    private final String type;
    private final String username;
    private final int ticketCount;

    public Transaction(String type, String username, int ticketCount) {
        this.type = type;
        this.username = username;
        this.ticketCount = ticketCount;
    }

    @Override
    public String toString() {
        if ("vendor".equalsIgnoreCase(type)) {
            return "Tickets added by vendor " + username + ": " + ticketCount + " tickets";
        } else if ("customer".equalsIgnoreCase(type)) {
            return "Tickets bought by customer " + username + ": " + ticketCount + " tickets";
        } else {
            return type + " by " + username + ": " + ticketCount + " tickets";
        }
    }
}
