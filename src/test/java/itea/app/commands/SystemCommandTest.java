package itea.app.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.OutputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;


public class SystemCommandTest {

    private Command systemCommand;

    private OutputStream mockedSink;

    @Before
    public void setUp() throws Exception {
        mockedSink = Mockito.mock(OutputStream.class);
        systemCommand = new SystemCommand(mockedSink);
    }

    @Test
    public void testCorrectCommandMatches() throws Exception {
        assertTrue(systemCommand.matches("system"));
        verifyZeroInteractions(mockedSink);
    }

    @Test
    public void testWrongCommandDoesntMatch() throws Exception {
        assertFalse(systemCommand.matches("banana"));
    }

    @Test
    public void testShouldPrintCurrentOS() throws Exception {
        systemCommand.execute();
        verify(mockedSink).write("Linux".getBytes());
    }

    @Test
    public void severalPossibleArg() throws Exception {
        assertTrue(systemCommand.matches("system"));
        assertFalse(systemCommand.matches("birthday"));
    }
}