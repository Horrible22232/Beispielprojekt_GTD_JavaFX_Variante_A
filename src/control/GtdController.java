package control;

import java.util.ArrayList;

import exceptions.GtdIOException;
import model.Gtd;
import model.Project;

/**
 * Main-Controller des GettingThingsDone(Gtd)-Programms. Hierüber sind alle
 * anderen Controller erreichbar.
 *
 * @author Florian
 */
public class GtdController {
	/**
	 * Kontextlisten-Controller
	 */
	private ContextlistController contextlistController;
	/**
	 * Inbox-Controller
	 */
	private InboxController inboxController;
	/**
	 * IO-Controller
	 */
	private IOController ioController;
	/**
	 * Tätigkeiten-Controller
	 */
	private JobController jobController;
	/**
	 * Projekt-Controller
	 */
	private ProjectController projectController;
	/**
	 * Das aktuellen GettingThingsDone-Objekt (Daten).
	 */
	private Gtd gtd;

	/**
	 * Der Konstruktor. Erzeugt die anderen Controller und ein neues Gtd-Objekt.
	 */
	public GtdController() {
		contextlistController = new ContextlistController(this);
		inboxController = new InboxController(this);
		ioController = new IOController(this);
		jobController = new JobController(this);
		projectController = new ProjectController(this);
		gtd = new Gtd();
		try {
			ioController.load();
		} catch (GtdIOException e) {
		}
	}

	/**
	 * Alle erledigte Tätigkeiten werden aus den Kontextlisten entfernt und in
	 * den Projekten wird der nächste Schritt auf die erste unerledigte
	 * Tätigkeit oder <em>null</em> gesetzt.
	 *
	 * @return CleanUpInfos mit Anzahl entfernter Tätigkeiten und fertigen
	 *         Projekten.
	 */
	public CleanUpInfos cleanUp() {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Gibt den Kontextlisten-Controller zurück.
	 *
	 * @return Der Kontextlisten-Controller.
	 */
	public ContextlistController getContextlistController() {
		return contextlistController;
	}

	/**
	 * Gibt das aktuelle Gtd-Objekt zurück.
	 *
	 * @return Das GettingThingsDone-Objekt.
	 */
	public Gtd getGtd() {
		return gtd;
	}

	/**
	 * Gibt den Inbox-Controller zurück.
	 *
	 * @return Der Inbox-Controller.
	 */
	public InboxController getInboxController() {
		return inboxController;
	}

	/**
	 * Gibt den IO-Controller zum Lesen und Speichern von Daten zurück.
	 *
	 * @return Der IO-Controller.
	 */
	public IOController getIOController() {
		return ioController;
	}

	/**
	 * Gibt den Tätigkeiten-Controller zurück.
	 *
	 * @return Der Tätigkeiten-Controller.
	 */
	public JobController getJobController() {
		return jobController;
	}

	/**
	 * Gibt den Projekt-Controller zurück.
	 *
	 * @return Der Projekt-Controller.
	 */
	public ProjectController getProjectController() {
		return projectController;
	}

	/**
	 * Ersetzt das Gtd-Objekt. Die Methode wird beim Laden einer anderen
	 * Gtd-Datei benötigt.
	 *
	 * @param gettingThingsDone
	 *            Das neue GettingThingsDone-Objekt.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 */
	protected void setGtd(Gtd gtd) throws NullPointerException {
		ParamVal.objNotNull(gtd, "gtd");
		this.gtd = gtd;
	}

}
