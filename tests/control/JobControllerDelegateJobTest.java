package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.JobsAUI;
import abstractuserinterfaces.ProjectTreeAUI;
import exceptions.EmptyStringParameterException;
import exceptions.GtdJobException;
import model.Contextlist;
import model.Delegation;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link JobController#delegateJob(Job, Delegation)}.
 *
 * @author Florian
 */
public class JobControllerDelegateJobTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.tomorrow();
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "Job";
	private static final String TO_PERSON = "toPerson";
	private static final LocalDate UNTIL = DateFactory.tomorrow();
	private GtdController gtdController;
	private JobController jobController;
	private TreeNode rootNode;
	private Delegation delegation;
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
		rootNode = gtdController.getGtd().getRootNode();
		Contextlist contextlist = new Contextlist("contextlist");
		gtdController.getGtd().getContextlists().add(contextlist);
		job = new Job(TITLE, COMPLETE_UNTIL, DESCRIPTION);
		contextlist.getJobs().add(job);
		delegation = new Delegation(TO_PERSON, UNTIL);
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() throws Exception {
		jobController.delegateJob(null, delegation);
	}

	/**
	 * Wenn das Objekt 'job' nicht in einer Kontextliste enthalten ist, wird
	 * eine GtdJobException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = GtdJobException.class)
	public void testJobNotInContextlists() throws Exception {
		// Vorbereitung
		Job jobNotInGtd = new Job("JobNotInGtd", UNTIL, DESCRIPTION);
		// Test
		jobController.delegateJob(jobNotInGtd, delegation);
	}

	/**
	 * Wenn der Parameter 'delegation' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = NullPointerException.class)
	public void testDelegationNull() throws Exception {
		jobController.delegateJob(job, null);
	}

	/**
	 * Wenn das Attribut 'toPerson' von Delegation null ist, wird eine
	 * NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = NullPointerException.class)
	public void testDelegationToPersonNull() throws Exception {
		jobController.delegateJob(job, new Delegation(null, UNTIL));
	}

	/**
	 * Wenn das Attribut 'toPerson' von Delegation leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testDelegationToPersonEmpty() throws Exception {
		jobController.delegateJob(job, new Delegation("", UNTIL));
	}

	/**
	 * Wenn das Attribut 'until' von Delegation null ist, wird eine
	 * NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = NullPointerException.class)
	public void testDelegationUntilNull() throws Exception {
		jobController.delegateJob(job, new Delegation(TO_PERSON, null));
	}

	/**
	 * Wenn das Attribut 'completeUntil' von Delegation in der Vergangeheit
	 * liegt, wird eine IllegalArgumentException geworfen.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDelegationUntilInPast() throws Exception {
		jobController.delegateJob(job, new Delegation(TO_PERSON, DateFactory.yesterday()));
	}

	/**
	 * Testet das erfolreiche Delegieren einer Tätigkeit.
	 *
	 * @throws Exception
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 */
	@Test
	public void test() throws Exception {
		assertNull(job.getDelegation());
		assertEquals(Status.EDITING, job.getStatus());
		jobController.delegateJob(job, delegation);
		assertEquals(delegation, job.getDelegation());
		assertEquals(Status.DELEGATED, job.getStatus());
	}

	@Test
	public void testProjectNextJobUpdated() throws Exception {
		// Vorbereitung
		Project project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		job.setStatus(Status.FINISHED);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		project.addJob(job);
		project.addJob(job2);
		// Test
		assertEquals(job2, project.getNextJob());
		jobController.delegateJob(job, delegation);
		assertEquals(job, project.getNextJob());
	}

	@Test
	public void testProjectFinishedUpdated() throws Exception {
		// Vorbereitung
		Project project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		job.setStatus(Status.FINISHED);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		job2.setStatus(Status.FINISHED);
		project.addJob(job);
		project.addJob(job2);
		// Test
		assertTrue(project.isFinished());
		jobController.delegateJob(job, delegation);
		assertFalse(project.isFinished());
		job.setStatus(Status.EDITING);
		job2.setStatus(Status.EDITING);
		assertFalse(project.isFinished());
		jobController.delegateJob(job, delegation);
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
		jobController.delegateJob(job, delegation);
		assertTrue(refreshJobCalled.get());
		assertTrue(refreshProjectCalled.get());
	}

}
