package main.app.ticket;

import main.app.person.Person;

import java.util.Currency;
import java.util.List;
import java.util.Map;

public class Ticket {
    private final int ticketId;
    private final String ticketName;
    private final List<Map.Entry<Person, Currency>> personPriceList;

    public Ticket(int ticketId, String ticketName, List<Map.Entry<Person, Currency>> personPriceList) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.personPriceList = personPriceList;
    }
}
