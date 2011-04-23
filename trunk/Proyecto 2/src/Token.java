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
	
	/**
	 * Indica si el token representa al tipo indicado como parámetro
	 * @param t El tipo que se desea verificar
	 * @return true Si el token es del tipo indicado. false Si no lo es.
	 */
	public boolean esTipo(String t){
		return tipo.equals(t);
	}
	
	/**
	 * Indica si el lexema es igual a la cadena indicada
	 * @param l La cadena con que se quiere comparar
	 * @return true Si el lexema es igual a la cadena. false Si no lo es.
	 */
	public boolean lexemaEs(String l){
		return lexema.equals(l);
	}
	
	@Override
	public boolean equals( Object o ){
		Token t = (Token) o;
		return tipo.equals(t.tipo) && lexema.equals(t.lexema);
	}
	
	@Override
	public String toString(){
		return "[" + tipo + ", " + lexema + "]";
	}
}
