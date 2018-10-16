package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Testet den Konstruktor {@link JobController#JobController(GtdController)}.
 *
 * @author Florian
 */
public class JobControllerConstructorTest {

	/**
	 * Konstruktortest, wenn null übergeben wird, wird eine NullPointerException
	 * geworfen.
	 * 
	 * @throws Exception
	 *             {@link JobController#JobController(GtdController)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParameterNull() throws Exception {
		new JobController(null);
	}

	/**
	 * Konstruktortest.
	 */
	@Test
	public void test() {
		// Vorbereitung
		GtdController gtdController = new GtdController();
		// Test
		JobController jobController = new JobController(gtdController);
		try {
			Field fieldGtdController = JobController.class.getDeclaredField("gtdController");
			fieldGtdController.setAccessible(true);
			GtdController gtdControllerField = (GtdController) fieldGtdController.get(jobController);
			assertNotNull(gtdControllerField);
			assertEquals(gtdController, gtdControllerField);
		} catch (Exception exception) {
			System.out.println("Diese Exception sollte niemals auftreten.");
			exception.printStackTrace();
		}
	}

}
