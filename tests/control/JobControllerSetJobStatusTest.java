package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.JobsAUI;
import abstractuserinterfaces.ProjectTreeAUI;
import exceptions.GtdJobException;
import model.Contextlist;
import model.Delegation;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link JobController#setJobStatus(Job, Status)}.
 *
 * @author Florian
 */
public class JobControllerSetJobStatusTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.tomorrow();
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "Job";
	private GtdController gtdController;
	private JobController jobController;
	private TreeNode rootNode;
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
		gtdController = new GtdController();
		rootNode = gtdController.getGtd().getRootNode();
		jobController = gtdController.getJobController();
		Contextlist contextlist = new Contextlist("Contextlist");
		gtdController.getGtd().getContextlists().add(contextlist);
		job = new Job(TITLE, COMPLETE_UNTIL, DESCRIPTION);
		contextlist.getJobs().add(job);
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#setJobStatus(Job, Status)}
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() throws Exception {
		jobController.setJobStatus(null, Status.EDITING);
	}

	/**
	 * Wenn das Objekt 'job' in keiner Kontextliste von Gtd ist, wird eine
	 * GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#setJobStatus(Job, Status)}
	 */
	@Test(expected = GtdJobException.class)
	public void testJobNotInContextlists() throws Exception {
		// Vorbereitung
		Job jobNotInContextlists = new Job(TITLE, COMPLETE_UNTIL, DESCRIPTION);
		// Test
		jobController.setJobStatus(jobNotInContextlists, Status.EDITING);
	}

	/**
	 * Wenn der Parameter 'status' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#setJobStatus(Job, Status)}
	 */
	@Test(expected = NullPointerException.class)
	public void testStatusNull() throws Exception {
		jobController.setJobStatus(job, null);
	}

	/**
	 * Wenn der Parameter 'status' DELEGATED ist, wird eine
	 * IllegalArgumentException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#setJobStatus(Job, Status)}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStatusDelegated() throws Exception {
		jobController.setJobStatus(job, Status.DELEGATED);
	}

	/**
	 * Testet das erfolgreiche Setzen des Status einer Tätigkeit.
	 *
	 * @throws Exception
	 *             {@link JobController#setJobStatus(Job, Status)}
	 */
	@Test
	public void test() throws Exception {
		assertTrue(jobController.setJobStatus(job, Status.SOMETIME));
		assertEquals(Status.SOMETIME, job.getStatus());
		assertTrue(jobController.setJobStatus(job, Status.EDITING));
		assertEquals(Status.EDITING, job.getStatus());
		assertTrue(jobController.setJobStatus(job, Status.FINISHED));
		assertEquals(Status.FINISHED, job.getStatus());
	}

	@Test
	public void testProjectNextJobUpdated() throws Exception {
		// Vorbereitung
		Project project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		project.addJob(job);
		project.addJob(job2);
		// Test
		assertEquals(job, project.getNextJob());
		jobController.setJobStatus(job, Status.FINISHED);
		assertEquals(job2, project.getNextJob());
		jobController.setJobStatus(job, Status.EDITING);
		assertEquals(job, project.getNextJob());
	}

	@Test
	public void testProjectFinishedUpdated() throws Exception {
		// Vorbereitung
		Project project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		job2.setStatus(Status.FINISHED);
		project.addJob(job);
		project.addJob(job2);
		// Test
		assertFalse(project.isFinished());
		jobController.setJobStatus(job, Status.FINISHED);
		assertTrue(project.isFinished());
		jobController.setJobStatus(job, Status.EDITING);
		assertFalse(project.isFinished());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem delegieren aufgerufen
	 * werden.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test
	public void testRefreshAUIsCalled() throws Exception {
		// Vorbereitung
		Project project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		project.addJob(job);
		AtomicBoolean refreshJobCalled = new AtomicBoolean(false);
		AtomicBoolean refreshProjectCalled = new AtomicBoolean(false);
		jobController.addJobsAUI(new JobsAUI() {

			@Override
			public void refreshJobs() {
			}

			@Override
			public void refreshJob() {
				refreshJobCalled.set(true);
			}
		});
		gtdController.getProjectController().addProjectTreeAUI(new ProjectTreeAUI() {

			@Override
			public void refreshProjectTree() {
			}

			@Override
			public void refreshProject() {
				refreshProjectCalled.set(true);
			}
		});
		// Test
		assertFalse(refreshJobCalled.get());
		assertFalse(refreshProjectCalled.get());
		jobController.setJobStatus(job, Status.FINISHED);
		assertTrue(refreshJobCalled.get());
		assertTrue(refreshProjectCalled.get());
	}

}
