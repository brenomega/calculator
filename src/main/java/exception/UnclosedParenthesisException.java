package exception;

public class UnclosedParenthesisException extends ParserException {
	private static final long serialVersionUID = 1L;

	public UnclosedParenthesisException(Object found) {
		super("Parêntese não fechado: esperado ')' mas encontrou: " + found);
	}
}
