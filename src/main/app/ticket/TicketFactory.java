package main.app.ticket;

import main.app.person.Person;

import java.util.Currency;
import java.util.List;

public class TicketFactory {

    Ticket create_equal_all(Person owner, Currency totalPrice);
    Ticket create_equal_list(Person owner, Currency totalPrice, List<Person> persons);
    Ticket create_individual_list(Person owner, List<java.util.Map.Entry<Person, Currency>> personAndPriceList);
}
