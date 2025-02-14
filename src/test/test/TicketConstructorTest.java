package test;

import app.person.Person;
import app.ticket.Ticket;
import app.ticket.TicketType;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


// Run with PowerMock, an extended version of Mockito
//@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
//@PrepareForTest(main.app.ticket.Ticket.class)
public class TicketConstructorTest {
    String OWNER_NAME = "TicketConstructor_Owner";
    Person owner = new Person(OWNER_NAME);

    String PERSON_NAME_ONE = "TicketConstructor_Person_one";
    String PERSON_NAME_TWO = "TicketConstructor_Person_two";
    Person personOne = new Person(PERSON_NAME_ONE);
    Person personTwo = new Person(PERSON_NAME_TWO);

    TicketType ticketType = TicketType.restaurant;

    @Test
    public void t_create_equal() throws Exception {
        String ticketName = "TicketConstructor_create_equal";
        List<Person> personList = Arrays.asList(personOne, personTwo);
        Integer totalPrice = 1000;

        Ticket ticket = Ticket.create_equal_list(owner, ticketName, totalPrice, personList, ticketType);

        assert ticket.getPriceOfPerson(personOne).equals(500);
        assert ticket.getPriceOfPerson(personTwo).equals(500);
    }

    @Test
    public void t_create_individual() throws Exception {
        String ticketName = "TicketConstructor_create_individual";
        Integer personOnePrice = 3000;
        Integer personTwoPrice = 7000;

        HashMap<Person, Integer> personAndPriceList = new HashMap<>();
        personAndPriceList.put(personOne, personOnePrice);
        personAndPriceList.put(personTwo, personTwoPrice);

        Ticket ticket = Ticket.create_individual_list(owner, ticketName, personAndPriceList, ticketType);

        assert ticket.getPriceOfPerson(personOne).equals(personOnePrice);
        assert ticket.getPriceOfPerson(personTwo).equals(personTwoPrice);
    }

    @Test
    public void t_internal_transactions_verification() throws Exception {

    }
}
