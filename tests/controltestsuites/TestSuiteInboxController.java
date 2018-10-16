package controltestsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.InboxControllerConstructorTest;
import control.InboxControllerCreateNoteTest;
import control.InboxControllerRemoveNoteTest;

/**
 * TestSuite zum Starten aller InboxController-Tests.
 *
 * @author Florian
 */
@RunWith(Suite.class)
@SuiteClasses({ InboxControllerConstructorTest.class, InboxControllerCreateNoteTest.class,
		InboxControllerRemoveNoteTest.class, })
public class TestSuiteInboxController {

}
