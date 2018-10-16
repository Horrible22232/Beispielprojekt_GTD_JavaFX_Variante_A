package exceptions;

/**
 * Exceptions zu Taetigkeiten. Erbt von {@link GtdException}.
 *
 * @author Florian
 *
 */
public class GtdJobException extends GtdException {
	private static final long serialVersionUID = 1L;

	public GtdJobException(String message) {
		super(message);
	}

}
