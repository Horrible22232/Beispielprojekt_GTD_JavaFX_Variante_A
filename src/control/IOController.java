package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exceptions.GtdIOException;
import model.Gtd;

/**
 * Controller für das Laden und Speichern.
 *
 * @author Florian
 */
public class IOController {
	/**
	 * Die Datei, in der die Daten gespeichert werden. (Hier nur eine Datei im
	 * Verzeichnis des Programms.)
	 */
	public static final File SAVE_FILE = new File("gtd.ser");
	/**
	 * Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * Controllern dient.
	 */
	private GtdController gtdController;

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
	protected IOController(GtdController gtdController) throws NullPointerException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Lädt die Gtd-Daten aus der Datei.
	 *
	 * @throws GtdIOException
	 *             Die Exception wird geworfen, wenn beim Laden ein Fehler
	 *             auftritt.
	 * @postconditions Der in Datei gespeicherte Zustand des Systems steht zur
	 *                 Benutzung zur Verfügung.
	 */
	public void load() throws GtdIOException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Speichert die Gtd-Daten in der Datei.
	 *
	 * @throws GtdIOException
	 *             Die Exception wird geworfen, wenn beim Speichern ein Fehler
	 *             auftritt.
	 */
	public void save() throws GtdIOException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

}
