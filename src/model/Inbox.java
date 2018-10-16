package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Die Inbox enthält eine Liste von Notizen und den Zeitpunkt der letzten
 * Notizkonvertierung.
 *
 * @author Florian
 */
public class Inbox {
	/**
	 * Liste aller Notizen.
	 */
	private ArrayList<Note> list;
	/**
	 * Zeitpunkt der letzten Notizkonvertierung.
	 */
	private LocalDateTime lastNoteConversion;

	/**
	 * Konstruktor, erzeugt neues Inbox-Objekt. Die Liste der Notizen und der
	 * Zeitpunkt der letzten Notizkonvertierung werden initualisiert.
	 */
	public Inbox() {
		list = new ArrayList<Note>();
		updateLastNoteConversion();
	}

	/**
	 * Hängt eine Notiz ans Ende der Notiz-Liste.
	 *
	 * @param note
	 *            Die neue Notiz.
	 */
	public void addNote(Note note) {
		list.add(note);
	}

	/**
	 * Gibt den Zeitpunkt der letzten Notizkonvertierung zurück.
	 *
	 * @return Zeitpunkt der letzten Notizkonvertierung.
	 */
	public LocalDateTime getLastNoteConversion() {
		return lastNoteConversion;
	}

	/**
	 * Gibt die Liste aller Notizen zurück.
	 *
	 * @return Liste aller Notizen.
	 */
	public ArrayList<Note> getList() {
		return list;
	}

	/**
	 * Entfernt eine Notiz, falls vorhanden, aus der Liste der Notizen.
	 *
	 * @param note
	 *            Die zu entfernende Notiz.
	 * @return <strong>true</strong> Notiz war vorhanden und wurde entfernt,<br>
	 *         <strong>false</strong> Notiz nicht vorhanden.
	 */
	public boolean removeNote(Note note) {
		return list.remove(note);
	}

	/**
	 * Setzt das Datum der letzten Notizkonvertierung auf den aktuellen
	 * Zeitpunkt.
	 */
	public void updateLastNoteConversion() {
		this.lastNoteConversion = LocalDateTime.now();
	}

}
