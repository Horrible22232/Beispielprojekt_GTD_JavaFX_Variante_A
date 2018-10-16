package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Ein Baumknoten ist ein Knoten innerhalb des Projektbaumes.<br>
 * Der Projektbaumknoten hat eine Bezeichnung und eine Liste von Unterprojekten.
 *
 * @author Florian
 */
public class TreeNode {
	/**
	 * Die Bezeichnung der Projektbaumwurzel: {@value}
	 */
	public static final String ROOT_TREENODE_TITLE = "ProjectTree";
	/**
	 * Die eindeutige Bezeichnung des Projektbaumsknotens.
	 */
	protected String title;
	/**
	 * Die Liste der Unterprojekte.
	 */
	protected ArrayList<Project> subprojects;

	/**
	 * Konstruktor, erzeugt neues TreeNode-Objekt mit Bezeichnung
	 * {@link TreeNode#ROOT_TREENODE_TITLE}.
	 */
	public TreeNode() {
		this(ROOT_TREENODE_TITLE);
	}

	/**
	 * Konstruktor, erzeugt neues TreeNode-Objekt mit eindeutige Bezeichnung im
	 * Projektbaum.
	 *
	 * @param title
	 *            Die Bezeichnung des Projektbaumsknotens.
	 * @preconditions <b>title</b> darf nicht <em>null</em> und nicht leer sein
	 *                und muss eindeutig sein.
	 */
	public TreeNode(String title) {
		this.title = title;
		subprojects = new ArrayList<Project>();
	}

	/**
	 * Fügt ein Unterprojekt hinzu und sortiert die Projektliste .<br>
	 * Sortierung anhand: {@link Project#compareTo(Project)}.
	 *
	 * @param project
	 *            Das neue Unterprojekt.
	 */
	public void addSubproject(Project project) {
		subprojects.add(project);
		Collections.sort(subprojects);
	}

	/**
	 * Gibt die Liste der Unterprojekte zurück.
	 *
	 * @return Liste der Unterprojekte.
	 */
	public ArrayList<Project> getSubprojects() {
		return subprojects;
	}

	/**
	 * Gibt die eindeutige Bezeichnung des Projektbaumsknotens zurück.
	 *
	 * @return Eindeutige Bezeichnung des Projektbaumsknotens.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Entfernt ein Projekt aus der Liste Unterprojekte.
	 *
	 * @param project
	 *            Das zu entfernende Projekt.
	 * @return true, wenn das Projekt enthalten war und entfernt wurde.
	 */
	public boolean removeSubproject(Project project) {
		return subprojects.remove(project);
	}

}
