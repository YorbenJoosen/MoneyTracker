package main.app.database;

import main.app.database.abstractDatabase.AbstractDatabaseFactory;
import main.app.database.abstractDatabase.AbstractPersonDatabase;
import main.app.database.abstractDatabase.AbstractTicketDatabase;
import main.app.database.inMemoryDatabase.InMemoryDatabaseFactory;
import main.app.observers.DatabaseUpdateListener;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DatabaseFacade {
    private static volatile DatabaseFacade instance;

    private final AbstractPersonDatabase personDatabase;
    private final AbstractTicketDatabase ticketDatabase;
    private final DatabaseUpdateListener personDatabaseListener;
    private final DatabaseUpdateListener ticketDatabaseListener;

    private DatabaseFacade(AbstractDatabaseFactory databaseFactory) {
        // Private constructor
        this.personDatabase = databaseFactory.createPersonDatabase();
        this.personDatabaseListener = new DatabaseUpdateListener(this.personDatabase);
        this.ticketDatabase = databaseFactory.createTicketDatabase();
        this.ticketDatabaseListener = new DatabaseUpdateListener(this.ticketDatabase);
    }

    public static DatabaseFacade getInstance() {
        InMemoryDatabaseFactory factory = new InMemoryDatabaseFactory(); // TODO Deduce Factory from Config Singleton

        if (instance == null) {
            synchronized (DatabaseFacade.class) {
                if (instance == null) {
                    instance = new DatabaseFacade(factory);
                }
            }
        }
        return instance;
    }

    public void storeTicket(Ticket ticket) {
        // TODO
    }

    public ArrayList<Ticket> fetchAllTickets() {
        // TODO
        return null;
    }

    private static long getMin(ArrayList<Transaction> transactions) {
        // Returns index of smallest
        return transactions.stream().min((i, j) -> Math.min(i.amount,j.amount)).map(transactions::indexOf).orElse(0);
    }
    private static long getMax(ArrayList<Transaction> transactions) {
        // Returns index of smallest
        return transactions.stream().max((i, j) -> Math.max(i.amount,j.amount)).map(transactions::indexOf).orElse(0);
    }

    public ArrayList<Transaction> getFinalTally() {
        // All tickets together can be seen as a graph where each person is a Vertex and each Amount is an Edge
        // Solution:
        // https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/

        // TODO
        return null;
    }
}
