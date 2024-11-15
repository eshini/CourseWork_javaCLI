//package org.example;
//import java.util.Scanner;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//public class Main {
//    // Main class to manage the ticket system
//        private static final TicketPool ticketPool = new TicketPool();
//        private static ExecutorService executorService;
//
//        public static void main(String[] args) {
//            Scanner scanner = new Scanner(System.in);
//            Configuration config = initializeConfig(scanner);
//
//            System.out.println("Starting the Ticket System...");
//            executorService = Executors.newCachedThreadPool();
//
//            executorService.execute(new Vendor(ticketPool, config.getTicketReleaseRate(), 1000));
//            executorService.execute(new Customer(ticketPool, config.getCustomerRetrievalRate()));
//
//            String command;
//            while (true) {
//                System.out.println("Type 'stop' to end the system:");
//                command = scanner.nextLine();
//                if ("stop".equalsIgnoreCase(command)) {
//                    shutdownSystem();
//                    break;
//                }
//            }
//            scanner.close();
//        }
//        // Collect settings for the ticket system from the user
//        private static Configuration initializeConfig(Scanner scanner) {
//            String role;
//            Configuration config = null;
//
//            while (true) {
//                System.out.print("*************************************************\n");
//                System.out.print("*** Welcome to the real-time ticketing system ***\n");
//                System.out.print("*************************************************\n");
//                System.out.print("Please select your role (Admin, Vendor, Customer): ");
//                role = scanner.next().trim().toLowerCase();
//
//                if ("admin".equals(role)) {
//                    // Admin initializes the system parameters
//                    System.out.print("Enter total number of tickets: ");
//                    int totalTickets = scanner.nextInt();
//
//                    System.out.print("Enter ticket release rate (tickets per interval): ");
//                    int ticketReleaseRate = scanner.nextInt();
//
//                    System.out.print("Enter customer ticket-buying speed (milliseconds): ");
//                    int customerRetrievalRate = scanner.nextInt();
//
//                    System.out.print("Enter maximum ticket capacity: ");
//                    int maxTicketCapacity = scanner.nextInt();
//
//                    config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
//                    System.out.println("System initialized successfully by Admin.");
//                    break;
//
//                } else {
//                    System.out.println("Only an Admin can initialize the system. Please log in as Admin first.");
//                }
//            }
//
//            // After Admin initialization, prompt for Vendor or Customer role
//            while (true
//            ) {
//                System.out.print("Please select your role (Vendor, Customer): ");
//                role = scanner.next().trim().toLowerCase();
//
//                if ("vendor".equals(role)) {
//                    System.out.println("Logged in as Vendor. You will start adding tickets...");
//                    startVendorProcess(config);
//                    break;
//
//                } else if ("customer".equals(role)) {
//                    System.out.println("Logged in as Customer. You will start buying tickets...");
//                    startCustomerProcess(config);
//                    break;
//
//                } else {
//                    System.out.println("Invalid role. Please enter Vendor or Customer.");
//                }
//            }
//
//            return config;
//        }
//
//    // Starts the vendor process for adding tickets in a separate thread
//    private static void startVendorProcess(Configuration config) {
//        TicketPool ticketPool = new TicketPool();
//        Vendor vendor = new Vendor(ticketPool, config.getTicketReleaseRate(), 1000);
//        Thread vendorThread = new Thread(vendor);
//        vendorThread.start();
//    }
//
//    // Starts the customer process for buying tickets in a separate thread
//    private static void startCustomerProcess(Configuration config) {
//        TicketPool ticketPool = new TicketPool();
//        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
//        Thread customerThread = new Thread(customer);
//        customerThread.start();
//    }
//
//    // Stop all ticket sellers and buyers and end the program
//        private static void shutdownSystem() {
//            System.out.println("Shutting down the Ticket System...");
//            if (executorService != null && !executorService.isShutdown()) {
//                executorService.shutdownNow();
//            }
//            System.out.println("System stopped.");
//        }
//}
package org.example;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final TicketPool ticketPool = new TicketPool();
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = initializeConfig(scanner);

        System.out.println("Starting the Ticket System...");
        executorService = Executors.newCachedThreadPool();

        boolean vendorSelected = false;
        boolean customerSelected = false;

        // Allow multiple role selection
        while (true) {
            String role = chooseUserRole(scanner);
            if ("vendor".equalsIgnoreCase(role)) {
                vendorSelected = true;
            } else if ("customer".equalsIgnoreCase(role)) {
                customerSelected = true;
            }
            System.out.print("Do you want to add another role? (yes/no): ");
            String anotherRole = scanner.next().trim().toLowerCase();
            if (!"yes".equalsIgnoreCase(anotherRole)) {
                break;
            }
        }

        // Start Vendor and Customer threads based on selected roles
        if (vendorSelected) {
            System.out.println("Logged in as Vendor. You will start adding tickets...");
            executorService.execute(new Vendor(ticketPool, config.getTicketReleaseRate(), 1000));
        }
        if (customerSelected) {
            System.out.println("Logged in as Customer. You will start buying tickets...");
            executorService.execute(new Customer(ticketPool, config.getCustomerRetrievalRate()));
        }

        // Monitor for stop command to shut down the system
        System.out.println("Type 'stop' to end the system:");
        while (true) {
            String command = scanner.nextLine();
            if ("stop".equalsIgnoreCase(command)) {
                shutdownSystem();
                break;
            }
        }
        scanner.close();
    }

    // Collect settings for the ticket system from the user
    private static Configuration initializeConfig(Scanner scanner) {
        Configuration config = null;
        while (true) {
            System.out.print("*************************************************\n");
            System.out.print("*** Welcome to the real-time ticketing system ***\n");
            System.out.print("*************************************************\n");
            System.out.print("Please select your role as Admin");
            String role = scanner.next().trim().toLowerCase();

            if ("admin".equals(role)) {
                System.out.print("Enter total number of tickets: ");
                int totalTickets = scanner.nextInt();

                System.out.print("Enter ticket release rate (tickets per interval): ");
                int ticketReleaseRate = scanner.nextInt();

                System.out.print("Enter customer ticket-buying speed (milliseconds): ");
                int customerRetrievalRate = scanner.nextInt();

                System.out.print("Enter maximum ticket capacity: ");
                int maxTicketCapacity = scanner.nextInt();

                config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                System.out.println("System initialized successfully by Admin.");
                scanner.nextLine();  // Consume newline left-over
                break;
            } else {
                System.out.println("Only an Admin can initialize the system. Please log in as Admin first.");
            }
        }
        return config;
    }

    // Prompt the user to select Vendor or Customer role after Admin initialization
    private static String chooseUserRole(Scanner scanner) {
        String role;
        while (true) {
            System.out.print("Please select your role (Vendor, Customer): ");
            role = scanner.next().trim().toLowerCase();

            if ("vendor".equals(role) || "customer".equals(role)) {
                break;
            } else {
                System.out.println("Invalid role. Please enter Vendor or Customer.");
            }
        }
        return role;
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
