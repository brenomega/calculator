package calculator;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import exception.ParserException;

public class Main {

	public static void main(String[] args) {
		String filePath = "cases/entrada3.txt";

		try {
			String expression = Files.readString(Paths.get(filePath));
			expression = expression.trim();
			Parser parser = new Parser(new Lexer(new StringReader(expression)));
			System.out.println(parser.parse());

		} catch (ParserException e) {
			System.err.println("Erro de análise: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}
}