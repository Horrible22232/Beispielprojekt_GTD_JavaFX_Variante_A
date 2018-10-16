package exceptions;

/**
 * Exceptions zum Laden/Speichern des GettingThingsDone-Programms. Erbt von
 * {@link GtdException}.
 *
 * @author Florian
 *
 */
public class GtdIOException extends GtdException {
	private static final long serialVersionUID = 1L;

	public GtdIOException(String message) {
		super(message);
	}

}
