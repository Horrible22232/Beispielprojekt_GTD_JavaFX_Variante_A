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
import exceptions.GtdJobException;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link JobController#removeJobFromProject(Job)}.
 *
 * @author Florian
 */
public class JobControllerRemoveJobFromProjectTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private JobController jobController;
	private TreeNode rootNode;
	private Project project;
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
		rootNode = gtdController.getGtd().getRootNode();
		project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		project.getJobs().add(job);
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#removeJobFromProject(Job)}
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() throws Exception {
		jobController.removeJobFromProject(null);
	}

	/**
	 * Wenn das Objekt 'job' nicht einem Projekt enthalten ist, wird eine
	 * GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#removeJobFromProject(Job)}
	 */
	@Test(expected = GtdJobException.class)
	public void testJobNotInProject() throws Exception {
		// Vorbereitung
		Job jobNotInProject = new Job("JobNotInProject", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		jobController.removeJobFromProject(jobNotInProject);
	}

	/**
	 * Testet das erfolgreiche Entfernen einer Tätigkeit aus einem Projekt (mit
	 * newProject = null).
	 *
	 * @throws Exception
	 *             {@link JobController#removeJobFromProject(Job)}
	 */
	@Test
	public void testRemoving() throws Exception {
		ArrayList<Job> jobsList = project.getJobs();
		assertTrue(jobsList.contains(job));
		jobController.removeJobFromProject(job);
		assertFalse(jobsList.contains(job));
	}

	@Test
	public void testProjectNextJobUpdated() throws Exception {
		// Vorbereitung
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		project.addJob(job2);
		// Test
		assertEquals(job, project.getNextJob());
		jobController.removeJobFromProject(job);
		assertEquals(job2, project.getNextJob());
		jobController.removeJobFromProject(job2);
		assertEquals(null, project.getNextJob());
	}

	@Test
	public void testProjectFinishedUpdated() throws Exception {
		// Vorbereitung
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		job2.setStatus(Status.FINISHED);
		project.addJob(job2);
		project.updateFinishedStatus();
		// Test
		assertFalse(project.isFinished());
		jobController.removeJobFromProject(job);
		assertTrue(project.isFinished());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Entfernen einer Tätigkeit
	 * aus einem Projekt aufgerufen werden.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
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
		jobController.removeJobFromProject(job);
		assertTrue(refreshJobsCalled.get());
	}

}
