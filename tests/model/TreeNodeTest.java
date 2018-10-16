package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import control.DateFactory;

/**
 * Testet die Klasse {@link TreeNode}.
 *
 * @author Florian
 */
public class TreeNodeTest {
	private static final String DESCRIPTION = "description";
	private TreeNode treeNode;
	private ArrayList<Project> subprojects;
	private Project project0;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		treeNode = new TreeNode();
		subprojects = treeNode.getSubprojects();
		project0 = new Project("Project0", DateFactory.now(), DESCRIPTION);
	}

	/**
	 * Testet den TreeNode-Konstruktor.<br>
	 * Die Liste der Projekte wurde erstellt und ist leer.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(subprojects);
		assertTrue(subprojects.isEmpty());
	}

	/**
	 * Testet das Hinzufügen einer Projekts in die nach completeUntil-Datum
	 * sortierte Liste der Subprojekte.
	 */
	@Test
	public void testAddSubproject() {
		// Vorbereitung
		Project project1 = new Project("Project1", DateFactory.tomorrow(), DESCRIPTION);
		Project project2a = new Project("Project2a", DateFactory.yesterday(), DESCRIPTION);
		Project project2b = new Project("Project2b", DateFactory.yesterday(), DESCRIPTION);
		// Test
		assertEquals(0, subprojects.size());
		// add tomorrow-Projekt
		treeNode.addSubproject(project1);
		assertEquals(project1, subprojects.get(0));
		assertEquals(1, subprojects.size());
		// add now-Projekt
		treeNode.addSubproject(project0);
		assertEquals(project0, subprojects.get(0));
		assertEquals(project1, subprojects.get(1));
		assertEquals(2, subprojects.size());
		// add yesterday-Projekt
		treeNode.addSubproject(project2b);
		assertEquals(project2b, subprojects.get(0));
		assertEquals(project0, subprojects.get(1));
		assertEquals(project1, subprojects.get(2));
		assertEquals(3, subprojects.size());
		// zwei Projekt mit gleichem completeUntil
		treeNode.addSubproject(project2a);
		assertEquals(project2a, subprojects.get(0));
		assertEquals(project2b, subprojects.get(1));
		assertEquals(project0, subprojects.get(2));
		assertEquals(project1, subprojects.get(3));
		assertEquals(4, subprojects.size());
	}

	/**
	 * Testet das Entfernen eines Subprojekts, das in der Liste vorhanden ist.
	 */
	@Test
	public void testRemoveSubprojectContains() {
		// Vorbereitung
		subprojects.add(project0);
		assertTrue(subprojects.contains(project0));
		assertEquals(1, subprojects.size());
		// Test
		treeNode.removeSubproject(project0);
		assertFalse(subprojects.contains(project0));
		assertEquals(0, subprojects.size());
	}

	/**
	 * Testet das Entfernen eines Subprojekts, das nicht in der Liste vorhanden
	 * ist.
	 */
	@Test
	public void testRemoveSubprojectContainsNot() {
		// Test
		assertFalse(subprojects.contains(project0));
		assertEquals(0, subprojects.size());
		treeNode.removeSubproject(project0);
		assertFalse(subprojects.contains(project0));
		assertEquals(0, subprojects.size());
	}

}
