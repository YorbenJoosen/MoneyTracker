package main.app.panels;

import main.app.database.DatabaseFacade;
import main.app.group.Group;
import main.app.person.Person;
import main.app.ticket.Ticket;
import main.app.ticket.TicketType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class AddIndividualTicket extends JPanel {

    private final JComboBox<Person> ownerSelector;
    private final JTextField ticketNameField;
    private final JList<Person> debtorSelector;
    private final JComboBox<TicketType> ticketTypeSelector;
    private final DatabaseFacade databaseFacade;

    private final HashMap<Person, Integer> debtorPrices = new HashMap<>();
    private final Group group;

    public AddIndividualTicket(DatabaseFacade databaseFacade, Group group) {
        this.databaseFacade = databaseFacade;
        this.group = group;

        ArrayList<Person> persons = group.getPersons(databaseFacade);

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

        // Debtors List
        JLabel debtorsLabel = new JLabel("Select Debtors:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(debtorsLabel, gbc);

        // Create selection field for all persons with multiple selection
        DefaultListModel<Person> listModel = new DefaultListModel<>();
        JList<Person> debtorSelector = new JList<>(listModel);
        debtorSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        debtorSelector.setVisibleRowCount(group.getPersonsUUIDs().size());
        // Add persons to selection field
        persons.forEach(listModel::addElement);
        this.debtorSelector = debtorSelector;
        JScrollPane debtorsScrollPane = new JScrollPane(debtorSelector);
        gbc.gridx = 1;
        add(debtorsScrollPane, gbc);

        // Assign Prices Button
        JButton assignPricesButton = new JButton("Assign Prices");
        assignPricesButton.addActionListener(e -> openPriceAssignmentDialog());
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(assignPricesButton, gbc);

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
        submitButton.addActionListener(new AddIndividualTicket.SubmitButtonListener());
        add(submitButton, gbc);
    }

    private void openPriceAssignmentDialog() {
        ArrayList<Person> selectedDebtors = new ArrayList<>(debtorSelector.getSelectedValuesList());
        if (selectedDebtors.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select debtors first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog priceDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Assign Prices", Dialog.ModalityType.APPLICATION_MODAL);
        priceDialog.setLayout(new BorderLayout()); // Set a layout manager for the dialog

        JPanel panel = new JPanel(new GridBagLayout()); // GridBagLayout for the form panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dynamically create rows for each selected debtor
        int row = 0;
        for (Person debtor : selectedDebtors) {
            JLabel label = new JLabel(debtor.getName());
            JTextField priceField = new JTextField(10);

            gbc.gridx = 0;
            gbc.gridy = row;
            panel.add(label, gbc);

            gbc.gridx = 1;
            panel.add(priceField, gbc);

            // Store price on user input
            priceField.addActionListener(e -> {
                try {
                    int price = Integer.parseInt(priceField.getText());
                    debtorPrices.put(debtor, price);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(priceDialog, "Invalid price for " + debtor.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            row++;
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        priceDialog.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the dialog

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> priceDialog.dispose());
        priceDialog.add(saveButton, BorderLayout.SOUTH); // Add the button at the bottom

        priceDialog.pack();
        priceDialog.setLocationRelativeTo(this); // Center the dialog relative to the parent
        priceDialog.setVisible(true);
    }


    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Person selectedOwner = (Person) ownerSelector.getSelectedItem();
            String ticketName = ticketNameField.getText();
            ArrayList<Person> selectedDebtors = new ArrayList<>(debtorSelector.getSelectedValuesList());
            TicketType ticketType = (TicketType) ticketTypeSelector.getSelectedItem();

            // Check that all fields have a value
            if (ticketName.isEmpty() || selectedOwner == null || ticketType == null || selectedDebtors.isEmpty()) {
                JOptionPane.showMessageDialog(AddIndividualTicket.this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check that all debtors have a price
            if (selectedDebtors.stream().anyMatch(debtor -> !debtorPrices.containsKey(debtor))) {
                JOptionPane.showMessageDialog(AddIndividualTicket.this, "Please assign prices for all selected debtors!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Ticket ticket = Ticket.create_individual_list(selectedOwner, ticketName, debtorPrices, ticketType);
                group.addTicket(ticket.getId());
                databaseFacade.addTicket(ticket);
                JOptionPane.showMessageDialog(AddIndividualTicket.this, "Ticket added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddIndividualTicket.this, "Error adding ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
