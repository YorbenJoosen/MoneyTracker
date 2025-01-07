package app.database;

import app.database.abstractDatabase.AbstractDatabaseFactory;
import app.database.abstractDatabase.AbstractGroupDatabase;
import app.database.abstractDatabase.AbstractPersonDatabase;
import app.database.abstractDatabase.AbstractTicketDatabase;
import app.database.inMemoryDatabase.InMemoryDatabaseFactory;
import app.group.Group;
import app.observers.DatabaseUpdateListener;
import app.person.Person;
import app.ticket.Ticket;
import app.ticket.Transaction;

import java.util.*;

public class DatabaseFacade {
    private static volatile DatabaseFacade instance;

    private final AbstractPersonDatabase personDatabase;
    private final AbstractTicketDatabase ticketDatabase;
    private final AbstractGroupDatabase groupDatabase;

    private DatabaseFacade(AbstractDatabaseFactory databaseFactory) {
        // Private constructor
        this.personDatabase = databaseFactory.createPersonDatabase();
        new DatabaseUpdateListener<>(this.personDatabase);
        this.ticketDatabase = databaseFactory.createTicketDatabase();
        new DatabaseUpdateListener<>(this.ticketDatabase);
        this.groupDatabase = databaseFactory.createGroupDatabase();
        new DatabaseUpdateListener<>(this.groupDatabase);

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
        return this.ticketDatabase.getAll();
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Ticket ticket : fetchAllTickets()) {
            result.addAll(ticket.getPersonPriceList());
        }
        return result;
    }

    public void addGroup(String name) {
        this.groupDatabase.addEntry(new Group(name));
    }
    public ArrayList<Group> getGroups() {
        return this.groupDatabase.getAll();
    }
    public ArrayList<Person> getPersonsOfGroup(UUID groupId) {
        return this.groupDatabase.getViaUUID(groupId).getPersons(this.personDatabase.getAll());
    }
    public ArrayList<Ticket> getTicketsOfGroup(UUID groupId) {
        return this.groupDatabase.getViaUUID(groupId).getTickets(this.ticketDatabase.getAll());
    }
    public ArrayList<Person> getPersons() {
        return this.personDatabase.getAll();
    }
    public void addPerson(Person person) {
        this.personDatabase.addEntry(person);
    }
    public void addTicket(Ticket ticket) {
        this.ticketDatabase.addEntry(ticket);
    }
    public Person getPersonViaUUID(UUID id) {
        return personDatabase.getViaUUID(id);
    }
    public Person getPersonViaName(String name) {
        return personDatabase.getViaName(name);
    }
    public Ticket getTicketViaUUID(UUID id) {
        return ticketDatabase.getViaUUID(id);
    }

    public void clear() {
        this.ticketDatabase.clear();
    }
}
