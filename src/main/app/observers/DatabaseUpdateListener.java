package app.observers;

import app.database.abstractDatabase.AbstractDatabase;

public class DatabaseUpdateListener<T> {
    public DatabaseUpdateListener(AbstractDatabase<T> database) {
        database.addPropertyChangeListener("Update", event -> System.out.println(event.getNewValue()));
    }
}
