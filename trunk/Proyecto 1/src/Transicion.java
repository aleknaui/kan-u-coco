/**
 * Esta clase representa una transición entre dos estados.
 * @author AleKnaui
 */
public class Transicion{
	
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
	
	/**
	 * Crea una transición tal que T(estadoA, simbolo) = estadoB
	 * @param estadoA El estado del que parte la transición
	 * @param estadoB El estado al que se dirige la transición
	 * @param simbolo El símbolo bajo el cual funciona la transición
	 */
	public Transicion( Estado estadoA, Estado estadoB, char simbolo ){
		this.estadoA = estadoA;
		this.estadoB = estadoB;
		this.simbolo = simbolo;
	}

	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Retorna el estado A
	 */
	public Estado darEstadoA(){
		return estadoA;
	}
	
	/**
	 * Retorna el estado B
	 */
	public Estado darEstadoB(){
		return estadoB;
	}
	
	/**
	 * Retorna el símbolo
	 */
	public char darSimbolo(){
		return simbolo;
	}
}
