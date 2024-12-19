package main.app.database.abstractDatabase;


import main.app.ticket.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

abstract public class AbstractTicketDatabase extends AbstractDatabase{
    private final HashMap<UUID, Ticket> ticketMap = new HashMap<>();
    public void addTicket(Ticket ticket) {
        ticketMap.put(ticket.getId(), ticket);
        this.support.firePropertyChange("ticket", null, ticket);
    }

    public ArrayList<Ticket> getTickets() {
        return new ArrayList<>(ticketMap.values());
    }

    public Ticket getTicket(UUID id) {
        return ticketMap.get(id);
    }
}
