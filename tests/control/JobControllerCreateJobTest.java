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
import exceptions.EmptyStringParameterException;
import exceptions.GtdJobException;
import exceptions.ObjectNotInGtdException;
import model.Contextlist;
import model.Job;

/**
 * Testet die Methode
 * {@link JobController#createJob(Contextlist, String, LocalDate, String)}.
 *
 * @author Florian
 */
public class JobControllerCreateJobTest {
	private static final String CONTEXTLIST_TITLE = "Contextlist";
	private static final String TITLE = "Job";
	private static final LocalDate COMPLETE_UNTIL = DateFactory.tomorrow();
	private static final String DESCRIPTION = "description";
	private JobController jobController;
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
		jobController = gtdController.getJobController();
		contextlist = new Contextlist(CONTEXTLIST_TITLE);
		gtdController.getGtd().getContextlists().add(contextlist);
	}

	/**
	 * Wenn der Parameter 'contextlist' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testContextlistNull() throws Exception {
		jobController.createJob(null, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn das Objekt 'contextlist' nicht in Gtd(Contextlists) enthalten ist,
	 * wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testContextlistNotInGtd() throws Exception {
		// Vorbereitung
		Contextlist contextlistNotInGtd = new Contextlist("ContextlistNotInGtd");
		// Test
		jobController.createJob(contextlistNotInGtd, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'title' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTitleNull() throws Exception {
		jobController.createJob(contextlist, null, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'title' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTitleEmpty() throws Exception {
		jobController.createJob(contextlist, "", COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn bereits eine Tätigkeit mit dem gleichen Titel in der Kontextliste
	 * vorhanden ist, wird eine GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = GtdJobException.class)
	public void testDuplicateJobTitleInContextlist() throws Exception {
		// Vorbereitung
		contextlist.getJobs().add(new Job(TITLE, COMPLETE_UNTIL, DESCRIPTION));
		// Test
		jobController.createJob(contextlist, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'completeUntil' null ist, wird eine
	 * NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testCompleteUntilNull() throws Exception {
		jobController.createJob(contextlist, TITLE, null, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'completeUntil' in der Vergangeheit liegt, wird eine
	 * IllegalArgumentException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompleteUntilInPast() throws Exception {
		jobController.createJob(contextlist, TITLE, DateFactory.yesterday(), DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'description' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testDescriptionNull() throws Exception {
		jobController.createJob(contextlist, TITLE, COMPLETE_UNTIL, null);
	}

	/**
	 * Testet das erfolreiche Entfernen einer Tätigkeit mit leerer Beschreibung
	 * (nicht null).
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test
	public void testCreationDescriptionEmpty() throws Exception {
		ArrayList<Job> jobsList = contextlist.getJobs();
		assertEquals(0, jobsList.size());
		jobController.createJob(contextlist, TITLE, COMPLETE_UNTIL, "");
		assertEquals(1, jobsList.size());
		assertEquals(TITLE, jobsList.get(0).getTitle());
	}

	/**
	 * Testet das erfolreiche Entfernen einer Tätigkeit mit nicht leerer
	 * Beschreibung.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test
	public void testCreation() throws Exception {
		ArrayList<Job> jobsList = contextlist.getJobs();
		assertEquals(0, jobsList.size());
		jobController.createJob(contextlist, TITLE, COMPLETE_UNTIL, DESCRIPTION);
		assertEquals(1, jobsList.size());
		assertEquals(TITLE, jobsList.get(0).getTitle());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Anlegen und Einfügen einer
	 * Tätigkeit aufgerufen werden.
	 *
	 * @throws Exception
	 *             {@link JobController#createJob(Contextlist, String, LocalDate, String)}
	 */
	@Test
	public void testRefreshJobsCalled() throws Exception {
		// Vorbereitung
		AtomicBoolean refreshJobsCalled = new AtomicBoolean(false);
		jobController.addJobsAUI(new JobsAUI() {

			@Override
			public void refreshJobs() {
				refreshJobsCalled.set(true);
			}

			@Override
			public void refreshJob() {
			}
		});
		// Test
		assertFalse(refreshJobsCalled.get());
		jobController.createJob(contextlist, TITLE, COMPLETE_UNTIL, DESCRIPTION);
		assertTrue(refreshJobsCalled.get());
	}

}
