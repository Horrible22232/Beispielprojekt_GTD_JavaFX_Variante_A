package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse {@link Gtd}.
 *
 * @author Florian
 */
public class GtdTest {
	private Gtd gtd;
	private ArrayList<Contextlist> contextlists;
	private Contextlist contextlist0;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		gtd = new Gtd();
		contextlists = gtd.getContextlists();
		contextlist0 = new Contextlist("Contextlist0");
	}

	/**
	 * Testet den Gtd-Konstruktor.<br>
	 * Die Liste der Kontextlisten, Inbox und RootNode sollen erstellt sein.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(gtd.getContextlists());
		assertTrue(gtd.getContextlists().isEmpty());
		assertNotNull(gtd.getInbox());
		assertNotNull(gtd.getRootNode());
	}

	/**
	 * Testet das Hinzufügen einer Kontextliste in die nach Titel sortierte
	 * Liste der Kontextlisten.
	 */
	@Test
	public void testAddContextlist() {
		// Vorbereitung
		Contextlist contextlist1 = new Contextlist("Contextlist1");
		Contextlist contextlist2 = new Contextlist("Contextlist2");
		// Test
		assertEquals(0, contextlists.size());
		// add contextlist2
		gtd.addContextlist(contextlist2);
		assertEquals(contextlist2, contextlists.get(0));
		assertEquals(1, contextlists.size());
		// add contextlist0
		gtd.addContextlist(contextlist0);
		assertEquals(contextlist0, contextlists.get(0));
		assertEquals(contextlist2, contextlists.get(1));
		assertEquals(2, contextlists.size());
		// add contextlist1
		gtd.addContextlist(contextlist1);
		assertEquals(contextlist0, contextlists.get(0));
		assertEquals(contextlist1, contextlists.get(1));
		assertEquals(contextlist2, contextlists.get(2));
		assertEquals(3, contextlists.size());
	}

	/**
	 * Testet das Entfernen einer Kontextliste, die in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveContextlistContains() {
		// Vorbereitung
		contextlists.add(contextlist0);
		assertTrue(contextlists.contains(contextlist0));
		assertEquals(1, contextlists.size());
		// Test
		gtd.removeContextlist(contextlist0);
		assertFalse(contextlists.contains(contextlist0));
		assertEquals(0, contextlists.size());
	}

	/**
	 * Testet das Entfernen einer Kontextliste, die nicht in der Liste vorhanden
	 * ist.
	 */
	@Test
	public void testRemoveContextlistContainsNot() {
		// Test
		assertFalse(contextlists.contains(contextlist0));
		assertEquals(0, contextlists.size());
		gtd.removeContextlist(contextlist0);
		assertFalse(contextlists.contains(contextlist0));
		assertEquals(0, contextlists.size());
	}

}
