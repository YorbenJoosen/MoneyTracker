package app.tally;

import app.person.Person;
import app.ticket.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class FirstFitStrategy implements TallyStrategy {
    public Integer totalIncomingForPerson(ArrayList<Transaction> transactions, Person person) {
        return transactions.
                stream().
                filter(transaction -> transaction.lhsPerson().equals(person)).
                map(Transaction::amount).
                reduce(0, Integer::sum);
    }

    public Integer totalOutgoingForPerson(ArrayList<Transaction> transactions, Person person) {
        return transactions.
                stream().
                filter(transaction -> transaction.rhsPerson().equals(person)).
                map(Transaction::amount).
                reduce(0, Integer::sum);
    }

    public Integer netAmountForPerson(ArrayList<Transaction> transactions, Person person) {
        return totalIncomingForPerson(transactions, person) - this.totalOutgoingForPerson(transactions, person);
    }

    public ArrayList<Transaction> reduceTransactions(ArrayList<Transaction> transactions) {
        // https://medium.com/@mithunmk93/algorithm-behind-splitwises-debt-simplification-feature-8ac485e97688

        if (transactions.isEmpty()) {
            return new ArrayList<>();
        } // Guard clause for empty

        // Collect all people in the input transactions and calculate the net cash flow (incoming - outgoing)
        ArrayList<Person> personList = new ArrayList<>();
        for (
                Transaction transaction : transactions) { // TODO Te veel persons toegevoegd
            if (!personList.contains(transaction.lhsPerson())) {
                personList.add(transaction.lhsPerson());
            }
            if (!personList.contains(transaction.rhsPerson())) {
                personList.add(transaction.rhsPerson());
            }
        }

        List<Integer> netCash = personList.stream().map(
                person -> this.netAmountForPerson(transactions, person)
        ).toList();

        // Seperate people in "givers" and "receivers"
        ArrayList<Integer> givers = new ArrayList<>(); // List of indices of Person instances (referencing personList)
        ArrayList<Integer> receivers = new ArrayList<>(); // List of indices of Person instances (referencing personList)

        for (int personIndex = 0; personIndex < personList.size(); personIndex++) {
            if (netCash.get(personIndex) < 0) {
                givers.add(personIndex);
            } else if (netCash.get(personIndex) > 0) {
                receivers.add(personIndex);
            }
        }

        // The result of this function is a series of transaction (total length shorter than input)
        ArrayList<Transaction> resultTransactions = new ArrayList<>();

        // Loop over all receivers and givers
        int receiverIndex = 0;
        Integer toReceive = netCash.get(receiverIndex);  // Initialize with first receiver
        for (Integer giverIndex : givers) {
            Integer toGive = netCash.get(giverIndex);

            // Check if we have more to give than to receive
            // Option A
            // Continue fetching more receivers until we satisfy condition
            while (toReceive <= toGive) {
                resultTransactions.add(new Transaction(personList.get(receivers.get(receiverIndex)), toReceive, personList.get(giverIndex)));

                toGive -= toReceive;

                receiverIndex++;
                toReceive = netCash.get(receivers.get(receiverIndex));
            }

            // Option B
            // We have enough in the current pool
            toReceive += toGive;  // Receive are positive numbers, give are negative numbers. ie. 1000 + (-1000) = 0
            resultTransactions.add(new Transaction(personList.get(receivers.get(receiverIndex)), abs(toGive), personList.get(giverIndex)));
        }

        // Return result!
        return resultTransactions;
    }
}
