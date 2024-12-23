package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;



// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(main.app.ticket.Ticket.class)
public class TicketConstructor_UTest {
    public TicketConstructor_UTest() {

    }

    @Before
    public void initialize() {

    }

    @Test
    public void t_create_equal() throws Exception {

//        Mockito.verify(mock_db, Mockito.times(1)).addEntry(mock_employee, mock_registerEntry);
    }
}
