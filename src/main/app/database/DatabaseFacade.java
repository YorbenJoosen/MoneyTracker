package main.app.database;

import main.app.database.abstractDatabase.AbstractDatabaseFactory;
import main.app.database.abstractDatabase.AbstractPersonDatabase;
import main.app.database.abstractDatabase.AbstractTicketDatabase;
import main.app.database.inMemoryDatabase.InMemoryDatabaseFactory;
import main.app.ticket.Ticket;

import java.util.ArrayList;

public class DatabaseFacade {
    private static DatabaseFacade instance;

    private final AbstractPersonDatabase personDatabase;
    private final AbstractTicketDatabase ticketDatabase;

    private DatabaseFacade(AbstractDatabaseFactory databaseFactory) {
        // Private constructor
        this.personDatabase = databaseFactory.getPersonDatabase();
        this.ticketDatabase = databaseFactory.getTicketDatabase();
    }

    public static DatabaseFacade getInstance() {
        InMemoryDatabaseFactory factory = new InMemoryDatabaseFactory(); // TODO Deduce Factory from Config Singleton

        if (instance == null) {
            instance = new DatabaseFacade(factory);
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
}
