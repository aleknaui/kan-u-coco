import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
/**
 * Esta clase representa una expresión regular construída a partir de una cadena.
 * La expresión regular se modela como un árbol sintáctico.
 * @author AleKnaui
 */
public class RegEx {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	public final static char OR = '|';
	public final static char CONCAT = '.';
	public final static char KLEENE = '*';
	public final static char POSITIVA = '+';
	public final static char PREGUNTA = '?';
	public final static char EPSILON = (char)127;

	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** La raíz del arbol */
	private char valor;
	/** El hijo izquierdo del arbol */
	private RegEx left;
	/** El hijo derecho del arbol */
	private RegEx right;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Caso base en el que la RegEx consta de un solo símbolo
	 */
	public RegEx(char simbolo){
		valor = simbolo;
		left = null;
		right = null;
	}
	
	/**
	 * Crea la RegEx con el símbolo e hijos indicados
	 * @param simbolo Símbolo en la raíz del árbol
	 * @param left Hijo izquierdo
	 * @param right Hijo derecho
	 */
	public RegEx( char simbolo, RegEx left, RegEx right ){
		this.valor = simbolo;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Crea la RegEx que representa la cerradura kleen del símbolo dado en el parámetro
	 * @param kleen La expresión regular a la que se aplicará la cerradura. Por convención
	 * se utilizará el nodo izquierdo y el derecho se dejará nulo.
	 */
	public RegEx( RegEx kleene ){
		valor = KLEENE;
		this.left = kleene;
		this.right = null;
	}
	
	/**
	 * Interpreta una cadena y crea su árbol sintáctico.
	 * @param regex La representación en cadena de la expresión regular.
	 */
	public RegEx(String regex) throws Exception{
		
	}
	
	private static String infix2postfix( String infix ) throws Exception{
		Stack<Character> stack = new Stack<Character>();
		String output = "";
		HashMap<Character,Integer> jerarquias = generarJerarquias();
		
		// While there are tokens to be read
		for( int i = 0; i < infix.length(); i++ ){
			
			// Read a token
			char actual = infix.charAt(i);
			
			// If the token is a number, then add it to the output queue.
			// En este caso, no son números sino símbolos.
			if( esSimbolo(actual) ){
				output += actual;
			}
			
			// If the token is a function token, then push it onto the stack.
			// If the token is a function argument separator (e.g., a comma):
			// No hay funciones para este tipo de problemas
			
			// If the token is an operator, o1, then:
			else if( esOperando(actual) ){
				/* while there is an operator token, o2, at the top of the stack, and
				either o1 is left-associative and its precedence is less than or equal to that of o2,
				or o1 is right-associative and its precedence is less than that of o2, */
				// En este caso no hay asociatividad por la derecha.
				boolean flag = true;
				while( stack.size() > 0 && flag ){
					if(esOperando(stack.peek())){
						if( jerarquias.get(new Character(actual)) <= jerarquias.get(new Character(stack.peek())) ){
							// pop o2 off the stack, onto the output queue;
							output += stack.pop();
						}
					} else flag = false;
				}
				// push o1 onto the stack.
				stack.push(actual);
			}
			
			// If the token is a left parenthesis, then push it onto the stack.
			else if( actual == '(' ) stack.push(actual);
			
			// If the token is a right parenthesis:
			else if( actual == ')' ){
				// Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
				while( ! (stack.peek() == '(') && ! stack.isEmpty())
					output += stack.pop();
				// Pop the left parenthesis from the stack, but not onto the output queue.
				if( stack.isEmpty() ) throw new Exception("Mismatched Parenthesis"); //If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
				// If the token at the top of the stack is a function token, pop it onto the output queue.
				// No hay funciones
				
				stack.pop();
			}
		}
		
		// When there are no more tokens to read:
		// While there are still operator tokens in the stack:
		while( ! stack.isEmpty() ){
			// If the operator token on the top of the stack is a parenthesis, then there are mismatched parentheses.
			if( stack.peek() == '(' || stack.peek() == ')' ) throw new Exception("Mismatched Parenthesis");
			// Pop the operator onto the output queue.
			output += stack.pop();
		}
		return output;
	}
	
	private static boolean esOperando( char caracter ){
		return caracter == OR || caracter == CONCAT || caracter == KLEENE || caracter == POSITIVA ||
		caracter == PREGUNTA;
	}
	
	private static boolean esSimbolo( char caracter ){
		return ! (esOperando(caracter) || caracter == '(' || caracter == ')' || caracter == EPSILON); 
	}
	
	private final static HashMap<Character,Integer> generarJerarquias(){
		HashMap<Character,Integer> retorno = new HashMap<Character,Integer>();
		retorno.put(OR, 0);
		retorno.put(PREGUNTA, 0);
		retorno.put(CONCAT, 1);
		retorno.put(KLEENE, 2);
		retorno.put(POSITIVA, 2);
		return retorno;
	}
	
	/**
	 * Esta clase main se corre para probar los métodos implementados.
	 */
	public static void main(String[] args) {
		String regex1 = "((a|b)*)*e((a|b)|e)*";
		print( regex1 );
		try {
			print( infix2postfix( regex1 ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void print( Object o ){
		System.out.println(o);
	}

}
