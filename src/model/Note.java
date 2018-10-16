package model;

import java.time.LocalDateTime;

/**
 * In einer Notiz wird ein Notiztext und dessen Entstehungszeitpunkt für die
 * Inbox gespeichert.
 *
 * @author Florian
 */
public class Note { // NOPMD
	/**
	 * Der Entstehungszeitpunkt der Notiz.
	 */
	private final LocalDateTime creationDate;
	/**
	 * Der Notiztext.
	 */
	private final String text;

	/**
	 * Konstruktor, erzeugt eine neue Inbox-Notiz.
	 *
	 * @param text
	 *            Der Notiztext.
	 * @preconditions <b>text</b> darf nicht <em>null</em> und nicht leer sein.
	 */
	public Note(final String text) {
		this.text = text;
		creationDate = LocalDateTime.now();
	}

	/**
	 * Gibt den Entstehungszeitpunkt der Notiz zurück.
	 *
	 * @return Der Entstehungszeitpunkt der Notiz.
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * Gibt den Notiztext zurück.
	 *
	 * @return Der Notiztext.
	 */
	public String getText() {
		return text;
	}

}
