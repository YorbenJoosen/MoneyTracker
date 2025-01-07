package app.tally;

import app.ticket.Transaction;

import java.util.ArrayList;

public interface TallyStrategy {
    ArrayList<Transaction> reduceTransactions(ArrayList<Transaction> transactions);
}
