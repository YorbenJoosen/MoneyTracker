package main.app.panels;

import main.app.database.DatabaseFacade;
import main.app.person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private final DatabaseFacade databaseFacade;
    private final JFrame frame;

    public MainMenu(DatabaseFacade databaseFacade, JFrame frame) {
        this.databaseFacade = databaseFacade;
        this.frame = frame;
        setLayout(new GridLayout(3, 1));

        JButton showGroupsButton = new JButton("Show Groups");
        JButton showPersonsButton = new JButton("Show Persons");
        JButton addGroupButton = new JButton("Add Group");

        // Action listeners for buttons
        showGroupsButton.addActionListener(e -> new ShowGroups(databaseFacade, frame));

        showPersonsButton.addActionListener(e -> showPersons());

        addGroupButton.addActionListener(e -> addGroup());

        add(showGroupsButton);
        add(showPersonsButton);
        add(addGroupButton);
    }

    private void showPersons() {
        ArrayList<Person> persons = databaseFacade.getAllPersons();

        // Show message and return if no persons exist
        if (persons.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No persons available yet.", "No Persons", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are persons
        JFrame personsFrame = new JFrame("Persons");
        personsFrame.setSize(1920, 1080);
        DefaultListModel<String> personListModel = new DefaultListModel<>();

        for (Person person : persons) {
            personListModel.addElement(person.getId() + ": " + person.getName());
        }

        JList<String> personList = new JList<>(personListModel);
        personsFrame.add(new JScrollPane(personList), BorderLayout.CENTER);

        personsFrame.setVisible(true);
    }

    private void addGroup() {
        String groupName = JOptionPane.showInputDialog(this, "Enter Group Name:");

        // Check if user pressed "OK" and entered a non-empty group name
        if (groupName != null && !groupName.trim().isEmpty()) {
            // Add group to the database
            databaseFacade.addGroup(groupName);
            JOptionPane.showMessageDialog(this, "Group '" + groupName + "' added!");
        } else {
            JOptionPane.showMessageDialog(this, "No group name entered. Group not added.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

