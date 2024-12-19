package main.app.panels;

import main.app.database.DatabaseFacade;
import main.app.group.Group;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.TicketType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddTicket extends JPanel {
    private final DatabaseFacade databaseFacade;
    private final Group group;

    // Components for ticket creation
    private final JTextField ticketNameField;
    private final JComboBox<String> ticketTypeComboBox;
    private final JComboBox<String> ownerComboBox;
    private final JList<String> tagsList;

    public AddTicket(DatabaseFacade databaseFacade, Group group) {
        this.databaseFacade = databaseFacade;
        this.group = group;

        setLayout(new GridLayout(5, 2));

        // Ticket name input
        JLabel nameLabel = new JLabel("Ticket Name:");
        ticketNameField = new JTextField();
        add(nameLabel);
        add(ticketNameField);

        // Ticket type selection
        JLabel typeLabel = new JLabel("Ticket Type:");
        ticketTypeComboBox = new JComboBox<>(TicketType.values().toString());
        add(typeLabel);
        add(ticketTypeComboBox);

        // Ticket owner selection
        JLabel ownerLabel = new JLabel("Ticket Owner:");
        ArrayList<Person> persons = databaseFacade.getPersons();  // Get list of all persons
        DefaultComboBoxModel<String> ownerModel = new DefaultComboBoxModel<>();
        for (Person person : persons) {
            ownerModel.addElement(person.getName());
        }
        ownerComboBox = new JComboBox<>(ownerModel);
        add(ownerLabel);
        add(ownerComboBox);

        // Tags selection (multi-select)
        JLabel tagsLabel = new JLabel("Select Tags:");
        String[] tags = {"Urgent", "New", "VIP", "Discount", "Priority"};
        tagsList = new JList<>(tags);
        tagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane tagsScrollPane = new JScrollPane(tagsList);
        add(tagsLabel);
        add(tagsScrollPane);

        // Add button to create ticket
        JButton addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(e -> addTicket());
        add(new JLabel());  // Empty label for layout purposes
        add(addTicketButton);
    }

    private void addTicket() {
        // Get ticket details from input fields
        String ticketName = ticketNameField.getText().trim();
        String ticketType = (String) ticketTypeComboBox.getSelectedItem();
        String ownerName = (String) ownerComboBox.getSelectedItem();

        if (ticketName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ticket Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the owner by name
        Person owner = null;
        for (Person person : databaseFacade.getPersons()) {
            if (person.getName().equals(ownerName)) {
                owner = person;
                break;
            }
        }

        if (owner == null) {
            JOptionPane.showMessageDialog(this, "Owner not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get selected tags
        ArrayList<String> selectedTags = tagsList.getSelectedValuesList();

        // Create the new ticket
        Ticket newTicket = new Ticket(ticketName, ticketType, owner, selectedTags);

        // Add the ticket to the group
        group.addTicket(newTicket);
        databaseFacade.addTicket(newTicket);

        JOptionPane.showMessageDialog(this, "Ticket '" + ticketName + "' added successfully!");
    }
}
