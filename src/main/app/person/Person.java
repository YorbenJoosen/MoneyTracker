package main.app.person;

public class Person extends InputPerson {
    private final int id;

    public Person(int id, String name) {
        super(name);
        this.id = id;
    }
}
