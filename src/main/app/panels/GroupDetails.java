package main.app.panels;

import main.app.database.DatabaseFacade;
import main.app.group.Group;
import main.app.person.Person;
import main.app.ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GroupDetails extends JPanel {
    private final DatabaseFacade databaseFacade;
    private final Group group;
    private final JFrame frame;

    public GroupDetails(DatabaseFacade databaseFacade, Group group, JFrame frame) {
        this.databaseFacade = databaseFacade;
        this.frame = frame;
        this.group = group;
        setLayout(new GridLayout(5, 1));

        JButton showPersonsButton = new JButton("Show Persons");
        JButton showTicketsButton = new JButton("Show Tickets");
        JButton addTicketButton = new JButton("Add Ticket");
        JButton addPersonButton = new JButton("Add Person");
        JButton showDebtsButton = new JButton("Show Debts");

        showPersonsButton.addActionListener(createButtonActionListener("persons"));
        showTicketsButton.addActionListener(createButtonActionListener("tickets"));
        addTicketButton.addActionListener(createButtonActionListener("addTicket"));
        addPersonButton.addActionListener(createButtonActionListener("addPerson"));
        showDebtsButton.addActionListener(createButtonActionListener("debts"));

        add(showPersonsButton);
        add(showTicketsButton);
        add(addTicketButton);
        add(addPersonButton);
        add(showDebtsButton);
    }

    private ActionListener createButtonActionListener(String action) {
        return e -> {
            switch (action) {
                case "persons":
                    showPersonsOfGroup();
                    break;
                case "tickets":
                    showTickets();
                    break;
                case "addTicket":
                    addTicket();
                    break;
                case "addPerson":
                    addPerson();
                    break;
                case "debts":
                    showDebts();
                    break;
            }
        };
    }

    private void showPersonsOfGroup() {
        ArrayList<Person> persons = this.group.getPersons();
        // Show message and return if no persons exist
        if (persons.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No persons available yet.", "No Persons", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are persons
        JFrame personsFrame = new JFrame("Persons");
        personsFrame.setSize(400, 300);
        DefaultListModel<String> personListModel = new DefaultListModel<>();

        for (Person person : persons) {
            personListModel.addElement(person.getId() + ": " + person.getName());
        }

        JList<String> personList = new JList<>(personListModel);
        personsFrame.add(new JScrollPane(personList), BorderLayout.CENTER);

        personsFrame.setVisible(true);
    }

    private void showTickets() {
        ArrayList<Ticket> tickets = this.group.getTickets();
        // Show message and return if no persons exist
        if (tickets.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tickets available yet.", "No Tickets", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are persons
        JFrame ticketsFrame = new JFrame("Tickets");
        ticketsFrame.setSize(400, 300);
        DefaultListModel<String> ticketListModel = new DefaultListModel<>();

        for (Ticket ticket : tickets) {
            ticketListModel.addElement(ticket.getId() + ": " + ticket.getTicketName() + ", " + ticket.getTicketType() + ", " + ticket.getOwner());
        }

        JList<String> personList = new JList<>(ticketListModel);
        ticketsFrame.add(new JScrollPane(personList), BorderLayout.CENTER);

        ticketsFrame.setVisible(true);
    }

    private void addTicket() {
        // Implement the logic to add a ticket to the group
    }

    private void addPerson() {
        // Show input dialog and get person name
        String personName = JOptionPane.showInputDialog(frame, "Enter Person Name:");

        // Check if user pressed "OK" and entered a non-empty group name
        if (personName != null && !personName.trim().isEmpty()) {
            // Save the person to the database
            Person person = new Person(personName);
            group.addPerson(person);
            databaseFacade.addPerson(person);
            JOptionPane.showMessageDialog(frame, "Person '" + personName + "' added!");
        } else {
            // Optionally, show a message if no valid input was provided
            JOptionPane.showMessageDialog(frame, "No person name entered. Group not added.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDebts() {
        // Implement the logic to show debts of the group
    }
}
