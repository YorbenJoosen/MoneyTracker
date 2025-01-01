package test;

import main.app.database.DatabaseFacade;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.TicketType;
import main.app.ticket.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

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
    String PERSON_TWO = "Person Two";
    String PERSON_THREE = "Person Three";
    Person personOne = new main.app.person.Person(PERSON_ONE);
    Person personTwo = new main.app.person.Person(PERSON_TWO);
    Person personThree = new Person(PERSON_THREE);

    @Test
    public void t_one_transaction_case() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();
        Integer amount = 1000;

        // Setup
        List<Person> personList = Collections.singletonList(personTwo);
        main.app.ticket.Ticket ticket = Ticket.create_equal_list(personOne,
                "Ticket",
                amount,
                personList,
                TicketType.restaurant
                );
        database.addTicket(ticket);

        // Execute
        ArrayList<Transaction> finalResult = database.getFinalTally();

        // Assert
        assert !finalResult.isEmpty();
        Transaction firstTransaction = finalResult.get(0);
        assert Objects.equals(firstTransaction.amount(), amount);
        assert firstTransaction.lhsPerson().equals(personOne);
        assert firstTransaction.rhsPerson().equals(personTwo);
    }

    @Test
    public void t_two_to_one_transaction_case() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();
        int amount = 1000;

        // Setup
        List<Person> personList = Arrays.asList(personTwo, personThree);
        main.app.ticket.Ticket ticket = Ticket.create_equal_list(personOne,
                "Ticket",
                amount,
                personList,
                TicketType.restaurant
        );
        database.addTicket(ticket);

        // Execute
        ArrayList<Transaction> finalResult = database.getFinalTally();

        // Assert
        assert !finalResult.isEmpty();
        assert finalResult.size() == 2;

        Transaction firstTransaction = finalResult.get(0);
        Transaction secondTransaction = finalResult.get(1);

        assert Objects.equals(firstTransaction.amount(), amount / 2);
        assert Objects.equals(secondTransaction.amount(), amount / 2);

        assert firstTransaction.lhsPerson().equals(personOne);
        assert secondTransaction.lhsPerson().equals(personOne);

        assert !firstTransaction.rhsPerson().equals(personOne);
        assert !secondTransaction.rhsPerson().equals(personOne);

        assert !firstTransaction.rhsPerson().equals(secondTransaction.rhsPerson());
    }
}
