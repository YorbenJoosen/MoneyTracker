import main.app.database.DatabaseFacade;
import main.app.ticket.Transaction;
import org.junit.Test;

import java.util.ArrayList;

public class TotalsCalculator_UTest {

    @Test
    public void t_empty_case() {
        DatabaseFacade database = DatabaseFacade.getInstance();
        ArrayList<Transaction> finalResult = database.getFinalTally();
        assert finalResult.isEmpty();
    }

    @Test
    public void t_simple_case() {
        DatabaseFacade database = DatabaseFacade.getInstance();

        // Setup

        // Execute
        ArrayList<Transaction> finalResult = database.getFinalTally();

        // Assert
    }
}
