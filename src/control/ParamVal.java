package control;

import java.time.LocalDate;

import exceptions.EmptyStringParameterException;

/**
 * Diese Klasse stellt Funktionen zur Validierung von Parametern bereit.
 *
 * @author Florian
 */
public class ParamVal {

	/**
	 * Private Konstruktor verhindert die Instanziierung.
	 */
	private ParamVal() {
	}

	/**
	 * Testet ein LocalDate-Parameter.<br>
	 * Wenn der Parameter <em>null</em> ist, wird die RuntimeException
	 * NullPointerException geworfen, und wenn das LocalDate vor dem aktuellen
	 * Datums liegt eine IllegalArgumentException.
	 *
	 * @param parameter
	 *            Der Parameter.
	 * @return Der Parameter.
	 * @param nameOfParameter
	 *            Der Name des Parameters.
	 * @throws NullPointerException
	 *             Der LocalDate-Parameter ist <em>null</em>.
	 * @throws IllegalArgumentException
	 *             Der LocalDate-Parameter liegt in der Vergangenheit.
	 */
	public static LocalDate dateNotNullAndInFuture(LocalDate parameter, String nameOfParameter)
			throws NullPointerException, IllegalArgumentException {
		if (parameter == null) {
			throw new NullPointerException("Der Parameter '" + nameOfParameter + "' ist null.");
		} else if (parameter.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException(
					"Der LocalDate-Parameter '" + nameOfParameter + "' liegt in der Vergangenheit.");
		}
		return parameter;
	}

	/**
	 * Testet einen Object-Parameter (Typ-unabhängig).<br>
	 * Wenn er <em>null</em> ist, wird die RuntimeException NullPointerException
	 * geworfen.
	 *
	 * @param <T>
	 *            Generischer Typ des Objects.
	 * @param parameter
	 *            Der Parameter.
	 * @param nameOfParameter
	 *            Der Name des Parameters.
	 * @throws NullPointerException
	 *             Der Object-Parameter ist <em>null</em>.
	 */
	public static <T> void objNotNull(T parameter, String nameOfParameter) throws NullPointerException {
		if (parameter == null) {
			throw new NullPointerException("Der Parameter '" + nameOfParameter + "' ist null.");
		}
	}

	/**
	 * Testet ein String-Parameter.<br>
	 * Wenn <em>null</em>, wird die RuntimeExceptions NullPointerException
	 * geworfen, und wenn leer EmptyStringParameterException.
	 *
	 * @param parameter
	 *            Der Parameter.
	 * @param nameOfParameter
	 *            Der Name des Parameters.
	 * @return Der Parameter.
	 * @throws NullPointerException
	 *             Der String-Parameter ist <em>null</em>.
	 * @throws EmptyStringParameterException
	 *             Der String-Parameter ist leer.
	 */
	public static String stringNotNullOrEmpty(String parameter, String nameOfParameter)
			throws NullPointerException, EmptyStringParameterException {
		if (parameter == null) {
			throw new NullPointerException("Der Parameter '" + nameOfParameter + "' ist null.");
		} else if (parameter.trim().isEmpty()) {
			throw new EmptyStringParameterException("Der Parameter '" + nameOfParameter + "' ist leer.");
		}
		return parameter;
	}

}
