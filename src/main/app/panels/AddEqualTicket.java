package app.panels;

import app.database.DatabaseFacade;
import app.group.Group;
import app.person.Person;
import app.ticket.Ticket;
import app.ticket.TicketType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddEqualTicket extends JPanel {

    private final JComboBox<Person> ownerSelector;
    private final JTextField ticketNameField;
    private final JTextField ticketPriceField;
    private final JList<Person> debtorSelector;
    private final JComboBox<TicketType> ticketTypeSelector;
    private final DatabaseFacade databaseFacade;
    private final Group group;

    public AddEqualTicket(DatabaseFacade databaseFacade, Group group) {
        this.databaseFacade = databaseFacade;
        this.group = group;
        ArrayList<Person> persons = databaseFacade.getPersonsOfGroup(group.getId());

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Owner Selector
        JLabel personLabel = new JLabel("Select Person:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(personLabel, gbc);

        // Create selection field for all persons
        JComboBox<Person> ownerSelector = new JComboBox<>();
        // Add persons to the selection field
        persons.forEach(ownerSelector::addItem);
        this.ownerSelector = ownerSelector;
        gbc.gridx = 1;
        add(ownerSelector, gbc);

        // Ticket Name Field
        JLabel nameLabel = new JLabel("Ticket Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        ticketNameField = new JTextField(20);
        gbc.gridx = 1;
        add(ticketNameField, gbc);

        // Ticket Price Field
        JLabel priceLabel = new JLabel("Ticket Price:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(priceLabel, gbc);

        ticketPriceField = new JTextField(20);
        gbc.gridx = 1;
        add(ticketPriceField, gbc);

        // Debtors List
        JLabel debtorsLabel = new JLabel("Select Debtors:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(debtorsLabel, gbc);

        // Create selection field for all persons with multiple selection
        DefaultListModel<Person> listModel = new DefaultListModel<>();
        JList<Person> debtorSelector = new JList<>(listModel);
        debtorSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        debtorSelector.setVisibleRowCount(group.getPersonsUUIDs().size());
        // Add persons to selection field
        persons.forEach(listModel::addElement);
        this.debtorSelector = debtorSelector;
        // Create new pane with the selection field
        JScrollPane debtorsScrollPane = new JScrollPane(debtorSelector);
        gbc.gridx = 1;
        add(debtorsScrollPane, gbc);

        // Ticket Type Selector
        JLabel ticketTypeLabel = new JLabel("Ticket Type:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(ticketTypeLabel, gbc);

        // Create selection field for all ticket types
        JComboBox<TicketType> ticketTypeSelector = new JComboBox<>();

        // Add all ticket types to the selection field
        for (TicketType type : TicketType.values()) {
            ticketTypeSelector.addItem(type);
        }

        this.ticketTypeSelector = ticketTypeSelector;
        gbc.gridx = 1;
        add(ticketTypeSelector, gbc);

        // Submit Button
        JButton submitButton = new JButton("Add Ticket");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton, gbc);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected/entered values
            Person selectedOwner = (Person) ownerSelector.getSelectedItem();
            String ticketName = ticketNameField.getText();
            String ticketPrice = ticketPriceField.getText();
            ArrayList<Person> selectedDebtors = new ArrayList<>(debtorSelector.getSelectedValuesList());
            TicketType ticketType = (TicketType) ticketTypeSelector.getSelectedItem();

            // Check if every field got a value
            if (ticketName.isEmpty() || ticketPrice.isEmpty() || selectedOwner == null || ticketType == null || selectedDebtors.isEmpty()) {
                JOptionPane.showMessageDialog(AddEqualTicket.this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // Create equal list ticket and save it to the database and to the group
                Ticket ticket = Ticket.create_equal_list(selectedOwner, ticketName, Integer.valueOf(ticketPrice), selectedDebtors, ticketType);
                group.addTicket(ticket.getId());
                databaseFacade.addTicket(ticket);
                JOptionPane.showMessageDialog(AddEqualTicket.this, "Ticket added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddEqualTicket.this, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

