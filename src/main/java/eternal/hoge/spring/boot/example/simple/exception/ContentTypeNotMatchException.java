package eternal.hoge.spring.boot.example.simple.exception;



/**
 * The Class ContentTypeNotMatchException.
 */
public class ContentTypeNotMatchException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3892116817542890495L;

	/**
	 * Instantiates a new content type not match exception.
	 *
	 * @param e
	 *            Exception
	 */
	public ContentTypeNotMatchException(Exception e) {
		super(e);
	}

	/**
	 * Instantiates a new content type not match exception.
	 *
	 * @param string
	 *            the string
	 */
	public ContentTypeNotMatchException(String string) {
		super(string);

	}

	/**
	 * Instantiates a new content type not match exception.
	 *
	 * @param string
	 *            the string
	 * @param guimasaage
	 *            the guimasaage
	 */
	public ContentTypeNotMatchException(String string, String guimasaage) {
		super(guimasaage);

	}
}
