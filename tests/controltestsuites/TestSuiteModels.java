package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import model.ContextlistTest;
import model.GtdTest;
import model.InboxTest;
import model.JobTest;
import model.ProjectTest;
import model.TreeNodeTest;

/**
 * TestSuite zum Starten aller Model-Klassen-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ ContextlistTest.class, GtdTest.class, InboxTest.class, JobTest.class, ProjectTest.class,
		TreeNodeTest.class, })
public class TestSuiteModels {

}
