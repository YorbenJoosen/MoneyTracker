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

import static java.lang.Math.abs;

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
        return this.ticketDatabase.getTickets();
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
                map(transaction -> transaction.amount()).
                reduce(0, Integer::sum);
    }

    public Integer totalOutgoingForPerson(Person person) {
        return this.getAllTransactions().
                stream().
                filter(transaction -> transaction.rhsPerson().equals(person)).
                map(transaction -> transaction.amount()).
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
        return transactions.stream().max(Comparator.comparing(transaction -> transaction.amount())).map(transaction -> transaction.rhsPerson());
    }

    public Optional<Person> personWithLargestIncoming(ArrayList<Transaction> transactions) {
        return transactions.stream().max(Comparator.comparing(transaction -> transaction.amount())).map(transaction -> transaction.lhsPerson());
    }

    public ArrayList<Transaction> calcTallyNaive(ArrayList<Transaction> transactions) throws Exception {
        // https://medium.com/@mithunmk93/algorithm-behind-splitwises-debt-simplification-feature-8ac485e97688

        if (transactions.isEmpty()) {return new ArrayList<>();} // Guard clause for empty

        // Collect all people in the input transactions and calculate the net cash flow (incoming - outgoing)
        ArrayList<Person> personList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            personList.add(transaction.lhsPerson());
            personList.add(transaction.rhsPerson());
        }

        List<Integer> netCash = personList.stream().map(this::netAmountForPerson).toList();

        // Seperate people in "givers" and "receivers"
        ArrayList<Integer> givers = new ArrayList<>(); // List of indices of Person instances (referencing personList)
        ArrayList<Integer> receivers = new ArrayList<>(); // List of indices of Person instances (referencing personList)
        int personIndex = 0;
        for (Person person : personList) {
            if (netCash.get(personIndex) < 0) {
                givers.add(personIndex);
            } else if (netCash.get(personIndex) > 0) {
                receivers.add(personIndex);
            }
            personIndex++;
        }

        // The result of this function is a series of transaction (total length shorter than input)
        ArrayList<Transaction> resultTransactions = new ArrayList<>();

        // Loop over all receivers and givers
        Integer receiverIndex = 0;
        Integer toReceive = netCash.get(receiverIndex);  // Initialize with first receiver
        for (Integer giverIndex : givers) {
            Integer toGive = netCash.get(giverIndex);

            // Check if we have more to give than to receive
            // Option A
            // Continue fetching more receivers until we satisfy condition
            while (toReceive <= toGive) {
                resultTransactions.add(new Transaction(personList.get(receiverIndex), toReceive, personList.get(giverIndex)));

                toGive -= toReceive;

                receiverIndex++;
                toReceive = netCash.get(receiverIndex);
            }

            // Option B
            toReceive -= toGive;
            resultTransactions.add(new Transaction(personList.get(receiverIndex), toGive, personList.get(giverIndex)));
        }

        // Return result!
        return resultTransactions;
    }

    public ArrayList<Transaction> getFinalTally() throws Exception {
        return this.calcTallyNaive(this.getAllTransactions());
    }
    public void addGroup(String name) {
        this.groupDatabase.addGroup(new Group(name));
    }
    public ArrayList<Group> getGroups() {
        return this.groupDatabase.getGroups();
    }
    public ArrayList<Person> getAllPersons() {
        return this.personDatabase.getPersons();
    }
    public void addPerson(Person person) {
        this.personDatabase.addPerson(person);
    }
    public void addTicket(Ticket ticket) {
        this.ticketDatabase.addTicket(ticket);
    }
    public Person getPersonViaUUID(UUID id) {
        return personDatabase.getPersonViaUUID(id);
    }
    public Person getPersonViaName(String name) {
        return personDatabase.getPersonViaName(name);
    }
    public Ticket getTicketViaUUID(UUID id) {
        return ticketDatabase.getTicketViaUUID(id);
    }

    public void clear() {
        this.ticketDatabase.clear();
    }
}
