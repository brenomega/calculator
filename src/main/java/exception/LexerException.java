package exception;

public class LexerException extends ParserException {
	private static final long serialVersionUID = 1L;

	public LexerException(String message, Throwable cause) {
		super(message);
		initCause(cause);
	}
}
