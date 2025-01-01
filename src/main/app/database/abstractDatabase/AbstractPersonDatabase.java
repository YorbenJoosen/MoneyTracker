package app.database.abstractDatabase;

import app.person.Person;

abstract public class AbstractPersonDatabase extends AbstractDatabase<Person> {
    public abstract Person getViaName(String name);
}
