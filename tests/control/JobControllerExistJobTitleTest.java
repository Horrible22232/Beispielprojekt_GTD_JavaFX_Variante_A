package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.EmptyStringParameterException;
import model.Contextlist;
import model.Job;

/**
 * Testet die Methode {@link JobController#existJobTitle(Contextlist, String)}.
 *
 * @author Florian
 */
public class JobControllerExistJobTitleTest {
	private static final String TITLE = "Job";
	private JobController jobController;
	private Contextlist contextlist;

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
		for (int i = 0; i < 10; i++) {
			contextlists.add(new Contextlist("Contextlist" + i));
		}
		contextlist = new Contextlist("Contextlist");
		contextlists.add(contextlist);
		contextlist.getJobs().add(new Job(TITLE, DateFactory.now(), "description"));
	}

	/**
	 * Wenn der Parameter 'contextlist' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testContextlistNull() throws Exception {
		jobController.existJobTitle(null, TITLE);
	}

	/**
	 * Wenn der Parameter 'title' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTitleNull() throws Exception {
		jobController.existJobTitle(contextlist, null);
	}

	/**
	 * Wenn der Parameter 'title' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTitleEmpty() throws Exception {
		jobController.existJobTitle(contextlist, "");
	}

	/**
	 * Testet ob false zurückgegeben wird, wenn nach einem Titel einer nicht
	 * enthaltenden Tätigkeit gesucht wird.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test
	public void testJobWithTitleExistNot() throws Exception {
		assertFalse(jobController.existJobTitle(contextlist, "JobExistNot"));
	}

	/**
	 * Testet, ob eine Tätigkeit erfolgreich gefunden wird.
	 *
	 * @throws Exception
	 *             {@link ContextlistController#existContextlistTitle(String)}
	 */
	@Test
	public void testJobWithTitleExist() throws Exception {
		assertTrue(jobController.existJobTitle(contextlist, TITLE));
	}

}
