package exceptions;

/**
 * Exceptions zu GTD. Erbt von {@link Exception}.
 *
 * @author Florian
 *
 */
public class GtdException extends Exception {
	private static final long serialVersionUID = 1L;

	public GtdException(String message) {
		super(message);
	}

}
