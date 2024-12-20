package main.app.group;

import main.app.database.DatabaseFacade;
import main.app.person.Person;
import main.app.ticket.Ticket;

import java.util.ArrayList;
import java.util.UUID;

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
    public ArrayList<Person> getPersons(DatabaseFacade databaseFacade) {
        ArrayList<Person> persons = new ArrayList<>();
        for (UUID id : this.personsUUIDs) {
            persons.add(databaseFacade.getPersonViaUUID(id));
        }
        return persons;
    }
    public void addTicket(UUID id) {
        this.ticketsUUIDs.add(id);
    }
    public ArrayList<Ticket> getTickets(DatabaseFacade databaseFacade) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (UUID id : this.ticketsUUIDs) {
            tickets.add(databaseFacade.getTicketViaUUID(id));
        }
        return tickets;
    }
    public boolean checkIfPersonExists(UUID id) {
        return this.personsUUIDs.contains(id);
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
