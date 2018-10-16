package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.GtdControllerCleanUpTest;
import control.GtdControllerConstructorTest;
import control.GtdControllerSetGtdTest;

/**
 * TestSuite zum Starten aller GtdController-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ GtdControllerCleanUpTest.class, GtdControllerConstructorTest.class, GtdControllerSetGtdTest.class, })
public class TestSuiteGtdController {

}
