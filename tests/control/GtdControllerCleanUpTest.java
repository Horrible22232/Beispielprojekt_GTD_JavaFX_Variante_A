package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Contextlist;
import model.Gtd;
import model.Job;
import model.Project;
import model.Status;

/**
 * Testet die Methode {@link GtdController#cleanUp()}.
 *
 * @author Florian
 */
public class GtdControllerCleanUpTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private GtdController gtdController;
	private Gtd gtd;
	private Contextlist contextlist;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		gtdController = new GtdController();
		gtd = gtdController.getGtd();
		contextlist = new Contextlist("Contextlist");
		gtd.getContextlists().add(contextlist);
	}

	/**
	 * Bei einem leerem (ohne Projekte und Tätigkeiten) oder neuen Gtd sollte
	 * nichts entfernt werden.
	 */
	@Test
	public void testEmptyGtd() {
		CleanUpInfos cleanUpInfos = gtdController.cleanUp();
		asserts(cleanUpInfos, 0, 0);
	}

	/**
	 * Methode mit Asserts, die von den Test-Methoden verwendet werden (die
	 * gleichen Asserts)
	 *
	 * @param cleanUpInfos
	 *            Die erstellten CleanUpInfos
	 * @param expectedRemovedJobs
	 *            Die erwartete Anzahl entfernter Tätigkeiten.
	 * @param expectedFinishedProjects
	 *            Die erwartete Anzahl fertiger (Root-)Projekte.
	 */
	private void asserts(CleanUpInfos cleanUpInfos, int expectedRemovedJobs, int expectedFinishedProjects) {
		assertNotNull(cleanUpInfos);
		assertEquals(expectedRemovedJobs, cleanUpInfos.getRemovedJobs());
		assertNotNull(cleanUpInfos.getFinishedProjects());
		assertEquals(expectedFinishedProjects, cleanUpInfos.getFinishedProjects().getSubprojects().size());
	}

	/**
	 * Testet, ob beim Aufräumen alle erledigten Tätigkeiten gefunden, entfernt
	 * und gezählt werden.
	 */
	@Test
	public void testJobsCleanUp() {
		int number = 10;
		// Vorbereitung
		ArrayList<Job> tmp = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Job job = new Job("Job" + i, COMPLETE_UNTIL, DESCRIPTION);
			contextlist.getJobs().add(job);
			tmp.add(job);
		}
		Job job = new Job("JobE", COMPLETE_UNTIL, DESCRIPTION);
		contextlist.getJobs().add(job);
		// Test no finished jobs
		CleanUpInfos cleanUpInfos = gtdController.cleanUp();
		assertEquals(number + 1, contextlist.getJobs().size());
		asserts(cleanUpInfos, 0, 0);
		// Test finished jobs
		for (Job e : tmp) {
			e.setStatus(Status.FINISHED);
		}
		cleanUpInfos = gtdController.cleanUp();
		assertEquals(1, contextlist.getJobs().size());
		asserts(cleanUpInfos, number, 0);
	}

	/**
	 * Testet, ob beim Aufräumen alle fertigen (Root-)Projekte gefunden werden.
	 */
	@Test
	public void testProjectsCleanUp() {
		// Vorbereitun
		Project rootProject = new Project("Project", COMPLETE_UNTIL, DESCRIPTION);
		gtd.getRootNode().getSubprojects().add(rootProject);
		Job job1 = new Job("Job1", COMPLETE_UNTIL, DESCRIPTION);
		Job job2 = new Job("Job2", COMPLETE_UNTIL, DESCRIPTION);
		rootProject.getJobs().add(job1);
		rootProject.getJobs().add(job2);
		contextlist.getJobs().add(job1);
		contextlist.getJobs().add(job2);
		// Test no finished jobs
		CleanUpInfos cleanUpInfos = gtdController.cleanUp();
		asserts(cleanUpInfos, 0, 0);
		// Test finished job1
		job1.setStatus(Status.FINISHED);
		cleanUpInfos = gtdController.cleanUp();
		asserts(cleanUpInfos, 1, 0);
		// Test finished job1 & job2 -> rootProject finished
		contextlist.getJobs().add(job1);
		job2.setStatus(Status.FINISHED);
		cleanUpInfos = gtdController.cleanUp();
		asserts(cleanUpInfos, 2, 1);
	}

}
