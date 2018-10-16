package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.ProjectTreeAUI;
import exceptions.GtdProjectException;
import exceptions.ObjectNotInGtdException;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#removeProject(Project)}.
 *
 * @author Florian
 */
public class ProjectControllerRemoveProjectTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
	private Project project;
	private Job job;
	private TreeNode rootNode;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		GtdController gtdController = new GtdController();
		projectController = gtdController.getProjectController();
		rootNode = gtdController.getGtd().getRootNode();
		project = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.addSubproject(project);
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		project.getJobs().add(job);
		project.updateNextJob();
	}

	/**
	 * Wenn der Parameter 'project' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test(expected = NullPointerException.class)
	public void testProjectNull() throws Exception {
		projectController.removeProject(null);
	}

	/**
	 * Wenn das Objekt 'project' nicht in Gtd(Projektbaum) enthalten ist, wird
	 * eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testProjectNotInGtd() throws Exception {
		// Vorbereitung
		Project projectNotInGtd = new Project("ProjectNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		assertFalse(rootNode.getSubprojects().contains(projectNotInGtd));
		projectController.removeProject(projectNotInGtd);
	}

	/**
	 * Wenn ein nicht fertiges Projekt entfernt werden soll, wird eine
	 * GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testProjectNotFinished() throws Exception {
		assertFalse(job.isFinished());
		assertFalse(project.isFinished());
		projectController.removeProject(project);
	}

	/**
	 * Testet das erfolgreiche Entfernen eines leeren Projekts (ohne Tätigkeiten
	 * oder Unterprojekte).
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test
	public void testProjectEmpty() throws Exception {
		// Vorbereitung
		project.removeJob(job);
		// Test
		assertFalse(project.isFinished());
		projectController.removeProject(project);
	}

	/**
	 * Testet das erfolgreiche Entfernen eines fertigen Projekts.
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test
	public void testProjectFinished() throws Exception {
		// Vorbereitung
		job.setStatus(Status.FINISHED);
		project.updateNextJob();
		// Test
		assertTrue(job.isFinished());
		assertTrue(project.isFinished());
		assertTrue(rootNode.getSubprojects().contains(project));
		projectController.removeProject(project);
		assertFalse(rootNode.getSubprojects().contains(project));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Entfernen des Projects
	 * aufgerufen werden.
	 *
	 * @throws Exception
	 *             {@link ProjectController#removeProject(Project)}
	 */
	@Test
	public void testRefreshProjectTreeCalled() throws Exception {
		// Vorbereitung
		job.setStatus(Status.FINISHED);
		project.updateNextJob();
		AtomicBoolean refreshProjectTreeCalled = new AtomicBoolean(false);
		projectController.addProjectTreeAUI(new ProjectTreeAUI() {

			@Override
			public void refreshProjectTree() {
				refreshProjectTreeCalled.set(true);
			}

			@Override
			public void refreshProject() {
			}
		});
		// Test
		assertFalse(refreshProjectTreeCalled.get());
		projectController.removeProject(project);
		assertTrue(refreshProjectTreeCalled.get());
	}

}
