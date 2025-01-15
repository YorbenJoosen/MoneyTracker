package test;

import app.config.Config;
import app.database.DatabaseFacade;
import app.group.Group;
import app.person.Person;
import app.ticket.Ticket;
import app.ticket.TicketType;
import app.ticket.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MVPIntegrationTest {
    String PERSON_ONE = "PersonA";
    String PERSON_TWO = "PersonB";
    String PERSON_THREE = "PersonC";
    Person personOne = new app.person.Person(PERSON_ONE);
    Person personTwo = new app.person.Person(PERSON_TWO);
    Person personThree = new Person(PERSON_THREE);

    String GROUP_NAME = "group";
    Group group = new Group(GROUP_NAME);

    @Before
    public void setup() throws Exception {
        DatabaseFacade database = DatabaseFacade.getInstance();
        database.addPerson(personOne);
        database.addPerson(personTwo);
        database.addPerson(personThree);

        database.addGroup(group);
        group.addPerson(personOne.getId());
        group.addPerson(personTwo.getId());
        group.addPerson(personThree.getId());
    }

    @Test
    public void t_full_test_suite() throws Exception {
        Config config = Config.getInstance();
        DatabaseFacade database = DatabaseFacade.getInstance();
        int amount = 1000;

        // Fetching persons
        ArrayList<Group> groups = database.getGroups();
        Group group = groups.get(0);
        ArrayList<Person> persons = database.getPersonsOfGroup(group.getId());
        persons.sort(Comparator.comparing(Person::getName));
        assert persons.size() == 3;

        Person personA = persons.get(0);
        Person personB = persons.get(1);
        Person personC = persons.get(2);

        // Setup
        List<Person> personList = Arrays.asList(personB, personC);
        app.ticket.Ticket ticketOne = Ticket.create_equal_list(personA,
                "Ticket",
                amount,
                personList,
                TicketType.restaurant
        );
        app.ticket.Ticket ticketTwo = Ticket.create_equal_list(personA,
                "Ticket",
                amount,
                personList,
                TicketType.airplane
        );
        database.addTicket(ticketOne);
        database.addTicket(ticketTwo);

        // Execute
        ArrayList<Transaction> finalResult = config.getTallyStrategy().reduceTransactions(database.getAllTransactions());

        // Assert
        assert !finalResult.isEmpty();
        assert finalResult.size() == 2;

        Transaction firstTransaction = finalResult.get(0);
        Transaction secondTransaction = finalResult.get(1);

        assert Objects.equals(firstTransaction.amount(), amount);
        assert Objects.equals(secondTransaction.amount(), amount);

        assert firstTransaction.lhsPerson().equals(personA);
        assert secondTransaction.lhsPerson().equals(personA);

        assert !firstTransaction.rhsPerson().equals(personA);
        assert !secondTransaction.rhsPerson().equals(personA);

        assert !firstTransaction.rhsPerson().equals(secondTransaction.rhsPerson());
    }
}
