import java.io.IOException;

/**
 * Esta clase se utiliza para realizar tests
 * de funcionamiento.
 * @author AleKnaui
 *
 */
public class Test {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	final static String IDENT = "" + RegEx.OPEN_PARENTHESIS + 'A' + RegEx.OR + 'B' + RegEx.OR + 'C' + RegEx.OR + 'D' + RegEx.OR + 'E' + RegEx.OR + 'F' + RegEx.OR + 'G' + RegEx.OR + 'H' + RegEx.OR + 'I' + RegEx.OR + 'J' + RegEx.OR + 'K' + RegEx.OR + 'L' + RegEx.OR + 'M' + RegEx.OR + 'N' + RegEx.OR + 'O' + RegEx.OR + 'P' + RegEx.OR + 'Q' + RegEx.OR + 'R' + RegEx.OR + 'S' + RegEx.OR + 'T' + RegEx.OR + 'U' + RegEx.OR + 'V' + RegEx.OR + 'W' + RegEx.OR + 'X' + RegEx.OR + 'Y' + RegEx.OR + 'Z' + RegEx.OR + 'a' + RegEx.OR + 'b' + RegEx.OR + 'c' + RegEx.OR + 'd' + RegEx.OR + 'e' + RegEx.OR + 'f' + RegEx.OR + 'g' + RegEx.OR + 'h' + RegEx.OR + 'i' + RegEx.OR + 'j' + RegEx.OR + 'k' + RegEx.OR + 'l' + RegEx.OR + 'm' + RegEx.OR + 'n' + RegEx.OR + 'o' + RegEx.OR + 'p' + RegEx.OR + 'q' + RegEx.OR + 'r' + RegEx.OR + 's' + RegEx.OR + 't' + RegEx.OR + 'u' + RegEx.OR + 'v' + RegEx.OR + 'w' + RegEx.OR + 'x' + RegEx.OR + 'y' + RegEx.OR + 'z' + RegEx.CLOSE_PARENTHESIS + RegEx.OPEN_PARENTHESIS + 'A' + RegEx.OR + 'B' + RegEx.OR + 'C' + RegEx.OR + 'D' + RegEx.OR + 'E' + RegEx.OR + 'F' + RegEx.OR + 'G' + RegEx.OR + 'H' + RegEx.OR + 'I' + RegEx.OR + 'J' + RegEx.OR + 'K' + RegEx.OR + 'L' + RegEx.OR + 'M' + RegEx.OR + 'N' + RegEx.OR + 'O' + RegEx.OR + 'P' + RegEx.OR + 'Q' + RegEx.OR + 'R' + RegEx.OR + 'S' + RegEx.OR + 'T' + RegEx.OR + 'U' + RegEx.OR + 'V' + RegEx.OR + 'W' + RegEx.OR + 'X' + RegEx.OR + 'Y' + RegEx.OR + 'Z' + RegEx.OR + 'a' + RegEx.OR + 'b' + RegEx.OR + 'c' + RegEx.OR + 'd' + RegEx.OR + 'e' + RegEx.OR + 'f' + RegEx.OR + 'g' + RegEx.OR + 'h' + RegEx.OR + 'i' + RegEx.OR + 'j' + RegEx.OR + 'k' + RegEx.OR + 'l' + RegEx.OR + 'm' + RegEx.OR + 'n' + RegEx.OR + 'o' + RegEx.OR + 'p' + RegEx.OR + 'q' + RegEx.OR + 'r' + RegEx.OR + 's' + RegEx.OR + 't' + RegEx.OR + 'u' + RegEx.OR + 'v' + RegEx.OR + 'w' + RegEx.OR + 'x' + RegEx.OR + 'y' + RegEx.OR + 'z'  + RegEx.OR + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9'  + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE;
	final static String NUMBER = "" + RegEx.OPEN_PARENTHESIS + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9'  + RegEx.CLOSE_PARENTHESIS + RegEx.OPEN_PARENTHESIS + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9'  + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE;
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception{
		Lexer lexer = new Lexer();
		lexer.addKeyword("while", "while");
		lexer.addToken("ident", IDENT);
		lexer.addToken("number", NUMBER);
		lexer.setWhitespace(" " + RegEx.OR + "\n" + RegEx.OR + "\t");
		lexer.asignarCadena("djfa as9d87f\n9dfja9\t923847");
		Token[] tokens = lexer.tokenize();
		for(Token token : tokens )
			print(token);
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
	public static void print(){
		System.out.println();
	}

}