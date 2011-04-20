import java.io.*;

/**
 * Esta clase reconoce un archivo .ATG con
 * sintaxis COCOl.
 * @author AleKnaui
 */
public class Recognizer {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	int LOOKAHEAD_DEPTH = 15;
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	private CharBuffer input;
	
	/*private BufferedReader reader;
	private StringBuffer buffer;
	private int c;*/
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	public Recognizer( String path ) throws IOException{
		input = new CharBuffer( new BufferedReader( new FileReader(path) ), LOOKAHEAD_DEPTH);
			Cocol();
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	private int LA(int i){
		return input.LA(i);
	}
	
	private void consume(){
		input.consume();
		Test.print((char)LA(1));
	}
	
	private void match(int c){
		consume();
	}
	
	// --------------------------------------------------------------------------------
	// Vocabulario de Cocol
	
	private String ident(){
		
		Test.print("[IDENT: ");
		
		String letras = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		String digitos = "0123456789";
		
		StringBuffer s = new StringBuffer();
		s.append(LA(1));
		consume(); // Consume la primera letra
		//while( Character.isLetter(LA(1)) || Character.isDigit(LA(1)) ){
		while( letras.contains(Character.toString((char)LA(1))) || digitos.contains(Character.toString((char)LA(1))) ){
			s.append(LA(1));
			consume();
		}
		Test.print("]");
		return s.toString();
	}
	
	private String number(){
		StringBuffer s = new StringBuffer();
		s.append(LA(1));
		consume(); // Consume el primer dígito
		while( Character.isDigit(LA(1)) ){
			s.append(LA(1));
			consume();
		}
		return s.toString();
	}
	
	private String string(){
		StringBuffer s = new StringBuffer();
		s.append(LA(1));
		consume(); // Consume las primeras comillas
		while( LA(1) != '"' ){
			s.append(LA(1));
			consume();
		}
		s.append(LA(1));
		consume(); // Consume las segundas comillas
		return s.toString();
	}
	
	private String character(){
		StringBuffer s = new StringBuffer();
		s.append(LA(1));
		consume(); // Consume la primera apóstrofe
		while( LA(1) != '\'' ){
			s.append(LA(1));
			consume();
		}
		s.append(LA(1));
		consume(); // Consume la segunda apóstrofe
		return s.toString();
	}
	
	// --------------------------------------------------------------------------------
	// Sintaxis de Cocol
	
	private void Cocol(){
		//match('C');
		match('O');
		match('M');
		match('P');
		match('I');
		match('L');
		match('E');
		match('R');
		
		ident();
		
		ScannerSpecification();
		ParserSpecification();
		
		match('E');
		match('N');
		match('D');
		
		ident();
		
		match('.');
	}
	
	// --------------------------------------------------------------------------------
	// Especificación de Scanner
	
	private void ScannerSpecification(){
		match('C');
		match('H');
		match('A');
		match('R');
		match('A');
		match('C');
		match('T');
		match('E');
		match('R');
		match('S');
		
		while( !( LA(1) == 'K' && LA(2) == 'E' && LA(3) == 'Y' && LA(4) == 'W' && LA(5) == 'O' && LA(6) == 'R' && LA(7) == 'D' && LA(8) == 'S' )  ){
			SetDecl();
		}
		
		match('K');
		match('E');
		match('Y');
		match('W');
		match('O');
		match('R');
		match('D');
		match('S');

		while( !( LA(1) == 'T' && LA(2) == 'O' && LA(3) == 'K' && LA(4) == 'E' && LA(5) == 'N' && LA(6) == 'S' ) ){
			KeywordDecl();
		}
		
		match('T');
		match('O');
		match('K');
		match('E');
		match('N');
		match('S');
		
		while( !( ( LA(1) == 'I' && LA(2) == 'G' && LA(3) == 'N' && LA(4) == 'O' && LA(5) == 'R' && LA(6) == 'E' ) || ( LA(1) == 'P' && LA(2) == 'R' && LA(3) == 'O' && LA(4) == 'D' && LA(5) == 'U' && LA(6) == 'C' && LA(7) == 'T' && LA(8) == 'I' && LA(9) == 'O' && LA(10) == 'N' && LA(11) == 'S' )) )
			TokenDecl();

		while( ( LA(1) == 'I' && LA(2) == 'G' && LA(3) == 'N' && LA(4) == 'O' && LA(5) == 'R' && LA(6) == 'E' ) ){
			WhiteSpaceDecl();
		}		
	}
	
	// --------------------------------------------------------------------------------
	// Sintáxis para sets
	
	private String SetDecl(){
		StringBuffer s = new StringBuffer();
		
		String ident = ident();
		s.append(s);
		
		match('=');
		
		Set();
		
		return s.toString();
	}
	
	private String Set(){
		StringBuffer s = new StringBuffer();
		
		BasicSet();
		
		while( LA(1) == '+' || LA(1) == '-' ){
		
			if( LA(1) == '+' ){
				match('+');
			} else if( LA(1) == '-' ){
				match('-');
			}
			
			BasicSet();
		
		}
		
		
		return s.toString();
	}
	
	private String BasicSet(){
		if( LA(1) == '"' )
			string();
		else if( LA(1) == '\'' || ( LA(1) == 'C' && LA(2) == 'H' && LA(3) == 'R' && LA(4) == '(' ) ){
			Char();
			if( LA(1) == '.' && LA(2) == '.' ){
				match('.');
				match('.');
				Char();
			}
		}
		else
			ident();
		return null;
	}
	
	private String Char(){
		if( LA(1) == '\'' )
			character();
		else if( LA(1) == 'C' && LA(2) == 'H' && LA(3) == 'R' && LA(4) == '(' ){
			match('C');
			match('H');
			match('R');
			match('(');
			
			number();
			
			match(')');
		}
		
		return null;
	}
	
	// --------------------------------------------------------------------------------
	// Sintaxis para Keywords
	
	private String KeywordDecl(){
		
		ident();
		match('=');
		string();
		
		return null;
	}
	
	// --------------------------------------------------------------------------------
	// Sintaxis para Tokens
	
	private String TokenDecl(){
		ident();
		
		if( LA(1) == '=' ){
			match('=');
			
			TokenExpr();
		}
		
		if( LA(1) != '.' ){
			match('E');
			match('X');
			match('C');
			match('E');
			match('P');
			match('T');
			match(' ');
			match('K');
			match('E');
			match('Y');
			match('W');
			match('O');
			match('R');
			match('D');
			match('S');

		}
		
		match('.');
		
		return null;
	}
	
	private String TokenExpr(){
		
		TokenTerm();
		
		while( LA(1) == '|' ){
			match('|');
			TokenTerm();
		}
		
		return null;
	}
	
	private String TokenTerm(){
		
		TokenFactor();
		
		while( !( ( LA(1) == 'E' && LA(2) == 'X' && LA(3) == 'C' && LA(4) == 'E' && LA(5) == 'P' && LA(6) == 'T' && LA(7) == ' ' && LA(8) == 'K' && LA(9) == 'E' && LA(10) == 'Y' && LA(11) == 'W' && LA(12) == 'O' && LA(13) == 'R' && LA(14) == 'D' && LA(15) == 'S' ) 
				| ( LA(1) == '.' ) ) ){
			TokenFactor();
		}
		
		return null;
		
	}
	
	private String TokenFactor(){
		switch(LA(1)){
		
		case '(':
			match('(');
			TokenExpr();
			match(')');
			break;
		
		case '[':
			match('[');
			TokenExpr();
			match(']');
			break;
		
		case '{':
			match('{');
			TokenExpr();
			match('}');
			break;
		}
		
		return null;
	}
	
	private String Symbol(){
		if( LA(1) == '"' )
			string();
		else if( LA(1) == '\'' )
			character();
		else
			ident();
		
		return null;
	}
	
	private String WhiteSpaceDecl(){
		match('I');
		match('G');
		match('N');
		match('O');
		match('R');
		match('E');
		
		Set();

		return null;
	}

	// --------------------------------------------------------------------------------
	// Especificación de Parser
	
	private String ParserSpecification(){
		return null;
	}

}
