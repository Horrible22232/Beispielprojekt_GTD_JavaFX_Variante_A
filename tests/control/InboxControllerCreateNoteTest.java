package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.InboxAUI;
import exceptions.EmptyStringParameterException;
import model.Inbox;
import model.Note;

/**
 * Testet die Methode {@link InboxController#createNote(String)}.
 *
 * @author Florian
 */
public class InboxControllerCreateNoteTest {
	private InboxController inboxController;
	private Inbox inbox;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		// Initialisierung des Tests
		GtdController gtdController = new GtdController();
		inboxController = gtdController.getInboxController();
		inbox = gtdController.getGtd().getInbox();
	}

	/**
	 * Wenn Parameter 'text' null ist, wird eine NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link InboxController#createNote(String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTextNull() throws Exception {
		inboxController.createNote(null);
	}

	/**
	 * Wenn der Parameter 'text' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link InboxController#createNote(String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTextEmpty() throws Exception {
		inboxController.createNote("");
	}

	/**
	 * Nach dem Erzeugen der Notiz, soll die Liste um ein Element größer sein
	 * und das letzte Element soll den Notiztext enthalten.
	 *
	 * @throws Exception
	 *             {@link InboxController#createNote(String)}
	 */
	@Test
	public void testCreation() throws Exception {
		ArrayList<Note> inboxList = inbox.getList();
		assertEquals(0, inboxList.size());
		inboxController.createNote("Test1");
		assertEquals(1, inboxList.size());
		assertEquals("Test1", inboxList.get(0).getText());
		inboxController.createNote("Test2");
		assertEquals(2, inboxList.size());
		assertEquals("Test2", inboxList.get(1).getText());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Erzeugen und Einfügen der
	 * Notiz aufgerufen wird.
	 *
	 * @throws Exception
	 *             {@link InboxController#createNote(String)}
	 */
	@Test
	public void testRefreshInboxCalled() throws Exception {
		// Vorbereitung
		AtomicBoolean refreshInboxCalled = new AtomicBoolean(false);
		inboxController.addInboxAUI(new InboxAUI() {

			@Override
			public void refreshLastNoteConversion() {
			}

			@Override
			public void refreshInbox() {
				refreshInboxCalled.set(true);
			}
		});
		// Test
		assertFalse(refreshInboxCalled.get());
		inboxController.createNote("Test1");
		assertTrue(refreshInboxCalled.get());
	}

}
