package eternal.hoge.spring.boot.example.simple.exception;



/**
 * The Class ValidationFailedException.
 */
public class ValidationFailedException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3892116817542890495L;

	/**
	 * Instantiates a new validation failed exception.
	 *
	 * @param e
	 *            Exception
	 */
	public ValidationFailedException(Exception e) {
		super(e);
	}

	/**
	 * Instantiates a new validation failed exception.
	 *
	 * @param string
	 *            the string
	 */
	public ValidationFailedException(String string) {
		super(string);

	}

	/**
	 * Instantiates a new validation failed exception.
	 *
	 * @param string
	 *            the string
	 * @param guimasaage
	 *            the guimasaage
	 */
	public ValidationFailedException(String string, String guimasaage) {
		super(guimasaage);

	}
}
