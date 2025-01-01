package app.panels;

import app.database.DatabaseFacade;
import app.group.Group;

import javax.swing.*;
import java.awt.*;

public class ShowGroups extends JPanel {
    private final DatabaseFacade databaseFacade;

    public ShowGroups(DatabaseFacade databaseFacade, JFrame frame) {
        this.databaseFacade = databaseFacade;

        // Show message and return if no groups exist
        if (databaseFacade.getGroups().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No groups available yet.", "No Groups", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create frame only if there are groups
        JFrame groupsFrame = new JFrame("Groups");
        groupsFrame.setSize(1920, 1080);
        groupsFrame.setLayout(new BorderLayout());

        // Create selection field of groups
        DefaultListModel<Group> groupListModel = new DefaultListModel<>();
        JList<Group> groupSelector = new JList<>(groupListModel);
        databaseFacade.getGroups().forEach(groupListModel::addElement);

        // Add double-click listener to navigate to group details
        groupSelector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Group selectedGroup = groupSelector.getSelectedValue();
                    // Check if a group is selected
                    if (selectedGroup != null) {
                        // Show group details of the selected group
                        showGroupDetails(selectedGroup);
                    }
                }
            }
        });
        // Add selection field to the frame
        groupsFrame.add(new JScrollPane(groupSelector), BorderLayout.CENTER);
        groupsFrame.setVisible(true);
    }

    private void showGroupDetails(Group group) {
        // Create new frame
        JFrame groupDetailsFrame = new JFrame("Group: " + group.getName());
        groupDetailsFrame.setSize(1920, 1080);

        // Create new panel
        GroupDetails groupDetailsPanel = new GroupDetails(databaseFacade, group, groupDetailsFrame);

        // Add panel to the frame
        groupDetailsFrame.add(groupDetailsPanel);
        groupDetailsFrame.setVisible(true);
    }
}
