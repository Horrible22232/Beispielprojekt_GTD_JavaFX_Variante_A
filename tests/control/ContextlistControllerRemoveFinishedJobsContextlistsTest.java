package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.JobsAUI;
import model.Contextlist;
import model.Job;
import model.Status;

/**
 * Testet die Methode
 * {@link ContextlistController#removeFinishedJobsInAllContextlists()}.
 *
 * @author Florian
 */
public class ContextlistControllerRemoveFinishedJobsContextlistsTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private GtdController gtdController;
	private ContextlistController contextlistController;
	private ArrayList<Contextlist> contextlists;
	private Contextlist contextlist1;
	private Contextlist contextlist2;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		// Initialisierung des Tests
		gtdController = new GtdController();
		contextlistController = gtdController.getContextlistController();
		contextlists = gtdController.getGtd().getContextlists();
		contextlist1 = new Contextlist("Contextlist1");
		contextlist2 = new Contextlist("Contextlist2");
		contextlists.add(contextlist1);
		contextlists.add(contextlist2);
	}

	/**
	 * Testet das Entfernen von erledigten Tätigkeiten ohne Kontextliste und mit
	 * leeren Kontextlisten.
	 */
	@Test
	public void testEmptyLists() {
		assertEquals(0, contextlistController.removeFinishedJobsInAllContextlists());
		contextlists.clear();
		assertEquals(0, contextlistController.removeFinishedJobsInAllContextlists());
	}

	/**
	 * Testet mit einer Liste mit unerledigten Tätigkeiten, ob nicht erledigten
	 * Tätigkeiten entfernt werden.
	 */
	@Test
	public void testWithoutFinishedJobs() {
		int elements = 5;
		// Vorbereitung
		for (int i = 0; i < elements; i++) {
			Job job = new Job("Job" + i, COMPLETE_UNTIL, DESCRIPTION);
			contextlist1.getJobs().add(job);
			contextlist2.getJobs().add(job);
		}
		// Test
		assertEquals(0, contextlistController.removeFinishedJobsInAllContextlists());
		assertEquals(elements, contextlist1.getJobs().size());
		assertEquals(elements, contextlist2.getJobs().size());
	}

	/**
	 * Testet mit einer Liste mit erledigten und unerledigten Tätigkeiten, ob
	 * alle erledigten Tätigkeiten entfernt werden und alle nicht unerledigten
	 * bleiben.
	 */
	@Test
	public void testWithFinishedJobs() {
		// Vorbereitung
		for (int i = 0; i < 21; i++) {
			Job job = new Job("Job" + i, COMPLETE_UNTIL, DESCRIPTION);
			if (i % 3 == 0) {
				// jeder n-te erledigt
				job.setStatus(Status.FINISHED);
			}
			contextlist1.getJobs().add(job);
		}
		for (int i = 0; i < 30; i++) {
			Job job = new Job("Job" + i, COMPLETE_UNTIL, DESCRIPTION);
			if (i % 3 == 0) {
				// jeder n-te erledigt
				job.setStatus(Status.FINISHED);
			}
			contextlist2.getJobs().add(job);
		}
		// Test
		assertEquals(17, contextlistController.removeFinishedJobsInAllContextlists());
		assertEquals(14, contextlist1.getJobs().size());
		assertEquals(20, contextlist2.getJobs().size());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces aufgerufen werden.
	 *
	 */
	@Test
	public void testRefreshJobsCalled() {
		// Vorbereitung
		AtomicBoolean refreshJobsCalled = new AtomicBoolean(false);
		gtdController.getJobController().addJobsAUI(new JobsAUI() {

			@Override
			public void refreshJobs() {
				refreshJobsCalled.set(true);
			}

			@Override
			public void refreshJob() {
			}
		});
		Job job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		contextlist1.getJobs().add(job);
		// Test
		assertFalse(refreshJobsCalled.get());
		contextlistController.removeFinishedJobsInAllContextlists();
		assertTrue(refreshJobsCalled.get());
	}

}
