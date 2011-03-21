import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que representa un Autómata Finito Determinista. Un AFD se puede crear
 * a partir de una Expresión Regular o a partir de un AFN ya creado.
 * El autómata se modela como un grafo, siendo sus vértices los diferentes estados
 * y los enlaces las transiciones. También se incluyen el alfabeto que se utiliza
 * y la expresión regular representada.
 * @author AleKnaui
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
	/** Expresión regular que reconoce el AFD */
	private RegEx regex;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	public AFD( ArrayList<Estado> estados, ArrayList<Transicion> transiciones,
			ArrayList<Character> alfabeto, RegEx regex ){
		this.estados = estados;
		this.transiciones = transiciones;
		this.alfabeto = alfabeto;
		this.regex = regex;
	}
	
	/**
	 * Construye un AFD partiendo de un AFN y usando el algoritmo de subconjuntos.
	 * @param El AFN del que se construye el AFD.
	 */
	public AFD( AFN afn ){
		
		alfabeto = afn.darAlfabeto();
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		regex = afn.darRegex();
		
		ArrayList<Estado> Sn = afn.darEstados();
		ArrayList<int[]> Sd = new ArrayList<int[]>();

		// Estado inicial del AFD
		Sd.add( afn.cerraduraEpsilon( new int[] { Sn.indexOf( afn.darEstadoInicial() ) } ) );
		//estados.add( new Estado( true, false ) );
		boolean PrimeroEsFinal = false;
		
		// Verificación sobre si U es estado de aceptación.
		int fn0 = Sn.indexOf(afn.darEstadoAceptacion());
		if( contains( afn.cerraduraEpsilon( new int[] { Sn.indexOf( afn.darEstadoInicial() ) } ) , fn0 ) )
			PrimeroEsFinal = true;
		
		Estado primero = new Estado( true, PrimeroEsFinal );
		estados.add( primero );
		
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
		this.regex = regex;
		
		regex = regex.sintacticoCompleto();
		
		HashMap<Integer, ArrayList<Integer>> siguientePos = regex.siguientePos();
		ArrayList<Character> hojas = regex.hojas();
		ArrayList<ArrayList<Integer>> Sd = new ArrayList<ArrayList<Integer>>();
		//Sd.add( regex.darPrimeraPos() );
		boolean primeroEsFinal = false;
		if( regex.darPrimeraPos().contains( hojas.size()-1 ) )
			primeroEsFinal = true;
		Estado primero = new Estado( true, primeroEsFinal );
		estados.add( primero );
		Sd.add(regex.darPrimeraPos());
		//estados.add( new Estado( true, false ) );
		
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
		
		long time = System.currentTimeMillis();
		
		Estado S = darEstadoInicial();
		for( int i = 0; i < w.length(); i++ ){
			char c = w.charAt(i);
			S = transicion( S, c );
			if( S == null ) return false;
		}
		
		System.out.println("Tiempo de la simulación del AFD: " + (System.currentTimeMillis() - time) + "ms.");
		
		return darEstadosAceptacion().contains( S );
	}
	
	/**
	 * Retorna el AFD mínimo que reconoce la expresión regular del AFD actual.
	 * @return el AFD con la menor cantidad de estados posibles.
	 */
	public AFD minimizar(){
		ArrayList<ArrayList<Integer>> particion = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> noFinales = new ArrayList<Integer>();
		ArrayList<Integer> finales = new ArrayList<Integer>();
		for( Estado e : estados )
			if( e.esAceptacion() ) finales.add(estados.indexOf(e));
			else noFinales.add( estados.indexOf(e) );
		particion.add(noFinales);
		particion.add(finales);
		
		particion = particionar( particion );
		ArrayList<Estado> estadosMinimos = new ArrayList<Estado>();
		ArrayList<Transicion> transicionesMinimas = new ArrayList<Transicion>();
		for( ArrayList<Integer> grupo : particion ){
			boolean esInicial = false;
			boolean esAceptacion = false;
			for( int e : grupo ){
				if( estados.get(e).esInicial() )
					esInicial = true;
				if( estados.get(e).esAceptacion() )
					esAceptacion = true;
			}
			if( ! (grupo.size() == 1 && grupo.contains(estados.size())) )
				estadosMinimos.add(new Estado( esInicial, esAceptacion ));
		}
		for( ArrayList<Integer> grupo : particion ){
			for( char a : alfabeto ){
				int trans = transicionInt( grupo.get(0), a );
				int iTrans = -1;
				for( int i = 0; i < particion.size(); i++ ){
					if( particion.get(i).contains(trans) )
						iTrans = i;
				}
				assert iTrans != -1;
				if( trans != estados.size() && iTrans != estados.size()){
					transicionesMinimas.add( new Transicion( estadosMinimos.get( particion.indexOf(grupo) ), estadosMinimos.get(iTrans), a ) );
				}
			}
		}
		
		return new AFD( estadosMinimos, transicionesMinimas, alfabeto, regex );
	}
	
	/**
	 * Método que divide los estados en grupos de estados equivalentes hasta que ya no se pueda
	 * @param particion La partición anterior (en el caso inicial, los estados finales y no finales)
	 * @return La partición final, con los grupos que representan los estados mínimos.
	 */
	private ArrayList<ArrayList<Integer>> particionar( ArrayList<ArrayList<Integer>> particion ){
		ArrayList<ArrayList<Integer>> nuevaParticion = new ArrayList<ArrayList<Integer>>();
		
		for( Estado e : estados ){
			boolean encontro = false;
			for( ArrayList<Integer> grupo : nuevaParticion ){
				assert !grupo.isEmpty() : "Once again, Ale sucks";
				if( equivalentes(estados.indexOf(e), grupo.get(0), particion) ){
					grupo.add(estados.indexOf(e));
					encontro = true;
					break;
				}
			}
			if( ! encontro ){
				nuevaParticion.add( new ArrayList<Integer>() );
				nuevaParticion.get(nuevaParticion.size()-1).add(estados.indexOf(e));
			}
		}
		
		if( ! nuevaParticion.equals(particion) ) nuevaParticion = particionar( nuevaParticion );
		
		return nuevaParticion;
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
	 * Este método trabaja similar al método transicion(),
	 * pero funciona con los índices de los estados en vez de
	 * los estados en sí. Se utiliza en la minimización de los AFDs,
	 * por lo que si no hay una transición para el estado s con el símbolo
	 * c, se retorna el "estado muerto" d. Éste estado tiene transiciones
	 * con todos los símbolos hacia él mismo.
	 * @param i El índice del estado del que parte la transición
	 * @param c El caracter bajo el cual se realiza la transición
	 * @return El índice de estado al que se dirige la transición
	 */
	private int transicionInt( int i, char c ){
		if( i == estados.size() ) return i;
		Estado s = estados.get(i);
		for( Transicion trans : transiciones){
			if( trans.darEstadoA() == s ){
				if( trans.darSimbolo() == c )
					return estados.indexOf(trans.darEstadoB());
			}
		}
		return estados.size();
	}
	
	/**
	 * Método que indica si los estados s y t son equivalentes con respecto a la partición indicada
	 * @param s Uno de los estados a comparar
	 * @param t El otro estado a comparar
	 * @param particion La partición en la cual se comparan
	 * @return true Si para cada símbolo del alfabeto, cada transición del estado se dirige al mismo grupo.
	 */
	private boolean equivalentes( int s, int t, ArrayList<ArrayList<Integer>> particion ){
		if( estados.get(s).esAceptacion() != estados.get(t).esAceptacion() ) return false;
		for( char c : alfabeto ){
			int trans1 = transicionInt(s, c);
			int trans2 = transicionInt(t, c);
			for( ArrayList<Integer> subgrupo : particion ){
				if( subgrupo.contains(trans1) != subgrupo.contains(trans2) )
					return false;
			}
		}
		return true;
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
		
		String retorno = "Alfabeto: ";
		for( char a : alfabeto )
			retorno += a + ", ";
		retorno = retorno.substring(0,retorno.length()-2) + "\n";
		
		retorno += "Expresión Regular: " + regex.toString() + "\n";
		
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
}