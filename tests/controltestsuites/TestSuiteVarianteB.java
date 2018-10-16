package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.JobControllerConstructorTest;
import control.JobControllerExistJobTitleTest;
import control.JobControllerRemoveJobFromProjectTest;
import control.JobControllerSearchContextlistTest;

/**
 * TestSuite zum Starten aller Tests für Variante B des Aufwärmprojekts.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ JobControllerConstructorTest.class, JobControllerExistJobTitleTest.class,
		JobControllerSearchContextlistTest.class, JobControllerRemoveJobFromProjectTest.class })
public class TestSuiteVarianteB {

}
