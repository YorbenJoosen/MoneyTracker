package main.app.database;

import main.app.database.abstractDatabase.AbstractDatabaseFactory;
import main.app.database.abstractDatabase.AbstractGroupDatabase;
import main.app.database.abstractDatabase.AbstractPersonDatabase;
import main.app.database.abstractDatabase.AbstractTicketDatabase;
import main.app.database.inMemoryDatabase.InMemoryDatabaseFactory;
import main.app.group.Group;
import main.app.observers.DatabaseUpdateListener;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.Transaction;

import java.util.*;

public class DatabaseFacade {
    private static volatile DatabaseFacade instance;

    private final AbstractPersonDatabase personDatabase;
    private final AbstractTicketDatabase ticketDatabase;
    private final AbstractGroupDatabase groupDatabase;

    private DatabaseFacade(AbstractDatabaseFactory databaseFactory) {
        // Private constructor
        this.personDatabase = databaseFactory.createPersonDatabase();
        new DatabaseUpdateListener(this.personDatabase);
        this.ticketDatabase = databaseFactory.createTicketDatabase();
        new DatabaseUpdateListener(this.ticketDatabase);
        this.groupDatabase = databaseFactory.createGroupDatabase();
        new DatabaseUpdateListener(this.groupDatabase);

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
    public void addGroup(String name) {
        this.groupDatabase.addGroup(new Group(name));
    }
    public ArrayList<Group> getGroups() {
        return this.groupDatabase.getGroups();
    }
    public ArrayList<Person> getPersons() {
        return this.personDatabase.getPersons();
    }
    public ArrayList<Person> getPersonsFromGroup(UUID id) {
        return this.groupDatabase.getGroup(id).getPersons();
    }
    public Group getGroup(UUID id) {
        return this.groupDatabase.getGroup(id);
    }
    public ArrayList<Ticket> getTickets(UUID id) {
        return this.groupDatabase.getGroup(id).getTickets();
    }
    public void addPerson(Person person) {
        this.personDatabase.addPerson(person);
    }
}
