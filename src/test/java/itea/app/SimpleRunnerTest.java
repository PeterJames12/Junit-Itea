package itea.app;

import itea.app.commands.Command;
import itea.app.commands.sources.CommandsSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class SimpleRunnerTest {

	private String TEST_INPUT = "banana";
	private Command mockedCommand;
	private CommandsSource source;


	@Before
	public void setUp() throws Exception {
		mockedCommand = mock(Command.class);
		source = mock(CommandsSource.class);
	}

	@Test
	public void build() throws Exception {
		when(mockedCommand.matches(anyString())).thenReturn(true);
	}

	@Test
	public void built() throws Exception {

		Runner runner = new SimpleRunner(source, mockedCommand);
		runner.run();

		mockedCommand.execute();

		boolean matches = mockedCommand.matches(TEST_INPUT);

		InOrder inOrder = inOrder(mockedCommand);
		when(mockedCommand.matches(anyString())).thenReturn(true);
		assertFalse(matches);
		inOrder.verify(mockedCommand).execute();
	}

	@Test
	public void testExecuteSelectedCommand() throws Exception {

		when(source.next()).thenReturn(TEST_INPUT);

		final Runner runner = new SimpleRunner(source, mockedCommand);
		runner.run();

		mockedCommand.execute();
		mockedCommand.execute();
		verify(mockedCommand, atLeast(2)).execute();
	}

	@Test (expected = TerminationException.class)
	public void throwException() throws Exception {
		doThrow(TerminationException.class).when(mockedCommand).execute();
		mockedCommand.execute();
	}

	@Test
	public void testTerminatesIfSourceDried() throws Exception {

		when(source.hasNext()).thenReturn(false);

		final Runner runner = new SimpleRunner(source);
		runner.run();

		assertTrue(runner.isTerminated());
	}
}