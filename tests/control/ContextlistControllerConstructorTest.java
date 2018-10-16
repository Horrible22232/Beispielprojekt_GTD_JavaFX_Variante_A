package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Testet den Konstruktor
 * {@link ContextlistController#ContextlistController(GtdController)}.
 *
 * @author Florian
 */
public class ContextlistControllerConstructorTest {

	/**
	 * Konstruktortest, wenn null übergeben wird, wird eine NullPointerException
	 * geworfen.
	 * 
	 * @throws Exception
	 *             {@link ContextlistController#ContextlistController(GtdController)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParameterNull() throws Exception {
		new ContextlistController(null);
	}

	/**
	 * Konstruktortest.
	 */
	@Test
	public void test() {
		// Vorbereitung
		GtdController gtdController = new GtdController();
		// Test
		ContextlistController contextlistController = new ContextlistController(gtdController);
		try {
			Field fieldGtdController = ContextlistController.class.getDeclaredField("gtdController");
			fieldGtdController.setAccessible(true);
			GtdController gtdControllerField = (GtdController) fieldGtdController.get(contextlistController);
			assertNotNull(gtdControllerField);
			assertEquals(gtdController, gtdControllerField);
		} catch (Exception exception) {
			System.out.println("Diese Exception sollte niemals auftreten.");
			exception.printStackTrace();
		}
	}

}
