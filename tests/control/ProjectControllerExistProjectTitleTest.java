package control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#existProjectTitle(String)}.
 *
 * @author Florian
 */
public class ProjectControllerExistProjectTitleTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
	private TreeNode rootNode;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		GtdController gtdController = new GtdController();
		projectController = gtdController.getProjectController();
		rootNode = gtdController.getGtd().getRootNode();
	}

	/**
	 * Testet ob false zurückgegeben wird, wenn nach einem Titel eines nicht in
	 * Gtd vorhandenen Projekte gesucht wird.
	 */
	@Test
	public void testProjectTitleExistNot() {
		assertFalse(projectController.existProjectTitle("ProjectNotExist"));
	}

	/**
	 * Testet, ob Projekte verschiedener Tiefe erfolgreich gefunden werden.
	 */
	@Test
	public void test() {
		// Vorbereitung
		Project project1 = new Project("Project1", COMPLETE_UNTIL, DESCRIPTION);
		Project project11 = new Project("Project11", COMPLETE_UNTIL, DESCRIPTION);
		Project project111 = new Project("Project111", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project1);
		project1.getSubprojects().add(project11);
		project11.getSubprojects().add(project111);
		// Test
		assertTrue(projectController.existProjectTitle("Project111"));
		assertTrue(projectController.existProjectTitle("Project11"));
		assertTrue(projectController.existProjectTitle("Project1"));
	}

}
