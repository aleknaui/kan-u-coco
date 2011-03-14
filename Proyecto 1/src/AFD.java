import java.util.ArrayList;
import java.util.HashMap;

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
	
	/**
	 * Construye un AFD directamente a partir de la expresión regular indicada.
	 * @param regex Un AFD que reconoce la expresión regular indicada.
	 */
	public AFD( RegEx regex ){
		
		alfabeto = regex.darAlfabeto();
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		
		regex = regex.sintacticoCompleto();
		
		HashMap<Integer, ArrayList<Integer>> siguientePos = regex.siguientePos();
		ArrayList<Character> hojas = regex.hojas();
		ArrayList<ArrayList<Integer>> Sd = new ArrayList<ArrayList<Integer>>();
		Sd.add( regex.darPrimeraPos() );
		estados.add( new Estado( true, false ) );
		
		for( int i = 0; i < Sd.size(); i++ ){
			Estado actual = estados.get(i);
			ArrayList<Integer> T = Sd.get(i);
			for( char a : alfabeto ){
				ArrayList<Integer> U = new ArrayList<Integer>();
				for( int j : T ){
					if( hojas.get(j) == a ){
						for( int sig : siguientePos.get(j) ){
							if( ! U.contains(sig) )
								U.add(sig);
						}
					}
				}
			int[] ordenado = toArray(U);
			U = new ArrayList<Integer>();
			for( int j = 0; j < ordenado.length; j++ ){
				U.add( ordenado[j] );
			}
			if( U.size() > 0 ){
				if( ! Sd.contains(U) ){
					boolean esFinal = false;
					if( U.contains( hojas.size()-1 ) )
						esFinal = true;
					Estado nuevo = new Estado( false, esFinal );
					transiciones.add( new Transicion( actual, nuevo, a ) );
					estados.add( nuevo );
					Sd.add(U);
				} else{
					Estado nuevo = estados.get( Sd.indexOf( U ) );
					transiciones.add( new Transicion( actual, nuevo, a ) );
				}
			}
			}
		}
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Realiza la simulación del AFD e indica si la cadena ingresada concuerda con la expresión regular representada.
	 * @param w La cadena de la que se quiere determinar si representa la regex.
	 * @return true Si la cadena es aceptada por el AFD. false Si no lo es.
	 */
	public boolean simular( String w ){
		
		Estado S = darEstadoInicial();
		for( int i = 0; i < w.length(); i++ ){
			char c = w.charAt(i);
			S = transicion( S, c );
			if( S == null ) return false;
		}
		return darEstadosAceptacion().contains( S );
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
	
	/**
	 * Retorna los estados de aceptación del AFD
	 * @return Los estados que indican que la cadena w introducida hasta el momento
	 * pertenece a la expresión regular.
	 */
	public ArrayList<Estado> darEstadosAceptacion(){
		ArrayList<Estado> retorno = new ArrayList<Estado>();
		
		for( Estado estado : estados )
			if( estado.esAceptacion() )
				retorno.add( estado );
		return retorno;
	}
	
	/**
	 * Realiza la operación de transición de el estado s con el símbolo c
	 * @param s El estado del que se quiere partir.
	 * @param c El símbolo que dirigirá la transición.
	 * @return El estado al que se dirige la transición.
	 */
	private Estado transicion( Estado s, char c ){
		
		for( Transicion trans : transiciones){
			if( trans.darEstadoA() == s ){
				if( trans.darSimbolo() == c )
					return trans.darEstadoB();
			}
		}
		return null;
		
	}

	/**
	 * Método que indica si el arreglo de enteros indicado se encuentra en el ArrayList indicado. 
	 * @param array El arreglo de enteros.
	 * @param list El ArrayList que podría o no contener a array
	 * @return El índice en el que se encuentra array dentro de list. -1 Si no se encuentra.
	 */
	private int yaExisteEstado( int[] array, ArrayList<int[]> list ){
		for( int i = 0; i < list.size(); i++ ){
			
			int[] ar = list.get(i);
			
			if( iguales( array, ar ) ) return i;
			
		}
			
		return -1;
	}
	
	/**
	 * Método que equivale a int[].equals( int[] )
	 * @param a1 El primer int[] a comparar.
	 * @param a2 El segundo int[] a comparar.
	 * @return true Si sus elementos son iguales. false Si no concuerdan en todos sus elementos.
	 */
	private boolean iguales( int[] a1, int[] a2 ){
		if( a1.length != a2.length ) return false;
		for( int i = 0; i < a1.length; i++ ){
			if( a1[i] != a2[i] ) return false;
		} return true;
	}
	
	/**
	 * Método que equivale a int[].contains( int )
	 * @param arr El arreglo de enteros que podría contener a in
	 * @param in El número que se quiere buscar en el arreglo.
	 * @return true Si el arreglo contiene al número. false Si no lo contiene.
	 */
	private boolean contains( int[] arr, int in ){
		for( int i = 0; i < arr.length; i++ )
			if( arr[i] == in ) return true;
		
		return false;
	}
	
	/**
	 * Convierte un ArrayList de enteros a un arreglo de enteros y ordena sus valores.
	 * @param alist El ArrayList que se quiere convertir a int[]
	 * @return El int[] ordenado con los mismos valores que alist.
	 */
	private int[] toArray( ArrayList<Integer> alist ){
		int[] retorno = new int[alist.size()];
		for( int i = 0; i < alist.size(); i++ ){
			retorno[i] = alist.get(i);
		}
		
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
	
	@Override
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
		
		retorno = retorno + "Transiciones:\n";
		for( Transicion transicion : transiciones )
			retorno = retorno + "\t T(" + estados.indexOf(transicion.darEstadoA()) + ", '" + transicion.darSimbolo() + "') = " + estados.indexOf(transicion.darEstadoB()) + "\n";
		
		return retorno;
	}
}