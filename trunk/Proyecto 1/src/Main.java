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
 * 
 * <b>Diseño:</b>
 * <p>Desde el principio se hizo claro que debía haber una clase para un una Expresión Regular,
 * un Autómata Finito Determinista y un Autómata Finito No Determinista. Luego de ver el
 * algoritmo de creación directa de AFDs pensé que sería bueno modelar una Expresión Regular
 * como un árbol sintáctico.</p>
 * 
 * <p>En realidad no se me ocurrió modelar los Autómatas con una matriz en vez de Estados y Transiciones
 * separadas, ya que contaba con el código que realicé para mi tarea 3.</p>
 * 
 * <p>Tenía en principio pensado modelar una sola clase que representara mi grafo, ya que la clase Estado
 * tendría un HashMap con la información de sus transiciones. A la hora de la implementación, me imaginé
 * que ésto sería muy complicado y decidí hacer una clase Estado y una clase Transición.</p>
 * 
 * <b>Discusion:</b>
 * <p>Lo más difícil del proyecto fue para mí la interpretación de la Expresión Regular, lo cual me retrasó
 * y preocupó bastante. Afortunadamente encontré el Shunting-Yard Algorithm de Djikstra para convertir expresiones
 * de notación infix a notación postfix. Luego de adaptar el algoritmo para usarlo con los operadores de expresiones
 * regulares, la generación del árbol fue mucho más sencilla.</p>
 * 
 * <p>Hubo un momento en el que quería borrar el proyecto y hacerlo en Python, porque ciertamente aunque tenía los
 * algoritmos casi terminados, me tardé bastante en implementarlos, ya que la declaración de los tipos de las variables
 * nunca terminaba y no contaba como utilidades tan prácticas como las listas y las tuplas. Afortunadamente logré terminar.</p>
 * 
 * <p>Me gustó mucho hacer la implementación del AFD directo, ya que fue casi lo último que hice, por lo que el código fue muy ordenado
 * y en tres corridas me funcionó. De similar manera la simulación.
 * </p>
 * 
 * <p>Aunque no me preocupé mucho sobre cómo realizaría el proyecto, ya que hice mi tarea y presté atención en las clases, ciertamente
 * tomó bastante tiempo. Traté de implementar una parte de la funcionalidad por día, empezando el Lunes. No lo logré, a decir verdad,
 * porque la creación de el árbol sintáctico no me funcionó el primer día, al igual que el AFD directo. Aunque, a excepción de la primera
 * parte, no tuve dudas sobre qué hacer, la implementación ciertamente tomó bastante tiempo.</p>
 * 
 * <b>Ejemplos y pruebas realizadas:</b>

 * 
 * <p>Finalmente, jugué mucho con el producto final, para verificar que se reconocieran correctamente las expresiones regulares.</p>
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
			AFD minimoDirecto = directo.minimizar();
			AFD minimoSubconjuntos = subconjuntos.minimizar();
			
			generarArchivo( nombre + "_AFN_Thompson", thompson.toString() );
			//System.out.println( thompson.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_Directo", directo.toString() );
			//System.out.println( directo.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_Subconjuntos", subconjuntos.toString() );
			//System.out.println( subconjuntos.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_MinimoDirecto", minimoDirecto.toString() );
			//System.out.println( subconjuntos.toString() );
			//System.out.println();
			generarArchivo( nombre + "_AFD_MinimoSubconjuntos", minimoSubconjuntos.toString() );
			//System.out.println( subconjuntos.toString() );
			
			System.out.println();
			
			if( directo.simular(cadenaW) && subconjuntos.simular(cadenaW) && thompson.simular(cadenaW) 
					&& minimoDirecto.simular(cadenaW) && minimoSubconjuntos.simular(cadenaW))
				System.out.println("La cadena \"" + cadenaW + "\" SI concuerda con la expresión regular.");
			else
				System.out.println("La cadena \"" + cadenaW + "\" NO concuerda con la expresión regular.");
			
			System.out.print("¿Desea evaluar otra cadena? (n = No) ");
			while( ! scanner.nextLine().equals("n")){
				cadenaW = obtenerW();
				if( directo.simular(cadenaW) && subconjuntos.simular(cadenaW) && thompson.simular(cadenaW)
						&& minimoDirecto.simular(cadenaW) && minimoSubconjuntos.simular(cadenaW))
					System.out.println("La cadena \"" + cadenaW + "\" SI concuerda con la expresión regular.");
				else
					System.out.println("La cadena \"" + cadenaW + "\" NO concuerda con la expresión regular.");
				System.out.print("¿Desea evaluar otra cadena? (n = No) ");
			}
			
		} catch( EmptyStackException e ){
			System.out.println( "La expresión regular no está escrita correctamente." );
		} catch( Exception e ){
			e.printStackTrace();
			//System.out.println( "Mismatched parentesis." );
		}
		
		/*try {
			System.out.println( new RegEx("(b|b)*abb(a|b)*").infix2postfix("(b|b)*abb(a|b)*") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
