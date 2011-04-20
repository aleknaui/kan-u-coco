/**
 * Clase que representa un token que anuncia que
 * una cadena concuerda con un tipo.
 * @author AleKnaui
 *
 */
public class Token {
	

	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	private String text;
	private int type;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	public Token(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public void setText(String t) { text=t; }
	String getText() { return text; }
	void setType(int t) { type=t; }
	int getType() { return type; }

}
