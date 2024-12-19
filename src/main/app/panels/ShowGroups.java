package main.app.panels;

import main.app.database.DatabaseFacade;
import main.app.group.Group;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class ShowGroups extends JPanel {
    private final DatabaseFacade databaseFacade;

    public ShowGroups(DatabaseFacade databaseFacade, JFrame frame) {
        this.databaseFacade = databaseFacade;
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

        GroupDetails groupDetailsPanel = new GroupDetails(databaseFacade, group, groupDetailsFrame);
        groupDetailsFrame.add(groupDetailsPanel);
        groupDetailsFrame.setVisible(true);
    }
}
