package calculator;

import java.io.StringReader;

import exception.ParserException;

public class Main {

	public static void main(String[] args) {
		String expression = "3**5/-(-3)";
		
		try {
			Parser parser = new Parser(new Lexer(new StringReader(expression)));
			System.out.println(parser.parse());
		} catch (ParserException e) {
			System.err.println("Erro de análise: " + e.getMessage());
		}
	}
}
