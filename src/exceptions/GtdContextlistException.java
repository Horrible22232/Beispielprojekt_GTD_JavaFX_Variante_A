package exceptions;

/**
 * Exceptions zu Kontextlisten. Erbt von {@link GtdException}.
 *
 * @author Florian
 *
 */
public class GtdContextlistException extends GtdException {
	private static final long serialVersionUID = 1L;

	public GtdContextlistException(String message) {
		super(message);
	}

}
