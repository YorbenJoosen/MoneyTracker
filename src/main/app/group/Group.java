package app.group;

import app.person.Person;
import app.ticket.Ticket;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class Group {
    private String name;
    private final UUID id;
    private final ArrayList<UUID> personsUUIDs = new ArrayList<>();
    private final ArrayList<UUID> ticketsUUIDs = new ArrayList<>();

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
    public void addPerson(UUID id) {
        this.personsUUIDs.add(id);
    }
    public ArrayList<UUID> getPersonsUUIDs() {
        return this.personsUUIDs;
    }

    public ArrayList<Person> getPersons(ArrayList<Person> persons) {
        return persons.stream().filter(person -> personsUUIDs.contains(person.getId())).collect(Collectors.toCollection(ArrayList::new));
    }
    public void removePerson(UUID id) {
        this.personsUUIDs.remove(id);
    }
    public boolean containsPerson(UUID id) {
        return this.personsUUIDs.contains(id);
    }

    public void addTicket(UUID id) {
        this.ticketsUUIDs.add(id);
    }

    public ArrayList<Ticket> getTickets(ArrayList<Ticket> tickets) {
        return tickets.stream().filter(ticket -> ticketsUUIDs.contains(ticket.getId())).collect(Collectors.toCollection(ArrayList::new));

    }
    public void removeTicket(UUID id) {
        this.ticketsUUIDs.remove(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UUID> getTicketsUUIDs() {
        return ticketsUUIDs;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
