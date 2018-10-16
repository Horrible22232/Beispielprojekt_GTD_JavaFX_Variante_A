package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import abstractuserinterfaces.ProjectTreeAUI;
import exceptions.GtdProjectException;
import exceptions.ObjectNotInGtdException;
import model.Project;
import model.TreeNode;

/**
 * Testet die Methode {@link ProjectController#moveProject(Project, TreeNode)}.
 *
 * @author Florian
 */
public class ProjectControllerMoveProjectTest {
	private static final LocalDate COMPLETE_UNTIL = DateFactory.now();
	private static final String DESCRIPTION = "description";
	private ProjectController projectController;
	private TreeNode rootNode;
	private Project project1, project11, project12, project121, project122, project1221, project2, project21;

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
		project1 = new Project("RootProject1", COMPLETE_UNTIL, DESCRIPTION);
		project11 = new Project("Subproject11", COMPLETE_UNTIL, DESCRIPTION);
		project12 = new Project("Subproject12", COMPLETE_UNTIL, DESCRIPTION);
		project121 = new Project("SubSubproject121", COMPLETE_UNTIL, DESCRIPTION);
		project122 = new Project("SubSubproject122", COMPLETE_UNTIL, DESCRIPTION);
		project1221 = new Project("SubSubSubproject1221", COMPLETE_UNTIL, DESCRIPTION);
		project2 = new Project("RootProject2", COMPLETE_UNTIL, DESCRIPTION);
		project21 = new Project("Subproject21", COMPLETE_UNTIL, DESCRIPTION);

		rootNode.getSubprojects().add(project1);
		project1.getSubprojects().add(project11);
		project1.getSubprojects().add(project12);
		project12.getSubprojects().add(project121);
		project12.getSubprojects().add(project122);
		project122.getSubprojects().add(project1221);
		rootNode.getSubprojects().add(project2);
		project2.getSubprojects().add(project21);
	}

	/**
	 * Wenn der Parameter 'project' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = NullPointerException.class)
	public void testProjectNull() throws Exception {
		projectController.moveProject(null, project2);
	}

	/**
	 * Wenn das Objekt 'project' nicht in Gtd(Projektbaum) enthalten ist, wird
	 * eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testProjectNotInGtd() throws Exception {
		// Vorbereitung
		Project projectNotInGtd = new Project("ProjectNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		projectController.moveProject(projectNotInGtd, project2);
	}

	/**
	 * Wenn der Parameter 'newParent' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParentNull() throws Exception {
		projectController.moveProject(project1, null);
	}

	/**
	 * Wenn das Objekt 'newParent' (vom Typ Project) nicht in Gtd(Projektbaum)
	 * enthalten ist, wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testParentProjectNotInGtd() throws Exception {
		// Vorbereitung
		Project projectNotInGtd = new Project("ProjectNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		projectController.moveProject(project1, projectNotInGtd);
	}

	/**
	 * Wenn das Objekt 'newParent' (vom Typ TreeNode) nicht in Gtd(Projektbaum)
	 * enthalten ist, wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testParentTreeNodeNotInGtd() throws Exception {
		// Vorbereitung
		TreeNode treeNodeNotInGtd = new TreeNode("TreeNoteNotInGtd");
		// Test
		projectController.moveProject(project1, treeNodeNotInGtd);
	}

	/**
	 * Soll ein RootProjekt in den gleichen Vaterknoten verschoben werden, wird
	 * nichts geändert.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject1ToRootNode() throws Exception {
		ArrayList<Project> listBefore = new ArrayList<>(rootNode.getSubprojects());
		projectController.moveProject(project1, rootNode);
		assertEquals(listBefore, rootNode.getSubprojects());
	}

	/**
	 * Soll ein RootProjekt in sich selber berschoben werden, wird eine
	 * GtdProjectException gewofen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject1ToProject1() throws Exception {
		projectController.moveProject(project1, project1);
	}

	/**
	 * Wenn ein RootProjekt in ein Unterprorjekt (beliebiger Tiefe) verschoben
	 * werden soll, wird eine GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject1ToProject12() throws Exception {
		projectController.moveProject(project1, project12);
	}

	/**
	 * Wenn ein RootProjekt in ein Unterprorjekt (beliebiger Tiefe) verschoben
	 * werden soll, wird eine GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject1ToProject122() throws Exception {
		projectController.moveProject(project1, project122);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines RootProjekts in ein anderes
	 * Unterprojekt des gleichen Vaterprojekts.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject1ToProject2() throws Exception {
		assertsMoveInOtherParent(rootNode, project1, project2);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines RootProjekts in ein anderes
	 * Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject1ToProject21() throws Exception {
		assertsMoveInOtherParent(rootNode, project1, project21);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines Unterprojekts in die
	 * Projektbaumwurzel.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject12ToRootNode() throws Exception {
		assertsMoveInOtherParent(project1, project12, rootNode);
	}

	/**
	 * Soll ein Unterprojekt in den gleichen Vaterknoten verschoben werden, wird
	 * nichts geändert.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject12ToProject1() throws Exception {
		ArrayList<Project> listBefore = new ArrayList<>(project1.getSubprojects());
		projectController.moveProject(project12, project1);
		assertEquals(listBefore, project1.getSubprojects());
	}

	/**
	 * Testet das erfolgreiche Verschieben eines Unterprojekts in ein
	 * Unterprojekt des gleichen Vaterprojekts.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject12ToProject11() throws Exception {
		assertsMoveInOtherParent(project1, project12, project11);
	}

	/**
	 * Soll ein Unterprojekt in sich selber berschoben werden, wird eine
	 * GtdProjectException gewofen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject12ToProject12() throws Exception {
		projectController.moveProject(project12, project12);
	}

	/**
	 * Wenn ein Unterprojekt in ein Unterprorjekt (beliebiger Tiefe) verschoben
	 * werden soll, wird eine GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject12ToProject122() throws Exception {
		projectController.moveProject(project12, project122);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines Unterprojekts in ein anderes
	 * Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject12ToProject2() throws Exception {
		assertsMoveInOtherParent(project1, project12, project2);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines Unterprojekts in ein anderes
	 * Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject12ToProject21() throws Exception {
		assertsMoveInOtherParent(project1, project12, project21);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in die
	 * Projektbaumwurzel.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToRootNode() throws Exception {
		assertsMoveInOtherParent(project12, project122, rootNode);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in ein
	 * RootProjekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject1() throws Exception {
		assertsMoveInOtherParent(project12, project122, project1);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in ein
	 * anderes Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject11() throws Exception {
		assertsMoveInOtherParent(project12, project122, project11);
	}

	/**
	 * Soll ein Projekt beliebiger Tiefe in den gleichen Vaterknoten verschoben
	 * werden, wird nichts geändert.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject12() throws Exception {
		ArrayList<Project> listBefore = new ArrayList<>(project12.getSubprojects());
		projectController.moveProject(project122, project12);
		assertEquals(listBefore, project12.getSubprojects());
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in ein
	 * Unterprojekt des gleichen Vaterprojekts.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject121() throws Exception {
		assertsMoveInOtherParent(project12, project122, project121);
	}

	/**
	 * Soll ein Projekt in sich selber berschoben werden, wird eine
	 * GtdProjectException gewofen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject122ToProject122() throws Exception {
		projectController.moveProject(project122, project122);
	}

	/**
	 * Wenn ein Projekt beliebiger Tiefe in ein Unterprorjekt (beliebiger Tiefe)
	 * verschoben werden soll, wird eine GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testMoveProject122ToProject1221() throws Exception {
		projectController.moveProject(project122, project1221);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in ein
	 * anderes Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject2() throws Exception {
		assertsMoveInOtherParent(project12, project122, project2);
	}

	/**
	 * Testet das erfolgreiche Verschieben eines UnterUnterprojekts in ein
	 * anderes Projekt.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testMoveProject122ToProject21() throws Exception {
		assertsMoveInOtherParent(project12, project122, project21);
	}

	/**
	 * Hilfsfunktion mit asserts, die von den Testmethoden verwendet wird.
	 *
	 * @param oldParent
	 *            Der alter Vaterknoten.
	 * @param movingProject
	 *            Das zu verschiebene Projekt.
	 * @param newParent
	 *            Der neue Vaterknoten.
	 * @throws NullPointerException
	 * @throws ObjectNotInGtdException
	 * @throws GtdProjectException
	 */
	private void assertsMoveInOtherParent(TreeNode oldParent, Project movingProject, TreeNode newParent)
			throws NullPointerException, ObjectNotInGtdException, GtdProjectException {
		ArrayList<Project> oldParentSubprojects = oldParent.getSubprojects();
		ArrayList<Project> newParentSubprojects = newParent.getSubprojects();
		assertTrue(oldParentSubprojects.contains(movingProject));
		assertFalse(newParentSubprojects.contains(movingProject));
		projectController.moveProject(movingProject, newParent);
		assertFalse(oldParentSubprojects.contains(movingProject));
		assertTrue(newParentSubprojects.contains(movingProject));
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Verschieben des Projects
	 * aufgerufen werden.
	 *
	 * @throws Exception
	 *             {@link ProjectController#moveProject(Project, TreeNode)}
	 */
	@Test
	public void testRefreshProjectTreeCalled() throws Exception {
		// Vorbereitung
		AtomicBoolean refreshProjectTreeCalled = new AtomicBoolean(false);
		projectController.addProjectTreeAUI(new ProjectTreeAUI() {

			@Override
			public void refreshProjectTree() {
				refreshProjectTreeCalled.set(true);
			}

			@Override
			public void refreshProject() {
			}
		});
		// Test
		assertFalse(refreshProjectTreeCalled.get());
		projectController.moveProject(project1, project2);
		assertTrue(refreshProjectTreeCalled.get());
	}

}
