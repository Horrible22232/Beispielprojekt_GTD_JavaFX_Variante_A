package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import control.DateFactory;

/**
 * Testet die Klasse {@link Project}.
 *
 * @author Florian
 */
public class ProjectTest {
	private static final String DESCRIPTION = "description";
	private Project project;
	private Job job0;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		project = new Project("Project", DateFactory.now(), DESCRIPTION);
		job0 = new Job("Job0", DateFactory.now(), DESCRIPTION);
	}

	/**
	 * Testet den Projekt-Konstruktor.<br>
	 * Der NachsteSchritt sollte null und Jobs und Subprojects initialisiert
	 * sein.
	 */
	@Test
	public void testConstructor() {
		// Teste ob nextJob gesetzt wurde.
		assertNull(project.getNextJob());
		assertNotNull(project.getJobs());
		assertNotNull(project.getSubprojects());
	}

	/**
	 * Testet die für die Sortierung verwendete Vergleichsmethode.
	 */
	@Test
	public void testCompareTo() {
		// Vorbereitung
		Project project1 = new Project("Project1", DateFactory.tomorrow(), DESCRIPTION);
		Project project2a = new Project("Project2a", DateFactory.yesterday(), DESCRIPTION);
		Project project2b = new Project("Project2b", DateFactory.yesterday(), DESCRIPTION);
		Project projectX = new Project("ProjectX", null, DESCRIPTION);
		Project projectY = new Project("ProjectY", null, DESCRIPTION);
		// Test
		// Test: Datum A1 < A2
		assertTrue(project.compareTo(project1) < 0);
		assertTrue(project2a.compareTo(project1) < 0);
		assertTrue(project2a.compareTo(project) < 0);
		// Test: Datum A1 > A2
		assertTrue(project1.compareTo(project) > 0);
		assertTrue(project1.compareTo(project2a) > 0);
		assertTrue(project.compareTo(project2a) > 0);
		// Test: Datum A1 == A2
		assertEquals(0, project.compareTo(project));
		// Test: Datum gleich, Vergleich anhand Bezeichnung
		assertTrue(project2a.compareTo(project2b) < 0);
		// Sonderfall: Projekte ohne Endzeitpunkt werden alphabetisch am Ende
		// einsortiert.
		assertTrue(project.compareTo(projectX) < 0);
		assertTrue(project1.compareTo(projectX) < 0);
		assertTrue(project.compareTo(projectY) < 0);
		assertTrue(project1.compareTo(projectY) < 0);
		assertTrue(projectX.compareTo(projectY) < 0);
		assertTrue(projectY.compareTo(projectX) > 0);
	}

	/**
	 * Testet das Hinzufügen einer Tätigkeit in die nach completeUntil-Datum
	 * sortierte Liste der Tätigkeiten.
	 */
	@Test
	public void testAddJob() {
		// Vorbereitung
		ArrayList<Job> jobs = project.getJobs();
		Job job1 = new Job("Title1", DateFactory.tomorrow(), DESCRIPTION);
		Job job2a = new Job("Title2a", DateFactory.yesterday(), DESCRIPTION);
		Job job2b = new Job("Title2b", DateFactory.yesterday(), DESCRIPTION);
		// Test
		assertEquals(0, jobs.size());
		assertNull(project.getNextJob());
		assertFalse(project.isFinished());
		// add tomorrow-job
		project.addJob(job1);
		assertEquals(jobs, Arrays.asList(job1));
		assertEquals(1, jobs.size());
		assertEquals(job1, project.getNextJob());
		// add now-job
		project.addJob(job0);
		assertEquals(jobs, Arrays.asList(job0, job1));
		assertEquals(2, jobs.size());
		assertEquals(job0, project.getNextJob());
		// add yesterday-job
		project.addJob(job2b);
		assertEquals(jobs, Arrays.asList(job2b, job0, job1));
		assertEquals(3, jobs.size());
		assertEquals(job2b, project.getNextJob());
		// zwei jobs mit gleichem completeUntil
		project.addJob(job2a);
		assertEquals(jobs, Arrays.asList(job2a, job2b, job0, job1));
		assertEquals(4, jobs.size());
		assertEquals(job2a, project.getNextJob());
	}

	/**
	 * Testet das Entfernen einer Tätigkeit, die in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveJobContains() {
		// Vorbereitung
		ArrayList<Job> jobs = project.getJobs();
		jobs.add(job0);
		assertTrue(jobs.contains(job0));
		assertEquals(1, jobs.size());
		// Test
		project.removeJob(job0);
		assertFalse(jobs.contains(job0));
		assertEquals(0, jobs.size());
	}

	/**
	 * Testet das Entfernen einer Tätigkeit, die nicht in der Liste vorhanden
	 * ist.
	 */
	@Test
	public void testRemoveJobContainsNot() {
		// Test
		ArrayList<Job> jobs = project.getJobs();
		assertFalse(jobs.contains(job0));
		assertEquals(0, jobs.size());
		project.removeJob(job0);
		assertFalse(jobs.contains(job0));
		assertEquals(0, jobs.size());
	}

	/**
	 * Testet das entfernen aller Tätigkeiten in Projekten.<br>
	 * Bei unerledigten Projekten wird nichts entfernt.
	 */
	@Test
	public void testRemoveAllJobs() {
		// Vorbereitung
		Project subproject = new Project("Subproject", DateFactory.now(), DESCRIPTION);
		project.addSubproject(subproject);
		Job jobRp = new Job("Job1", DateFactory.now(), DESCRIPTION);
		Job jobSp = new Job("Job2", DateFactory.now(), DESCRIPTION);
		project.addJob(jobRp);
		subproject.addJob(jobSp);
		jobRp.setStatus(Status.EDITING);
		jobSp.setStatus(Status.EDITING);
		ArrayList<Job> jobsRootProject = project.getJobs();
		ArrayList<Job> jobsSubproject = subproject.getJobs();
		// Testvorbereitung
		assertFalse(jobsRootProject.isEmpty());
		assertFalse(jobsSubproject.isEmpty());
		subproject.updateFinishedStatus();
		project.updateFinishedStatus();
		// Test kein Entfernen
		project.removeAllJobs();
		assertFalse(jobsRootProject.isEmpty());
		assertFalse(jobsSubproject.isEmpty());
		// Testvorbereitung
		jobSp.setStatus(Status.FINISHED);
		subproject.updateFinishedStatus();
		project.updateFinishedStatus();
		// Test keine Jobs werden entfernt
		project.removeAllJobs();
		assertFalse(jobsRootProject.isEmpty());
		assertFalse(jobsSubproject.isEmpty());
		// Test alle Jobs im Subprojekt werden entfernt
		subproject.removeAllJobs();
		assertFalse(jobsRootProject.isEmpty());
		assertTrue(jobsSubproject.isEmpty());
		// Testreset
		subproject.addJob(jobSp);
		assertFalse(jobsRootProject.isEmpty());
		assertFalse(jobsSubproject.isEmpty());
		jobRp.setStatus(Status.FINISHED);
		subproject.updateFinishedStatus();
		project.updateFinishedStatus();
		// Test alle Jobs werden entfernt
		project.removeAllJobs();
		assertTrue(jobsRootProject.isEmpty());
		assertTrue(jobsSubproject.isEmpty());
	}

	/**
	 * Testet, ob nextJob in Projekten korrekt gesetzt wird.<br>
	 * Der nextJob wird auf die erste unerledigte Tätigkeit oder auf null
	 * gesetzt.
	 */
	@Test
	public void testUpdateNextJob() {
		// Vorbereitung
		Job job1 = new Job("Job1", DateFactory.tomorrow(), DESCRIPTION);
		Job job2 = new Job("Job2", DateFactory.tomorrow(), DESCRIPTION);
		// Test: keine Tätigkeiten -> kein nächster Schritt
		project.updateNextJob();
		assertNull(project.getNextJob());

		// Test: eine Tätigkeit hinzugefügt -> nextJob=neue Tätigkeit
		project.addJob(job2);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job2, project.getNextJob());

		// Test: zweite Tätigkeit hinzugefügt (gleiches Datum, Titel vor altem
		// Tätigkeitentitel)
		project.addJob(job1);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job1, project.getNextJob());

		// Test: dritte Tätigkeit hinzugefügt (kleinstes Datum)
		project.addJob(job0);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job0, project.getNextJob());

		// Test: nextJob auf nächste unerledigte Tätigkeit (job0) setzen.
		job1.setStatus(Status.FINISHED);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job0, project.getNextJob());

		// Test: nextJob auf nächste unerledigte Tätigkeit (job2) setzen.
		job0.setStatus(Status.FINISHED);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job2, project.getNextJob());

		// Test: nextJob auf nächste unerledigte Tätigkeit (job1) setzen.
		job1.setStatus(Status.EDITING);
		project.updateNextJob();
		assertNotNull(project.getNextJob());
		assertEquals(job1, project.getNextJob());

		// Test: keine unerledigten Tätigkeiten mehr -> nextJob=null
		job1.setStatus(Status.FINISHED);
		job2.setStatus(Status.FINISHED);
		project.updateNextJob();
		assertNull(project.getNextJob());
	}

	/**
	 * Testet, ob der Projektstatus korrekt berechnet wird.<br>
	 * Ist die Liste der Tätigkeiten leer, (mind.) eine der Tätigkeiten oder
	 * (mind.) ein Subprojekt nicht fertig, so ist auch das Projekt nicht
	 * fertig.
	 */
	@Test
	public void testUpdateFinishedStatus() {
		// Vorbereitung
		Project subproject = new Project("Subproject", DateFactory.now(), DESCRIPTION);
		project.addSubproject(subproject);
		Job jobRp = new Job("Job1", DateFactory.now(), DESCRIPTION);
		Job jobSp = new Job("Job2", DateFactory.now(), DESCRIPTION);
		project.addJob(jobRp);
		subproject.addJob(jobSp);
		jobRp.setStatus(Status.FINISHED);
		jobSp.setStatus(Status.FINISHED);
		// Test Enthaltende Tätigkeiten und Unterprojekte erledigt
		project.updateFinishedStatus();
		assertTrue(project.isFinished());
		// Test Enthaltende Tätigkeiten erledigt, Unterprojekte nicht erledigt
		jobSp.setStatus(Status.EDITING);
		project.updateFinishedStatus();
		assertFalse(project.isFinished());
		// Test Enthaltende Tätigkeiten nicht erledigt, Unterprojekte erledigt
		jobRp.setStatus(Status.EDITING);
		jobSp.setStatus(Status.FINISHED);
		project.updateFinishedStatus();
		assertFalse(project.isFinished());
		// Test Enthaltende Tätigkeiten nicht erledigt, Unterprojekte nicht
		// erledigt
		jobRp.setStatus(Status.EDITING);
		jobSp.setStatus(Status.EDITING);
		project.updateFinishedStatus();
		assertFalse(project.isFinished());
		// Test Enthaltende Tätigkeiten leer, Unterprojekte erledigt
		project.getJobs().clear();
		jobSp.setStatus(Status.FINISHED);
		project.updateFinishedStatus();
		assertFalse(project.isFinished());
	}

}
