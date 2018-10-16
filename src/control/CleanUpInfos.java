package control;

import java.util.ArrayList;

import model.Project;
import model.TreeNode;

/**
 * Objekte dieser Klasse werden als Ergebnis beim Aufräumen erzeugt und erhalten
 * Informationen, wie viele erledigte Tätigkeiten entfernt und wie viele
 * (Root-)Projekte erledigt sind und gelöscht werden könnten.
 *
 * @author Florian
 */
public class CleanUpInfos {
	/**
	 * Anzahl entfernter erledigter Tätigkeiten.
	 */
	private final int removedJobs;
	/**
	 * Ein Projektbaum mit allen erledigten (Root-)Projekten.
	 */
	private final TreeNode finishedProjects;

	/**
	 * Konstruktor.
	 *
	 * @param removedJobs
	 *            Die Anzahl entfernter erledigter Tätigkeiten.
	 * @param finishedProjects
	 *            Liste mit allen erledigten (Root-)Projekten.
	 */
	public CleanUpInfos(final int removedJobs, final ArrayList<Project> finishedProjects) {
		this.removedJobs = removedJobs;
		this.finishedProjects = new TreeNode();
		this.finishedProjects.getSubprojects().addAll(finishedProjects);
	}

	/**
	 * Gibt die Anzahl entfernter erledigter Tätigkeiten zurück.
	 *
	 * @return Anzahl entfernter erledigter Tätigkeiten.
	 */
	public TreeNode getFinishedProjects() {
		return finishedProjects;
	}

	/**
	 * Gibt ein Projektbaum mit allen erledigten (Root-)Projekten zurück.
	 *
	 * @return Treenode mit allen erledigten (Root-)Projekten.
	 */
	public int getRemovedJobs() {
		return removedJobs;
	}

}
