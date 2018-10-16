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
import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link JobController#moveJobToProject(Job, Project)}.
 *
 * @author Florian
 */
public class JobControllerMoveJobToProjectTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private GtdController gtdController;
	private JobController jobController;
	private Project project0;
	private Project project1111;
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
		jobController = gtdController.getJobController();
		project0 = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		Project project1 = new Project("Project1", COMPLETE_UNTIL, DESCRIPTION);
		Project project11 = new Project("Project11", COMPLETE_UNTIL, DESCRIPTION);
		Project project111 = new Project("Project111", COMPLETE_UNTIL, DESCRIPTION);
		project1111 = new Project("Project1111", COMPLETE_UNTIL, DESCRIPTION);
		TreeNode rootNode = gtdController.getGtd().getRootNode();
		rootNode.getSubprojects().add(project0);
		rootNode.getSubprojects().add(project1);
		project1.getSubprojects().add(project11);
		project11.getSubprojects().add(project111);
		project111.getSubprojects().add(project1111);
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		Job job2 = new Job("Job2", DateFactory.tomorrow(), DESCRIPTION);
		project0.getJobs().add(job);
		project1111.getJobs().add(job2);
		project1111.updateNextJob();
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() throws Exception {
		jobController.moveJobToProject(null, project1111);
	}

	/**
	 * Wenn das Objekt 'job' nicht in Gtd enthalten ist, wird eine
	 * ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testJobNotInGtd() throws Exception {
		// Vorbereitung
		Job jobNotInGtd = new Job("JobNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		jobController.moveJobToProject(jobNotInGtd, project0);
	}

	/**
	 * Wenn der Parameter 'newProject' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test(expected = NullPointerException.class)
	public void testProjectNull() throws Exception {
		jobController.moveJobToProject(job, null);
	}

	/**
	 * Wenn das Objekt 'newProject' nicht in Gtd enthalten ist, wird eine
	 * ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testProjectNotInGtd() throws Exception {
		// Vorbereitung
		Project projectNotInGtd = new Project("ProjectNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		jobController.moveJobToProject(job, projectNotInGtd);
	}

	/**
	 * Wenn bereits eine Tätigkeit mit dem gleichen Titel in dem neuen Project
	 * vorhanden ist, wird eine GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test(expected = GtdJobException.class)
	public void testDuplicateJobTitleInProject() throws Exception {
		// Vorbereitung
		Job jobDuplicate = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		project1111.getJobs().add(jobDuplicate);
		// Test
		jobController.moveJobToProject(job, project1111);
	}

	/**
	 * Testet das erfolgreiche Verschieben einer Tätigkeit in das gleiche
	 * Projekt, in dem die Tätigkeit bereits ist (also oldProject = newProject).
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test
	public void testMoveToSameProject() throws Exception {
		ArrayList<Job> jobsList = project0.getJobs();
		assertTrue(jobsList.contains(job));
		jobController.moveJobToProject(job, project0);
		assertTrue(jobsList.contains(job));
	}

	/**
	 * Testet das erfolgreiche Verschieben einer Tätigkeit in ein Projekt
	 * (vorher in keinem Projekt).
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test
	public void testMoveFromNullToProject() throws Exception {
		// Vorbereitung
		ArrayList<Job> jobsList0 = project0.getJobs();
		jobsList0.remove(job);
		Contextlist contextlist = new Contextlist("Contextlist");
		contextlist.addJob(job);
		gtdController.getGtd().getContextlists().add(contextlist);
		// Test
		assertFalse(jobsList0.contains(job));
		jobController.moveJobToProject(job, project0);
		assertTrue(jobsList0.contains(job));
	}

	/**
	 * Testet das erfolgreiche Verschieben einer Tätigkeit in ein anderes
	 * Projekt.<br>
	 * Nach dem Verschieben ist die Tätigkeit nicht mehr in dem alten Projekt
	 * und dafür in dem Neunen enthalten.
	 *
	 * @throws Exception
	 *             {@link JobController#moveJobToProject(Job, Project)}
	 */
	@Test
	public void testMoveToOtherProject() throws Exception {
		// Vorbereitung
		ArrayList<Job> jobsList0 = project0.getJobs();
		ArrayList<Job> jobsList1111 = project1111.getJobs();
		// Test
		assertTrue(jobsList0.contains(job));
		assertFalse(jobsList1111.contains(job));
		jobController.moveJobToProject(job, project1111);
		assertFalse(jobsList0.contains(job));
		assertTrue(jobsList1111.contains(job));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Verschieben einer
	 * Tätigkeit eine Projekt aufgerufen werden.
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
		jobController.moveJobToProject(job, project1111);
		assertTrue(refreshJobsCalled.get());
	}

}
