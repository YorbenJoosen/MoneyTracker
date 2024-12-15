package main.app.database.abstractDatabase;

public abstract class AbstractDatabaseFactory {

    public abstract AbstractTicketDatabase getTicketDatabase();
    public abstract AbstractPersonDatabase getPersonDatabase();
}
