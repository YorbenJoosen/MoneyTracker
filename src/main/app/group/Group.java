package main.app.group;

import main.app.person.Person;
import main.app.ticket.Ticket;

import java.util.ArrayList;
import java.util.UUID;

public class Group {
    private String name;
    private final UUID id;
    private final ArrayList<Person> persons = new ArrayList<>();
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    public Group(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPerson(Person person) {
        this.persons.add(person);
    }
    public void removePerson(Person person) {
        this.persons.remove(person);
    }
    public ArrayList<Person> getPersons() {
        return this.persons;
    }
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
    }
    public ArrayList<Ticket> getTickets() {
        return this.tickets;
    }
}
