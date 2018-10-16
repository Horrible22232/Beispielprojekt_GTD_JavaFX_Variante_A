package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.JobControllerConstructorTest;
import control.JobControllerCreateJobTest;
import control.JobControllerDelegateJobTest;
import control.JobControllerExistJobTitleTest;
import control.JobControllerMoveJobToContextlistTest;
import control.JobControllerMoveJobToProjectTest;
import control.JobControllerRemoveJobFromProjectTest;
import control.JobControllerSearchContextlistTest;
import control.JobControllerSearchProjectTest;
import control.JobControllerSetJobStatusTest;

/**
 * TestSuite zum Starten aller JobController-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ JobControllerConstructorTest.class, JobControllerCreateJobTest.class,
		JobControllerDelegateJobTest.class, JobControllerExistJobTitleTest.class,
		JobControllerMoveJobToContextlistTest.class, JobControllerMoveJobToProjectTest.class,
		JobControllerRemoveJobFromProjectTest.class, JobControllerSearchContextlistTest.class,
		JobControllerSearchProjectTest.class, JobControllerSetJobStatusTest.class, })
public class TestSuiteJobController {

}
