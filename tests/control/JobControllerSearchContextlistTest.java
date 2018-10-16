package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Contextlist;
import model.Job;

/**
 * Testet die Methode {@link JobController#searchContextlist(Job)}.
 *
 * @author Florian
 */
public class JobControllerSearchContextlistTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private JobController jobController;
	private Contextlist contextlist;
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
		ArrayList<Contextlist> contextlists = gtdController.getGtd().getContextlists();
		for (int a = 0; a < 10; a++) {
			Contextlist contextlistX = new Contextlist("Contextlist" + a);
			contextlists.add(contextlistX);
			for (int b = 0; b < 10; b++) {
				contextlistX.getJobs().add(new Job("Job-" + a + "-" + b, COMPLETE_UNTIL, DESCRIPTION));
			}
		}
		contextlist = new Contextlist("Contextlist");
		contextlists.add(contextlist);
		job = new Job("Job", COMPLETE_UNTIL, DESCRIPTION);
		contextlist.getJobs().add(job);
	}

	/**
	 * Wenn der Parameter 'job' null ist, wird eine NullPointerException
	 * geworfen.
	 */
	@Test(expected = NullPointerException.class)
	public void testJobNull() {
		jobController.searchContextlist(null);
	}

	/**
	 * Wenn eine Tätigkeit nicht in einer Kontextliste ist, sollte null
	 * zurückgegeben werden.
	 */
	@Test
	public void testJobNotInContextlist() {
		// Vorbereitung
		Job jobNotInContextlist = new Job("JobNotInContextlist", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		Contextlist searchedContextlist = jobController.searchContextlist(jobNotInContextlist);
		assertNull(searchedContextlist);
	}

	/**
	 * Wenn eine Tätigkeit in einer Kontextliste ist, sollte diese zurückgegeben
	 * werden.
	 */
	@Test
	public void testJobInContextlist() {
		Contextlist searchedContextlist = jobController.searchContextlist(job);
		assertNotNull(searchedContextlist);
		assertEquals(contextlist, searchedContextlist);
	}

}
