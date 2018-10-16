package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Ein Projekt besteht aus einer Menge von Tätigkeiten und Unterprojekten. <br>
 * Ein Projekt ist wieder eine Wurzel eines (Teil-)Projektbaumes.
 *
 * @author Florian
 */
public class Project extends TreeNode implements Comparable<Project> {
	/**
	 * Das Datum, bis wann das Projekt erledigt sein soll.
	 */
	private LocalDate completeUntil;
	/**
	 * Die detaillierte Beschreibung des Projekts.
	 */
	private String description;
	/**
	 * Status, ob das Projekt erledigt ist.<br>
	 * Ist das Projekt erledigt, sind alle Unterprojekte und alle enthaltenden
	 * Tätigkeiten erledigt.
	 */
	private boolean finished;
	/**
	 * Die Liste der Tätigkeiten des Projekts.
	 */
	private ArrayList<Job> jobs;
	/**
	 * Der nächste Schritt in einem Projekt.
	 */
	private Job nextJob;

	/**
	 * Konstruktor, erzeugt ein neues nicht-erledigtes Projekt.
	 *
	 * @param title
	 *            Die eindeutige Bezeichnung des Projekts.
	 * @param completeUntil
	 *            Das Datum, bis wann das Projekt erledigt sein soll.
	 * @param description
	 *            Die detaillierte Beschreibung des Projekts.
	 * @preconditions <b>title</b> darf nicht <em>null</em> und nicht leer sein.
	 *                <br>
	 *                <b>completeUntil</b> darf nicht <em>null</em> sein und
	 *                muss in der Zukunft liegen.<br>
	 *                <b>description</b> darf nicht <em>null</em> sein aber leer
	 *                sein.
	 */
	public Project(String title, LocalDate completeUntil, String description) {
		super(title);
		this.completeUntil = completeUntil;
		this.description = description;
		finished = false;
		jobs = new ArrayList<Job>();
		nextJob = null;
	}

	/**
	 * Fügt eine Tätigkeit zu diesem Projekt hinzu und sortiert die
	 * Tätigkeitenliste .<br>
	 * Sortierung anhand: {@link Job#compareTo(Job)}.<br>
	 * Anschließend wird der nächste Schritt und der Status des Projekts
	 * aktualisiert.
	 *
	 * @param job
	 *            Die neue Tätigkeit.
	 */
	public void addJob(Job job) {
		jobs.add(job);
		Collections.sort(jobs);
		updateNextJob();
	}

	/**
	 * Vergleichsfunktion für die Sortierung von Projekten.<br>
	 * Projekte werden primär nach dem Endzeitpunkt, sekundär nach der
	 * Bezeichnung sortiert.
	 */
	@Override
	public int compareTo(Project project) {
		if (project.completeUntil == null) {
			if (this.completeUntil == null) {
				return this.title.compareTo(project.title);
			} else {
				return -1;
			}
		} else if (this.completeUntil == null) {
			if (project.completeUntil == null) {
				return this.title.compareTo(project.title);
			} else {
				return 1;
			}
		}
		int compareValue = this.completeUntil.compareTo(project.completeUntil);
		if (compareValue == 0) {
			return this.title.compareTo(project.title);
		} else {
			return compareValue;
		}
	}

	/**
	 * Gibt das Datum, bis wann das Projekt erledigt sein soll, zurück.
	 *
	 * @return Das Datum, bis wann das Projekt erledigt sein soll.
	 */
	public LocalDate getCompleteUntil() {
		return completeUntil;
	}

	/**
	 * Gibt die detaillierte Beschreibung des Projekts zurück.
	 *
	 * @return Beschreibung des Projekts.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gibt die Liste der Tätigkeiten des Projekts zurück.
	 *
	 * @return Liste der Tätigkeiten des Projekts.
	 */
	public ArrayList<Job> getJobs() {
		return jobs;
	}

	/**
	 * Gibt den nächsten Schritt in einem Projekt zurück.<br>
	 * Gibt es keinen nächsten Schritt (leere Liste, alle Tätigkeiten fertig),
	 * wird null zurückgegeben.
	 *
	 * @return Der nächste Schritt in einem Projekt oder <em>null</em>.
	 */
	public Job getNextJob() {
		return nextJob;
	}

	/**
	 * Gibt zurück, ob das Projekt erledigt ist.<br>
	 * Ist das Projekt erledigt, sind alle Unterprojekte und alle enthaltenden
	 * Tätigkeiten erledigt.
	 *
	 * @return Projekt erledigt.
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Wenn das Projekt erledigt ist, wird<br>
	 * <ul>
	 * <li>die Funktion in allen Unterprojekten aufgerufen (Delegation an
	 * Unterprojekte).</li>
	 * <li>die Liste der Tätigkeiten in diesem Projekt geleert (Tätigkeiten sind
	 * dann alle auch erledigt),</li>
	 * </ul>
	 */
	public void removeAllJobs() {
		if (finished) {
			for (Project subproject : subprojects) {
				subproject.removeAllJobs();
			}
			jobs.clear();
		}
	}

	/**
	 * Entfernt eine Tätigkeit aus der Liste von Tätigkeiten in diesem Projekt.
	 *
	 * @param job
	 *            Die zu entfernende Tätigkeit.
	 * @return Ob Tätigkeit entfernt wuede.
	 * @preconditions Tätigkeit nicht null.
	 */
	public boolean removeJob(Job job) {
		return jobs.remove(job);
	}

	/**
	 * Aktualisiert den Erledigt-Status des Projekts.<br>
	 * Ist eines der Unterprojekte oder eine Tätigkeit unerledigt oder die Liste
	 * der Tätigkeiten, ist auch dieses Projekt unerledigt.
	 */
	public void updateFinishedStatus() {
		if (jobs.isEmpty()) {
			finished = false;
		} else {
			finished = true;
			for (int i = 0; i < subprojects.size(); i++) {
				subprojects.get(i).updateFinishedStatus();
				if (!subprojects.get(i).isFinished()) {
					finished = false;
				}
			}
			for (int i = 0; finished && i < jobs.size(); i++) {
				if (!jobs.get(i).isFinished()) {
					finished = false;
				}
			}
		}
	}

	/**
	 * Setzt den nächsten Schritt auf die erste nicht-erledigte Tätigkeit in
	 * diesem Projekt und ruft die Funktion in allen Unterprojekten auf.<br>
	 * Nach der Aktualisierung des nächsten Schritts, wird der Projektstatus
	 * aktualisiert.
	 */
	public void updateNextJob() {
		for (Project subproject : subprojects) {
			subproject.updateNextJob();
		}
		nextJob = null;
		for (int i = 0; i < jobs.size(); i++) {
			if (!jobs.get(i).isFinished()) {
				if (nextJob == null || jobs.get(i).getCompleteUntil().isBefore(nextJob.getCompleteUntil())) {
					nextJob = jobs.get(i);
				}
			}
		}
		updateFinishedStatus();
	}

}
