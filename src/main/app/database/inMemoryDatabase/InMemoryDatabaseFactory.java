package main.app.database.inMemoryDatabase;

import main.app.database.abstractDatabase.AbstractDatabaseFactory;

public class InMemoryDatabaseFactory extends AbstractDatabaseFactory {

    @Override
    public InMemoryTicketDatabase getTicketDatabase() {
        return null;
    }

    @Override
    public InMemoryPersonDatabase getPersonDatabase() {
        return null;
    }
}
