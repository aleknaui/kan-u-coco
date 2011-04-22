/**
 * Clase que representa un Token anunciado por un lexer.
 * @author AleKnaui
 */
public class Token {

	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Clase a la que pertenece el token anunciado */
	private String tipo;
	/** Lexema por el cual se anunció el token */
	private String lexema;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Crea un Token.
	 */
	public Token(String tipo, String lexema){
		this.tipo = tipo;
		this.lexema = lexema;
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Informa el tipo de token representado por el objeto
	 */
	public String darTipo(){
		return tipo;
	}
	
	/**
	 * Informa el lexema que causó la anunciación del token 
	 */
	public String darLexema(){
		return lexema;
	}
	
	@Override
	public String toString(){
		return "[" + tipo + ", " + lexema + "]";
	}
}
