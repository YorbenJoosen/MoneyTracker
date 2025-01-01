package app.database.inMemoryDatabase;

import app.database.abstractDatabase.AbstractPersonDatabase;
import app.person.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InMemoryPersonDatabase extends AbstractPersonDatabase {
    private final HashMap<UUID, Person> personMap = new HashMap<>();

    public InMemoryPersonDatabase() {}

    @Override
    public void addEntry(Person person) {
        personMap.put(person.getId(), person);
        this.support.firePropertyChange("person", null, person);
    }

    @Override
    public void removeEntry(Person entry) {
        personMap.remove(entry.getId());
    }

    @Override
    public void clear() {
        personMap.clear();
    }

    @Override
    public ArrayList<Person> getAll() {
        return new ArrayList<>(personMap.values());
    }

    @Override
    public Person getViaUUID(UUID id) {
        return personMap.get(id);
    }

    @Override
    public Person getViaName(String name) {
        for (Person person : personMap.values()) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
}
