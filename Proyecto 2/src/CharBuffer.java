import java.io.IOException;
import java.io.Reader;


public class CharBuffer {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	private int[] buf;
	private Reader f;
	private int k;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	public CharBuffer(Reader in, int k){
		this.k = k;
		buf = new int[k];
		f = in;
		try{
			for( int i = 0; i < k; i++ ){
				buf[i] = f.read();
			}
		} catch( IOException io ){
			System.err.println(io.getMessage());
			System.exit(1);
		}
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	public int LA(int i){
		if( i >= 1 && i <= k ){
			return buf[i-1];
		} return 0;
	}
	
	public void consume(){
		for( int i = 0; i<k-1; i++ ){
			buf[i]=buf[i+1];
		}
		try{
			buf[k-1] = f.read();
			if( buf[k-1]=='\r' )
				buf[k-1]=f.read();
		} catch( IOException io ){
			System.err.println(io.getMessage());
			System.exit(1);
		}
		
		/*
		String coso = "[";
		for( int i = 0; i < k; i++ ){
			coso += (char)buf[i];
		}
		coso += ']';
		Test.print(coso);
		//*/
	}
}
