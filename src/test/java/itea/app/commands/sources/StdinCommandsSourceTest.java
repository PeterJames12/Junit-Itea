package itea.app.commands.sources;

import org.hamcrest.CoreMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class StdinCommandsSourceTest {

	@Test
	public void testReturnsInputString() throws Exception {
		System.setIn(new ByteArrayInputStream("word".getBytes()));
		CommandsSource commandsSource = new StdinCommandsSource();

		assertTrue(commandsSource.hasNext());
		assertThat(commandsSource.next(), CoreMatchers.containsString("word"));
	}

	@Test (expected = IllegalStateException.class)
	public void hasFalse() throws Exception {
		System.setIn(new ByteArrayInputStream("exit".getBytes()));
		CommandsSource commandsSource = new StdinCommandsSource();

		commandsSource.next();
		assertFalse(commandsSource.hasNext());
		commandsSource.next();
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void stop() throws Exception {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Producer is stopped");

		System.setIn(new ByteArrayInputStream("exit".getBytes()));
		CommandsSource commandsSource = new StdinCommandsSource();
		commandsSource.next();
		assertFalse(commandsSource.hasNext());

		commandsSource.next();
	}
}