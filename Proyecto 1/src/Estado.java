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
	
	public Estado( /*int id,*/ boolean inicial, boolean aceptacion ){
		//this.id = id;
		esInicial = inicial;
		esAceptacion = aceptacion;
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------

	public void setAsInicial(){
		esInicial = true;
	}
	
	public void setAsNoInicial(){
		esInicial = false;
	}
	
	public void setAsAceptacion(){
		esAceptacion = true;
	}
	
	public void setAsNoAceptacion(){
		esAceptacion = false;
	}
	
	public boolean esInicial(){
		return esInicial;
	}
	
	public boolean esAceptacion(){
		return esAceptacion;
	}
	
	public int getId(){
		return this.hashCode();
	}
	/*
	public boolean equals( Estado estado ){
		return id == estado.id;
	}*/
	
}
