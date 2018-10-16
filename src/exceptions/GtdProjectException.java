package exceptions;

/**
 * Exceptions zu Projekte. Erbt von {@link GtdException}.
 *
 * @author Florian
 *
 */
public class GtdProjectException extends GtdException {
	private static final long serialVersionUID = 1L;

	public GtdProjectException(String message) {
		super(message);
	}

}
