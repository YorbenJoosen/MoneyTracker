package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import register_entry.RegisterEntry;

// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(RegistrationController.class)
public class TicketConstructor_UTest {
    public TicketConstructor_UTest() {

    }

    @Before
    public void initialize() {

    }

    @Test
    public void t_checkIn() throws Exception {
        // Create mock objects for database and employee
//        Database mock_db = Mockito.mock(Database.class);
//        Employee mock_employee = Mockito.mock(Employee.class);
//
//        // Make sure the constructor of RegisterEntry is mocked too
//        RegisterEntry mock_registerEntry = Mockito.mock(RegisterEntry.class);
//        Mockito.when(mock_registerEntry.isCheckedIn()).thenReturn(true);
//        PowerMockito.whenNew(RegisterEntry.class).withArguments(true).thenReturn(mock_registerEntry);

        /*
            1. "new RegisterEntry" will return a mock object, carried out by 3 lines above
            2. The mocked database will be called, with method "addEntry"
            3. Verify that the method "addEntry" got called, with the right arguments: (mock_employee, mock_registerEntry)
         */
//        Controller controllerUnderTest = new RegistrationController(mock_db);
//        controllerUnderTest.checkIn(mock_employee);
//        Mockito.verify(mock_db, Mockito.times(1)).addEntry(mock_employee, mock_registerEntry);
    }
}
