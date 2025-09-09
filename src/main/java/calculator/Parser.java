package calculator;

import calculator.Lexer.Token;
import calculator.Lexer.TokenType;
import exception.InvalidUnaryOperationException;
import exception.LexerException;
import exception.UnclosedParenthesisException;
import exception.UnexpectedTokenException;

public class Parser {

	private final Lexer lexer;
	private Lexer.Token current;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
		advance();
	}

	private void advance() {
		try {
			Lexer.Token t = lexer.yylex();
			current = (t == null) ? Lexer.Token.eof() : t;
		} catch (Exception e) {
			throw new LexerException("Erro no lexer", e);
		}
	}

	private final class Value {

		private final double value;
		private final boolean isInteger;

		public Value(double value, boolean isInteger) {
			this.value = value;
			this.isInteger = isInteger;
		}

		public long asLongTrunc() {
			return (long) value;
		}

		@Override
		public String toString() {
			if (isInteger)
				return String.format("%d", (long) value);
			return Double.toString(value);
		}
	}

	public Value parse() {
		Value result = parseExpression();
		if (current.type() != Token.eof().type()) {
			throw new UnexpectedTokenException("EOF", current);
		}
		return result;
	}

	private Value parseExpression() {
		Value leftTerm = parseTerm();

		while (current.type() == TokenType.PLUS || current.type() == TokenType.MINUS) {
			TokenType op = current.type();
			advance();
			Value rightTerm = parseTerm();

			leftTerm = applyBinary(op, leftTerm, rightTerm);
		}

		Value expression = leftTerm;
		return expression;
	}

	private Value parseTerm() {
		Value leftPower = parsePower();

		while (current.type() == TokenType.TIMES || current.type() == TokenType.DIV
				|| current.type() == TokenType.INTDIV) {
			TokenType op = current.type();
			advance();
			Value rightPower = parsePower();

			leftPower = applyBinary(op, leftPower, rightPower);
		}

		Value term = leftPower;
		return term;
	}

	private Value parsePower() {
		Value leftUnary = parseUnary();
		Value power = leftUnary;

		if (current.type() == TokenType.POW) {
			advance();
			Value rightPower = parsePower();

			power = applyPow(leftUnary, rightPower);
		}

		return power;
	}

	private Value parseUnary() {
		if (current.type() == TokenType.MINUS) {
			advance();
			if (!(current.type() == TokenType.INT || current.type() == Lexer.TokenType.FLOAT
					|| current.type() == Lexer.TokenType.LPAREN)) {
				throw new InvalidUnaryOperationException(current);
			}
			Value primary = parsePrimary();
			return new Value(-primary.value, primary.isInteger);
		}
		return parsePrimary();
	}

	private Value parsePrimary() {
		switch (current.type()) {
		case INT -> {
			long value = Long.parseLong(current.value());
			advance();
			return new Value(value, true);
		}
		case FLOAT -> {
			double value = Double.parseDouble(current.value());
			advance();
			return new Value(value, false);
		}
		case LPAREN -> {
			advance();
			Value value = parseExpression();
			if (current.type() != TokenType.RPAREN) {
				throw new UnclosedParenthesisException(current);
			}
			advance();
			return value;
		}
		default -> throw new UnexpectedTokenException("número ou '('", current);
		}
	}

	private Value applyBinary(TokenType op, Value a, Value b) {
		switch (op) {
		case PLUS -> {
			if (a.isInteger && b.isInteger)
				return new Value((double) (a.asLongTrunc() + b.asLongTrunc()), true);
			return new Value(a.value + b.value, false);
		}
		case MINUS -> {
			if (a.isInteger && b.isInteger)
				return new Value((double) (a.asLongTrunc() - b.asLongTrunc()), true);
			return new Value(a.value - b.value, false);
		}
		case TIMES -> {
			if (a.isInteger && b.isInteger)
				return new Value((double) (a.asLongTrunc() * b.asLongTrunc()), true);
			return new Value(a.value * b.value, false);
		}
		case DIV -> {
			if (b.value == 0.0)
				throw new ArithmeticException("Divisão por zero");
			if (a.isInteger == true && b.isInteger == true)
				return new Value(a.value / b.value, true);
			return new Value(a.value / b.value, false);
		}
		case INTDIV -> {
			if (b.asLongTrunc() == 0L)
				throw new ArithmeticException("Divisão inteira por zero");
			return new Value((double) (a.asLongTrunc() / b.asLongTrunc()), true);
		}
		default -> throw new UnexpectedTokenException("operador binário válido", op);
		}
	}

	private Value applyPow(Value a, Value b) {
		double result = Math.pow(a.value, b.value);
		if (a.isInteger && b.isInteger && b.value >= 0 && result == Math.floor(result)) {
			return new Value(result, true);
		}
		return new Value(result, false);
	}
}
