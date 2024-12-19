package main.app.view;

import main.app.database.DatabaseFacade;
import main.app.panels.MainMenu;

import javax.swing.*;
import java.awt.*;

public class ViewFrame {

    public ViewFrame(DatabaseFacade databaseFacade) {
        JFrame frame = new JFrame("Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create the main panel for navigation
        MainMenu mainMenuPanel = new MainMenu(databaseFacade, frame);

        // Add the main menu panel to the frame
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
