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

import static java.lang.Math.abs;

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

    public Integer totalIncomingForPerson(Person person) {
        return this.getAllTransactions().
                stream().
                filter(transaction -> transaction.lhsPerson().equals(person)).
                map(Transaction::amount).
                reduce(0, Integer::sum);
    }

    public Integer totalOutgoingForPerson(Person person) {
        return this.getAllTransactions().
                stream().
                filter(transaction -> transaction.rhsPerson().equals(person)).
                map(Transaction::amount).
                reduce(0, Integer::sum);
    }

    public Integer netAmountForPerson(Person person) {
        return totalIncomingForPerson(person) - this.totalOutgoingForPerson(person);
    }

    public Integer largestOutgoing(ArrayList<Transaction> transactions) {
        return transactions.stream().map(Transaction::amount).max(Integer::compareTo).orElse(0);
    }

    public Integer largestIncoming(ArrayList<Transaction> transactions) {
        return transactions.stream().map(Transaction::amount).min(Integer::compareTo).orElse(0);
    }

    public Optional<Person> personWithLargestOutgoing(ArrayList<Transaction> transactions) {
        return transactions.stream().max(Comparator.comparing(Transaction::amount)).map(Transaction::rhsPerson);
    }

    public Optional<Person> personWithLargestIncoming(ArrayList<Transaction> transactions) {
        return transactions.stream().max(Comparator.comparing(Transaction::amount)).map(Transaction::lhsPerson);
    }

    public ArrayList<Transaction> calcTallyNaive(ArrayList<Transaction> transactions) throws Exception {
        // https://medium.com/@mithunmk93/algorithm-behind-splitwises-debt-simplification-feature-8ac485e97688

        if (transactions.isEmpty()) {return new ArrayList<>();} // Guard clause for empty

        // Collect all people in the input transactions and calculate the net cash flow (incoming - outgoing)
        ArrayList<Person> personList = new ArrayList<>();
        for (Transaction transaction : transactions) { // TODO Te veel persons toegevoegd
            if (!personList.contains(transaction.lhsPerson())) {personList.add(transaction.lhsPerson());}
            if (!personList.contains(transaction.rhsPerson())) {personList.add(transaction.rhsPerson());}
        }

        List<Integer> netCash = personList.stream().map(this::netAmountForPerson).toList();

        // Seperate people in "givers" and "receivers"
        ArrayList<Integer> givers = new ArrayList<>(); // List of indices of Person instances (referencing personList)
        ArrayList<Integer> receivers = new ArrayList<>(); // List of indices of Person instances (referencing personList)

        for (int personIndex = 0; personIndex < personList.size(); personIndex++) {
            if (netCash.get(personIndex) < 0) {
                givers.add(personIndex);
            } else if (netCash.get(personIndex) > 0) {
                receivers.add(personIndex);
            }
        }

        // The result of this function is a series of transaction (total length shorter than input)
        ArrayList<Transaction> resultTransactions = new ArrayList<>();

        // Loop over all receivers and givers
        int receiverIndex = 0;
        Integer toReceive = netCash.get(receiverIndex);  // Initialize with first receiver
        for (Integer giverIndex : givers) {
            Integer toGive = netCash.get(giverIndex);

            // Check if we have more to give than to receive
            // Option A
            // Continue fetching more receivers until we satisfy condition
            while (toReceive <= toGive) {
                resultTransactions.add(new Transaction(personList.get(receivers.get(receiverIndex)), toReceive, personList.get(giverIndex)));

                toGive -= toReceive;

                receiverIndex++;
                toReceive = netCash.get(receivers.get(receiverIndex));
            }

            // Option B
            // We have enough in the current pool
            toReceive += toGive;  // Receive are positive numbers, give are negative numbers. ie. 1000 + (-1000) = 0
            resultTransactions.add(new Transaction(personList.get(receivers.get(receiverIndex)), abs(toGive), personList.get(giverIndex)));
        }

        // Return result!
        return resultTransactions;
    }

    public ArrayList<Transaction> getFinalTally() throws Exception {
        return this.calcTallyNaive(this.getAllTransactions());
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
