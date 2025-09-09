package exception;

public class UnexpectedTokenException extends ParserException {
	private static final long serialVersionUID = 1L;

	public UnexpectedTokenException(String expected, Object found) {
		super("Esperado: " + expected + ", mas encontrado: " + found);
	}
}
