package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import control.DateFactory;

/**
 * Testet die Klasse {@link Job}.
 *
 * @author Florian
 */
public class JobTest {
	private Job job;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception
	 *             Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		job = new Job("Titel", DateFactory.now(), "description");
	}

	/**
	 * Testet den Job-Konstruktor.<br>
	 * Eine neu angelegte Tätigkeit sollte nicht delegiert sein(delegation=null)
	 * und den Status EDITING haben.
	 */
	@Test
	public void testConstructor() {
		assertFalse(job.isFinished());
		assertEquals(Status.EDITING, job.getStatus());
		assertNull(job.getDelegation());
	}

	/**
	 * Überprüft, ob eine eine erledigte Tätigkeit den Status FINISHED hat.
	 */
	@Test
	public void testIsFinished() {
		assertFalse(job.isFinished());
		assertEquals(Status.EDITING, job.getStatus());
		job.setStatus(Status.FINISHED);
		assertTrue(job.isFinished());
		assertEquals(Status.FINISHED, job.getStatus());
	}

	/**
	 * Testet das delegieren und Aufheben der Delegation einer Tätigkeit.<br>
	 * Wird die Delegation gesetzt, wird der Status auf DELEGATED gesetzt. Bei
	 * einer Aufhebung der Delegation wird der Status auf EDITING gesetzt.
	 */
	@Test
	public void testSetDelegation() {
		// Test Delegation: delegation!=null & status=DELEGATED
		job.setDelegation(new Delegation("Maxin Mustermann", DateFactory.now()));
		assertEquals(Status.DELEGATED, job.getStatus());
		assertNotNull(job.getDelegation());
		// Test null: return false
		job.setStatus(Status.EDITING);
		assertEquals(Status.EDITING, job.getStatus());
		assertNull(job.getDelegation());
		job.setDelegation(null);
		assertEquals(Status.EDITING, job.getStatus());
		assertNull(job.getDelegation());
	}

	/**
	 * Testet das Setzen des Status.<br>
	 * Mit der setStatus-Methode sollte der Status nicht auf DELEGATED gesetzt
	 * werden können. Hat die Tätigkeit den Status DELEGATED, kann kein anderer
	 * Status gesetzt werden. Zum Aufheben der Delegation muss
	 * setDelegation(null) verwendet werden.
	 */
	@Test
	public void testSetStatus() {
		assertEquals(Status.EDITING, job.getStatus());
		// Test set DELEGATED
		assertFalse(job.setStatus(Status.DELEGATED));
		assertEquals(Status.EDITING, job.getStatus());
		// Teste von Delegation zu anderem Status
		job.setDelegation(new Delegation("Maxin Mustermann", DateFactory.now()));
		assertEquals(Status.DELEGATED, job.getStatus());
		// Test Status SOMETIME
		assertTrue(job.setStatus(Status.SOMETIME));
		assertEquals(Status.SOMETIME, job.getStatus());
		// Test Status FINISHED
		assertTrue(job.setStatus(Status.FINISHED));
		assertEquals(Status.FINISHED, job.getStatus());
		// Test Status EDITING
		assertTrue(job.setStatus(Status.EDITING));
		assertEquals(Status.EDITING, job.getStatus());
	}

}
