package app.panels;

import app.database.DatabaseFacade;
import app.group.Group;
import app.person.Person;

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

        // Create buttons
        JButton showGroupsButton = new JButton("Show Groups");
        JButton showPersonsButton = new JButton("Show Persons");
        JButton addGroupButton = new JButton("Add Group");

        // Add action listeners to buttons
        showGroupsButton.addActionListener(e -> new ShowGroups(databaseFacade, frame));
        showPersonsButton.addActionListener(e -> showPersons());
        addGroupButton.addActionListener(e -> addGroup());

        // Add buttons to panel
        add(showGroupsButton);
        add(showPersonsButton);
        add(addGroupButton);
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
        personsFrame.setSize(1920, 1080);

        DefaultListModel<Person> personListModel = new DefaultListModel<>();
        JList<Person> personList = new JList<>(personListModel);
        persons.forEach(personListModel::addElement);

        // Add context menu for deleting persons
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem deletePerson = new JMenuItem("Delete");
        contextMenu.add(deletePerson);

        // Add action listener for the delete option
        deletePerson.addActionListener(e -> {
            Person selectedPerson = personList.getSelectedValue();
            if (selectedPerson != null) {
                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(personsFrame,
                        "Are you sure you want to delete the person: " + selectedPerson.getName() + "?",
                        "Delete Person", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Remove person in person database
                    databaseFacade.removePerson(selectedPerson.getId());
                    // Remove person from all groups its in
                    for (Group group : databaseFacade.getGroups()) {
                        if (group.containsPerson(selectedPerson.getId())) {
                            group.removePerson(selectedPerson.getId());
                        }
                    }
                    // Remove person from list
                    personListModel.removeElement(selectedPerson);
                }
            }
        });

        // Show context menu on right-click
        personList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    showContextMenu(evt);
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    showContextMenu(evt);
                }
            }

            private void showContextMenu(java.awt.event.MouseEvent evt) {
                personList.setSelectedIndex(personList.locationToIndex(evt.getPoint()));
                contextMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        });

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

