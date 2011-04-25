import java.io.FileReader;
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
	
	static boolean debug = true;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public static void main(String[] args)throws Exception{
		
		long tiempoInicial = System.currentTimeMillis();
		try{
		CocolReader cr = new CocolReader( "test.ATG" );
		} catch(Exception e){
			Test.print(e.getMessage());
			e.printStackTrace();
		}
		
		Test.print( "Tiempo de ejecución: " + (System.currentTimeMillis() - tiempoInicial) + " ms" );
		
	}
	
	public static void print(Object o){
		if( debug )
			System.out.println(o);
	}
	public static void print(){
		if( debug )
			System.out.println();
	}

}