package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Testet den Konstruktor
 * {@link ProjectController#ProjectController(GtdController)}.
 *
 * @author Florian
 */
public class ProjectControllerConstructorTest {

	/**
	 * Konstruktortest, wenn null übergeben wird, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#ProjectController(GtdController)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParameterNull() throws Exception {
		new ProjectController(null);
	}

	/**
	 * Konstruktortest.
	 */
	@Test
	public void test() {
		// Vorbereitung
		GtdController gtdController = new GtdController();
		// Test
		ProjectController projectController = new ProjectController(gtdController);
		try {
			Field fieldGtdController = ProjectController.class.getDeclaredField("gtdController");
			fieldGtdController.setAccessible(true);
			GtdController gtdControllerField = (GtdController) fieldGtdController.get(projectController);
			assertNotNull(gtdControllerField);
			assertEquals(gtdController, gtdControllerField);
		} catch (Exception exception) {
			System.out.println("Diese Exception sollte niemals auftreten.");
			exception.printStackTrace();
		}
	}

}
