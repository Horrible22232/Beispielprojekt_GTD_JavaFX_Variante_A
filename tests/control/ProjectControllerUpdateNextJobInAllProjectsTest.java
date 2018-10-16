package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.ProjectTreeAUI;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#updateNextJobInAllProjects()}.
 *
 * @author Florian
 */
public class ProjectControllerUpdateNextJobInAllProjectsTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
	private Project project0;
	private Project project1;
	private Project project11;
	private Job job0;
	private Job job1;
	private Job job11;

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
		TreeNode rootNode = gtdController.getGtd().getRootNode();
		project0 = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		project1 = new Project("Project1", COMPLETE_UNTIL, DESCRIPTION);
		project11 = new Project("Project11", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project0);
		rootNode.getSubprojects().add(project1);
		project1.getSubprojects().add(project11);
		job0 = new Job("Job0", COMPLETE_UNTIL, DESCRIPTION);
		job1 = new Job("Job1", COMPLETE_UNTIL, DESCRIPTION);
		job11 = new Job("Job11", COMPLETE_UNTIL, DESCRIPTION);
		project0.getJobs().add(job0);
		project1.getJobs().add(job1);
		project11.getJobs().add(job11);
		for (Project project : Arrays.asList(project0, project1, project11)) {
			project.updateNextJob();
		}
		assertNotNull(project0.getNextJob());
		assertEquals(job0, project0.getNextJob());
		assertFalse(project0.isFinished());
		assertNotNull(project11.getNextJob());
		assertEquals(job11, project11.getNextJob());
		assertFalse(project11.isFinished());
		assertNotNull(project1.getNextJob());
		assertEquals(job1, project1.getNextJob());
		assertFalse(project1.isFinished());
	}

	/**
	 * Testet, ob in allen Projekten die nächste Tätigkeit (nextJob) korrekt
	 * gesetzt und der Projektstatus korrekt aktualisiert wurde.<br>
	 * Wenn keine Tätigkeiten erledigt sind, wird nextJob aktualisiert in das
	 * Projekt ist nicht fertig.
	 */
	@Test
	public void testAllJobsNotFinished() {
		projectController.updateNextJobInAllProjects();
		// sollten unverändert sein
		assertNotNull(project0.getNextJob());
		assertEquals(job0, project0.getNextJob());
		assertFalse(project0.isFinished());
		assertNotNull(project11.getNextJob());
		assertEquals(job11, project11.getNextJob());
		assertFalse(project11.isFinished());
		assertNotNull(project1.getNextJob());
		assertEquals(job1, project1.getNextJob());
		assertFalse(project1.isFinished());
	}

	/**
	 * Wenn keine Änderungen im Projekt (Tätigkeiten als erledigt markiert,
	 * Tätigkeiten hinzufügen oder löschen) gemacht werden, sollte sich die
	 * nächsten Tätigkeit oder der Projektstatus nicht ändern.
	 */
	@Test
	public void testSingleRootProjecFinished() {
		// Vorbereitung
		job0.setStatus(Status.FINISHED);
		// Test
		projectController.updateNextJobInAllProjects();
		assertNull(project0.getNextJob());
		assertTrue(project0.isFinished());
		// sollten unverändert sein
		assertNotNull(project11.getNextJob());
		assertEquals(job11, project11.getNextJob());
		assertFalse(project11.isFinished());
		assertNotNull(project1.getNextJob());
		assertEquals(job1, project1.getNextJob());
		assertFalse(project1.isFinished());
	}

	/**
	 * Wenn in einem Projekt alle Tätigkeiten erledigt sind und die Subprojekte
	 * nicht fertig sind, wird die nächsten Tätigkeit auf null und das Projekt
	 * auf nicht erledigt gesetzt.
	 */
	@Test
	public void testRootProjectFinishedSubprojectNotFinished() {
		// Vorbereitung
		job1.setStatus(Status.FINISHED);
		job11.setStatus(Status.EDITING);
		// Test
		projectController.updateNextJobInAllProjects();
		assertNotNull(project11.getNextJob());
		assertEquals(job11, project11.getNextJob());
		assertFalse(project11.isFinished());
		assertNull(project1.getNextJob());
		assertFalse(project1.isFinished());
	}

	/**
	 * Wenn in einem Projekt alle Tätigkeiten nicht erledigt sind und die
	 * Subprojekte fertig sind, wird die nächsten Tätigkeit auf die erste
	 * unerledigte Tätigkeit und das Projekt auf nicht erledigt gesetzt.
	 */
	@Test
	public void testRootProjectNotFinishedSubprojectFinished() {
		// Vorbereitung
		job1.setStatus(Status.EDITING);
		job11.setStatus(Status.FINISHED);
		// Test
		projectController.updateNextJobInAllProjects();
		assertNull(project11.getNextJob());
		assertTrue(project11.isFinished());
		assertNotNull(project1.getNextJob());
		assertEquals(job1, project1.getNextJob());
		assertFalse(project1.isFinished());
	}

	/**
	 * Wenn in einem Projekt alle Tätigkeiten erledigt sind und die Subprojekte
	 * fertig sind, wird die nächsten Tätigkeit auf null und das Projekt auf
	 * erledigt gesetzt.
	 */
	@Test
	public void testRootProjectFinishedSubprojectFinished() {
		// Vorbereitung
		job1.setStatus(Status.FINISHED);
		job11.setStatus(Status.FINISHED);
		// Test
		projectController.updateNextJobInAllProjects();
		assertNull(project11.getNextJob());
		assertTrue(project11.isFinished());
		assertNull(project1.getNextJob());
		assertTrue(project1.isFinished());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Update der nächsten
	 * Tätigkeit in allen Projekten aufgerufen wird.
	 */
	@Test
	public void testRefreshProjectCalled() {
		// Vorbereitung
		AtomicBoolean refreshProjectCalled = new AtomicBoolean(false);
		projectController.addProjectTreeAUI(new ProjectTreeAUI() {

			@Override
			public void refreshProjectTree() {
			}

			@Override
			public void refreshProject() {
				refreshProjectCalled.set(true);
			}
		});
		// Test
		assertFalse(refreshProjectCalled.get());
		projectController.updateNextJobInAllProjects();
		assertTrue(refreshProjectCalled.get());
	}

}
