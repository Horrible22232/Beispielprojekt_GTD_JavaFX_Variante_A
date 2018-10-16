package model;

import java.time.LocalDate;

/**
 * In einer Delegation wird gespeichert, an wen und bis wann eine Tätigkeit
 * delegiert wird.
 *
 * @author Florian
 */
public class Delegation {
	/**
	 * An wen die Tätigkeit delegiert wird.
	 */
	private final String toPerson;
	/**
	 * Das Datum, bis wann die Tätigkeit delegiert wird.
	 */
	private final LocalDate until;

	/**
	 * Konstruktor, erzeugt ein Delegationsobjekt mit der Information an wen und
	 * bis wann delegiert wird.
	 *
	 * @param toPerson
	 *            An wen die Tätigkeit delegiert wird.
	 * @param until
	 *            Das Datum, bis wann die Tätigkeit delegiert wird.
	 * @preconditions <b>anPerson</b> darf nicht <em>null</em> und nicht leer
	 *                sein.<br>
	 *                <b>bisWann</b> darf nicht <em>null</em> sein und muss in
	 *                der Zukunft liegen.
	 */
	public Delegation(String toPerson, LocalDate until) {
		this.toPerson = toPerson;
		this.until = until;
	}

	/**
	 * Gibt zurück, an wen die Tätigkeit delegiert wird.
	 *
	 * @return An wen die Tätigkeit delegiert wird.
	 */
	public String getToPerson() {
		return toPerson;
	}

	/**
	 * Gibt zurück, bis wann die Tätigkeit delegiert wird.
	 *
	 * @return Das Datum, bis wann die Tätigkeit delegiert wird.
	 */
	public LocalDate getUntil() {
		return until;
	}

}
