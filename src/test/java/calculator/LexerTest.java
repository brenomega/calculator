package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import calculator.Lexer.Token;
import calculator.Lexer.TokenType;

public class LexerTest {

	@Test
	void testExpressionTokens() throws Exception {
		String expression = "12 + 3.14 * (2 - 5) / 2 ** 3 // 1";

		Lexer lexer = new Lexer(new StringReader(expression));

		List<Token> tokens = new ArrayList<>();
		Token token;
		while ((token = lexer.yylex()) != null) {
			tokens.add(token);
			if (token.type() == Token.eof().type())
				break;
		}

		TokenType[] expectedTypes = { TokenType.INT, TokenType.PLUS, TokenType.FLOAT, TokenType.TIMES, TokenType.LPAREN,
				TokenType.INT, TokenType.MINUS, TokenType.INT, TokenType.RPAREN, TokenType.DIV, TokenType.INT,
				TokenType.POW, TokenType.INT, TokenType.INTDIV, TokenType.INT, TokenType.EOF };

		assertEquals(expectedTypes.length, tokens.size(), "Número de tokens incorreto");

		for (int i = 0; i < expectedTypes.length; i++) {
			assertEquals(expectedTypes[i], tokens.get(i).type(), "Token #" + i + " incorreto");
		}
	}
}
