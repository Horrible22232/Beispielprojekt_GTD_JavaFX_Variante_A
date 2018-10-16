package control;

import java.time.LocalDate;

/**
 * Klasse zum einfachen Erstellen von LocalDate-Objekten.<br>
 * Die Zeit-Parameter sind in folgenden Formaten anzugeben:
 * <ul>
 * <li><b>year</b>: yyyy, z.B. 2014</li>
 * <li><b>month</b>: mm (1-12), z.B. 6</li>
 * <li><b>dayOfMonth</b>: (1-31), z.B. 25</li>
 * </ul>
 *
 * @author Florian
 *
 */
public class DateFactory {

	private DateFactory() {
	}

	/**
	 * Erstellt ein Objekt mit dem aktuellen Datum.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @return Das aktuelle Datum.
	 */
	public static LocalDate now() {
		return LocalDate.now();
	}

	/**
	 * Erstellt ein Objekt mit dem gestrigen Datum.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @return Das gestrige Datum.
	 */
	public static LocalDate yesterday() {
		return LocalDate.now().minusDays(1);
	}

	/**
	 * Erstellt ein Objekt mit dem morgigen Datum.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @return Das morgige Datum.
	 */
	public static LocalDate tomorrow() {
		return LocalDate.now().plusDays(1);
	}

	/**
	 * Erstellt ein Datum, alle nicht angegebenen Werte werden von dem aktuellen
	 * Datum übernommen.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @param year
	 *            Das Jahr des Datums.
	 * @return Das erstellte Datum.
	 */
	public static LocalDate date(int year) {
		LocalDate localDate = LocalDate.now();
		return date(year, localDate.getMonth().getValue(), localDate.getDayOfMonth());
	}

	/**
	 * Erstellt ein Datum, alle nicht angegebenen Werte werden von dem aktuellen
	 * Datum übernommen.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @param year
	 *            Das Jahr des Datums.
	 * @param month
	 *            Der Monat des Datums.
	 * @return Das erstellte Datum.
	 */
	public static LocalDate date(int year, int month) {
		LocalDate localDate = LocalDate.now();
		return date(year, month, localDate.getDayOfMonth());
	}

	/**
	 * Erstellt ein Datum, alle nicht angegebenen Werte werden von dem aktuellen
	 * Datum übernommen.
	 *
	 * @see DateFactory Formatbeschreibung der Parameter.
	 * @param year
	 *            Das Jahr des Datums.
	 * @param month
	 *            Der Monat des Datums.
	 * @param dayOfMonth
	 *            Der Tag des Datums.
	 * @return Das erstellte Datum.
	 */
	public static LocalDate date(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth);
	}

}
