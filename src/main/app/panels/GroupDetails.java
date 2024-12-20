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
        setLayout(new GridLayout(3, 2));

        // Create buttons
        JButton showPersonsButton = new JButton("Show Persons");
        JButton showTicketsButton = new JButton("Show Tickets");
        JButton addEqualTicketButton = new JButton("Add Equal List Ticket");
        JButton addIndividualTicketButton = new JButton("Add Individual List Ticket");
        JButton addPersonButton = new JButton("Add Person");
        JButton showDebtsButton = new JButton("Show Debts");

        // Add listeners for the buttons
        showPersonsButton.addActionListener(createButtonActionListener("Show Persons"));
        showTicketsButton.addActionListener(createButtonActionListener("Show Tickets"));
        addEqualTicketButton.addActionListener(createButtonActionListener("Add Equal List Ticket"));
        addIndividualTicketButton.addActionListener(createButtonActionListener("Add Individual List Ticket"));
        addPersonButton.addActionListener(createButtonActionListener("Add Person"));
        showDebtsButton.addActionListener(createButtonActionListener("Show Debts"));

        // Add buttons to the panel
        add(showPersonsButton);
        add(showTicketsButton);
        add(addEqualTicketButton);
        add(addIndividualTicketButton);
        add(addPersonButton);
        add(showDebtsButton);
    }

    private ActionListener createButtonActionListener(String action) {
        return e -> {
            switch (action) {
                case "Show Persons":
                    showPersons();
                    break;
                case "Show Tickets":
                    showTickets();
                    break;
                case "Add Equal List Ticket":
                    addEqualListTicket();
                    break;
                case "Add Individual List Ticket":
                    addIndividualListTicket();
                    break;
                case "Add Person":
                    addPerson();
                    break;
                case "Show Debts":
                    showDebts();
                    break;
            }
        };
    }

    private void showPersons() {
        ArrayList<Person> persons = this.group.getPersons(this.databaseFacade);

        // Show message and return if no persons exist
        if (persons.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No persons available yet.", "No Persons", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are persons
        JFrame personsFrame = new JFrame("Persons");
        personsFrame.setSize(1920, 1080);

        // Create selection field of persons
        DefaultListModel<Person> personListModel = new DefaultListModel<>();
        JList<Person> personList = new JList<>(personListModel);
        persons.forEach(personListModel::addElement);

        // Add selection field to frame
        personsFrame.add(new JScrollPane(personList));
        personsFrame.setVisible(true);
    }

    private void showTickets() {
        ArrayList<Ticket> tickets = this.group.getTickets(databaseFacade);

        // Show message and return if no tickets exist
        if (tickets.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tickets available yet.", "No Tickets", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are tickets
        JFrame ticketsFrame = new JFrame("Tickets");
        ticketsFrame.setSize(1920, 1080);

        // Create selection field of tickets
        DefaultListModel<Ticket> ticketListModel = new DefaultListModel<>();
        JList<Ticket> ticketList = new JList<>(ticketListModel);
        tickets.forEach(ticketListModel::addElement);

        // Add selection field to the frame
        ticketsFrame.add(new JScrollPane(ticketList));
        ticketsFrame.setVisible(true);
    }

    private void addIndividualListTicket() {
        // Create new frame
        JFrame addTicketFrame = new JFrame("Add Individual List Ticket");
        addTicketFrame.setSize(1920, 1080);

        // Create new panel
        AddIndividualTicket addTicketPanel = new AddIndividualTicket(databaseFacade, group);

        // Add panel to the frame
        addTicketFrame.add(addTicketPanel);
        addTicketFrame.setVisible(true);
    }
    private void addEqualListTicket() {
        // Create new frame
        JFrame addTicketFrame = new JFrame("Add Equal List Ticket");
        addTicketFrame.setSize(1920, 1080);

        // Create new panel
        AddEqualTicket addEqualTicketPanel = new AddEqualTicket(databaseFacade, group);

        // Add panel to the frame
        addTicketFrame.add(addEqualTicketPanel);
        addTicketFrame.setVisible(true);
    }

    private void addPerson() {
        // Show input dialog and get person name
        String personName = JOptionPane.showInputDialog(frame, "Enter Person Name:");

        // Check if user pressed "OK" and entered a non-empty person name
        if (personName != null && !personName.trim().isEmpty()) {
            // Check if person exists in the database
            if (databaseFacade.getPersonViaName(personName) != null) {
                // Get the person from the database
                Person person = databaseFacade.getPersonViaName(personName);
                // Check if person is already in the group
                if (group.checkIfPersonExists(person.getId())) {
                    // Warning message that person is already in the group
                    JOptionPane.showMessageDialog(frame, "Person is already in the group.", "Person is a member", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    // Person exists in database so only need to add to the group
                    group.addPerson(person.getId());
                    // Success message
                    JOptionPane.showMessageDialog(frame, "Person successful added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            // If person doesn't exist in database, create it and add it to the group
            Person person = new Person(personName);
            databaseFacade.addPerson(person);
            group.addPerson(person.getId());
            // Success message
            JOptionPane.showMessageDialog(frame, "Person successful added.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Invalid input message
            JOptionPane.showMessageDialog(frame, "No person name entered. Person not added.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDebts() {
        // Implement the logic to show debts of the group
    }
}
