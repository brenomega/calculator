package exception;

public class InvalidUnaryOperationException extends ParserException {
	private static final long serialVersionUID = 1L;

	public InvalidUnaryOperationException(Object found) {
		super("Uso inválido de '-' unário: deve preceder número ou '('. Encontrado: " + found);
	}
}
