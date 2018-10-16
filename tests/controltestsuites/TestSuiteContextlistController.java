package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.ContextlistControllerConstructorTest;
import control.ContextlistControllerCreateContextlistTest;
import control.ContextlistControllerExistContextlistTitleTest;
import control.ContextlistControllerRemoveContextlistTest;
import control.ContextlistControllerRemoveFinishedJobsContextlistsTest;

/**
 * TestSuite zum Starten aller ContextlistController-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ ContextlistControllerConstructorTest.class, ContextlistControllerCreateContextlistTest.class,
		ContextlistControllerExistContextlistTitleTest.class, ContextlistControllerRemoveContextlistTest.class,
		ContextlistControllerRemoveFinishedJobsContextlistsTest.class, })
public class TestSuiteContextlistController {

}
