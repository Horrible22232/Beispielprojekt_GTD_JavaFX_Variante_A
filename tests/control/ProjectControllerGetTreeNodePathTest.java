package control;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#getTreeNodePath(TreeNode)}.
 *
 * @author Florian
 */
public class ProjectControllerGetTreeNodePathTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
	private TreeNode rootNode;
	private Project rootProject1, subproject10, subproject11, subSubproject111, rootProject2;
	private ArrayList<TreeNode> expected, actual;

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
		rootProject1 = new Project("RootProject1", COMPLETE_UNTIL, DESCRIPTION);
		subproject10 = new Project("Subproject10", COMPLETE_UNTIL, DESCRIPTION);
		subproject11 = new Project("Subproject11", COMPLETE_UNTIL, DESCRIPTION);
		subSubproject111 = new Project("SubSubproject111", COMPLETE_UNTIL, DESCRIPTION);
		rootProject2 = new Project("RootProject2", COMPLETE_UNTIL, DESCRIPTION);

		rootNode.getSubprojects().add(rootProject1);
		rootNode.getSubprojects().add(rootProject2);
		rootProject1.getSubprojects().add(subproject10);
		rootProject1.getSubprojects().add(subproject11);
		subproject11.getSubprojects().add(subSubproject111);
	}

	/**
	 * Testet, ob der korrekte Pfad für die projektbaumwurzel zurückgegeben
	 * wird.
	 */
	@Test
	public void testRootNode() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode));
		// Test
		actual = projectController.getTreeNodePath(rootNode);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für ein RootProjekt zurückgegeben wird.
	 */
	@Test
	public void testRootProject2() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode, rootProject2));
		// Test
		actual = projectController.getTreeNodePath(rootProject2);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für ein RootProjekt zurückgegeben wird.
	 */
	@Test
	public void testRootProject1() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode, rootProject1));
		// Test
		actual = projectController.getTreeNodePath(rootProject1);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für ein Unterprojekt zurückgegeben wird.
	 */
	@Test
	public void testSubproject10() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode, rootProject1, subproject10));
		// Test
		actual = projectController.getTreeNodePath(subproject10);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für ein Unterprojekt zurückgegeben wird.
	 */
	@Test
	public void testSubproject11() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode, rootProject1, subproject11));
		// Test
		actual = projectController.getTreeNodePath(subproject11);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für ein UnterUnterprojekt zurückgegeben
	 * wird.
	 */
	@Test
	public void testSubSubproject111() {
		// Vorbereitung
		expected = new ArrayList<>(Arrays.asList(rootNode, rootProject1, subproject11, subSubproject111));
		// Test
		actual = projectController.getTreeNodePath(subSubproject111);
		assertEquals(expected, actual);
	}

	/**
	 * Testet, ob der korrekte Pfad für einen nicht im Projektbaum vorhandenen
	 * Projektbaumknoten zurückgegeben wird (erwartet: leerer Pfad).
	 */
	@Test
	public void testProjectNotInTree() {
		// Vorbereitung
		Project projectNotInTree = new Project("ProjectNotInTree", COMPLETE_UNTIL, DESCRIPTION);
		expected = new ArrayList<>(Arrays.asList());
		// Test
		actual = projectController.getTreeNodePath(projectNotInTree);
		assertEquals(expected, actual);
	}

}
