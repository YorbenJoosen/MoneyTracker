package app.database.abstractDatabase;

public abstract class AbstractDatabaseFactory {

    public abstract AbstractTicketDatabase createTicketDatabase();
    public abstract AbstractPersonDatabase createPersonDatabase();
    public abstract AbstractGroupDatabase createGroupDatabase();
}
