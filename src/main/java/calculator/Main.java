package calculator;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import exception.ParserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import calculator.Lexer.Token;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int caseNumber;

		System.out.println("Escolha o caso de teste (1, 2, 3 ou 4):");

		while (true) {
			if (scanner.hasNextInt()) {
				caseNumber = scanner.nextInt();
				if (caseNumber >= 1 && caseNumber <= 4) {
					break;
				}
			}
			System.out.println("Entrada inválida. Por favor, digite 1, 2, 3 ou 4.");
			scanner.nextLine();
		}
		scanner.close();

		String filePath = "cases/entrada" + caseNumber + ".txt";

		try {
			String expression = Files.readString(Paths.get(filePath));
			expression = expression.trim();
			//Parser parser = new Parser(new Lexer(new StringReader(expression)));
			//System.out.println(parser.parse());

			Lexer lexer = new Lexer(new StringReader(expression));
			List<Token> tokens = new ArrayList<>();
			System.out.println("\nTokenização da expressão:\n" + expression + "\n");

			Token token;
			while ((token = lexer.yylex()) != null && token.type() != Token.eof().type()) {
				tokens.add(token);
			}
			tokens.add(Token.eof());

			// Imprimindo a lista de tokens
			for (Token t : tokens) {
				System.out.println(t);
			}

		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}
}