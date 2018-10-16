package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import model.Gtd;

/**
 * Testet die Methode {@link GtdController#setGtd(Gtd)}.
 *
 * @author Florian
 */
public class GtdControllerSetGtdTest {
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
	 * Wenn 'gtd' null ist, wird eine NullPointerException geworfen.
	 */
	@Test(expected = NullPointerException.class)
	public void testGtdNull() {
		gtdController.setGtd(null);
	}

	/**
	 * Testet, ob das neue Gtd-Objekt erfolgreich gesetzt wird.
	 */
	@Test
	public void test() {
		Gtd oldGtd = gtdController.getGtd();
		Gtd createdGtd = new Gtd();
		gtdController.setGtd(createdGtd);
		assertNotNull(gtdController.getGtd());
		assertNotEquals(oldGtd, gtdController.getGtd());
		assertEquals(createdGtd, gtdController.getGtd());
	}

}
