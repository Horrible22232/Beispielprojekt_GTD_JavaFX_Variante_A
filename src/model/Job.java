package model;

import java.time.LocalDate;

/**
 * Eine Tätigkeit ist eine kontextbezogene Aufgabe mit den Eigenschaften:
 * <ul>
 * <li>Bezeichnung (title)</li>
 * <li>Zu erledigen bis (completeUntil)</li>
 * <li>Beschreibung (description)</li>
 * <li>{@link Status}</li>
 * <li>{@link Delegation}</li>
 * </ul>
 *
 * @author Florian
 */
public class Job implements Comparable<Job> { // NOPMD
	/**
	 * Die Beteichnung der Tätigkeit. <br>
	 * Die Bezeichnung ist innerhalb eines Projekts und einer Kontextliste
	 * eindeutig.
	 */
	private String title;
	/**
	 * Das Datum, bis wann die Tätigkeit erledigt sein soll.
	 */
	private LocalDate completeUntil;
	/**
	 * Die detaillierte Beschreibung der Tätigkeit.
	 */
	private String description;
	/**
	 * Der {@link Status} der Tätigkeit.
	 */
	private Status status;
	/**
	 * Delegation (an wen und bis wann), wenn Tätigkeit delegiert wird. Null
	 * wenn nicht delegiert.
	 */
	private Delegation delegation;

	/**
	 * Konstruktor, erzeugt eine neue Tätigkeit mit dem Status
	 * {@link Status#EDITING}, die zunächst nicht delegiert ist. Eine spätere
	 * Delegation ist möglich.
	 *
	 * @param title
	 *            Die Bezeichnung der Tätigkeit. Die Bezeichnung ist innerhalb
	 *            eines Projekts und einer Kontextliste eindeutig.
	 * @param completeUntil
	 *            Das Datum, bis wann die Tätigkeit erledigt sein soll, muss in
	 *            der Zukunft liegen.
	 * @param description
	 *            Die detaillierte Beschreibung der Tätigkeit.
	 * @preconditions <b>title</b> darf nicht <em>null</em> und nicht leer sein.
	 *                <br>
	 *                <b>completeUntil</b> darf nicht <em>null</em> sein und
	 *                muss in der Zukunft liegen.<br>
	 *                <b>description</b> darf nicht <em>null</em> sein aber leer
	 *                sein.
	 * @postconditions Eine zu bearbeitende Tätigkeit mit den übergebenen
	 *                 Eigenschaften ist erzeugt worden.
	 */
	public Job(String title, LocalDate completeUntil, String description) {
		this.title = title;
		this.completeUntil = completeUntil;
		this.description = description;
		status = Status.EDITING;
		delegation = null;
	}

	/**
	 * Vergleichsfunktion für eine Sortierung von Tätigkeiten in einer Liste.
	 * <br>
	 * Tätigkeiten werden primär nach dem Endzeitpunkt, sekundär nach der
	 * Bezeichnung sortiert.
	 */
	@Override
	public int compareTo(Job job) {
		int compareValue = this.completeUntil.compareTo(job.completeUntil);
		if (compareValue == 0) {
			return this.title.compareTo(job.title);
		} else {
			return compareValue;
		}
	}

	/**
	 * Gibt das Datum, bis wann die Tätigkeit erledigt sein soll, zurück.
	 *
	 * @return Das Datum, bis wann die Tätigkeit erledigt sein soll.
	 */
	public LocalDate getCompleteUntil() {
		return completeUntil;
	}

	/**
	 * Gibt die {@link Delegation} (an wen und bis wann) einer Tätigkeit zurück.
	 *
	 * @return <strong>Delegation</strong> Tätigkeit delegiert,<br>
	 *         <strong>null</strong> Tätigkeit nicht delegiert.
	 */
	public Delegation getDelegation() {
		return delegation;
	}

	/**
	 * Gibt die detaillierte Beschreibung der Tätigkeit zurück.
	 *
	 * @return Die detaillierte Beschreibung der Tätigkeit.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gibt den {@link Status} der Tätigkeit zurück.
	 *
	 * @return Der Status der Tätigkeit.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Gibt die Bezeichnung (Kurzbeschreibung) der Tätigkeit zurück.<br>
	 * Die Bezeichnung ist innerhalb einer Kontextliste und eines Projekts
	 * eindeutig.
	 *
	 * @return Die Bezeichnung der Tätigkeit.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gibt zurück, ob die Tätigkeit erledigt ist oder nicht.
	 *
	 * @return <strong>true</strong> Tätigkeit erledigt,<br>
	 *         <strong>false</strong> Tätigkeit nicht erledigt.
	 */
	public boolean isFinished() {
		return status.equals(Status.FINISHED);
	}

	/**
	 * Setzt die {@link Delegation} der Tätigkeit.<br>
	 * Wenn eine Delegation(not <em>null</em>) übergeben wird, wird der Status
	 * auf {@link Status#DELEGATED} gesetzt.<br>
	 * Wenn der Wert des Parameters <em>null</em> ist, wird nicht gemacht.<br>
	 * Um die Delegation aufzuheben, verwenden Sie stattdessen
	 * {@link Job#setStatus(Status)}.
	 *
	 * @param delegation
	 *            Die Delegation.
	 */
	public void setDelegation(Delegation delegation) {
		if (delegation != null) {
			this.delegation = delegation;
			this.status = Status.DELEGATED;
		}
	}

	/**
	 * Ersetzt den Status der Tätigkeit durch einen neuen Status.<br>
	 * Eine Status-Änderung auf {@link Status#DELEGATED} ist mit dieser Methode
	 * nicht möglich (da das entsprechende Delegations-Objekt fehlt), verwenden
	 * Sie dafür die Methode {@link Job#setDelegation(Delegation)}.Wenn der
	 * aktuelle Status von {@link Status#DELEGATED} auf einen anderen geändert
	 * wird, wird das Attribut <i>delegation</i> auf <em>null</em> gesetzt.
	 *
	 * @param status
	 *            Der neue Status.
	 * @return <b>true</b> wenn Status geändert wurde, sonst <b>false</b>.
	 */
	public boolean setStatus(Status status) {
		if (status.equals(Status.DELEGATED)) {
			return false;
		}
		this.status = status;
		delegation = null;
		return true;
	}

}
