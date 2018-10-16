package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.ContextlistsAUI;
import exceptions.EmptyStringParameterException;
import exceptions.GtdContextlistException;
import model.Contextlist;

/**
 * Testet die Methode {@link ContextlistController#createContextlist(String)}.
 *
 * @author Florian
 */
public class ContextlistControllerCreateContextlistTest {
	private static final String TITLE = "Contextlist";
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
	 *             {@link ContextlistController#createContextlist(String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTitleNull() throws Exception {
		contextlistController.createContextlist(null);
	}

	/**
	 * Wenn der Parameter 'title' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#createContextlist(String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTitleEmpty() throws Exception {
		contextlistController.createContextlist("");
	}

	/**
	 * Wenn bereits eine Kontextliste mit dem gleichen Titel vorhanden ist, wird
	 * eine GtdContextlistException geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#createContextlist(String)}
	 */
	@Test(expected = GtdContextlistException.class)
	public void testDuplicateContextlistTitle() throws Exception {
		// Vorbereitung
		Contextlist contextlistDuplicate = new Contextlist(TITLE);
		contextlists.add(contextlistDuplicate);
		// Test
		contextlistController.createContextlist(TITLE);
	}

	/**
	 * Testet das erfolreiche Erzeugen einer Kontextliste.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#createContextlist(String)}
	 */
	@Test
	public void testCreation() throws Exception {
		assertEquals(0, contextlists.size());
		contextlistController.createContextlist(TITLE);
		assertEquals(1, contextlists.size());
		assertEquals(TITLE, contextlists.get(0).getTitle());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Erzeugen und Einfügen der
	 * Kontextliste aufgerufen wird.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#createContextlist(String)}
	 */
	@Test
	public void testRefreshContextlistsCalled() throws Exception {
		// Vorbereitung
		AtomicBoolean refreshContextlistsCalled = new AtomicBoolean(false);
		contextlistController.addContextlistsAUI(new ContextlistsAUI() {

			@Override
			public void refreshContextlists() {
				refreshContextlistsCalled.set(true);
			}
		});
		// Test
		assertFalse(refreshContextlistsCalled.get());
		contextlistController.createContextlist(TITLE);
		assertTrue(refreshContextlistsCalled.get());
	}

}
