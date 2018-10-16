package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.ContextlistsAUI;
import exceptions.GtdContextlistException;
import exceptions.ObjectNotInGtdException;
import model.Contextlist;
import model.Job;
import model.Status;

/**
 * Testet die Methode
 * {@link ContextlistController#removeContextlist(Contextlist)}.
 *
 * @author Florian
 */
public class ContextlistControllerRemoveContextlistTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "Contextlist";
	private ContextlistController contextlistController;
	private ArrayList<Contextlist> contextlists;
	private Contextlist contextlist;

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
		contextlist = new Contextlist(TITLE);
		contextlists.add(contextlist);
	}

	/**
	 * Wenn der Parameter 'contextlist' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#removeContextlist(Contextlist)}
	 */
	@Test(expected = NullPointerException.class)
	public void testContextlistNull() throws Exception {
		contextlistController.removeContextlist(null);
	}

	/**
	 * Wenn das Objekt 'contextlist' nicht in Gtd(Contextlists) enthalten ist,
	 * wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#removeContextlist(Contextlist)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testContextlistNotInGtd() throws Exception {
		// Vorbereitung
		Contextlist contextlistNotInGtd = new Contextlist("ContextlistNotInGtd");
		// Test
		assertFalse(contextlists.contains(contextlistNotInGtd));
		contextlistController.removeContextlist(contextlistNotInGtd);
	}

	/**
	 * Testet das Entfernen einer Kontextliste mit einer Tätigkeitenliste, die
	 * nicht nur erledigte Tätigkeiten enthält.<br>
	 * Es sollte eine GtdContextlistException geworfen werden.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#removeContextlist(Contextlist)}
	 */
	@Test(expected = GtdContextlistException.class)
	public void testJobsNotAllFinished() throws Exception {
		// Vorbereitung
		Job job1 = new Job("Job1", COMPLETE_UNTIL, DESCRIPTION);
		job1.setStatus(Status.FINISHED);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		contextlist.getJobs().add(job1);
		contextlist.getJobs().add(job2);
		// Test
		assertTrue(contextlists.contains(contextlist));
		contextlistController.removeContextlist(contextlist);
		assertTrue(contextlists.contains(contextlist));
	}

	/**
	 * Testet das Entfernen einer Kontextliste mit einer Tätigkeitenliste, die
	 * nur erledigte Tätigkeiten enthält.<br>
	 * Es sollte eine GtdContextlistException geworfen werden.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#removeContextlist(Contextlist)}
	 */
	@Test(expected = GtdContextlistException.class)
	public void testJobsAllFinished() throws Exception {
		// Vorbereitung
		Job job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		job.setStatus(Status.FINISHED);
		contextlist.getJobs().add(job);
		// Test
		assertTrue(contextlists.contains(contextlist));
		contextlistController.removeContextlist(contextlist);
		assertFalse(contextlists.contains(contextlist));
	}

	/**
	 * Testet das Entfernen einer Kontextliste mit leerer Tätigkeitenliste.<br>
	 * Die Kontextliste sollte entfernt werden.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#removeContextlist(Contextlist)}
	 */
	@Test
	public void testJobsEmpty() throws Exception {
		assertTrue(contextlists.contains(contextlist));
		contextlistController.removeContextlist(contextlist);
		assertFalse(contextlists.contains(contextlist));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Entfernen der Kontextliste
	 * aufgerufen wird.
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
		contextlistController.removeContextlist(contextlist);
		assertTrue(refreshContextlistsCalled.get());
	}

}
