package app;

import app.database.DatabaseFacade;
import app.view.ViewFrame;


public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }

    public Main() {}

    public void run() throws Exception {
        DatabaseFacade databaseFacade = DatabaseFacade.getInstance();
        new ViewFrame(databaseFacade);
    }
}
