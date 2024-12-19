package main.app.ticket;

import main.app.person.Person;

import java.util.*;

public class Ticket {
    private final String ticketName;
    private final TicketType ticketType;
    private final Person owner;
    private final UUID id;

    // All transactions in a ticket form a Weighted Graph
    private final List<Transaction> personPriceList;

    public Ticket(Person owner, String ticketName, TicketType ticketType, List<Transaction> personPriceList) {
        this.owner = owner;
        this.ticketName = ticketName;
        this.ticketType = ticketType;
        this.personPriceList = personPriceList;
        this.id = UUID.randomUUID();
    }

    public static Ticket create_equal_list(Person owner, String ticketName, Integer totalPrice, List<Person> persons, TicketType ticketType) {
        int pricePerPerson = totalPrice / persons.size();

        ArrayList<Transaction> personPriceList = new ArrayList<>();
        for (Person person : persons) {
            personPriceList.add(new Transaction(owner, pricePerPerson, person));
        }

        return new Ticket(owner, ticketName, ticketType, personPriceList);
    }

    public static Ticket create_individual_list(Person owner, String ticketName, HashMap<Person, Integer> personAndPriceList, TicketType ticketType) {

        ArrayList<Transaction> personPriceList = new ArrayList<>();
        for (Map.Entry<Person, Integer> entry : personAndPriceList.entrySet()) {
            personPriceList.add(new Transaction(owner, entry.getValue(), entry.getKey()));
        }

        return new Ticket(owner, ticketName, ticketType, personPriceList);
    }

    public String getTicketName() {
        return ticketName;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Person getOwner() {
        return owner;
    }

    public List<Transaction> getPersonPriceList() {
        return personPriceList;
    }

    public UUID getId() {
        return id;
    }
}
