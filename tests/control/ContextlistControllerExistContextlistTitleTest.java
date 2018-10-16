package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.EmptyStringParameterException;
import model.Contextlist;

/**
 * Testet die Methode
 * {@link ContextlistController#existContextlistTitle(String)}.
 *
 * @author Florian
 */
public class ContextlistControllerExistContextlistTitleTest {
	private ContextlistController contextlistController;
	private ArrayList<Contextlist> contextlists;

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
		contextlistController = gtdController.getContextlistController();
		contextlists = gtdController.getGtd().getContextlists();
	}

	/**
	 * Wenn der Parameter 'title' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTitleNull() throws Exception {
		contextlistController.existContextlistTitle(null);
	}

	/**
	 * Wenn der Parameter 'title' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTitleEmpty() throws Exception {
		contextlistController.existContextlistTitle("");
	}

	/**
	 * Testet ob false zurückgegeben wird, wenn nach einem Titel einer nicht in
	 * Gtd vorhandenen Kontextliste gesucht wird.
	 */
	@Test
	public void testContextlistTitleExistNot() {
		// Test empty list
		assertFalse(contextlistController.existContextlistTitle("ContextlistExistNot"));
		// Test nonempty list
		for (int i = 0; i < 10; i++) {
			contextlists.add(new Contextlist("Contextlist" + i));
		}
		assertFalse(contextlistController.existContextlistTitle("ContextlistExistNot"));
	}

	/**
	 * Testet den korrekten Stringvergleich mit equals bei der Suche.
	 */
	@Test
	public void testTitleCorrectStringComparison() {
		// Vorbereitung
		String name1 = "Contextlist1";
		String name2 = new String("Contextlist1");
		String name3 = new String("Contextlist3");
		Contextlist contextlist1 = new Contextlist(name1);
		Contextlist contextlist2 = new Contextlist(name2);
		Contextlist contextlist3 = new Contextlist(name3);
		contextlists.add(contextlist1);
		contextlists.add(contextlist2);
		contextlists.add(contextlist3);
		// Test
		assertFalse(name1 == name2);
		assertTrue(name1.equals(name2));
		assertTrue(contextlistController.existContextlistTitle(name1));
		assertTrue(contextlistController.existContextlistTitle(name2));
		assertTrue(contextlistController.existContextlistTitle(name3));
	}

	/**
	 * Testet, ob eine Kontextliste erfolgreich gefunden wird.
	 */
	@Test
	public void test() {
		// Vorbereitung
		for (int i = 0; i < 10; i++) {
			contextlists.add(new Contextlist("Contextlist" + i));
		}
		// Test
		for (int i = 9; i > -1; i--) {
			assertTrue(contextlistController.existContextlistTitle("Contextlist" + i));
		}
	}

}
