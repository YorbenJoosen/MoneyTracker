package app.tally;

import app.ticket.Transaction;

import java.util.ArrayList;

public interface TallyStrategy {
    default ArrayList<Transaction> reduceTransactions(ArrayList<Transaction> transactions) {
        return new FirstFitStrategy().reduceTransactions(transactions);
    }
}
