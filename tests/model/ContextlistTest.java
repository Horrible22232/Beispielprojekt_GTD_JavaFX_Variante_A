package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import control.DateFactory;

/**
 * Testet die Klasse {@link Contextlist}.
 *
 * @author Florian
 */
public class ContextlistTest {
	private static final int COUNT_ELEMENTS = 5;
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "Test-Kontextliste";
	private Contextlist contextlist;
	private Job job0;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		contextlist = new Contextlist(TITLE);
		job0 = new Job("Job0", DateFactory.now(), DESCRIPTION);
	}

	/**
	 * Testen des Contextlist-Konstruktors.<br>
	 * Die Liste der Taetigkeiten wurde erstellt und ist leer.
	 */
	@Test
	public void testConstructor() {
		// Teste ob Liste initialisiert wurden.
		assertNotNull(contextlist.getJobs());
		assertEquals(TITLE, contextlist.getTitle());
		assertTrue(contextlist.isEmpty());
	}

	/**
	 * Testet die für die Sortierung verwendete Vergleichsmethode.
	 */
	@Test
	public void testCompareTo() {
		// Vorbereitung
		int numberOfElements = 5;
		ArrayList<Contextlist> contextlists = new ArrayList<Contextlist>();
		contextlists.add(new Contextlist("Kontextliste0"));
		Contextlist contextlist0b = new Contextlist("Kontextliste0");
		for (int index = 1; index < numberOfElements; index++) {
			contextlists.add(new Contextlist("Kontextliste" + index));
		}
		// Test:
		for (int i = 1; i < contextlists.size(); i++) {
			// A1 < A2
			assertEquals(-1, contextlists.get(i - 1).compareTo(contextlists.get(i)));
			// A1 > A2
			assertEquals(1, contextlists.get(i).compareTo(contextlists.get(i - 1)));
		}
		// Test: A1 = A2
		assertEquals(0, contextlists.get(0).compareTo(contextlist0b));
		for (int i = 0; i < contextlists.size(); i++) {
			assertEquals(0, contextlists.get(i).compareTo(contextlists.get(i)));
		}
	}

	/**
	 * Testet das Hinzufügen einer Tätigkeit in die nach completeUntil-Datum
	 * sortierte Liste der Tätigkeiten.
	 */
	@Test
	public void testAddJob() {
		// Vorbereitung
		ArrayList<Job> jobs = contextlist.getJobs();
		Job job1 = new Job("Title1", DateFactory.tomorrow(), DESCRIPTION);
		Job job2a = new Job("Title2a", DateFactory.yesterday(), DESCRIPTION);
		Job job2b = new Job("Title2b", DateFactory.yesterday(), DESCRIPTION);
		// Test
		assertEquals(0, jobs.size());
		// add tomorrow-job
		contextlist.addJob(job1);
		assertEquals(job1, jobs.get(0));
		assertEquals(1, jobs.size());
		// add now-job
		contextlist.addJob(job0);
		assertEquals(job0, jobs.get(0));
		assertEquals(job1, jobs.get(1));
		assertEquals(2, jobs.size());
		// add yesterday-job
		contextlist.addJob(job2b);
		assertEquals(job2b, jobs.get(0));
		assertEquals(job0, jobs.get(1));
		assertEquals(job1, jobs.get(2));
		assertEquals(3, jobs.size());
		// zwei jobs mit gleichem completeUntil
		contextlist.addJob(job2a);
		assertEquals(job2a, jobs.get(0));
		assertEquals(job2b, jobs.get(1));
		assertEquals(job0, jobs.get(2));
		assertEquals(job1, jobs.get(3));
		assertEquals(4, jobs.size());
	}

	/**
	 * Testet das Entfernen einer Tätigkeit, die in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveJobContains() {
		// Vorbereitung
		ArrayList<Job> jobs = contextlist.getJobs();
		jobs.add(job0);
		assertTrue(jobs.contains(job0));
		assertEquals(1, jobs.size());
		// Test
		contextlist.removeJob(job0);
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
		ArrayList<Job> jobs = contextlist.getJobs();
		assertFalse(jobs.contains(job0));
		assertEquals(0, jobs.size());
		contextlist.removeJob(job0);
		assertFalse(jobs.contains(job0));
		assertEquals(0, jobs.size());
	}

	/**
	 * Testet das Entfernen aller erledigten Taetigkeiten in der Kontextliste
	 * mit leerer Tätigkeitenliste.
	 */
	@Test
	public void testRemoveAllFinishedJobsEmpty() {
		// Test
		assertEquals(0, contextlist.removeAllFinishedJobs());
	}

	/**
	 * Testet das Entfernen aller erledigten Taetigkeiten in der Kontextliste.
	 * <br>
	 * Nach dem Methodenaufruf sollte alle erledigten Tätigkeiten entfernt
	 * worden sein.
	 */
	@Test
	public void testRemoveAllFinishedJobsAllFinished() {
		// Vorbereitung
		for (int i = 0; i < COUNT_ELEMENTS; i++) {
			Job job = new Job("Title" + i, LocalDate.now(), "description");
			job.setStatus(Status.FINISHED);
			assertTrue(job.isFinished());
			contextlist.addJob(job);
		}
		// Test
		assertEquals(COUNT_ELEMENTS, contextlist.removeAllFinishedJobs());
	}

	/**
	 * Testet das Entfernen aller erledigten Taetigkeiten in der Kontextliste.
	 * <br>
	 * Nach dem Methodenaufruf sollte sich die Tätigkeitenliste mit nur
	 * unerledigten Tätigkeiten nicht geändert haben.
	 */
	@Test
	public void testRemoveAllFinishedJobsNoneFinished() {
		// Vorbereitung
		for (int i = 0; i < COUNT_ELEMENTS; i++) {
			Job job = new Job("Title" + i, LocalDate.now(), "");
			job.setStatus(Status.EDITING);
			assertFalse(job.isFinished());
			contextlist.addJob(job);
		}
		// Test
		assertEquals(COUNT_ELEMENTS, contextlist.getJobs().size());
		assertEquals(0, contextlist.removeAllFinishedJobs());
	}

	/**
	 * Testet das Entfernen aller erledigten Taetigkeiten in der Kontextliste.
	 * <br>
	 * Nach dem Methodenaufruf sollte die Kontextliste nur Nicht-Erledigte
	 * Taetigkeiten enthalten und alle entfernten Taetigkeiten sollten den
	 * Status erledigt haben.
	 */
	@Test
	public void testRemoveAllFinishedJobsMixed() {
		// Vorbereitung
		ArrayList<Job> compareList = new ArrayList<>();
		ArrayList<Job> jobs = contextlist.getJobs();
		for (int i = 0; i < COUNT_ELEMENTS * 3; i++) {
			Job job = new Job("Title" + i, LocalDate.now(), "");
			contextlist.addJob(job);
			compareList.add(job);
			// Jede 3. Taetigkeit wird auf FINISHED gesetzt.
			if (i % 3 == 0) {
				job.setStatus(Status.FINISHED);
				assertTrue(job.isFinished());
			} else {
				assertFalse(job.isFinished());
			}
		}
		// Test
		int removedFinishedJobs = contextlist.removeAllFinishedJobs();
		// COUNT_ELEMENTS sollten entfernt worden sein.
		assertEquals(COUNT_ELEMENTS, removedFinishedJobs);
		// Liste sollte noch 2*COUNT_ELEMENTS Jobs enthalten.
		assertEquals(COUNT_ELEMENTS * 2, contextlist.getJobs().size());
		// Jede erledigte Tätigkeit sollte nicht in der Kontextliste sein, jeder
		// unerledigte schon.
		for (int i = 0; i < compareList.size(); i++) {
			assertEquals(i % 3 > 0, jobs.contains(compareList.get(i)));
		}
	}

}
