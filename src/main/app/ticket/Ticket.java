package main.app.ticket;

import main.app.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ticket {
    private final String ticketName;
    private final TicketType ticketType;
    private final Person owner;
    private final List<Map.Entry<Person, Integer>> personPriceList;

    public Ticket(Person owner, String ticketName, TicketType ticketType, List<Map.Entry<Person, Integer>> personPriceList) {
        this.owner = owner;
        this.ticketName = ticketName;
        this.ticketType = ticketType;
        this.personPriceList = personPriceList;
    }

    public static Ticket create_equal_list(Person owner, String ticketName, Integer totalPrice, List<Person> persons, TicketType ticketType) {
        int pricePerPerson = totalPrice / persons.size();

        ArrayList<Map.Entry<Person, Integer>> personPriceList = new ArrayList<Map.Entry<Person, Integer>>() {};
        for (Person person : persons) {
            personPriceList.add(Map.entry(person, pricePerPerson));
        }

        return new Ticket(owner, ticketName, ticketType, personPriceList);
    }

    public static Ticket create_individual_list(Person owner, String ticketName, List<java.util.Map.Entry<Person, Integer>> personAndPriceList, TicketType ticketType) {
        return new Ticket(owner, ticketName, ticketType, personAndPriceList);
    }
}
