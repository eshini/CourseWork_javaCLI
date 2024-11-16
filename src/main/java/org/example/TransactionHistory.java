package org.example;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            transactions.forEach(System.out::println);
        }
    }
}
