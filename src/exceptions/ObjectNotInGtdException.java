package exceptions;

/**
 * RuntimeExceptions für Object-Parameter, die nicht in GTD sind.
 *
 * @author Florian
 *
 */
public class ObjectNotInGtdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotInGtdException(String parameterName) {
		super(parameterName);
	}

}
