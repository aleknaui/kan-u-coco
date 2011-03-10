import java.util.ArrayList;

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
			int tamanioAfn1 = afn1.estados.size();
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
	
	public Estado darEstadoInicial(){
		for( Estado estado : estados ){
			if( estado.esInicial() ) return estado;
		} assert false == true; // Aserción fallada automáticamente porque no
								// hay un estado inicial
		return null;			// Retorna null por salud mental del compilador
	}
	
	public Estado darEstadoAceptacion(){
		
		for( int i = estados.size()-1; i >= 0; i-- ){
			if( estados.get(i).esAceptacion() ) return estados.get(i);
		} assert false == true; // Aserción fallada automáticamente porque no
								// hay un estado de aceptación
		return null;			// Retorna null por salud mental del compilador
		
	}
	
	public String toString(){
		String retorno = "Estados: ";
		for( Estado estado : estados )
			retorno = retorno + estados.indexOf(estado) + ", ";
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno += "Estado Inicial: " + estados.indexOf(darEstadoInicial()) + "\n";
		retorno += "Estado de Aceptación: " + estados.indexOf(darEstadoAceptacion()) + "\n";
		
		retorno = retorno + "Transiciones:\n";
		for( Transicion transicion : transiciones )
			retorno = retorno + "\t T(" + estados.indexOf(transicion.darEstadoA()) + ", " + transicion.darSimbolo() + ") = " + estados.indexOf(transicion.darEstadoB()) + "\n";
		
		return retorno;
	}
	
	private Estado addEstado( boolean inicial, boolean aceptacion, int indice ){
		if( indice == -1 ) indice = estados.size();
		Estado nuevo = new Estado(inicial, aceptacion);
		estados.add(indice, nuevo);
		return nuevo;
	}
	
	private void addEstado( Estado estado ){
		estados.add( estado );
	}
	
	private Transicion addTransicion( Estado inicial, Estado finale, char transicion ){
		Transicion nueva = new Transicion( inicial, finale, transicion );
		transiciones.add( nueva );
		return nueva;
	}
	
	private void addTransicion( Transicion transicion ){
		transiciones.add( transicion );
	}

}
