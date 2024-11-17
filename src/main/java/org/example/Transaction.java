package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final String username;
    private final int ticketCount;

    private final LocalDateTime dateTime;

    public Transaction(String type, String username, int ticketCount) {
        this.type = type;
        this.username = username;
        this.ticketCount = ticketCount;
        this.dateTime = LocalDateTime.now();
    }

//    @Override
//    public String toString() {
//        if ("vendor".equalsIgnoreCase(type)) {
//            return "Tickets added by vendor " + username + ": " + ticketCount + " tickets";
//        } else if ("customer".equalsIgnoreCase(type)) {
//            return "Tickets bought by customer " + username + ": " + ticketCount + " tickets";
//        } else {
//            return type + " by " + username + ": " + ticketCount + " tickets";
//        }
//    }

    @Override
    public String toString() {
        // Format date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);


        if ("vendor".equalsIgnoreCase(type)) {
            return String.format(
                    "[%s] Tickets added by vendor %s: %d tickets",
                    formattedDateTime, username, ticketCount
            );
        } else if ("customer".equalsIgnoreCase(type)) {
            return String.format(
                    "[%s] Tickets bought by customer %s: %d tickets",
                    formattedDateTime, username, ticketCount
            );
        } else {
            return String.format(
                    "[%s] %s by %s: %d tickets",
                    formattedDateTime, type, username, ticketCount
            );
        }
    }
}
