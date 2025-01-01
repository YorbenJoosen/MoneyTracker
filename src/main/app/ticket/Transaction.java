package app.ticket;

import app.person.Person;

/**
 * @param lhsPerson Who will receive the money
 * @param rhsPerson Who will give the money
 */
public record Transaction(Person lhsPerson, Integer amount, Person rhsPerson) {

    @Override
    public String toString() {
        return "Transaction{" +
                "lhsPerson=" + lhsPerson +
                ", rhsPerson=" + rhsPerson +
                ", amount=" + amount +
                '}';
    }
}
