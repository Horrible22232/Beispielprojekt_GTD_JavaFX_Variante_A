package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * In Kontextlisten werden Tätigkeiten mit demselben Kontext gesammelt.
 *
 * @author Florian
 */
public class Contextlist implements Comparable<Contextlist> {
	/**
	 * Die eindeutige Bezeichnung der Kontextliste.
	 */
	private String title;
	/**
	 * Die Liste von Tätigkeiten dieser Kontextliste.
	 */
	private ArrayList<Job> jobs;

	/**
	 * Konstruktor, erzeugt eine Kontextliste mit der übergebenen Bezeichnung.
	 *
	 * @param title
	 *            Die eindeutige Bezeichnung der Kontextliste.
	 * @preconditions <b>title</b> darf nicht <em>null</em> und nicht leer sein
	 *                und muss eindeutig sein.
	 */
	public Contextlist(String title) {
		this.title = title;
		jobs = new ArrayList<Job>();
	}

	/**
	 * Fügt eine Tätigkeit zu dieser Kontextliste hinzu und sortiert die
	 * Tätigkeitenliste .<br>
	 * Sortierung anhand: {@link Job#compareTo(Job)}.
	 *
	 * @param job
	 *            Die neue Tätigkeit.
	 */
	public void addJob(Job job) {
		jobs.add(job);
		Collections.sort(jobs);
	}

	/**
	 * Vergleichsfunktion zweier Kontextlisten anhand der Bezeichnung für die
	 * Sortierung der Kontextlisten.
	 */
	@Override
	public int compareTo(Contextlist contextlist) {
		return this.title.compareTo(contextlist.title);
	}

	/**
	 * Gibt die Liste von Tätigkeiten dieser Kontextliste zurück.
	 *
	 * @return Die Liste von Tätigkeiten dieser Kontextliste.
	 */
	public ArrayList<Job> getJobs() {
		return jobs;
	}

	/**
	 * Gibt die eindeutige Bezeichnung der Kontextliste zurück.
	 *
	 * @return Die eindeutige Bezeichnung der Kontextliste.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gibt zurück, ob die Kontextliste Tätigkeiten enthält oder nicht.
	 *
	 * @return <strong>true</strong> Kontextliste enthält keine Tätigkeiten,
	 *         <br>
	 *         <strong>false</strong> Kontextliste enthält Tätigkeiten.
	 */
	public boolean isEmpty() {
		return jobs.isEmpty();
	}

	/**
	 * Entfernt und zählt alle erledigten Tätigkeiten ({@link Status#FINISHED})
	 * in dieser Kontextliste.
	 *
	 * @return Die Anzahl entfernter Tätigkeiten.
	 * @postconditions Die Kontextliste enthält nur Nicht-Erledigte Tätigkeiten.
	 */
	public int removeAllFinishedJobs() {
		int finishedJobs = 0;
		for (int index = jobs.size() - 1; index > -1; index--) {
			if (jobs.get(index).isFinished()) {
				jobs.remove(index);
				finishedJobs++;
			}
		}
		return finishedJobs;
	}

	/**
	 * Entfernt eine Tätigkeit aus der Liste von Tätigkeiten in dieser
	 * Kontextliste.
	 *
	 * @param job
	 *            Die zu entfernende Tätigkeit.
	 * @return Ob Tätigkeit entfernt wuede.
	 */
	public boolean removeJob(Job job) {
		return jobs.remove(job);
	}

}
