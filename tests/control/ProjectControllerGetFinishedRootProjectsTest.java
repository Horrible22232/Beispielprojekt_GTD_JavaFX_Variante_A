package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#getFinishedRootProjects()}.
 *
 * @author Florian
 */
public class ProjectControllerGetFinishedRootProjectsTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
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
	}

	/**
	 * Wenn keine Projekte im Projektbaum sind, wird eine leere Liste fertiger
	 * Projekte zurückgegeben.
	 */
	@Test
	public void testEmptyProjectTree() {
		ArrayList<Project> finishedRootProjects = projectController.getFinishedRootProjects();
		assertNotNull(finishedRootProjects);
		assertTrue(finishedRootProjects.isEmpty());
	}

	/**
	 * Wenn keine fertigen Projekte im Projektbaum sind, wird eine leere Liste
	 * fertiger Projekte zurückgegeben.
	 */
	@Test
	public void testNoFinishedProjects() {
		// Vorbereitung
		Project project = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project);
		Job job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		project.getJobs().add(job);
		// Test
		ArrayList<Project> finishedRootProjects = projectController.getFinishedRootProjects();
		assertNotNull(finishedRootProjects);
		assertTrue(finishedRootProjects.isEmpty());
	}

	/**
	 * Wenn fertige Projekte im Projektbaum sind, wird eine Liste mit diesen
	 * Projekten zurückgegeben.
	 */
	@Test
	public void testContainsFinishedProjects() {
		// Vorbereitung
		Project project0 = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		Project project1 = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project0);
		rootNode.getSubprojects().add(project1);
		Job job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		job.setStatus(Status.FINISHED);
		project0.getJobs().add(job);
		project0.updateNextJob();
		// Test
		ArrayList<Project> finishedRootProjects = projectController.getFinishedRootProjects();
		assertNotNull(finishedRootProjects);
		assertFalse(finishedRootProjects.isEmpty());
		assertEquals(1, finishedRootProjects.size());
		assertEquals(project0, finishedRootProjects.get(0));
	}

}
