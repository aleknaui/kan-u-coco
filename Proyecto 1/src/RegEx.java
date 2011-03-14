import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
/**
 * Esta clase representa una expresión regular construída a partir de una cadena.
 * La expresión regular se modela como un árbol sintáctico.
 * 
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
	public final static char EPSILON = 'e';

	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Símbolos utilizados en la Expresión Regular */
	private ArrayList<Character> alfabeto;
	/** La raíz del arbol */
	private char valor;
	/** El hijo izquierdo del arbol */
	private RegEx left;
	/** El hijo derecho del arbol */
	private RegEx right;
	
	/** Este valor indica la posibilidad de que esta expresión regular pueda ser el valor EPSILON */
	private boolean anulable;
	/** Lista de los posibles símbolos que pueden iniciar la expresión regular */
	private ArrayList<Integer> primeraPos = new ArrayList<Integer>();
	/** Lista de los posibles símbolos que pueden finalizar la expresión regular */
	private ArrayList<Integer> ultimaPos = new ArrayList<Integer>();
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Caso base en el que la RegEx consta de un solo símbolo
	 */
	public RegEx(char simbolo, int numHoja){
		valor = simbolo;
		left = null;
		right = null;
		anulable = valor == EPSILON ? true : false;
		if( ! ( valor == EPSILON ) ){
			primeraPos.add( numHoja );
			ultimaPos.add( numHoja );
		}
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
		switch(simbolo){
		case CONCAT:
			this.anulable = this.left.anulable && this.right.anulable;
			
			if( this.left.anulable ){
				for( int i : this.left.primeraPos )
					this.primeraPos.add(i);
				for( int i : this.right.primeraPos )
					this.primeraPos.add(i);
			} else this.primeraPos = this.left.primeraPos;
			
			if( this.right.anulable ){
				for( int i : this.left.ultimaPos )
					this.ultimaPos.add(i);
				for( int i : this.right.ultimaPos )
					this.ultimaPos.add(i);
			} else this.ultimaPos = this.right.ultimaPos;
			
			break;
		case OR:
			
			this.anulable = this.left.anulable || this.right.anulable;
			
			for( int i : this.left.primeraPos )
				this.primeraPos.add(i);
			for( int i : this.right.primeraPos )
				this.primeraPos.add(i);
			
			for( int i : this.left.ultimaPos )
				this.ultimaPos.add(i);
			for( int i : this.right.ultimaPos )
				this.ultimaPos.add(i);
			
			break;
		}
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
		
		this.anulable = true;
		this.primeraPos = this.left.primeraPos;
		this.ultimaPos = this.left.ultimaPos;
	}
	
	/**
	 * Interpreta una cadena y crea su árbol sintáctico.
	 * @param regex La representación en cadena de la expresión regular.
	 */
	public RegEx(String regex) throws Exception{
		
		alfabeto = new ArrayList<Character>();
		//print( "Inicial: " + regex );
		// Agrega las concatenaciones implícitas y convierte a postfix
		regex = infix2postfix(  addConcats( regex ) );
		// Quita las cerraduras redundantes
		while( regex.contains("**") ) regex = regex.replace("**","*");
		
		//print( "Postfix: " + regex );
		
		Stack<RegEx> stack = new Stack<RegEx>();
		int contador = 0;
		
		for( int i = 0; i < regex.length(); i++ ){
			char actual = regex.charAt(i);
			//print(contador + "," + actual);
			//print(actual);
			//print(i);
			if( esSimbolo( actual ) ){
				RegEx nueva = new RegEx(actual, contador); contador++;
				if( ! alfabeto.contains( actual ) ) alfabeto.add(actual);
				stack.push( nueva );
			}
			else if( actual == KLEENE ){
				RegEx nueva = new RegEx( stack.pop() );
				
				stack.push( nueva );
			}
			else if( actual == POSITIVA ){
				RegEx operando = stack.pop();
				RegEx kleene = new RegEx( operando );
				RegEx nueva = new RegEx( CONCAT, operando, kleene );
				
				stack.push( nueva );
			}
			else if( actual == PREGUNTA ){
				RegEx epsilon = new RegEx(EPSILON, contador); contador++;
				RegEx operando = stack.pop();
				RegEx nueva = new RegEx( OR, operando, epsilon );
				stack.push( nueva );
			}
			else{
				RegEx right = stack.pop();
				RegEx left = stack.pop();
				RegEx nueva = new RegEx(actual, left, right);
				stack.push( nueva );
			}
		}
		
		/*// Obtiene la expresión regular y le concatena el #.
		assert stack.size() == 1;
		RegEx finale = stack.pop();
		valor = CONCAT;
		left = finale;
		right = new RegEx('#');
		//*/
		
		assert stack.size() == 1;
		RegEx finale = stack.pop();
		valor = finale.valor;
		left = finale.left;
		right = finale.right;
		anulable = finale.anulable;
		primeraPos = finale.primeraPos;
		ultimaPos = finale.ultimaPos;
		
		//print( "Postorden: " + postorden() );
		//print( "Inorden: " + inorden() );
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Este método obtiene una expresión regular en notación común (infix) y la convierte en una
	 * cadena en notación postfix usando el Shunting-yard algorithm de Dijkstra descrito en
	 * <a href="http://en.wikipedia.org/wiki/Shunting-yard_algorithm"> Wikipedia </a>
	 * @param infix La cadena en infix.
	 * @return La cadena en notación postfix.
	 * @throws Exception Si hay un paréntesis no cerrado (o no abierto, según el caso).
	 */
	private String infix2postfix( String infix ) throws Exception{
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
			else if( esOperador(actual) ){
				/* while there is an operator token, o2, at the top of the stack, and
				either o1 is left-associative and its precedence is less than or equal to that of o2,
				or o1 is right-associative and its precedence is less than that of o2, */
				// En este caso no hay asociatividad por la derecha.
				boolean flag = true;
				while( stack.size() > 0 && flag ){
					if(esOperador(stack.peek())){
						if( jerarquias.get(new Character(actual)) <= jerarquias.get(new Character(stack.peek())) ){
							// pop o2 off the stack, onto the output queue;
							output += stack.pop();
						} else flag = false;
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
	
	/**
	 * Deduce las posiciones de la operación concat en la cadena de entrada para la regex.
	 * No importa si la cadena ya tenía todos, algunos o ningún operador de concatenación.
	 * @param regex La cadena que representa la expresión regular.
	 * @return La cadena que representa la expresión regular ya con sus concatenaciones explícitas.
	 */
	private String addConcats(String regex){
		for( int i = 1; i < regex.length(); i++ ){
			// Se concatena si hay un símbolo después de algo que no sea OR o (
			// o si es un ) o símbolo antes de un (
			if( ( (esSimbolo(regex.charAt(i)) || regex.charAt(i) == '(' ) && ( regex.charAt(i-1) != OR && regex.charAt(i-1) != '(' ) ) ||
					(esSimbolo(regex.charAt(i-1)) || regex.charAt(i-1) == ')') && regex.charAt(i) == '('){
				String pre = regex.substring(0,i);
				String post = regex.substring(i);
				regex = pre + CONCAT + post;
				i++;
			}
		}
		while( regex.contains("..") ) regex = regex.replace("..",".");
		return regex;
	}

	/**
	 * Método que genera un Hash que sirve para consultar las jerarquías de los operadores.
	 * @return El hash de las jerarquías.
	 */
	private HashMap<Character,Integer> generarJerarquias(){
		HashMap<Character,Integer> retorno = new HashMap<Character,Integer>();
		retorno.put(OR, 0);
		retorno.put(PREGUNTA, 0);
		retorno.put(CONCAT, 1);
		retorno.put(KLEENE, 2);
		retorno.put(POSITIVA, 2);
		return retorno;
	}

	/**
	 * Método que retorna el árbol sintáctico de la expresión regular regex.#
	 * @return El árbol sintáctico de la expresión regular
	 */
	public RegEx sintacticoCompleto() {
		return new RegEx( CONCAT, this, new RegEx('#', hojas().size()) );
	}

	/**
	 * Retorna el alfabeto del cual se construye la Expresión Regular.
	 * @return El alfabeto.
	 */
	public ArrayList<Character> darAlfabeto(){
		return alfabeto;
	}
	
	/**
	 * Indica el valor que se encuentra en la raíz de la RegEx
	 * @return El valor de la raíz del objeto.
	 */
	public char darValor(){
		return valor;
	}

	/**
	 * Retorna la Expresión Regular que se encuentra en el nodo hijo
	 * izquierdo de esta expresión regular.
	 * @return El hijo izquierdo de esta expresión regular.
	 */
	public RegEx darLeft(){
		return left;
	}

	/**
	 * Retorna la Expresión Regular que se encuentra en el nodo hijo
	 * derecho de esta expresión regular.
	 * @return El hijo derecho de esta expresión regular.
	 */
	public RegEx darRight(){
		return right;
	}

	/**
	 * Genera una lista que sirve de referencia a el símbolo referenciado por la hoja
	 * que se encuentra en la posición i.
	 * @return Lista de símbolos representados por cada hoja.
	 */
	public ArrayList<Character> hojas(){
		ArrayList<Character> arreglo = new ArrayList<Character>();
		
		if( left == null && right == null )
			arreglo.add(valor);
		else{
			for( char c : left.hojas() )
				arreglo.add( c );
			if( right != null )
				for( char c : right.hojas() )
					arreglo.add( c );
		}
		
		return arreglo;
	}

	/**
	 * Retorna el valor de primeraPos para la expresión
	 * @return Las posibles hojas con las cuales puede empezar una instancia
	 * de la expresión regular.
	 */
	public ArrayList<Integer> darPrimeraPos(){
		return primeraPos;
	}

	/**
	 * Genera la tabla de siguientes posiciones de las diferentes hojas de la regex.
	 * @return Un hashmap que indica para cada hoja las posiciones que le siguen.
	 */
	public HashMap<Integer, ArrayList<Integer>> siguientePos(){
		ArrayList<RegEx> postOrden = postOrden();
		
		HashMap<Integer, ArrayList<Integer>> retorno = new HashMap<Integer, ArrayList<Integer>>();
		for( int i = 0; i < hojas().size(); i++ )
			retorno.put(i, new ArrayList<Integer>());
		for( RegEx r : postOrden ){
			if( r.valor == CONCAT ){
				for( int prim : r.left.ultimaPos ){
					for( int ult : r.right.primeraPos ){
						if( ! retorno.get(prim).contains(ult) )
							retorno.get(prim).add(ult);
					}
				}
			} else if( r.valor == KLEENE ){
				for( int prim : r.left.ultimaPos ){
					for( int ult : r.left.primeraPos ){
						if( ! retorno.get(prim).contains(ult) )
							retorno.get(prim).add(ult);
					}
				}
			}
		}
		return retorno;
	}

	/**
	 * Método que recorre el árbol sintáctico en inorden. Si el algoritmo está bien implementado, este
	 * recorrido debería retornar algo muy similar a la cadena inicial (lo único que se pierde son los
	 * paréntesis)
	 * @return El recorrido en inorden del árbol sintáctico.
	 */
	public String inorden(){
		
		String inicial = "";
		
		if( ! esSimbolo(valor) )inicial += "(";
		if( this.left != null ) inicial += this.left.inorden();// else inicial+= "null";
		inicial += this.valor;
		if( this.right != null ) inicial += this.right.inorden();// else inicial+= "null";
		if( ! esSimbolo(valor) )inicial += ")";
		
		return inicial;
	}
	
	/**
	 * Método que recorre el árbol sintáctico en postorden. Si el algoritmo está bien implementado,
	 * este recorrido debería retornar algo muy similar a la cadena en notación postfix, pero con
	 * las cerraduras + y ? reemplazadas por aa* y a|e respectivamente.
	 * @return El recorrido en postorden del árbol sintáctico.
	 */
	public String postorden(){
		
		String inicial = "";
		
		if( this.left != null ) inicial += this.left.postorden();// else inicial+= "null";
		if( this.right != null ) inicial += this.right.postorden();// else inicial+= "null";
		inicial += this.valor;
		
		return inicial;
	}
	
	private ArrayList<RegEx> postOrden(){
		ArrayList<RegEx> postOrden = new ArrayList<RegEx>();
		
		if( left != null ){
			for( RegEx r : left.postOrden() )
				postOrden.add(r);
				//postOrden.add( left.postOrden().get(0) );
		}
		if( right != null ){
			for( RegEx r : right.postOrden() )
				postOrden.add( r );
		}
		postOrden.add( this );
	
		return postOrden;
	}

	/**
	 * Método que indica si un caracter es un operador de expresiones regulares.
	 * @param caracter El caracter que se quiere
	 * @return
	 */
	public static boolean esOperador( char caracter ){
		return caracter == OR || caracter == CONCAT || caracter == KLEENE || caracter == POSITIVA ||
		caracter == PREGUNTA;
	}
	
	/**
	 * Método que indica si un caracter es un símbolo perteneciente al lenguaje.
	 * @param caracter El caracter que se quiere clasificar como símbolo o no símbolo.
	 * @return true Si el caracter es un símbolo perteneciente al lenguaje. false Si no lo es.
	 */
	public static boolean esSimbolo( char caracter ){
		return ! (esOperador(caracter) || caracter == '(' || caracter == ')' ); 
	}
	
	@Override
	public String toString(){
		return inorden();
	}
}
