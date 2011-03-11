import java.util.ArrayList;

/**
 * Clase que representa un Autómata Finito Determinista.
 * @author ale
 *
 */
public class AFD {

	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Alfabeto utilizado en el AFD */
	private ArrayList<Character> alfabeto;
	/** Lista de los estados que contiene el AFD */
	private ArrayList<Estado> estados;
	/** Lista de las transiciones entre los estados del AFD */
	private ArrayList<Transicion> transiciones;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Construye un AFD partiendo de un AFN y usando el algoritmo de subconjuntos.
	 */
	public AFD( AFN afn ){
		
		alfabeto = afn.darAlfabeto();
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		
		ArrayList<Estado> Sn = afn.darEstados();
		ArrayList<int[]> Sd = new ArrayList<int[]>();

		// Estado inicial del AFD
		Sd.add( afn.cerraduraEpsilon( new int[] { Sn.indexOf( afn.darEstadoInicial() ) } ) );
		estados.add( new Estado( true, false ) );
		
		for( int i = 0; i < Sd.size(); i++ ){
			int[] T = Sd.get(i);
			Estado estadoT = estados.get(i);
			for( char a : alfabeto ){
				int[] U = afn.cerraduraEpsilon( afn.mueve(T, a) );
				if( U.length != 0 ){
					if( -1 == yaExisteEstado( U, Sd ) ){
						Sd.add(U);
						
						boolean esFinal = false;
						
						// Verificación sobre si U es estado de aceptación.
						int fn = Sn.indexOf(afn.darEstadoAceptacion());
						if( contains( U, fn ) )
							esFinal = true;
						
						Estado nuevo = new Estado( false, esFinal );
						estados.add( nuevo );
						transiciones.add( new Transicion( estadoT, nuevo, a ) );
					}
					else{
						Estado estado = estados.get( yaExisteEstado( U, Sd ) );
						transiciones.add( new Transicion( estadoT, estado, a ) );
					}
				}// else{ RegEx.print("WTH: "); print(U); }
			}
		}
		
	}
	
	public static void print( int[] a ){
		for( int i = 0; i < a.length; i++ )
			System.out.print( a[i] + ", " );
		System.out.print("\n");
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Describe de forma escrita el AFN.
	 */
	public String toString(){
		
		String retorno = "Estados: ";
		for( Estado estado : estados )
			retorno = retorno + estados.indexOf(estado) + ", ";
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno += "Estado Inicial: " + estados.indexOf(darEstadoInicial()) + "\n";
		retorno += "Estado(s) de Aceptación: ";
		
		ArrayList<Estado> aceptacion = darEstadosAceptacion();
		for( Estado estado : aceptacion )
			retorno += estados.indexOf( estado ) + ", ";
		
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		retorno += "\n";
		
		retorno = retorno + "Transiciones:\n";
		for( Transicion transicion : transiciones )
			retorno = retorno + "\t T(" + estados.indexOf(transicion.darEstadoA()) + ", '" + transicion.darSimbolo() + "') = " + estados.indexOf(transicion.darEstadoB()) + "\n";
		
		return retorno;
	}
	
	/**
	 * Retorna el estado inicial del AFD.
	 * @return El estado inicial del AFD.
	 */
	public Estado darEstadoInicial(){
		for( Estado estado : estados )
			if( estado.esInicial() )
				return estado;
		assert false == true;
		return null;
	}
	
	public ArrayList<Estado> darEstadosAceptacion(){
		ArrayList<Estado> retorno = new ArrayList<Estado>();
		
		for( Estado estado : estados )
			if( estado.esAceptacion() )
				retorno.add( estado );
		return retorno;
	}
	
	public int yaExisteEstado( int[] array, ArrayList<int[]> list ){
		for( int i = 0; i < list.size(); i++ ){
			
			int[] ar = list.get(i);
			
			if( iguales( array, ar ) ) return i;
			
		}
			
		return -1;
	}
	
	private boolean iguales( int[] a1, int[] a2 ){
		if( a1.length != a2.length ) return false;
		for( int i = 0; i < a1.length; i++ ){
			if( a1[i] != a2[i] ) return false;
		} return true;
	}
	
	public static boolean contains( int[] arr, int in ){
		for( int i = 0; i < arr.length; i++ )
			if( arr[i] == in ) return true;
		
		return false;
	}
	
}