package main.app.ticket;

import main.app.person.Person;

import java.util.Currency;
import java.util.List;
import java.util.Map;

public class Ticket {
    private final String ticketName;
    private final Person owner;
    private final List<Map.Entry<Person, Currency>> personPriceList;

    public Ticket(Person owner, String ticketName, List<Map.Entry<Person, Currency>> personPriceList) {
        this.owner = owner;
        this.ticketName = ticketName;
        this.personPriceList = personPriceList;
    }

    public static Ticket create_equal_all(Person owner, String ticketName, Currency totalPrice, TicketType ticketType);
    public static Ticket create_equal_list(Person owner, String ticketName, Currency totalPrice, List<Person> persons, TicketType ticketType);

    public static Ticket create_individual_list(Person owner, String ticketName, List<java.util.Map.Entry<Person, Currency>> personAndPriceList, TicketType ticketType) {
        return new Ticket(owner, ticketName, personAndPriceList);
    }
}
