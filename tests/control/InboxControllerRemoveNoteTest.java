package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.InboxAUI;
import exceptions.ObjectNotInGtdException;
import model.Inbox;
import model.Note;

/**
 * Testet die Methode {@link InboxController#removeNote(Note)}.
 *
 * @author Florian
 */
public class InboxControllerRemoveNoteTest {
	private static final String TEXT = "Note";
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
	 * Wenn der Parameter 'note' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link InboxController#removeNote(Note)}
	 */
	@Test(expected = NullPointerException.class)
	public void testNoteNull() throws Exception {
		inboxController.removeNote(null);
	}

	/**
	 * Wenn das Objekt 'note' nicht in Gtd(Inbox) enthalten ist, wird eine
	 * ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link InboxController#removeNote(Note)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testNoteNotInGtd() throws Exception {
		// Vorbereitung
		Note noteNotInGtd = new Note(TEXT);
		// Test
		assertFalse(inbox.getList().contains(noteNotInGtd));
		inboxController.removeNote(noteNotInGtd);
	}

	/**
	 * Testet das erfolreiche Entfernen einer Notiz aus der Inbox.
	 *
	 * @throws Exception
	 *             {@link InboxController#removeNote(Note)}
	 */
	@Test
	public void test() throws Exception {
		// Vorbereitung
		ArrayList<Note> inboxList = inbox.getList();
		Note note = new Note(TEXT);
		inboxList.add(note);
		// Test
		assertTrue(inboxList.contains(note));
		inboxController.removeNote(note);
		assertFalse(inboxList.contains(note));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Entfernen der Notiz
	 * aufgerufen wird.
	 *
	 * @throws Exception
	 *             {@link InboxController#removeNote(Note)}
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
		Note note = new Note(TEXT);
		inbox.getList().add(note);
		// Test
		assertFalse(refreshInboxCalled.get());
		inboxController.removeNote(note);
		assertTrue(refreshInboxCalled.get());
	}

}
