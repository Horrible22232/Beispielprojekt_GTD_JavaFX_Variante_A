package exceptions;

/**
 * RuntimeExceptions zu Leer-String-Parametern (müssen nicht explizit abgefangen
 * werden).<br>
 * Erbt von {@link RuntimeException}.
 *
 * @author Florian
 *
 */
public class EmptyStringParameterException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyStringParameterException(String message) {
		super(message);
	}

}
