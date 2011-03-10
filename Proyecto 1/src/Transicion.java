/**
 * Esta clase representa una transición entre dos estados.
 * @author AleKnaui
 */
public class Transicion {
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Estado del que parte la transición */
	private Estado estadoA;
	/** Estado al que se dirige la transición */
	private Estado estadoB;
	/** Síbolo que dirige la transición */
	private char simbolo;
	
	// --------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------
	
	public Transicion( Estado estadoA, Estado estadoB, char simbolo ){
		this.estadoA = estadoA;
		this.estadoB = estadoB;
		this.simbolo = simbolo;
	}

	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public Estado darEstadoA(){
		return estadoA;
	}
	
	public Estado darEstadoB(){
		return estadoB;
	}
	
	public char darSimbolo(){
		return simbolo;
	}
}
