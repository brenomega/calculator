package calculator;

%% 

%class Lexer
%unicode
%public
%type calculator.Lexer.Token
%{
	public enum TokenType {
		INT, FLOAT, PLUS, MINUS, TIMES, POW, DIV, INTDIV, LPAREN, RPAREN, EOF
	}
	
	public record Token(TokenType type, String value) {
		public static Token eof() {
			return new Token(TokenType.EOF, "");
		}
		@Override
		public String toString() {
			return type + "('" + value + "')";
		}
	}
%}

d1 = [0-9]
d2 = [1-9]
integer = 0|{d2}{d1}*
float = ((0)|({d2}{d1}*))\.{d1}+

%%

[ \t\n\r]+ {}

{float} {
	return new Token(TokenType.FLOAT, yytext());
}

{integer} {
	return new Token(TokenType.INT, yytext());
}

"**" {
	return new Token(TokenType.POW, yytext());
}

"//" {
	return new Token(TokenType.INTDIV, yytext());
}

"*" {
	return new Token(TokenType.TIMES, yytext());
}

"/" {
	return new Token(TokenType.DIV, yytext());
}

"+" {
	return new Token(TokenType.PLUS, yytext());
}

"-" {
	return new Token(TokenType.MINUS, yytext());
}

"(" {
	return new Token(TokenType.LPAREN, yytext());
}

")" {
	return new Token(TokenType.RPAREN, yytext());
}

<<EOF>> {
	return Token.eof();
}

. {
	throw new Error("Caractere inválido: " + yytext());
}