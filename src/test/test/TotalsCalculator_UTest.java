package test;

import main.app.database.DatabaseFacade;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.TicketType;
import main.app.ticket.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
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
    main.app.person.Person personOne = new main.app.person.Person(PERSON_ONE);
    main.app.person.Person personTwo = new main.app.person.Person(PERSON_TWO);

    @Test
    public void t_one_transaction_case() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();

        // Setup
        List<Person> personList = Collections.singletonList(personTwo);
        main.app.ticket.Ticket ticket = Ticket.create_equal_list(personOne,
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
