package app.database.abstractDatabase;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.UUID;

abstract public class AbstractDatabase<T> {
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    protected AbstractDatabase() {}

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }
    public abstract T getViaUUID(UUID id);
    public abstract ArrayList<T> getAll();
    public abstract void addEntry(T entry);
    public abstract void removeEntry(UUID id);
    public abstract void clear();
}
