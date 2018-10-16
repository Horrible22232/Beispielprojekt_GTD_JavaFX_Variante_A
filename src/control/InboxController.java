package control;

import java.util.concurrent.CopyOnWriteArraySet;

import abstractuserinterfaces.InboxAUI;
import exceptions.EmptyStringParameterException;
import exceptions.ObjectNotInGtdException;
import model.Inbox;
import model.Note;

/**
 * Controller für Funktionen zur Verwaltung der Inbox.
 *
 * @author Florian
 */
public class InboxController {
	/**
	 * Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * Controllern dient.
	 */
	private GtdController gtdController;
	/**
	 * Liste von {@link InboxAUI}-AbstractUserInterfaces.
	 */
	private CopyOnWriteArraySet<InboxAUI> inboxAUIs = new CopyOnWriteArraySet<>();

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
	protected InboxController(GtdController gtdController) throws NullPointerException {
		// Ist der Parameter null, wird eine Runtime-NullPointerException
		// geworfen.
		ParamVal.objNotNull(gtdController, "gtdController");
		this.gtdController = gtdController;
	}

	/**
	 * Fügt ein neues {@link InboxAUI} zur Liste hinzu.
	 *
	 * @param inboxAUI
	 *            Das neue AbstractUserInterface.
	 */
	public void addInboxAUI(InboxAUI inboxAUI) {
		inboxAUIs.add(inboxAUI);
	}

	/**
	 * Ruft die Methode {@link InboxAUI#refreshInbox()} in allen Interfaces auf.
	 */
	public void callRefreshInbox() {
		for (InboxAUI inboxAUI : inboxAUIs) {
			inboxAUI.refreshInbox();
		}
	}

	/**
	 * Ruft die Methode {@link InboxAUI#refreshLastNoteConversion()} in allen
	 * Interfaces auf.
	 */
	public void callRefreshLastNoteConversion() {
		for (InboxAUI inboxAUI : inboxAUIs) {
			inboxAUI.refreshLastNoteConversion();
		}
	}

	/**
	 * Fügt eine neue Notiz (Text) zur Inbox hinzu.
	 *
	 * @param text
	 *            Der Text der neuen Inbox-Notiz.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Exception wird geworfen, wenn der Notiztext leer ist.
	 * @postconditions In der Inbox existiert eine Notiz mit übergebenem Text
	 *                 und aktuellem Datum.
	 */
	public void createNote(String text) throws NullPointerException, EmptyStringParameterException {
		// Notiz erstellen nur wenn der Notiztext nicht null oder leer ist.
		ParamVal.stringNotNullOrEmpty(text, "text");
		gtdController.getGtd().getInbox().addNote(new Note(text));
		callRefreshInbox();
	}

	/**
	 * Entfernt die Notiz aus der Inbox.
	 *
	 * @param note
	 *            Die zu entfernende Notiz.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter nicht
	 *             in Gtd ist.
	 * @postconditions Die Inbox ist leer oder enthält einen Eintrag weniger als
	 *                 vor dem Aufruf.
	 */
	public void removeNote(Note note) throws NullPointerException, ObjectNotInGtdException {
		ParamVal.objNotNull(note, "note");
		Inbox inbox = gtdController.getGtd().getInbox();
		if (!inbox.getList().contains(note)) {
			throw new ObjectNotInGtdException("note");
		}
		inbox.removeNote(note);
		callRefreshInbox();
	}

	/**
	 * Aktualisiert das Datum der letzen Inbox-Notizumwandlung.
	 */
	public void updateLastNoteConversion() {
		gtdController.getGtd().getInbox().updateLastNoteConversion();
		callRefreshLastNoteConversion();
	}

}
