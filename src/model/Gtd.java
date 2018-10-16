package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Die Klasse Gtd (für GettingThingsDone) ist die zentrale Einheit des Models.
 *
 * @author Florian
 */
public class Gtd { // NOPMD
	/**
	 * Die Liste aller Kontextlisten.
	 */
	private ArrayList<Contextlist> contextlists;
	/**
	 * Die Inbox, die eine Liste aller Notizen und den Zeitpunkt der letzten
	 * Notizkonvertierung enthält.
	 */
	private Inbox inbox;
	/**
	 * Die Wurzel des Projektbaums.
	 */
	private TreeNode rootNode;

	/**
	 * Konstruktor, erzeugt neues Gtd-Objekt.
	 */
	public Gtd() {
		contextlists = new ArrayList<>();
		inbox = new Inbox();
		rootNode = new TreeNode();
	}

	/**
	 * Fügt eine Kontextliste hinzu und sortiert die Kontextlistenliste .<br>
	 * Sortierung anhand: {@link Contextlist#compareTo(Contextlist)}.
	 *
	 * @param contextlist
	 *            Die neue Kontextliste.
	 */
	public void addContextlist(Contextlist contextlist) {
		contextlists.add(contextlist);
		Collections.sort(contextlists);
	}

	/**
	 * Gibt die Liste aller Kontextlisten zurück.
	 *
	 * @return Liste aller Kontextlisten.
	 */
	public ArrayList<Contextlist> getContextlists() {
		return contextlists;
	}

	/**
	 * Gibt die Inbox zurück.
	 *
	 * @return Inbox.
	 */
	public Inbox getInbox() {
		return inbox;
	}

	/**
	 * Gibt die Wurzel des Projektbaums zurück.
	 *
	 * @return Wurzel des Projektbaums.
	 */
	public TreeNode getRootNode() {
		return rootNode;
	}

	/**
	 * Entfernt eine Kontextliste, falls vorhanden.
	 *
	 * @param contextlist
	 *            Die zu entfernende Kontextliste.
	 * @return <strong>true</strong> Kontextliste war vorhanden und wurde
	 *         entfernt,<br>
	 *         <strong>false</strong> Kontextliste nicht vorhanden.
	 */
	public boolean removeContextlist(Contextlist contextlist) {
		return contextlists.remove(contextlist);
	}

}
