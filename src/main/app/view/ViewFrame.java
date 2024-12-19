package main.app.view;

import main.app.database.DatabaseFacade;
import main.app.group.Group;
import main.app.person.Person;
import main.app.ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class ViewFrame {
    private final JFrame frame;
    private final DatabaseFacade databaseFacade;

    public ViewFrame(DatabaseFacade databaseFacade) {
        this.databaseFacade = databaseFacade;
        frame = new JFrame("Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        // Buttons for home page
        JButton showGroupsButton = new JButton("Show Groups");
        JButton showPersonsButton = new JButton("Show Persons");
        JButton addGroupButton = new JButton("Add Group");

        // Add action listeners using lambdas
        showGroupsButton.addActionListener(e -> showGroups());
        showPersonsButton.addActionListener(e -> showPersons());
        addGroupButton.addActionListener(e -> addGroup());

        // Add buttons to the frame
        frame.add(showGroupsButton);
        frame.add(showPersonsButton);
        frame.add(addGroupButton);

        frame.setVisible(true);
    }

    private void showGroups() {
        ArrayList<Group> groups = databaseFacade.getGroups();

        // Show message and return if no groups exist
        if (groups.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No groups available yet.", "No Groups", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are groups
        JFrame groupsFrame = new JFrame("Groups");
        groupsFrame.setSize(400, 300);
        groupsFrame.setLayout(new BorderLayout());
        DefaultListModel<String> groupListModel = new DefaultListModel<>();

        for (Group group : groups) {
            groupListModel.addElement(group.getId() + ": " + group.getName());
        }

        JList<String> groupList = getStringJList(groupListModel);

        groupsFrame.add(new JScrollPane(groupList), BorderLayout.CENTER);
        groupsFrame.setVisible(true);
    }

    private JList<String> getStringJList(DefaultListModel<String> groupListModel) {
        JList<String> groupList = new JList<>(groupListModel);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add double-click listener to navigate to group details
        groupList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selectedGroup = groupList.getSelectedValue();
                    if (selectedGroup != null) {
                        // Extract group ID from the string (before the ":")
                        String groupId = selectedGroup.split(":")[0].trim();
                        showGroupDetails(UUID.fromString(groupId)); // Pass the extracted ID
                    }
                }
            }
        });
        return groupList;
    }


    private void showGroupDetails(UUID groupId) {
        Group group = databaseFacade.getGroup(groupId);
        JFrame groupDetailsFrame = new JFrame("Group: " + group.getName());
        groupDetailsFrame.setSize(400, 400);
        groupDetailsFrame.setLayout(new GridLayout(5, 1));

        // Add buttons for various actions using lambdas
        JButton showPersonsButton = new JButton("Show Persons");
        showPersonsButton.addActionListener(e -> showPersonsOfGroup(groupId));

        JButton showTicketsButton = new JButton("Show Tickets");
        showTicketsButton.addActionListener(e -> showTickets(groupId));

        JButton addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(e -> addTicket(groupId));

        JButton addPersonButton = new JButton("Add Person");
        addPersonButton.addActionListener(e -> addPerson(groupId));

        JButton showDebtsButton = new JButton("Show Debts");
        showDebtsButton.addActionListener(e -> showDebts(groupId));

        groupDetailsFrame.add(showPersonsButton);
        groupDetailsFrame.add(showTicketsButton);
        groupDetailsFrame.add(addTicketButton);
        groupDetailsFrame.add(addPersonButton);
        groupDetailsFrame.add(showDebtsButton);

        groupDetailsFrame.setVisible(true);
    }

    private void showPersonsOfGroup(UUID groupId) {
        Group group = databaseFacade.getGroup(groupId);
        JFrame personsFrame = new JFrame("Group: " + group.getName() + " Persons");
        personsFrame.setSize(400, 300);

        DefaultListModel<String> personListModel = new DefaultListModel<>();
        ArrayList<Person> persons = group.getPersons();
        for (Person person : persons) {
            personListModel.addElement(person.getId() + ": " + person.getName());
        }
        JList<String> personList = new JList<>(personListModel);
        personsFrame.add(new JScrollPane(personList), BorderLayout.CENTER);
        personsFrame.setVisible(true);
    }

    private void showTickets(UUID groupId) {
        Group group = databaseFacade.getGroup(groupId);
        JFrame ticketsFrame = new JFrame("Group: " + group.getName() + " Tickets");
        ticketsFrame.setSize(400, 300);

        DefaultListModel<String> ticketListModel = new DefaultListModel<>();
        ArrayList<Ticket> tickets = group.getTickets();
        for (Ticket ticket : tickets) {
            ticketListModel.addElement(ticket.getId() + ": " + ticket.getTicketName() + ", " + ticket.getTicketType() + ", " + ticket.getOwner());
        }
        JList<String> ticketList = new JList<>(ticketListModel);
        ticketsFrame.add(new JScrollPane(ticketList), BorderLayout.CENTER);
        ticketsFrame.setVisible(true);
    }

    private void addTicket(UUID groupId) {
        // Implementation for adding a ticket goes here
        JOptionPane.showMessageDialog(frame, "Adding a ticket for group ID: " + groupId);
    }

    private void addPerson(UUID groupId) {
        // Show input dialog and get group name
        String personName = JOptionPane.showInputDialog(frame, "Enter Person Name:");
        Group group = databaseFacade.getGroup(groupId);

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

    private void showDebts(UUID groupId) {
        // Implementation for showing debts goes here
        JOptionPane.showMessageDialog(frame, "Showing debts for group ID: " + groupId);
    }

    private void showPersons() {
        ArrayList<Person> persons = databaseFacade.getPersons();

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


    private void addGroup() {
        // Show input dialog and get group name
        String groupName = JOptionPane.showInputDialog(frame, "Enter Group Name:");

        // Check if user pressed "OK" and entered a non-empty group name
        if (groupName != null && !groupName.trim().isEmpty()) {
            // Save the group to the database
            databaseFacade.addGroup(groupName);
            JOptionPane.showMessageDialog(frame, "Group '" + groupName + "' added!");
        } else {
            // Optionally, show a message if no valid input was provided
            JOptionPane.showMessageDialog(frame, "No group name entered. Group not added.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
