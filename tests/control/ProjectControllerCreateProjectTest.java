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
import exceptions.EmptyStringParameterException;
import exceptions.GtdProjectException;
import exceptions.ObjectNotInGtdException;
import model.Project;
import model.TreeNode;

/**
 * Testet die Methode
 * {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}.
 *
 * @author Florian
 */
public class ProjectControllerCreateProjectTest {
	private static final String TITLE = "Project";
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
	 * Wenn der Parameter 'parent' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTreeNodeNull() throws Exception {
		projectController.createProject(null, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn das Objekt 'parent' vom Typ TreeNode nicht in Gtd(Projektbaum)
	 * enthalten ist, wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testTreeNodeParentNotInGtd() throws Exception {
		// Vorbereitung
		TreeNode treeNodeNotInGtd = new TreeNode("TreeNodeNotInGtd");
		// Test
		projectController.createProject(treeNodeNotInGtd, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn das Objekt 'parent' vom Typ Project nicht in Gtd(Projektbaum)
	 * enthalten ist, wird eine ObjectNotInGtdException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = ObjectNotInGtdException.class)
	public void testProjectParentNotInGtd() throws Exception {
		// Vorbereitung
		Project projectNotInGtd = new Project("ProjectNotInGtd", COMPLETE_UNTIL, DESCRIPTION);
		// Test
		projectController.createProject(projectNotInGtd, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'title' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testTitleNull() throws Exception {
		projectController.createProject(rootNode, null, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'title' leer ist, wird eine
	 * EmptyStringParameterException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = EmptyStringParameterException.class)
	public void testTitleEmpty() throws Exception {
		projectController.createProject(rootNode, "", COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn bereits ein Projekt mit dem gleichen Titel vorhanden ist, wird eine
	 * GtdProjectException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = GtdProjectException.class)
	public void testDuplicateProjectTitle() throws Exception {
		// Vorbereitung
		Project projectDuplicate = new Project(TITLE, COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(projectDuplicate);
		// Test
		projectController.createProject(rootNode, TITLE, COMPLETE_UNTIL, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'completeUntil' null ist, wird eine
	 * NullPointerException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testCompleteUntilNull() throws Exception {
		projectController.createProject(rootNode, TITLE, null, DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'completeUntil' in der Vergangeheit liegt, wird eine
	 * IllegalArgumentException geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompleteUntilInPast() throws Exception {
		projectController.createProject(rootNode, TITLE, DateFactory.yesterday(), DESCRIPTION);
	}

	/**
	 * Wenn der Parameter 'description' null ist, wird eine NullPointerException
	 * geworfen.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test(expected = NullPointerException.class)
	public void testDescriptionNull() throws Exception {
		projectController.createProject(rootNode, TITLE, COMPLETE_UNTIL, null);
	}

	/**
	 * Testet das erfolreiche Erzeugen eines (Root-)Projekts mit leerer
	 * Beschreibung.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test
	public void testCreationDescriptionEmpty() throws Exception {
		ArrayList<Project> subprojectsList = rootNode.getSubprojects();
		assertEquals(0, subprojectsList.size());
		projectController.createProject(rootNode, TITLE, COMPLETE_UNTIL, "");
		assertEquals(1, subprojectsList.size());
		assertEquals(TITLE, subprojectsList.get(0).getTitle());
	}

	/**
	 * Testet das erfolreiche Erzeugen eines (Root-)Projekts mit nicht leerer
	 * Beschreibung.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test
	public void testCreationRootProject() throws Exception {
		ArrayList<Project> parentSubprojects = rootNode.getSubprojects();
		assertEquals(0, parentSubprojects.size());
		projectController.createProject(rootNode, TITLE, COMPLETE_UNTIL, DESCRIPTION);
		assertEquals(1, parentSubprojects.size());
		assertEquals(TITLE, parentSubprojects.get(0).getTitle());
	}

	/**
	 * Testet das erfolreiche Erzeugen eines Subprojekts.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
	 */
	@Test
	public void testCreationSubproject() throws Exception {
		// Vorbereitung
		Project rootProject = new Project("RootProject", COMPLETE_UNTIL, DESCRIPTION);
		rootNode.getSubprojects().add(rootProject);
		// Test
		ArrayList<Project> parentSubprojects = rootProject.getSubprojects();
		assertEquals(0, parentSubprojects.size());
		projectController.createProject(rootProject, TITLE, COMPLETE_UNTIL, DESCRIPTION);
		assertEquals(1, parentSubprojects.size());
		assertEquals(TITLE, parentSubprojects.get(0).getTitle());
	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Erstellen und Einfügen
	 * eines Projekts aufgerufen werden.
	 *
	 * @throws Exception
	 *             {@link ProjectController#createProject(TreeNode, String, LocalDate, String)}
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
		projectController.createProject(rootNode, TITLE, COMPLETE_UNTIL, DESCRIPTION);
		assertTrue(refreshProjectTreeCalled.get());
	}

}
