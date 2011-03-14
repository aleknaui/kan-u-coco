import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase que representa un Autómata Finito No Determinista. Un AFN
 * se construye a partir del árbol sintáctico de una Expresión Regular
 * por medio del algoritmo de Thompson.
 * @author AleKnaui
 */
public class AFN {
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Alfabeto utilizado en el AFN */
	private ArrayList<Character> alfabeto;
	/** Lista de los estados que contiene el AFN */
	private ArrayList<Estado> estados;
	/** Lista de las transiciones entre los estados del AFN */
	private ArrayList<Transicion> transiciones;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Crea un AFN que corresponde al caso base del algoritmo de Thompson
	 */
	public AFN( char simbolo ){
		
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		
		Estado inicial = new Estado( true, false );
		estados.add(inicial);
		Estado finale = new Estado( false, true );
		estados.add(finale);
		Transicion trans = new Transicion( inicial, finale, simbolo );
		transiciones.add(trans);
		
	}
	
	/**
	 * Implementa el algoritmo de Thompson para "operar" AFNs
	 * @param afn1 El AFN que se encuentra a la izquierda en la Expresión Regular
	 * @param afn2 El AFN que se encuentra a la derecha en la Expresión Regular. null Si simbolo == RegEx.KLEENE
	 * @param simbolo El operador que determinará el algoritmo de Thompson a utilizar.s
	 */
	public AFN( AFN afn1, AFN afn2, char simbolo ){
		assert simbolo == RegEx.OR || simbolo == RegEx.CONCAT ||
		simbolo == RegEx.KLEENE;
		
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		
		/*
		 * Para este algoritmo se cambian las transiciones del estado
		 * final del primer afn hacia el primer estado del segundo afn
		 * para concatenar los afns
		 */
		if( simbolo == RegEx.CONCAT ){
			Estado inicial2 = afn2.darEstadoInicial();
			Estado final1 = afn1.darEstadoAceptacion();
			
			ArrayList<Transicion> paraCambiar = new ArrayList<Transicion>();
			for( Transicion transicion : afn1.transiciones ){
				if( transicion.darEstadoB().equals( final1 ) )
					paraCambiar.add( transicion );
			}
			
			for( Transicion transicion : paraCambiar ){
				afn1.transiciones.remove( transicion );
				afn1.transiciones.add( new Transicion( transicion.darEstadoA(), inicial2, transicion.darSimbolo() ) );
			}
			
			for( Estado estado : afn1.estados )
				addEstado(estado);
			for( Estado estado : afn2.estados )
				addEstado(estado);
			for( Transicion transicion : afn1.transiciones )
				addTransicion(transicion);
			for( Transicion transicion : afn2.transiciones )
				addTransicion(transicion);
			
			this.estados.remove(final1);
			this.estados.get( estados.indexOf(inicial2) ).setAsNoInicial();
		}
		
		/*
		 * Para este algoritmo se ponen los dos AFNs en
		 * "paralelo" por medio de dos estados extra y
		 * transiciones epsilon.
		 */
		else if( simbolo == RegEx.OR ){
			Estado inicial = addEstado( true, false, 0 );
			for( Estado estado : afn1.estados )
				addEstado(estado);
			assert estados.get(1).esAceptacion();
			estados.get(1).setAsNoAceptacion();
			int tamanioAfn1 = afn1.estados.size();
			assert ! estados.get(1).esAceptacion() : "No se quitó estado de inicial al coso";
			Estado inicial1 = estados.get(1);
			
			addTransicion( inicial, inicial1, RegEx.EPSILON );
			
			darEstadoAceptacion().setAsNoAceptacion();
			Estado final1 = estados.get( estados.size()-1 );
			
			for( Estado estado : afn2.estados )
				addEstado(estado);
			assert estados.get(tamanioAfn1+1).esAceptacion();
			estados.get(tamanioAfn1+1).setAsNoAceptacion();
			Estado inicial2 = estados.get(tamanioAfn1+1);
			
			addTransicion( inicial, inicial2, RegEx.EPSILON );
			
			darEstadoAceptacion().setAsNoAceptacion();
			Estado final2 = estados.get( estados.size()-1 );
			
			for( Transicion transicion : afn1.transiciones )
				addTransicion( transicion );
			for( Transicion transicion : afn2.transiciones )
				addTransicion( transicion );
			
			Estado finale = addEstado( false, true, -1 );
			addTransicion( final1, finale, RegEx.EPSILON );
			addTransicion( final2, finale, RegEx.EPSILON );
			
		}
		
		/*
		 * Para la cerradura Kleene se debe asegurar que el segundo afn
		 * es null, ya que no tiene relevancia para la operación y es
		 * muy probable que sea un error de la programadora que es
		 * medio cleta. 
		 */
		else{
			assert afn2 == null;
			Estado inicial = addEstado( true, false, 0 );
			for( Estado estado : afn1.estados )
				addEstado(estado);
			assert estados.get(1).esAceptacion();
			estados.get(1).setAsNoAceptacion();
			assert ! estados.get(1).esAceptacion() : "No se quitó estado de inicial al coso";
			Estado inicial1 = estados.get(1);
			
			addTransicion( inicial, inicial1, RegEx.EPSILON );
			
			darEstadoAceptacion().setAsNoAceptacion();
			
			Estado final1 = estados.get( estados.size()-1 );
			
			for( Transicion transicion : afn1.transiciones )
				addTransicion( transicion );
			
			Estado finale = addEstado( false, true, -1 );
			
			addTransicion( final1, finale, RegEx.EPSILON );
			
			addTransicion( final1, inicial1, RegEx.EPSILON );
			
			addTransicion( inicial, finale, RegEx.EPSILON );
			
		}
		
	}
	
	/**
	 * Crea un AFN a partir de un árbol sintáctico de una expresión regular.
	 * @param regex La expresión regular de la que se origina el AFN
	 */
	public AFN( RegEx regex ){
		
		alfabeto = regex.darAlfabeto();
		
		// Le quita el .# del final
		// regex = regex.darLeft();
		
		//RegEx.print( "Valor: " + regex.darValor() );
		if( RegEx.esSimbolo(regex.darValor()) ){
			//RegEx.print( "Es símbolo" );
			AFN automata = new AFN( regex.darValor() );
			estados = automata.estados;
			transiciones = automata.transiciones;
			//RegEx.print( this );
		}
		else{
			
			AFN afn1 = regex.darLeft() == null ? null : new AFN( regex.darLeft() );
			AFN afn2 =  regex.darRight() == null ? null : new AFN( regex.darRight() );
			char valor = regex.darValor();
			//RegEx.print( afn1 );
			//RegEx.print( afn2 );
			AFN automata = new AFN( afn1, afn2, valor );
			
			//RegEx.print(this);
			
			estados = automata.estados;
			transiciones = automata.transiciones;
		}
		//RegEx.print("Done with " + regex.darValor());
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Retorna la lista de los estados accesibles por medio
	 * de transiciones-e desde T
	 * @param T La lista de los estados iniciales
	 * @return La lista de los estados accesibles por medio de transiciones-e desde T.
	 */
	public int[] cerraduraEpsilon( int[] T ){
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
	public int[] mueve( int[] T, char simbolo ){
		
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
	 * Realiza la simulación del AFN e indica si la cadena ingresada concuerda con la expresión regular representada.
	 * @param w La cadena de la que se quiere determinar si representa la regex.
	 * @return true Si la cadena es aceptada por el AFN. false Si no lo es.
	 */
	public boolean simular( String w ){
		
		int[] S = cerraduraEpsilon( new int[] {estados.indexOf( darEstadoInicial() )} );
		for( int i = 0; i < w.length(); i++ ){
			char c = w.charAt(i);
			S = cerraduraEpsilon( mueve( S, c ) );
			if( S.length == 0 ) return false;
		}
		
		for( int i = 0; i < S.length; i++ ){
			if( estados.get(S[i]) == darEstadoAceptacion() ) return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna la lista de todos los estados del AFN
	 * @return Retorna la lista de todos los estados del AFN
	 */
	public ArrayList<Estado> darEstados(){
		return estados;
	}
	
	/**
	 * Retorna las transiciones del AFN
	 * @return Las transiciones del AFN
	 */
	public ArrayList<Transicion> darTransiciones(){
		return transiciones;
	}
	
	/**
	 * Retorna el estado inicial del AFN.
	 * @return El estado inicial.
	 */
	public Estado darEstadoInicial(){
		for( Estado estado : estados ){
			if( estado.esInicial() ) return estado;
		} assert false == true; // Aserción fallada automáticamente porque no
								// hay un estado inicial
		return null;			// Retorna null por salud mental del compilador
	}
	
	/**
	 * Retorna el estado de aceptación del AFN
	 * @return El estado de aceptación del AFN
	 */
	public Estado darEstadoAceptacion(){
		
		for( int i = estados.size()-1; i >= 0; i-- ){
			if( estados.get(i).esAceptacion() ) return estados.get(i);
		} assert false == true; // Aserción fallada automáticamente porque no
								// hay un estado de aceptación
		return null;			// Retorna null por salud mental del compilador
		
	}
	
	/**
	 * Retorna el alfabeto sobre el cual está construído el AFN
	 * @return El alfabeto
	 */
	public ArrayList<Character> darAlfabeto() {
		return alfabeto;
	}

	/**
	 * Agrega en el índice indicado a la lista de estados un nuevo estado
	 * con los atributos indicados en los parámetros. Retorna el estado creado.
	 * @param inicial true Si el estado es inicial para el Autómata
	 * @param aceptacion true Si el estado es de aceptación para el Autómata
	 * @param indice El índice en el que se desea colocar el estado en la lista de estados
	 * del AFN. -1 Si se quiere colocar al final de la lista.
	 * @return El estado creado.
	 */
	private Estado addEstado( boolean inicial, boolean aceptacion, int indice ){
		if( indice == -1 ) indice = estados.size();
		Estado nuevo = new Estado(inicial, aceptacion);
		estados.add(indice, nuevo);
		return nuevo;
	}

	/**
	 * Agrega al final de la lista de estados el estado indicado.
	 * @param estado El estado a agregar a la lista.
	 */
	private void addEstado( Estado estado ){
		estados.add( estado );
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
	 * Agrega a la lista de transiciones la transición indicada.
	 * @param transicion La transición que se quiere agregar a la lista.
	 */
	private void addTransicion( Transicion transicion ){
		transiciones.add( transicion );
	}

	@Override
	public String toString(){
		
		String retorno = "Estados: ";
		for( Estado estado : estados )
			retorno = retorno + estados.indexOf(estado) + ", ";
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno += "Estado Inicial: " + estados.indexOf(darEstadoInicial()) + "\n";
		retorno += "Estado de Aceptación: " + estados.indexOf(darEstadoAceptacion()) + "\n";
		
		retorno = retorno + "Transiciones:\n";
		for( Transicion transicion : transiciones )
			retorno = retorno + "\t T(" + estados.indexOf(transicion.darEstadoA()) + ", '" + transicion.darSimbolo() + "') = " + estados.indexOf(transicion.darEstadoB()) + "\n";
		
		return retorno;
	}
	
}
