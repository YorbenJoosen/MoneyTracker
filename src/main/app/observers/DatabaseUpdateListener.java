package main.app.observers;

import main.app.database.abstractDatabase.AbstractDatabase;

public class DatabaseUpdateListener {
    public DatabaseUpdateListener(AbstractDatabase database) {
        database.addPropertyChangeListener("Update", event -> System.out.println(event.getNewValue()));
    }
}
