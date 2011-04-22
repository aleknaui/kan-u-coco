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
	
	public static void main(String[] args) throws Exception{
		
		long tiempoInicial = System.currentTimeMillis();
		
		new CocolReader( "test.ATG" );
		
		Test.print( "Tiempo de ejecución: " + (System.currentTimeMillis() - tiempoInicial) + " ms" );
		
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
	public static void print(){
		System.out.println();
	}

}