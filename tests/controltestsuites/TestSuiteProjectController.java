package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.ProjectControllerConstructorTest;
import control.ProjectControllerCreateProjectTest;
import control.ProjectControllerExistProjectTitleTest;
import control.ProjectControllerGetFinishedRootProjectsTest;
import control.ProjectControllerGetTreeNodePathTest;
import control.ProjectControllerMoveProjectTest;
import control.ProjectControllerRemoveProjectTest;
import control.ProjectControllerSearchParentTreeNodeTest;
import control.ProjectControllerUpdateNextJobInAllProjectsTest;

/**
 * TestSuite zum Starten aller ProjectController-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ ProjectControllerConstructorTest.class, ProjectControllerCreateProjectTest.class,
		ProjectControllerExistProjectTitleTest.class, ProjectControllerGetFinishedRootProjectsTest.class,
		ProjectControllerGetTreeNodePathTest.class, ProjectControllerMoveProjectTest.class,
		ProjectControllerRemoveProjectTest.class, ProjectControllerSearchParentTreeNodeTest.class,
		ProjectControllerUpdateNextJobInAllProjectsTest.class, })
public class TestSuiteProjectController {

}
