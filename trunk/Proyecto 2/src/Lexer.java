import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Esta clase es el analizador sintáctico que se
 * construye poco a poco añadiendo diferentes reconocedores
 * de tokens (en forma de AFDs mínimos) con una pequeña variación
 * de el algoritmo de Thompson.
 * 
 * La idea inicial era heredar del AFN, pero aparentemente
 * será mejor hacer una versión más efectiva. No obstante,
 * la clase AFD será útil para los tókens individuales, así
 * como las clases Estado y Transicion.
 * @author AleKnaui
 *
 */
public class Lexer {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Estados del autómata */
	private ArrayList<Estado> estados;
	
	/** Transiciones entre los estados del autómata */
	private ArrayList<Transicion> transiciones;
	
	/** Tabla de Keywords */
	private HashMap<Estado, String> keywords;
	
	/** Tabla de Tokens */
	private HashMap<Estado, String> tokens;
	
	/** Estados que representan whitespace */
	private ArrayList<Estado> whitespace;
	
	/** Cadena que se estará analizando */
	private String cadena;
	
	/** Puntero para el análisis léxico por partes. */
	private int puntero;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Constructor que inicializa el Lexer con únicamente
	 * un estado que será el que una todos los AFDs de los
	 * tokens.
	 */
	public Lexer(){
		
		// Inicialización de las variables
		
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		keywords = new HashMap<Estado, String>();
		tokens = new HashMap<Estado, String>();
		whitespace = new ArrayList<Estado>();
		
		// Creación del estado inicial
		
		Estado inicial = new Estado( true, false );
		estados.add(inicial);
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// -------------------------------------------------------------------------------
	
	/**
	 * Añade un token al lexer para su futuro reconocimiento.
	 * @param nombre El nombre del token
	 * @param regex La expresión regular que define al token
	 * @throws Exception Si la expresión regular indicada no es válida.
	 */
	public void addToken( String nombre, String regexs ) throws Exception{
		
		// Se crea AFD que reconoce al token.
		RegEx regex = new RegEx(regexs);
		AFD afd = new AFD( regex ).minimizar();
		
		Estado primero = null;
		ArrayList<Estado> finales = new ArrayList<Estado>();
		
		// Se añade el AFD a este autómata, manteniendo los
		// estados de aceptación del AFD, pero no su estado inicial
		for( int i = 0; i < afd.darEstados().size(); i++){
			Estado estado = afd.darEstados().get(i);
			if( estado.esInicial() ){
				estado.setAsNoInicial();
				primero = estado;
			}
			else if( estado.esAceptacion() )
				finales.add(estado);
			estados.add(estado);
		}
		
		for(Transicion transicion : afd.darTransiciones())
			transiciones.add(transicion);
		addTransicion(darEstadoInicial(), primero, RegEx.EPSILON);
		
		// Se agrega la información de los tokens para que el Lexer la
		// utilice durante su ejecución.
		for( Estado estado : finales )
			tokens.put(estado, nombre);
	}
	
	/**
	 * Añade una keyword al lexer para su futuro reconocimiento.
	 * @param nombre El nombre del token
	 * @param regex La expresión regular que define al token
	 * @throws Exception Si la expresión regular indicada no es válida.
	 */
	public void addKeyword( String nombre, String regexs ) throws Exception{
		
		// Se crea AFD que reconoce al keyword.
		RegEx regex = new RegEx(regexs);
		AFD afd = new AFD( regex ).minimizar();
		
		Estado primero = null;
		ArrayList<Estado> finales = new ArrayList<Estado>();
		
		// Se añade el AFD a este autómata, manteniendo los
		// estados de aceptación del AFD, pero no su estado inicial
		for( int i = 0; i < afd.darEstados().size(); i++){
			Estado estado = afd.darEstados().get(i);
			if( estado.esInicial() ){
				estado.setAsNoInicial();
				primero = estado;
			}
			else if( estado.esAceptacion() )
				finales.add(estado);
			estados.add(estado);
		}
		
		for(Transicion transicion : afd.darTransiciones())
			transiciones.add(transicion);
		addTransicion(darEstadoInicial(), primero, RegEx.EPSILON);
		
		// Se agrega la información de los keywords para que el Lexer la
		// utilice durante su ejecución.
		for( Estado estado : finales )
			keywords.put(estado, nombre);
	}
	
	/**
	 * Método que establece el whitespace que será ignorado por el analizador.
	 * @param regexs La expresión regular que describe el whitespace.
	 * @throws Exception Por error de la expresión regular.
	 */
	public void setWhitespace( String regexs ) throws Exception{
		// Se crea AFD que reconoce al whitespace.
		RegEx regex = new RegEx(regexs);
		AFD afd = new AFD( regex ).minimizar();
		
		Estado primero = null;
		ArrayList<Estado> finales = new ArrayList<Estado>();
		
		// Se añade el AFD a este autómata, manteniendo los
		// estados de aceptación del AFD, pero no su estado inicial
		for( int i = 0; i < afd.darEstados().size(); i++){
			Estado estado = afd.darEstados().get(i);
			if( estado.esInicial() ){
				estado.setAsNoInicial();
				primero = estado;
			}
			else if( estado.esAceptacion() )
				finales.add(estado);
			estados.add(estado);
		}
		
		for(Transicion transicion : afd.darTransiciones())
			transiciones.add(transicion);
		addTransicion(darEstadoInicial(), primero, RegEx.EPSILON);
		
		// Se agrega la información del whitespace para que el Lexer la
		// utilice durante su ejecución.
		for( Estado estado : finales )
			whitespace.add(estado);
	}
	
	/**
	 * Prepara una cadena para su análisis. 
	 * @param cadena La cadena a analizar.
	 */
	public void asignarCadena( String cadena ){
		assert cadena == null : "Ale... cuidado"; // Verifica que no se haya inicializado ya para que no loquee.
		assert false == true;
		
		this.cadena = cadena;
		puntero = 0;
	}
	
	/**
	 * Cadena que retorna el siguiente Token de la cadena en base a la
	 * posición del puntero. Ignora el whitespace.s
	 * @return El Token más largo posible. null Si ya se acabó la cadena.
	 * @throws NoHayTokenException Si la cadena no puede producir un token.
	 */
	public Token nextToken() throws NoHayTokenException{
		
		if( puntero == cadena.length() ) return null;
		
		// Almacena el/los último(s) estado(s) de aceptación que encontró 
		ArrayList<Estado> ultimosAceptacion = new ArrayList<Estado>();
		
		// Almacena los punteros que servirán para obtener el lexema de mayor largo posible.
		int puntero0 = puntero;
		int puntero2 = -1;
		
		int[] S = cerraduraEpsilon( new int[] {estados.indexOf( darEstadoInicial() )} );
		for( int i = puntero; i < cadena.length(); i++ ){
			char c = cadena.charAt(i);
			S = cerraduraEpsilon( mueve( S, c ) );
			// Si alguno de los estados en los que se encuentra es de aceptación,
			// actualiza el puntero2 y la lista de los últimos estados de aceptación
			for( int j = 0; j < S.length; j++ ){
				Estado estado = estados.get(S[j]);
				if( estado.esAceptacion() ){
					// Si ya se había detectado otro estado de aceptación en esta fase,
					// simplemente se añade el estado a la lista
					if( puntero2 == i )
						ultimosAceptacion.add(estado);
					// Si este es el primer estado de aceptación detectado en esta fase,
					// se actualiza el puntero y borra la lista, para añadir el primer estado.
					else{
						puntero2 = i;
						ultimosAceptacion.clear();
						ultimosAceptacion.add(estado);
					}
				}
			}
			
			// Si ya no hay más transiciones, se retorna el último Token que se encontró
			if( S.length == 0 )break;
		}
		
		// Si no se llegó a ningún estado de aceptación, se tira una excepción
		if( puntero2 == -1 || ultimosAceptacion.isEmpty() ) throw new NoHayTokenException();
		// Si se llegó a algún estado de aceptación, se verifica que sea Keyword, Token o
		// Whitespace.
		else{
			// En teoría, los t/k/ws se agregan en orden de prioridad, por lo que el
			// Token a retornar es el primero que se encuentre. :S
			Estado aceptacion = ultimosAceptacion.get(0);
			String keyword = keywords.get(aceptacion);
			if( keyword != null ){
				puntero = puntero2+1;
				return new Token( keyword, cadena.substring(puntero0, puntero2+1) );
			}
			else{
				String token = tokens.get(aceptacion);
				if( token != null ){
					puntero = puntero2+1;
					return new Token( token, cadena.substring(puntero0, puntero2+1) );
				}
				else{
					if( whitespace.contains(aceptacion) ){
						puntero = puntero2+1;
						return nextToken();
					}
					else throw new NoHayTokenException();
				}
			}
		}
	}
	
	/**
	 * Método que analiza léxicamente la cadena desde principio a fin.
	 * @return Un arreglo que contiene todos los tokens contenidos en la cadena.
	 * @throws NoHayTokenException Si en algun punto no se ha encontrado un Token.
	 */
	public Token[] tokenize() throws NoHayTokenException{
		
		ArrayList<Token> altokens = new ArrayList<Token>();
		Token token = nextToken();
		while( token != null ){
			altokens.add(token);
			token = nextToken();
		}
		return altokens.toArray( new Token[altokens.size()] );
	}
	
	/**
	 * Retorna la lista de los estados accesibles por medio
	 * de transiciones-e desde T
	 * @param T La lista de los estados iniciales
	 * @return La lista de los estados accesibles por medio de transiciones-e desde T.
	 */
	private int[] cerraduraEpsilon( int[] T ){
		ArrayList<Integer> cerradura = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		for( int i = 0; i < T.length; i++ ){
			//Estado estado = estados.get(i);
			stack.push(T[i]);
			cerradura.add(T[i]);
		}
		while( ! stack.isEmpty() ){
			Estado t = estados.get(stack.pop());
			for( Transicion trans : transiciones ){
				if( trans.darEstadoA() == t 
						&& trans.darSimbolo() == RegEx.EPSILON 
						&& ! cerradura.contains( estados.indexOf(trans.darEstadoB() ))){
					cerradura.add(estados.indexOf(trans.darEstadoB()));
					stack.push(estados.indexOf(trans.darEstadoB()));
				}
			}
		}
		
		int[] retorno = new int[cerradura.size()];
		for( int i = 0; i < cerradura.size(); i++ )
			retorno[i] = cerradura.get(i);
		
		for (int i = 0; i < retorno.length; i++) {
	         for (int j = i; j > 0; j--) {
	            if (retorno[j-1] > retorno[j]) {
	               int swap = retorno[j];
	               retorno[j] = retorno[j-1];
	               retorno[j-1] = swap;
	            }
	         }
	      }
		
		return retorno;
	}

	/**
	 * Retorna los estados a los que se accede a partir de los estados indicados y el
	 * símbolo indicado.
	 * @param T Los estados de los que se parte.
	 * @param simbolo El símbolo que inicia la transición.
	 * @return Los estados que se acceden.
	 */
	private int[] mueve( int[] T, char simbolo ){
		
		ArrayList<Integer> mueve = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		
		for( int i = 0; i < T.length; i++ ){
			stack.push(T[i]);
		}
		while( ! stack.isEmpty() ){
			Estado t = estados.get(stack.pop());
			for( Transicion trans : transiciones ){
				if( trans.darEstadoA() == t 
						&& trans.darSimbolo() == simbolo
						&& ! mueve.contains( estados.indexOf(trans.darEstadoB() ))){
					mueve.add(estados.indexOf(trans.darEstadoB()));
				}
			}
		}
		
		int[] retorno = new int[mueve.size()];
		for( int i = 0; i < mueve.size(); i++ )
			retorno[i] = mueve.get(i);
		
		for (int i = 0; i < retorno.length; i++) {
	         for (int j = i; j > 0; j--) {
	            if (retorno[j-1] > retorno[j]) {
	               int swap = retorno[j];
	               retorno[j] = retorno[j-1];
	               retorno[j-1] = swap;
	            }
	         }
	      }
		
		return retorno;
	}

	/**
	 * Agrega a la lista de transiciones una nueva
	 * transición  con los parámetros indicados. La transición creada se retorna.
	 * @param inicial El estado del que parte la transición
	 * @param finale El estado hacia el cual se dirige la transición
	 * @param transicion El símbolo que "causa" la transicións
	 * @return La transición creada.
	 */
	private Transicion addTransicion( Estado inicial, Estado finale, char transicion ){
		Transicion nueva = new Transicion( inicial, finale, transicion );
		transiciones.add( nueva );
		return nueva;
	}
	
	/**
	 * Retorna los estados de aceptación del AFD
	 * @return Los estados que indican que la cadena w introducida hasta el momento
	 * pertenece a la expresión regular.
	 */
	private ArrayList<Estado> darEstadosAceptacion(){
		ArrayList<Estado> retorno = new ArrayList<Estado>();
		
		for( Estado estado : estados )
			if( estado.esAceptacion() )
				retorno.add( estado );
		return retorno;
	}
	
	/**
	 * Retorna el estado inicial del AFN.
	 * @return El estado inicial.
	 */
	private Estado darEstadoInicial(){
		return estados.get(0);
	}

	@Override
	public String toString(){
		String retorno = "";
		retorno += "Estados: ";
		for( Estado estado : estados )
			retorno = retorno + estados.indexOf(estado) + ", ";
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno += "Estado Inicial: " + estados.indexOf(darEstadoInicial()) + "\n";
		retorno += "Estado(s) de Aceptación: ";
		
		ArrayList<Estado> aceptacion = darEstadosAceptacion();
		for( Estado estado : aceptacion )
			retorno += estados.indexOf( estado ) + ", ";
		
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno = retorno + "Transiciones:\n";
		for( Transicion transicion : transiciones )
			retorno = retorno + "\t T(" + estados.indexOf(transicion.darEstadoA()) + ", '" + transicion.darSimbolo() + "') = " + estados.indexOf(transicion.darEstadoB()) + "\n";
		
		return retorno;
	}
	
	/**
	 * Clase que indica que el analizador Léxico no encontró un Token
	 * para la cadena que analizó.
	 * @author AleKnaui
	 */
	class NoHayTokenException extends Exception{
		private static final long serialVersionUID = 1715114051188122042L;

		public NoHayTokenException(){
			super();
		}
	}
	
}
