package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Testet den Konstruktor {@link InboxController#InboxController(GtdController)}
 * .
 *
 * @author Florian
 */
public class InboxControllerConstructorTest {

	/**
	 * Konstruktortest, wenn null übergeben wird, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link InboxController#InboxController(GtdController)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParameterNull() throws Exception {
		new InboxController(null);
	}

	/**
	 * Konstruktortest.
	 */
	@Test
	public void test() {
		// Vorbereitung
		GtdController gtdController = new GtdController();
		// Test
		InboxController inboxController = new InboxController(gtdController);
		try {
			Field fieldGtdController = InboxController.class.getDeclaredField("gtdController");
			fieldGtdController.setAccessible(true);
			GtdController gtdControllerField = (GtdController) fieldGtdController.get(inboxController);
			assertNotNull(gtdControllerField);
			assertEquals(gtdController, gtdControllerField);
		} catch (Exception exception) {
			System.out.println("Diese Exception sollte niemals auftreten.");
			exception.printStackTrace();
		}
	}

}
