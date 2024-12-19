package main.app.database.abstractDatabase;

import main.app.person.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

abstract public class AbstractPersonDatabase extends AbstractDatabase {
    private final HashMap<UUID, Person> personMap = new HashMap<>();
    public void addPerson(Person person) {
        personMap.put(person.getId(), person);
    }

    public ArrayList<Person> getPersons() {
        return new ArrayList<>(personMap.values());    }
    public Person getPerson(UUID id) {
        return personMap.get(id);
    }
}
