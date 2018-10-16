package control;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet den Konstruktor {@link GtdController#GtdController()}.
 *
 * @author Florian
 */
public class GtdControllerConstructorTest {
	private GtdController gtdController;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		gtdController = new GtdController();
	}

	/**
	 * Testet den GtdController-Konstruktor.<br>
	 * Alle anderen Konstruktoren sollten initialisiert und neues Gtd-Objekt
	 * erzeugt sein.
	 */
	@Test
	public void test() {
		assertNotNull(gtdController.getContextlistController());
		assertNotNull(gtdController.getInboxController());
		assertNotNull(gtdController.getInboxController());
		assertNotNull(gtdController.getIOController());
		assertNotNull(gtdController.getJobController());
		assertNotNull(gtdController.getProjectController());
		assertNotNull(gtdController.getGtd());
	}

}
