package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import abstractuserinterfaces.JobsAUI;
import exceptions.EmptyStringParameterException;
import exceptions.GtdJobException;
import exceptions.ObjectNotInGtdException;
import model.Contextlist;
import model.Delegation;
import model.Job;
import model.Project;
import model.Status;
import model.TreeNode;

/**
 * Controller für Funktionen zur Verwaltung der Tätigkeiten.
 *
 * @author Florian
 */
public class JobController {
	/**
	 * Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * Controllern dient.
	 */
	private GtdController gtdController;
	/**
	 * Liste von {@link JobsAUI}-AbstractUserInterfaces.
	 */
	private CopyOnWriteArraySet<JobsAUI> jobsAUIs = new CopyOnWriteArraySet<>();

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
	protected JobController(GtdController gtdController) throws NullPointerException {
		ParamVal.objNotNull(gtdController, "gtdController");
		this.gtdController = gtdController;
	}

	/**
	 * Fügt ein neues {@link JobsAUI} zur Liste hinzu.
	 *
	 * @param jobsAUI
	 *            Das neue AbstractUserInterface.
	 */
	public void addJobsAUI(JobsAUI jobsAUI) {
		jobsAUIs.add(jobsAUI);
	}

	/**
	 * Ruft die Methode {@link JobsAUI#refreshJob()} in allen Interfaces auf.
	 */
	public void callRefreshJob() {
		for (JobsAUI jobsAUI : jobsAUIs) {
			jobsAUI.refreshJob();
		}
	}

	/**
	 * Ruft die Methode {@link JobsAUI#refreshJobs()} in allen Interfaces auf.
	 */
	public void callRefreshJobs() {
		for (JobsAUI jobsAUI : jobsAUIs) {
			jobsAUI.refreshJobs();
		}
	}

	/**
	 * Erzeugt in der Kontextliste mit dem übergebenen Titel eine neue Tätigkeit
	 * mit dem übergebenen Bezeichner.
	 *
	 * @param contextlist
	 *            Die Kontextliste.
	 * @param title
	 *            Der Titel der Tätigkeit.
	 * @param completeUntil
	 *            Das Datum, bis wann die Tätigkeit erledigt sein soll. Muss in
	 *            der Zukunft liegen.
	 * @param description
	 *            Die detaillierte Beschreibung der Tätigkeit. Darf ein leerer
	 *            String, aber nicht <em>null</em> sein.
	 * @return Die erstellte Tätigkeit.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn der Titel leer ist.
	 * @throws IllegalArgumentException
	 *             Die Runtime-Exception wird geworfen, wenn das übergebene
	 *             Datum in der Vergangenheit liegt.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn die übergebene
	 *             Kontextliste nicht in Gtd enthalten ist.
	 * @throws GtdJobException
	 *             Die Exception wird geworfen, wenn bereits eine Tätigkeit mit
	 *             dem Titel in der Kontextliste existiert.
	 * @postconditions Eine Tätigkeit mit dem Bezeichner, der Beschreibung und
	 *                 dem Zieldatum ist in der angegebenen Kontextliste
	 *                 vorhanden.
	 */
	public Job createJob(Contextlist contextlist, String title, LocalDate completeUntil, String description)
			throws NullPointerException, EmptyStringParameterException, IllegalArgumentException,
			ObjectNotInGtdException, GtdJobException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return null;
	}

	/**
	 * Delegiert eine Tätigkeit an eine andere Person bis zu einem bestimmten
	 * Datum.
	 *
	 * @param job
	 *            Die zu delegierende Tätigkeit.
	 * @param delegation
	 *            Das Delegation-Objekt.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen String-Parameter leer ist.
	 * @throws IllegalArgumentException
	 *             Die Runtime-Exception wird geworfen, wenn das übergebene
	 *             Datum in der Vergangenheit liegt.
	 * @throws GtdJobException
	 *             Eine Exception wird geworfen, wenn die Tätigkeit nicht in
	 *             einer Kontextliste ist.
	 */
	public void delegateJob(Job job, Delegation delegation)
			throws NullPointerException, EmptyStringParameterException, IllegalArgumentException, GtdJobException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Überprüft, ob eine Tätgkeit mit dem Titel bereits existiert.
	 *
	 * @param contextlist
	 *            Die Kontextliste in der gesucht werden soll.
	 * @param title
	 *            Der Titel der Tätigkeit, nach der gesucht werden soll.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws EmptyStringParameterException
	 *             Die Runtime-Exception wird geworfen, wenn der Titel leer ist.
	 * @return true, wenn eine Tätigkeit mit dem Titel exisitert.
	 */
	protected boolean existJobTitle(Contextlist contextlist, String title)
			throws NullPointerException, EmptyStringParameterException {
		ParamVal.objNotNull(contextlist, "contextlist");
		ParamVal.stringNotNullOrEmpty(title, "title");
		for (Job job : contextlist.getJobs()) {
			if (job.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verschiebt eine Tätigkeit von einer Kontextliste in eine Andere. Wenn die
	 * Ziel-Kontextliste gleich der Ursprungs-Kontextliste ist, ändert sich
	 * nichts. Wenn in der Ziel-Kontextliste bereits eine Tätigkeit mit dem
	 * gleichen Titel existiert, wird die Tätigkeit nicht verschoben.
	 *
	 * @param job
	 *            Die Tätigkeit
	 * @param newContextlist
	 *            Die neue Kontextliste
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn die übergebene
	 *             Kontextliste nicht in Gtd enthalten ist.
	 * @throws GtdJobException
	 *             Eine Exception wird geworfen, wenn die Tätigkeit nicht in
	 *             einer Kontextliste ist oder eine andere Tätigkeit mit dem
	 *             gleichen Titel in der Ziel-Kontexliste bereits existiert.
	 */
	public void moveJobToContextlist(Job job, Contextlist newContextlist)
			throws NullPointerException, ObjectNotInGtdException, GtdJobException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Verschiebt eine Tätigkeit in ein Projekt. Ist die Tätigkeit bereits einem
	 * Projekt zugeordnet, wird diese aus dem alten Projekt entfernt. Wenn der
	 * Projekt-Parameter nicht <em>null</em> ist, wird die Tätigkeit in das neue
	 * Projekt eingefügt.
	 *
	 * @param job
	 *            Die zu verschiebene Tätigkeit.
	 * @param newProject
	 *            Das (neue) Projekt.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn ein Parameter
	 *             <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn eines der Objekte
	 *             nicht in Gtd enthalten ist.
	 * @throws GtdJobException
	 *             Die Exception wird geworfen, wenn bereits eine andere
	 *             Tätigkeit mit dem gleichen Titel im neuen Projekt existiert.
	 */
	public void moveJobToProject(Job job, Project newProject)
			throws NullPointerException, ObjectNotInGtdException, GtdJobException {
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
	}

	/**
	 * Entfernt eine Tätigkeite aus dem Projekt(baum) und aktualisiert
	 * anschließend die nächste Tätigkeit und den Projektstatus.
	 *
	 * @param job
	 *            Die zu entfernende Tätigkeit.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Job-Parameter <em>null</em> ist.
	 * @throws ObjectNotInGtdException
	 *             Die Runtime-Exception wird geworfen, wenn die Tätigkeit nicht
	 *             in Gtd enthalten ist.
	 * @throws GtdJobException
	 *             Die Exception wird geworfen, wenn die Tätigkeit nicht in
	 *             einer Projekt ist.
	 */
	public void removeJobFromProject(Job job) throws NullPointerException, ObjectNotInGtdException, GtdJobException {
		// Überprüfe Parameter: not null
		ParamVal.objNotNull(job, "job");
		Project project = searchProject(job);
		// Teste ob Tätigkeit in Gtd
		if (project == null) {
			throw new GtdJobException("Die Tätigkeit ist nicht in einem Projekt.");
		}
		// Entferne Tätigkeit aus Projekt
		project.removeJob(job);
		project.updateNextJob();
		gtdController.getProjectController().updateFinishedStatusInAllProjects();
		callRefreshJobs();
	}

	/**
	 * Sucht die Kontextliste, in der die Tätigkeit ist.
	 *
	 * @param job
	 *            Die Tätigkeit für die Suche.
	 * @return Die gefundene Kontextliste mit der Tätigkeit oder <em>null</em>.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 */
	public Contextlist searchContextlist(Job job) throws NullPointerException {
		ParamVal.objNotNull(job, "job");

		ArrayList<Contextlist> contextlists = gtdController.getGtd().getContextlists();
		for (Contextlist contextlist : contextlists) {
			if (contextlist.getJobs().contains(job)) {
				return contextlist;
			}
		}
		return null;
	}

	/**
	 * Sucht mögliches Projekt, zu dem die Tätigkeit gehört.
	 *
	 * @param job
	 *            Die Tätigkeit für die Suche.
	 * @return Das gefundene Projekt mit der Tätigkeit oder <em>null</em>.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter
	 *             <em>null</em> ist.
	 */
	public Project searchProject(Job job) throws NullPointerException {
		ParamVal.objNotNull(job, "job");
		return searchProjectRek(job, gtdController.getGtd().getRootNode());
	}

	/**
	 * Rekursive Submethode von searchProject, die den Baum rekursiv nach dem
	 * Projekt mit der Tätigkeit durchsucht.
	 *
	 * @param job
	 *            Die Tätigkeit für die Suche.
	 * @param treeNode
	 *            Der Vaterknoten von Subprojekten.
	 * @return Das gefundene Projekt mit der Tätigkeit oder <em>null</em>.
	 */
	private Project searchProjectRek(Job job, TreeNode treeNode) {
		Project result = null;
		for (int i = 0; result == null && i < treeNode.getSubprojects().size(); i++) {
			Project project = treeNode.getSubprojects().get(i);
			if (project.getJobs().contains(job)) {
				result = project;
			} else {
				result = searchProjectRek(job, project);
			}
		}
		return result;
	}

	/**
	 * Ersetzt den Status einer Tätigkeit mit einem neuen Status-Wert (bis auf
	 * {@link Status#DELEGATED}). Liefert "true", wenn der Status geändert
	 * wurde.
	 *
	 * @param job
	 *            Die Tätigkeit.
	 * @param status
	 *            Der neue Status.
	 * @return Ob der Status geändert wurde.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn einer der
	 *             übergebenen Parameter <em>null</em> ist.
	 * @throws IllegalArgumentException
	 *             Die Runtime-Exception wird geworfen, wenn der übergebene
	 *             Status DELEGATED ist, da hier die Delegationsinformationen
	 *             fehlen. Verwenden Sie stattdessen
	 *             {@link JobController#delegateJob(Job, Delegation)}
	 * @throws GtdJobException
	 *             Die Exception wird geworfen, wenn der Status nicht geändert
	 *             werden kann, da die Tätigkeit in keiner Kontextliste mehr
	 *             ist.
	 */
	public boolean setJobStatus(Job job, Status status) throws NullPointerException, GtdJobException {
		boolean statusWurdeGesetzt = false;
		// Diese Funktion muss im Aufwärmprojekt nicht implementiert werden.
		return statusWurdeGesetzt;
	}

}
