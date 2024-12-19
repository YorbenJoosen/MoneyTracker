package main.app.person;

import java.util.UUID;

public class Person extends InputPerson {
    private final UUID id;

    public Person(String name) {
        super(name);
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
