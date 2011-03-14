import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * Clase principal del proyecto. Obtiene un nombre, una expresión regular y una cadena a evaluar.
 * Genera el árbol sintáctico de la expresión regular, un AFN por Thompson, un AFD en base al AFN
 * y otro AFD directamente a partir del la expresión regular. 
 * @author AleKnaui
 */
public class Main {
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Scanner para obtención de parámetros */
	private static Scanner scanner = new Scanner( System.in );
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	private static String obtenerNombre(  ){
		System.out.println("Ingrese un nombre para la expresión regular (No puede ser la cadena vacía): ");
		String nombre =  scanner.nextLine();
		if( nombre.isEmpty() || nombre == null ){
			nombre = obtenerNombre( );
		}
		return nombre;
	}
	
	private static String obtenerRegex(  ){
		System.out.println("Ingrese una expresión regular.\n" +
				"\tUtilice '" + RegEx.CONCAT + "' para indicar concatenación\n" +
				"\tUtilice '" + RegEx.OR + "' para indicar OR\n" + 
				"\tUtilice '" + RegEx.KLEENE + "' para indicar la cerradura Kleene\n" +
				"\tUtilice '" + RegEx.POSITIVA + "' para indicar la cerradura Positiva\n" +
				"\tUtilice '" + RegEx.PREGUNTA + "' para indicar que la expresión es opcional\n" +
				"\tUtilice '" + RegEx.EPSILON + "' para indicar la cadena vacía\n" +
				"No puede ingresar la cadena vacía.");
		String regex =  scanner.nextLine();
		if( regex.isEmpty() || regex == null ){
			regex = obtenerRegex( );
		}
		return regex;
	}
	
	private static String obtenerW(  ){
		System.out.println("Ingrese una cadena para comparar con la expresión regular. La cadena vacía es válida.");
		String w =  scanner.nextLine();
		return w;
	}
	
	private static void generarArchivo( String nombre, String contenido ){
		try {
			PrintWriter writer = new PrintWriter( new FileOutputStream( new File( nombre + ".txt" ) ) );
			String[] cadenas =  contenido.split("\n");
			for( int i = 0; i < cadenas.length; i++ ){
				writer.println( cadenas[i] );
			}
			writer.close();
			System.out.println("El archivo " + nombre + " ha sido generado.");
		} catch (FileNotFoundException e) {
			System.out.println("El archivo " + nombre + " no se pudo generar.");
		}
	}
	
	// --------------------------------------------------------------------------------
	// Método main
	// --------------------------------------------------------------------------------

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
		String nombre = obtenerNombre();
		System.out.println();
		String cadenaRegex = obtenerRegex();
		System.out.println();
		String cadenaW = obtenerW();
		System.out.println();
		RegEx regex;
		try{
			regex = new RegEx( cadenaRegex );
			AFN thompson = new AFN( regex );
			AFD directo = new AFD( regex );
			AFD subconjuntos = new AFD( thompson );
			
			generarArchivo( nombre + "_AFN_Thompson", thompson.toString() );
			//System.out.println( thompson.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_Directo", directo.toString() );
			//System.out.println( directo.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_Subconjuntos", subconjuntos.toString() );
			//System.out.println( subconjuntos.toString() );
			
			System.out.println();
			
			if( directo.simular(cadenaW) )
				System.out.println("La cadena \"" + cadenaW + "\" SI concuerda con la expresión regular.");
			else
				System.out.println("La cadena \"" + cadenaW + "\" NO concuerda con la expresión regular.");
			
		} catch( EmptyStackException e ){
			System.out.println( "La expresión regular no está escrita correctamente." );
		} catch( Exception e ){
			System.out.println( "Mismatched parentesis." );
		}
		
	}

}
