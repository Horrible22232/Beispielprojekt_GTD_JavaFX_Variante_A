package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#searchParentTreeNode(Project)}.
 *
 * @author Florian
 */
public class ProjectControllerSearchParentTreeNodeTest {
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
	 * Wenn ein Projekt nicht im Projektbaum ist, sollte null zurückgegeben
	 * werden.
	 */
	@Test
	public void testNotInProjectTree() {
		// Vorbereitung
		Project projectNotInTree = new Project("ProjectNotInTree", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		assertNull(projectController.searchParentTreeNode(projectNotInTree));
	}

	/**
	 * Wenn ein Projekt im Projektbaum ist, sollte der Vater(TreeNode)
	 * zurückgegeben werden.
	 */
	@Test
	public void testInProjectTree() {
		// Vorbereitung
		Project project0 = new Project("Project0", COMPLETE_UNTIL, DESCRIPTION);
		Project project1 = new Project("Project1", COMPLETE_UNTIL, DESCRIPTION);
		Project project11 = new Project("Project11", COMPLETE_UNTIL, DESCRIPTION);
		Project project111 = new Project("Project111", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(project0);
		rootNode.getSubprojects().add(project1);
		project1.getSubprojects().add(project11);
		project11.getSubprojects().add(project111);
		// Test
		assertEquals(rootNode, projectController.searchParentTreeNode(project0));
		assertEquals(rootNode, projectController.searchParentTreeNode(project1));
		assertEquals(project1, projectController.searchParentTreeNode(project11));
		assertEquals(project11, projectController.searchParentTreeNode(project111));
	}

}
