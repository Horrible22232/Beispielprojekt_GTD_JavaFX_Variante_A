package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse {@link Inbox}.
 *
 * @author Florian
 */
public class InboxTest {
	private Inbox inbox;
	private ArrayList<Note> notes;
	private Note note0;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		inbox = new Inbox();
		notes = inbox.getList();
		note0 = new Note("Text0");
	}

	/**
	 * Testen des Inbox-Konstruktors.<br>
	 * Die Liste der Tätigkeiten wurde erstellt und ist leer.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(notes);
		assertTrue(notes.isEmpty());
		assertNotNull(inbox.getLastNoteConversion());
	}

	/**
	 * Testet das Hinzufügen einer Notiz in die Inbox.
	 */
	@Test
	public void testAddNote() {
		// Test
		assertFalse(notes.contains(note0));
		assertEquals(0, notes.size());
		inbox.addNote(note0);
		assertTrue(notes.contains(note0));
		assertEquals(1, notes.size());
	}

	/**
	 * Testet das Entfernen einer Notiz, die in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveNoteContains() {
		// Vorbereitung
		notes.add(note0);
		assertTrue(notes.contains(note0));
		assertEquals(1, notes.size());
		// Test
		inbox.removeNote(note0);
		assertFalse(notes.contains(note0));
		assertEquals(0, notes.size());
	}

	/**
	 * Testet das Entfernen einer Notiz, die nicht in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveNoteContainsNot() {
		// Test
		assertFalse(notes.contains(note0));
		assertEquals(0, notes.size());
		inbox.removeNote(note0);
		assertFalse(notes.contains(note0));
		assertEquals(0, notes.size());
	}

	/**
	 * Testet, ob der Zeitpunkt lastNoteConversion geändert wird und nach der
	 * letzen liegt.
	 */
	@Test
	public void testSetLastNoteConversion() {
		// Vorbereitung
		LocalDateTime lastNoteConversion = inbox.getLastNoteConversion();
		assertNotNull(lastNoteConversion);
		assertTrue(lastNoteConversion.equals(inbox.getLastNoteConversion()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Test
		inbox.updateLastNoteConversion();
		assertNotNull(inbox.getLastNoteConversion());
		assertFalse(lastNoteConversion.equals(inbox.getLastNoteConversion()));
		assertTrue(lastNoteConversion.isBefore(inbox.getLastNoteConversion()));
	}

}
