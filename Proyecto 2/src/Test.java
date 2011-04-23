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
		
		long tiempoInicial = System.currentTimeMillis();
		try{
		CocolReader cr = new CocolReader( "test.ATG" );
		cr.verificarInicio();
		cr.leerCharacters();
		} catch(Exception e){
			print(e.getMessage());
		}
		
		Test.print( "Tiempo de ejecución: " + (System.currentTimeMillis() - tiempoInicial) + " ms" );
		
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
	public static void print(){
		System.out.println();
	}

}