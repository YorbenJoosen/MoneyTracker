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
    public Person getPersonViaUUID(UUID id) {
        return personMap.get(id);
    }
    public Person getPersonViaName(String name) {
        for (Person person : personMap.values()) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
}
