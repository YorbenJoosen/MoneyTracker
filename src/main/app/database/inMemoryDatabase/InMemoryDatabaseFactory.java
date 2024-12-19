package main.app.database.inMemoryDatabase;

import main.app.database.abstractDatabase.AbstractDatabaseFactory;

public class InMemoryDatabaseFactory extends AbstractDatabaseFactory {

    @Override
    public InMemoryTicketDatabase createTicketDatabase() {
        return new InMemoryTicketDatabase();
    }

    @Override
    public InMemoryPersonDatabase createPersonDatabase() {
        return new InMemoryPersonDatabase();
    }

    @Override
    public InMemoryGroupDatabase createGroupDatabase() {return new InMemoryGroupDatabase();}

}
