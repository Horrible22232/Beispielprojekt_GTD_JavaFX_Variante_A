package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.ContextlistControllerCreateContextlistTest;
import control.ContextlistControllerExistContextlistTitleTest;
import control.ContextlistControllerRemoveFinishedJobsContextlistsTest;

/**
 * TestSuite zum Starten aller Tests für Variante A des Aufwärmprojekts.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ ContextlistControllerExistContextlistTitleTest.class,
		ContextlistControllerCreateContextlistTest.class,
		ContextlistControllerRemoveFinishedJobsContextlistsTest.class })
public class TestSuiteVarianteA {

}
