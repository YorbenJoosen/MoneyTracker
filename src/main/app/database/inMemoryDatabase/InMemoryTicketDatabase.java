package app.database.inMemoryDatabase;

import app.database.abstractDatabase.AbstractTicketDatabase;
import app.ticket.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InMemoryTicketDatabase extends AbstractTicketDatabase {
    private final HashMap<UUID, Ticket> ticketMap = new HashMap<>();

    public InMemoryTicketDatabase() {}

    @Override
    public void addEntry(Ticket ticket) {
        ticketMap.put(ticket.getId(), ticket);
        this.support.firePropertyChange("Update", null, ticket);
    }

    @Override
    public void removeEntry(UUID ticketId) {
        ticketMap.remove(ticketId);
    }

    @Override
    public ArrayList<Ticket> getAll() {
        return new ArrayList<>(ticketMap.values());
    }

    @Override
    public Ticket getViaUUID(UUID id) {
        return ticketMap.get(id);
    }

    @Override
    public void clear() {
        this.ticketMap.clear();
    }
}
