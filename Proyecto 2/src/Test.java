import java.io.IOException;

/**
 * Esta clase se utiliza para realizar tests
 * de funcionamiento.
 * @author AleKnaui
 *
 */
public class Test {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public static void main(String[] args){
		try {
			new Recognizer( "test.ATG" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
	public static void print(){
		System.out.println();
	}

}
