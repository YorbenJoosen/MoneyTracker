package test;

import app.database.DatabaseFacade;
import app.person.Person;
import app.ticket.Ticket;
import app.ticket.TicketType;
import app.ticket.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TotalsCalculator_UTest {

    @Before
    public void clear_database() {
        DatabaseFacade database = DatabaseFacade.getInstance();
        database.clear();
    }

    @Test
    public void t_empty_case() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();
        ArrayList<Transaction> finalResult = database.getFinalTally();
        assert finalResult.isEmpty();
    }

    String PERSON_ONE = "Person One";
    String PERSON_TWO = "Person One";
    app.person.Person personOne = new app.person.Person(PERSON_ONE);
    app.person.Person personTwo = new app.person.Person(PERSON_TWO);

    @Test
    public void t_one_transaction_case() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();

        // Setup
        List<Person> personList = Collections.singletonList(personTwo);
        app.ticket.Ticket ticket = Ticket.create_equal_list(personOne,
                "Ticket",
                1000,
                personList,
                TicketType.restaurant
                );
        database.addTicket(ticket);

        // Execute
        ArrayList<Transaction> finalResult = database.getFinalTally();

        // Assert
        assert !finalResult.isEmpty();
    }
}
