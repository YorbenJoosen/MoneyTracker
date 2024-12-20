package main.app.ticket;

import main.app.person.Person;

public class Transaction {
    public final Person lhsPerson; // Who will receive the money
    public final Person rhsPerson; // Who will give the money
    public final Integer amount;

    public Transaction(Person lhsPerson, Integer amount, Person rhsPerson) {
        this.lhsPerson = lhsPerson;
        this.rhsPerson = rhsPerson;
        this.amount = amount;
    }

    public Person getLhsPerson() {
        return lhsPerson;
    }

    public Person getRhsPerson() {
        return rhsPerson;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "lhsPerson=" + lhsPerson +
                ", rhsPerson=" + rhsPerson +
                ", amount=" + amount +
                '}';
    }
}
