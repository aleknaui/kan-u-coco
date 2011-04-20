/**
 * Esta clase contiene todas las excepciones que se pueden presentar
 * @author ale
 *
 */
public class Exceptions {
	
	class ParserException extends Exception {
		private static final long serialVersionUID = 4136214899881785405L;

		public ParserException(String msg) {
			super(msg);
		}
		
		public ParserException() {
			super();
		}
	}
	class MismatchedTokenException extends ParserException {
		private static final long serialVersionUID = 4113096275724735566L;

		public MismatchedTokenException(String msg) {
			super(msg);
		}
		public MismatchedTokenException() {
			super();
		}
	}
	
	class NoViableAltException extends ParserException {
		private static final long serialVersionUID = -8128723562531373949L;

		public NoViableAltException(String msg) {
			super(msg);
		}
		public NoViableAltException() {
			super();
		}
	}
}
