package main.app.database.abstractDatabase;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

abstract public class AbstractDatabase {
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    protected AbstractDatabase() {}

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }
}
