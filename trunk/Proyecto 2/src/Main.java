import javax.swing.*;

/**
 * Clase principal del proyecto. Obtiene el path de un
 * archivo (preferiblemente .ATG, pero no es necesario)
 * y a partir de su especificación, crea un analizador léxico
 * con la forma de un proyecto de Eclipse. 
 *
 * @author AleKnaui
 */
public class Main extends JFrame{
	
	// --------------------------------------------------------------------------------
	// Método main
	// --------------------------------------------------------------------------------

	public Main() throws Exception{
		this.setVisible(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Test.print("Seleccione el archivo que contiene la gramática deseada.");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("kan-u-coco? - Generador de analizadores léxicos.");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setMultiSelectionEnabled(false);
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    	Test.print("Se ha seleccionado el archivo " + chooser.getSelectedFile().getAbsolutePath());
	    	Test.print("Generando analizador léxico...");
			long tiempoInicial = System.currentTimeMillis();
	    	try{
	    		new CocolReader(chooser.getSelectedFile().getPath());
	    	} catch( Exception e ){
	    		Test.print( e.getMessage() );
	    	}
	    	Test.print( "Tiempo de ejecución: " + (System.currentTimeMillis() - tiempoInicial) + " ms" );
	    }
	    else {
	    	Test.print("No se seleccionó ningún archivo.");
	    }
	    dispose();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		System.out.println("Alejandra Canahui");
		System.out.println("09-303");
		System.out.println("Kan U Coco? - Proyecto 2");
		System.out.println();
		new Main();
		
	}
		
}
