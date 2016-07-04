package itea.app.commands;

import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BirthdayCommandTest {

	private Command birthdayCommand;

	private OutputStream mockedSink;

	@Before
	public void setUp() throws Exception {
		mockedSink = mock(OutputStream.class);
		birthdayCommand = new BirthdayCommand(mockedSink);
	}

	@Test
	public void testCorrectCommandMatches() throws Exception {
		assertTrue(birthdayCommand.matches("birthday"));

		verifyZeroInteractions(mockedSink);
	}

	@Test
	public void testWrongCommandDoesntMatch() throws Exception {
		assertFalse(birthdayCommand.matches("banana"));

		verifyZeroInteractions(mockedSink);
	}

	@Test
	public void testShouldWriteAmountOfDay() throws Exception {
		birthdayCommand.execute();

		byte[] bytes = Integer.toString(21).getBytes();
		verify(mockedSink).write(bytes);
	}
}