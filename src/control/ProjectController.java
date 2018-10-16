package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import abstractuserinterfaces.ProjectTreeAUI;
import exceptions.EmptyStringParameterException;
import exceptions.GtdProjectException;
import exceptions.ObjectNotInGtdException;
import model.Project;
import model.TreeNode;

/**
 * Controller für Funktionen zur Verwaltung der Projekte.
 *
 * @author Florian
 */
public class ProjectController {
	private static final int TREEPATH_MINSIZE_CONTAINS_TREENODE = 1;
	/**
	 * Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * Controllern dient.
	 */
	private GtdController gtdController;
	/**
	 * Liste von {@link ProjectTreeAUI}-AbstractUserInterfaces.
	 */
	private CopyOnWriteArraySet<ProjectTreeAUI> projectTreeAUIs = new CopyOnWriteArraySet<>();

	/**
	 * Konstruktor.
	 *
	 * @param gtdController
	 *            Die Referenz auf den zentralen Controller, der zum Austausch
	 *            zwischen den Controllern dient.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 * @postconditions Das Attribut gtdController verweist auf den zentralen
	 *                 Controller, sofern nicht <em>null</em> übergeben wurde.
	 */
	protected ProjectController(GtdController gtdController) throws NullPointerException {
		ParamVal.objNotNull(gtdController, "gtdController");
		this.gtdController = gtdController;
	}

	/**
	 * Fügt ein neues {@link ProjectTreeAUI} zur Liste hinzu.
	 *
	 * @param projectTreeAUI
	 *            Das neue AbstractUserInterface.
	 */
	public void addProjectTreeAUI(ProjectTreeAUI projectTreeAUI) {
		projectTreeAUIs.add(projectTreeAUI);
	}

	/**
	 * Ruft die Methode {@link ProjectTreeAUI#refreshProject()} in allen
	 * Interfaces auf.
	 */
	public void callRefreshProject() {
		for (ProjectTreeAUI projectTreeAUI : projectTreeAUIs) {
			projectTreeAUI.refreshProject();
		}
	}

	/**
	 * Ruft die Methode {@link ProjectTreeAUI#refreshProjectTree()} in allen
	 * Interfaces auf.
	 */
	public void callRefreshProjectTree() {
		for (ProjectTreeAUI projectTreeAUI : projectTreeAUIs) {
			projectTreeAUI.refreshProjectTree();
		}
	}

	/**
	 * Erzeugt ein neues Projekt mit einem eindeutigen Titel.<br>
	 * Der Titel des Projekts muss im Projektbaum eindeutig sein.
	 * 
	 * @param parent
	 *            Ein Vaterprojekt oder die Wurzel des Projektbaums.
	 * @param title
	 *            Der Titel des (neuen) Projekts. Der Titel des Projekts darf
	 *            nicht <em>null</em> und nicht leer sein und muss eindeutig
	 *            innerhalb des Projektbaums sein.
	 * @param completeUntil
	 *            Das Datum, bis wann das Projekt erledigt sein soll.
	 * @param description
	 *            Die detaillierte Beschreibung des Projekts. Diese darf nicht
	 *            <em>null</em>, aber leer sein.
	 * @return Das erstellte Projekt.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen String-Parameter (bis auf die Beschreibung) leer
	 *             ist.
	 * @throws IllegalArgumentException
	 *             Die Runtime-Exception wird geworfen, wenn das übergebene
	 *             Datum in der Vergangenheit liegt.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             TreeNode parent nicht in Gtd enthalten ist.
	 * @throws GtdProjectException
	 *             Die Exception wird geworfen, wenn ein Projekt mit dieser
	 *             Bezeichnung bereits existiert.
	 */
	public Project createProject(TreeNode parent, String title, LocalDate completeUntil, String description)
			throws NullPointerException, EmptyStringParameterException, IllegalArgumentException,
			ObjectNotInGtdException, GtdProjectException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Überprüft, ob ein Projekt im Projektbaum mit dem Titel bereits existiert.
	 *
	 * @param title
	 *            Der Titel für die Suche.
	 * @return true, wenn bereits ein Projekt mit Titel existiert, sonst false
	 * @preconditions title kein leerer String.
	 */
	protected boolean existProjectTitle(String title) {
		return existProjectTitleRek(title, gtdController.getGtd().getRootNode());
	}

	/**
	 * Rekursive Subfunktion.
	 */
	private boolean existProjectTitleRek(String title, TreeNode treeNode) {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return false;
	}

	/**
	 * Erzeugt eine Liste mit allen fertigen Projekten der Projektbaumwurzel.
	 * <br>
	 * Wenn keine Projekte im Projektbaum sind oder alle nicht fertig sind, wird
	 * eine leere Liste zurückgegeben.
	 *
	 * @return Liste mit allen fertigen Projekten oder leere Liste
	 */
	protected ArrayList<Project> getFinishedRootProjects() {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Berechnet den Pfad von der Projektbaumwurzel bis zum Projektbaumknoten.
	 * <br>
	 * Wenn der Endknoten (treeNode) nicht im Projektbaum ist, wird eine leere
	 * Liste zurückgegeben.
	 *
	 * @param treeNode
	 *            Der Endknoten des Pfades.
	 * @return Pfad von Projektbaumwurzel bis Projektbaumknoten oder leere
	 *         Liste.
	 */
	public ArrayList<TreeNode> getTreeNodePath(TreeNode treeNode) {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Rekursive Subfunktion zu Bestimmung des TreeNodePath.
	 *
	 * @param projectTreePath
	 *            Liste mit aktuellem TreeNodePath
	 * @param treeNode
	 *            Der Endknoten des Pfades.
	 * @param currentTreeNode
	 *            Der aktuelle Projektbaumknoten
	 * @return true wenn der Endknoten erreicht wurde, ansonsten false.
	 */
	private boolean getTreeNodePathRek(ArrayList<TreeNode> projectTreePath, TreeNode treeNode,
			TreeNode currentTreeNode) {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return false;
	}

	/**
	 * Verschiebt ein Projekt in ein anderes Projekt oder die Projektbaumwurzel.
	 *
	 * @param project
	 *            Das zu verschiebene Projekt.
	 * @param newParent
	 *            Der neue Vaterknoten des Projekts (ein Projekt oder die
	 *            Projektbaumwurzel).
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der Parameter
	 *             <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn einer der Parameter
	 *             nicht in Gtd enthalten ist.
	 * @throws GtdProjectException
	 *             Die Exception wird geworfen, wenn das Projekt nicht
	 *             verschoben werden kann.
	 */
	public void moveProject(Project project, TreeNode newParent)
			throws NullPointerException, ObjectNotInGtdException, GtdProjectException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Entfernt ein Projekt aus dem Projektbaum, falls es vorhanden und erledigt
	 * oder ohne Tätigkeiten und Unterprojekte ist.
	 *
	 * @param project
	 *            Das zu entfernende Projekt.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn das übergebene
	 *             Projekt <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn das übergebene
	 *             Projekt nicht in Gtd enthalten ist.
	 * @throws GtdProjectException
	 *             Die Exception wird geworfen, wenn das Projekt nicht erledigt
	 *             ist.
	 */
	public void removeProject(Project project)
			throws NullPointerException, ObjectNotInGtdException, GtdProjectException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Sucht den Vaterknoten für ein Projekt(-Kindknoten).<br>
	 * Wenn das Projekt keine Vater hat (also nicht im Projektbaum ist), wird
	 * <em>null</em> zurückgegeben.
	 *
	 * @param project
	 *            Das Projekt für die Suche. Darf nicht <em>null</em> sein.
	 * @return Den Vaterknoten (TreeNode) oder <em>null</em>.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der Parameter
	 *             <em>null</em> ist.
	 */
	public TreeNode searchParentTreeNode(Project project) throws NullPointerException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Aktualisiert den Erledgt-Status in allen Projekten.
	 */
	protected void updateFinishedStatusInAllProjects() {
		ArrayList<Project> subprojects = gtdController.getGtd().getRootNode().getSubprojects();
		for (Project project : subprojects) {
			project.updateFinishedStatus();
		}
		callRefreshProject();
	}

	/**
	 * Setzt in allen Projekten den nächsten Schritt auf die erste
	 * nicht-erledigte Tätigkeit des Projekts oder auf <em>null</em> wenn keine
	 * existiert und aktualisiert dann den Projektstatus.
	 */
	protected void updateNextJobInAllProjects() {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

}
