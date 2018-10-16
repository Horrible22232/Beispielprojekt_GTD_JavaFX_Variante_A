package control;

import java.util.concurrent.CopyOnWriteArraySet;

import abstractuserinterfaces.ContextlistsAUI;
import exceptions.EmptyStringParameterException;
import exceptions.GtdContextlistException;
import exceptions.ObjectNotInGtdException;
import model.Contextlist;
import model.Job;

/**
 * Controller für Funktionen zur Verwaltung der Kontextlisten.
 *
 * @author Florian
 */
public class ContextlistController {
	/**
	 * Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * Controllern dient.
	 */
	private GtdController gtdController;
	/**
	 * Liste von {@link ContextlistsAUI}-AbstractUserInterfaces.
	 */
	private CopyOnWriteArraySet<ContextlistsAUI> contextlistsAUIs = new CopyOnWriteArraySet<>();

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
	protected ContextlistController(GtdController gtdController) throws NullPointerException {
		ParamVal.objNotNull(gtdController, "gtdController");
		this.gtdController = gtdController;
	}

	/**
	 * Fügt ein neues {@link ContextlistsAUI} zur Liste hinzu.
	 *
	 * @param contextlistsAUI
	 *            Das neue AbstractUserInterface.
	 */
	public void addContextlistsAUI(ContextlistsAUI contextlistsAUI) {
		contextlistsAUIs.add(contextlistsAUI);
	}

	/**
	 * Ruft die Methode {@link ContextlistsAUI#refreshContextlists()} in allen
	 * Interfaces auf.
	 */
	public void callRefreshContextlists() {
		for (ContextlistsAUI contextlistsAUI : contextlistsAUIs) {
			contextlistsAUI.refreshContextlists();
		}
	}

	/**
	 * Erzeugt eine neue Kontextliste mit dem übergebenen Titel.<br>
	 * Der Titel der Kontextliste wird nur dann akzeptiert, wenn er innerhalb
	 * des Systems eindeutig ist. Ein neues Kontextlisten-Objekt wird erzeugt,
	 * an Gtd übergeben und dort eingefügt.
	 *
	 * @param title
	 *            Der Titel der (neuen) Kontextliste. Der Titel der Kontextliste
	 *            darf nicht <em>null</em> und nicht leer sein und muss
	 *            eindeutig sein.
	 * @return Die erstellte Kontextliste.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Titel der Kontextliste <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Titel der Kontextliste leer ist.
	 * @throws GtdContextlistException
	 *             Die Exception wird geworfen, wenn eine Kontextliste mit
	 *             gleichem Bezeichner bereits existiert.
	 */
	public Contextlist createContextlist(String title)
			throws NullPointerException, EmptyStringParameterException, GtdContextlistException {
		ParamVal.stringNotNullOrEmpty(title, "title");
		if(existContextlistTitle(title)) throw new GtdContextlistException("Title is already in the List");
		Contextlist currentlist = new Contextlist(title);
		gtdController.getGtd().addContextlist(currentlist);
		callRefreshContextlists();
		return currentlist;
	}

	/**
	 * Überprüft ob eine Kontextliste mit dem übergebenen Titel exisitert.
	 *
	 * @param title
	 *            Der Titel der Kontextliste.
	 * @return true wenn existiContextlistControllerConstructorTest.class,ert.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Titel der Kontextliste <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Titel der Kontextliste leer ist.
	 * @preconditions title kein leerer String.
	 */
	protected boolean existContextlistTitle(String title) throws NullPointerException, EmptyStringParameterException {
		if(title == null) throw new NullPointerException("Title can not be NULL!");
		if(title.equals("")) throw new EmptyStringParameterException("Title can not be empty!");
		for(Contextlist c : gtdController.getGtd().getContextlists()) 
			if(c.getTitle().equals(title)) return true;
		return false;
	}

	/**
	 * Entfernt eine Kontextliste nur dann, wenn sie leer ist oder geleert
	 * werden kann. Bevor überprüft wird, ob die Kontextliste leer ist, werden
	 * alle erledigten Tätigkeiten aus der Kontextliste entfernt.
	 *
	 * @param contextlist
	 *            Die zu entferndende Kontextliste.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn die übergebene
	 *             Kontextliste <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn die übergebene
	 *             Kontextliste nicht in Gtd ist.
	 * @throws GtdContextlistException
	 *             Die Exception wird geworfen, wenn die Kontextliste nicht
	 *             entfernt werden kann, weil die Kontextliste Tätigkeiten
	 *             enthält.
	 */
	public void removeContextlist(Contextlist contextlist)
			throws NullPointerException, ObjectNotInGtdException, GtdContextlistException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Entfernt alle erledigten Tätigkeiten aus allen Kontextlisten.<br>
	 * Wird für die Wochendurchsicht benötigt. Die Kontextlisten werden
	 * nacheinander durchlaufen und für jede Kontextliste das Entfernen der
	 * erledigten Tätigkeiten aufgerufen.
	 *
	 * @postconditions In allen Kontextliste existieren keine erledigten
	 *                 Tätigkeiten mehr.
	 */
	protected int removeFinishedJobsInAllContextlists() {
		int finishedJobs = 0;
		for(Contextlist c : gtdController.getGtd().getContextlists()) {
			for(Job j : c.getJobs()){
				if(j.isFinished()) finishedJobs++;
			}
			c.removeAllFinishedJobs();
		}
		gtdController.getJobController().callRefreshJobs();
		return finishedJobs;
	}

}
