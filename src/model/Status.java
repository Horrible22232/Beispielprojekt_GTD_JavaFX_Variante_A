package model;

/**
 * Status einer Tätigkeit.<br>
 * Folgende Stati gibt es:
 * <ul>
 * <li>EDITING</li>
 * <li>FINISHED</li>
 * <li>DELEGATED</li>
 * <li>SOMETIME</li>
 * </ul>
 *
 * @author Florian
 */
public enum Status {
	/**
	 * Die Tätigkeit muss noch bearbeitet werden.
	 */
	EDITING,
	/**
	 * Die Tätigkeit ist erledigt.
	 */
	FINISHED,
	/**
	 * Die Tätigkeit ist an eine andere Person delegiert.
	 */
	DELEGATED,
	/**
	 * Die Tätigkeit wird verschoben / wird irgendwann bearbeitet.
	 */
	SOMETIME;
}
