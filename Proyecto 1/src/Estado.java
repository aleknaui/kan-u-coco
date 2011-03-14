/**
 * Clase que representa un estado dentro de un Autómata Finito.
 * @author AleKnaui
 */
public class Estado {
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** ID único del estado */
	//private int id;
	/** Indica si el estado es el estado inicial del autómata en que se encuentra */
	private boolean esInicial;
	/** Indica si el estado es un estado de aceptación del autómata en que se encuentra */
	private boolean esAceptacion;
	
	// --------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------
	
	/**
	 * Crea un estado ya sea inicial, de aceptación, ambos o ninguno.
	 * @param inicial true Si el estado es un estado inicial para el autómata
	 * @param aceptacion true Si el estado es un estado de aceptación para el autómata 
	 */
	public Estado( /*int id,*/ boolean inicial, boolean aceptacion ){
		//this.id = id;
		esInicial = inicial;
		esAceptacion = aceptacion;
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------

	/**
	 * Marca el estado como un estado inicial
	 */
	public void setAsInicial(){
		esInicial = true;
	}
	
	/**
	 * Marca el estado como un estado no inicial
	 */
	public void setAsNoInicial(){
		esInicial = false;
	}
	
	/**
	 * Marca el estado como un estado de aceptación
	 */
	public void setAsAceptacion(){
		esAceptacion = true;
	}
	
	/**
	 * Marca el estado como un estado de no aceptación
	 */
	public void setAsNoAceptacion(){
		esAceptacion = false;
	}
	
	/**
	 * Indica si el estado es inicial para el autómata en el que se encuentra
	 * @return true Si el estado es inicial. false Si no lo es.
	 */
	public boolean esInicial(){
		return esInicial;
	}
	
	/**
	 * Indica si el estado es de aceptación para el autómata en el que se encuentra
	 * @return true Si el estado es de aceptación. false Si no lo es.
	 */
	public boolean esAceptacion(){
		return esAceptacion;
	}
	
}
