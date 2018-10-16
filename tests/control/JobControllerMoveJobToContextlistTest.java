package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.JobsAUI;
import exceptions.GtdJobException;
import exceptions.ObjectNotInGtdException;
import model.Contextlist;
import model.Job;

/**
 * Testet die Methode
 * {@link JobController#moveJobToContextlist(Job, Contextlist)}.
 *
 * @author Florian
 */
public class JobControllerMoveJobToContextlistTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private JobController jobController;
	private Contextlist contextlist1, contextlist2;
	private Job job;

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
		contextlist1 = new Contextlist("Contextlist1");
		contextlist2 = new Contextlist("Contextlist2");
		gtdController.getGtd().getContextlists().add(contextlist1);
		gtdController.getGtd().getContextlists().add(contextlist2);
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		contextlist1.getJobs().add(job);
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() throws Exception {
		jobController.moveJobToContextlist(null, contextlist2);
	}

	/**
	 * Wenn das Objekt 'job' in keiner Kontextliste von Gtd ist, wird eine
	 * GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test(expected = GtdJobException.class)
	public void testJobNotInContextlists() throws Exception {
		// Vorbereitung
		Job jobNotInContextlists = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		jobController.moveJobToContextlist(jobNotInContextlists, contextlist2);
	}

	/**
	 * Wenn der Parameter 'newContextlist' null ist, wird eine
	 * NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test(expected = NullPointerException.class)
	public void testContextlistNull() throws Exception {
		jobController.moveJobToContextlist(job, null);
	}

	/**
	 * Wenn das Objekt 'newContextlist' nicht in Gtd(Contextlists) enthalten
	 * ist, wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testContextlistNotInGtd() throws Exception {
		// Vorbereitung
		Contextlist contextlistNotInGtd = new Contextlist("ContextlistNotInGtd");
		// Test
		jobController.moveJobToContextlist(job, contextlistNotInGtd);
	}

	/**
	 * Wenn bereits eine Tätigkeit mit dem gleichen Titel in der neuen
	 * Kontextliste vorhanden ist, wird eine GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test(expected = GtdJobException.class)
	public void testDuplicateJobTitleInContextlist() throws Exception {
		// Vorbereitung
		Job jobDuplicate = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		contextlist2.getJobs().add(jobDuplicate);
		// Test
		jobController.moveJobToContextlist(job, contextlist2);
	}

	/**
	 * Testet das erfolgreiche Verschieben einer Tätigkeit in die gleiche
	 * Kontextliste, in der die Tätigkeit bereits ist (also oldContextlist =
	 * newContextlist).
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test
	public void testMoveToSameContextlist() throws Exception {
		ArrayList<Job> contextlist1List = contextlist1.getJobs();
		ArrayList<Job> contextlist2List = contextlist2.getJobs();
		assertTrue(contextlist1List.contains(job));
		assertFalse(contextlist2List.contains(job));
		jobController.moveJobToContextlist(job, contextlist1);
		assertTrue(contextlist1List.contains(job));
		assertFalse(contextlist2List.contains(job));
	}

	/**
	 * Testet das erfolgreiche Verschieben einer Tätigkeit in eine andere
	 * Kontextliste.<br>
	 * Nach dem Verschieben ist die Tätigkeit nicht mehr in der alten
	 * Kontextliste und dafür in der Neunen enthalten.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToContextlist(Job, Contextlist)}
	 */
	@Test
	public void testMoveToOtherContextlist() throws Exception {
		ArrayList<Job> contextlist1List = contextlist1.getJobs();
		ArrayList<Job> contextlist2List = contextlist2.getJobs();
		assertTrue(contextlist1List.contains(job));
		assertFalse(contextlist2List.contains(job));
		jobController.moveJobToContextlist(job, contextlist2);
		assertFalse(contextlist1List.contains(job));
		assertTrue(contextlist2List.contains(job));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Verschieben einer
	 * Tätigkeit eine Kontextliste aufgerufen werden.
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
		jobController.moveJobToContextlist(job, contextlist2);
		assertTrue(refreshJobsCalled.get());
	}

}
