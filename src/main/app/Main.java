package app;

import app.database.DatabaseFacade;
import app.view.ViewFrame;


public class Main {
    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    public Main() {}

    public void run() {
        DatabaseFacade databaseFacade = DatabaseFacade.getInstance();
        new ViewFrame(databaseFacade);
    }
}
