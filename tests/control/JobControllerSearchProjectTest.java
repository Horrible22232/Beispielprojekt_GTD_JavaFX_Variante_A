package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link JobController#searchProject(Job)}.
 *
 * @author Florian
 */
public class JobControllerSearchProjectTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private JobController jobController;
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
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		project = new Project("ProjectX", COMPLETE_UNTIL, DESCRIPTION);
		project.getJobs().add(job);
		TreeNode rootNode = gtdController.getGtd().getRootNode();
		// Erzeuge Projektstruktur
		for (int a = 0; a < 5; a++) {
			Project projectA = new Project("Project-" + a, COMPLETE_UNTIL, DESCRIPTION);
			rootNode.getSubprojects().add(projectA);
			for (int b = 0; b < 5; b++) {
				if (a == 3 && b == 3) {
					// Füge project für erfolgreiche Suche ein
					projectA.getSubprojects().add(project);
				} else {
					Project projectB = new Project("Project-" + a + "-" + b, COMPLETE_UNTIL, DESCRIPTION);
					projectA.getSubprojects().add(projectB);
				}
			}
		}
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() {
		jobController.searchProject(null);
	}

	/**
	 * Wenn eine Tätigkeit nicht in einem Projekt ist, sollte null zurückgegeben
	 * werden.
	 */
	@Test
	public void testJobNotInProject() {
		// Vorbereitung
		Job jobNotInProject = new Job("JobNotInProject", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		Project searchedProject = jobController.searchProject(jobNotInProject);
		assertNull(searchedProject);
	}

	/**
	 * Wenn eine Tätigkeit in einem Projekt ist, sollte dieses zurückgegeben
	 * werden.
	 */
	@Test
	public void testJobInProject() {
		Project searchedProject = jobController.searchProject(job);
		assertNotNull(searchedProject);
		assertEquals(project, searchedProject);
	}

}
