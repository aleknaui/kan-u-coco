import java.io.FileReader;
import java.util.ArrayList;

/**
 * Esta clase interpreta el lenguaje Cocol para
 * generar un analizador sintáctico.
 * @author AleKnaui
 */
public class CocolReader {
	
	// --------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------
	
	/** Cadenas que describen la expresión regular de los diferentes tokens, keywords o caracteres  */
	private final static String IDENT = "" + RegEx.OPEN_PARENTHESIS + 'A' + RegEx.OR + 'B' + RegEx.OR + 'C' + RegEx.OR + 'D' + RegEx.OR + 'E' + RegEx.OR + 'F' + RegEx.OR + 'G' + RegEx.OR + 'H' + RegEx.OR + 'I' + RegEx.OR + 'J' + RegEx.OR + 'K' + RegEx.OR + 'L' + RegEx.OR + 'M' + RegEx.OR + 'N' + RegEx.OR + 'O' + RegEx.OR + 'P' + RegEx.OR + 'Q' + RegEx.OR + 'R' + RegEx.OR + 'S' + RegEx.OR + 'T' + RegEx.OR + 'U' + RegEx.OR + 'V' + RegEx.OR + 'W' + RegEx.OR + 'X' + RegEx.OR + 'Y' + RegEx.OR + 'Z' + RegEx.OR + 'a' + RegEx.OR + 'b' + RegEx.OR + 'c' + RegEx.OR + 'd' + RegEx.OR + 'e' + RegEx.OR + 'f' + RegEx.OR + 'g' + RegEx.OR + 'h' + RegEx.OR + 'i' + RegEx.OR + 'j' + RegEx.OR + 'k' + RegEx.OR + 'l' + RegEx.OR + 'm' + RegEx.OR + 'n' + RegEx.OR + 'o' + RegEx.OR + 'p' + RegEx.OR + 'q' + RegEx.OR + 'r' + RegEx.OR + 's' + RegEx.OR + 't' + RegEx.OR + 'u' + RegEx.OR + 'v' + RegEx.OR + 'w' + RegEx.OR + 'x' + RegEx.OR + 'y' + RegEx.OR + 'z' + RegEx.CLOSE_PARENTHESIS + RegEx.OPEN_PARENTHESIS + RegEx.OPEN_PARENTHESIS + 'A' + RegEx.OR + 'B' + RegEx.OR + 'C' + RegEx.OR + 'D' + RegEx.OR + 'E' + RegEx.OR + 'F' + RegEx.OR + 'G' + RegEx.OR + 'H' + RegEx.OR + 'I' + RegEx.OR + 'J' + RegEx.OR + 'K' + RegEx.OR + 'L' + RegEx.OR + 'M' + RegEx.OR + 'N' + RegEx.OR + 'O' + RegEx.OR + 'P' + RegEx.OR + 'Q' + RegEx.OR + 'R' + RegEx.OR + 'S' + RegEx.OR + 'T' + RegEx.OR + 'U' + RegEx.OR + 'V' + RegEx.OR + 'W' + RegEx.OR + 'X' + RegEx.OR + 'Y' + RegEx.OR + 'Z' + RegEx.OR + 'a' + RegEx.OR + 'b' + RegEx.OR + 'c' + RegEx.OR + 'd' + RegEx.OR + 'e' + RegEx.OR + 'f' + RegEx.OR + 'g' + RegEx.OR + 'h' + RegEx.OR + 'i' + RegEx.OR + 'j' + RegEx.OR + 'k' + RegEx.OR + 'l' + RegEx.OR + 'm' + RegEx.OR + 'n' + RegEx.OR + 'o' + RegEx.OR + 'p' + RegEx.OR + 'q' + RegEx.OR + 'r' + RegEx.OR + 's' + RegEx.OR + 't' + RegEx.OR + 'u' + RegEx.OR + 'v' + RegEx.OR + 'w' + RegEx.OR + 'x' + RegEx.OR + 'y' + RegEx.OR + 'z' + RegEx.CLOSE_PARENTHESIS + RegEx.OR + RegEx.OPEN_PARENTHESIS + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9' + RegEx.CLOSE_PARENTHESIS + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE;
	private final static String NUMBER = "" + RegEx.OPEN_PARENTHESIS + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9'  + RegEx.CLOSE_PARENTHESIS + RegEx.OPEN_PARENTHESIS + '0' + RegEx.OR + '1' + RegEx.OR + '2' + RegEx.OR + '3' + RegEx.OR + '4' + RegEx.OR + '5' + RegEx.OR + '6' + RegEx.OR + '7' + RegEx.OR + '8' + RegEx.OR + '9'  + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE;
	private final static String STRING = "" + '"' + RegEx.OPEN_PARENTHESIS + (char) 0 + RegEx.OR + (char) 1 + RegEx.OR + (char) 2 + RegEx.OR + (char) 3 + RegEx.OR + (char) 4 + RegEx.OR + (char) 5 + RegEx.OR + (char) 6 + RegEx.OR + (char) 7 + RegEx.OR + (char) 8 + RegEx.OR + (char) 9 + RegEx.OR + (char) 10 + RegEx.OR + (char) 11 + RegEx.OR + (char) 12 + RegEx.OR + (char) 13 + RegEx.OR + (char) 14 + RegEx.OR + (char) 15 + RegEx.OR + (char) 16 + RegEx.OR + (char) 17 + RegEx.OR + (char) 18 + RegEx.OR + (char) 19 + RegEx.OR + (char) 20 + RegEx.OR + (char) 21 + RegEx.OR + (char) 22 + RegEx.OR + (char) 23 + RegEx.OR + (char) 24 + RegEx.OR + (char) 25 + RegEx.OR + (char) 26 + RegEx.OR + (char) 27 + RegEx.OR + (char) 28 + RegEx.OR + (char) 29 + RegEx.OR + (char) 30 + RegEx.OR + (char) 31 + RegEx.OR + (char) 32 + RegEx.OR + (char) 33 + RegEx.OR + (char) 35 + RegEx.OR + (char) 36 + RegEx.OR + (char) 37 + RegEx.OR + (char) 38 + RegEx.OR + (char) 39 + RegEx.OR + (char) 40 + RegEx.OR + (char) 41 + RegEx.OR + (char) 42 + RegEx.OR + (char) 43 + RegEx.OR + (char) 44 + RegEx.OR + (char) 45 + RegEx.OR + (char) 46 + RegEx.OR + (char) 47 + RegEx.OR + (char) 48 + RegEx.OR + (char) 49 + RegEx.OR + (char) 50 + RegEx.OR + (char) 51 + RegEx.OR + (char) 52 + RegEx.OR + (char) 53 + RegEx.OR + (char) 54 + RegEx.OR + (char) 55 + RegEx.OR + (char) 56 + RegEx.OR + (char) 57 + RegEx.OR + (char) 58 + RegEx.OR + (char) 59 + RegEx.OR + (char) 60 + RegEx.OR + (char) 61 + RegEx.OR + (char) 62 + RegEx.OR + (char) 63 + RegEx.OR + (char) 64 + RegEx.OR + (char) 65 + RegEx.OR + (char) 66 + RegEx.OR + (char) 67 + RegEx.OR + (char) 68 + RegEx.OR + (char) 69 + RegEx.OR + (char) 70 + RegEx.OR + (char) 71 + RegEx.OR + (char) 72 + RegEx.OR + (char) 73 + RegEx.OR + (char) 74 + RegEx.OR + (char) 75 + RegEx.OR + (char) 76 + RegEx.OR + (char) 77 + RegEx.OR + (char) 78 + RegEx.OR + (char) 79 + RegEx.OR + (char) 80 + RegEx.OR + (char) 81 + RegEx.OR + (char) 82 + RegEx.OR + (char) 83 + RegEx.OR + (char) 84 + RegEx.OR + (char) 85 + RegEx.OR + (char) 86 + RegEx.OR + (char) 87 + RegEx.OR + (char) 88 + RegEx.OR + (char) 89 + RegEx.OR + (char) 90 + RegEx.OR + (char) 91 + RegEx.OR + (char) 92 + RegEx.OR + (char) 93 + RegEx.OR + (char) 94 + RegEx.OR + (char) 95 + RegEx.OR + (char) 96 + RegEx.OR + (char) 97 + RegEx.OR + (char) 98 + RegEx.OR + (char) 99 + RegEx.OR + (char) 100 + RegEx.OR + (char) 101 + RegEx.OR + (char) 102 + RegEx.OR + (char) 103 + RegEx.OR + (char) 104 + RegEx.OR + (char) 105 + RegEx.OR + (char) 106 + RegEx.OR + (char) 107 + RegEx.OR + (char) 108 + RegEx.OR + (char) 109 + RegEx.OR + (char) 110 + RegEx.OR + (char) 111 + RegEx.OR + (char) 112 + RegEx.OR + (char) 113 + RegEx.OR + (char) 114 + RegEx.OR + (char) 115 + RegEx.OR + (char) 116 + RegEx.OR + (char) 117 + RegEx.OR + (char) 118 + RegEx.OR + (char) 119 + RegEx.OR + (char) 120 + RegEx.OR + (char) 121 + RegEx.OR + (char) 122 + RegEx.OR + (char) 123 + RegEx.OR + (char) 124 + RegEx.OR + (char) 125 + RegEx.OR + (char) 126 + RegEx.OR + (char) 127 + RegEx.OR + (char) 128 + RegEx.OR + (char) 129 + RegEx.OR + (char) 130 + RegEx.OR + (char) 131 + RegEx.OR + (char) 132 + RegEx.OR + (char) 133 + RegEx.OR + (char) 134 + RegEx.OR + (char) 135 + RegEx.OR + (char) 136 + RegEx.OR + (char) 137 + RegEx.OR + (char) 138 + RegEx.OR + (char) 139 + RegEx.OR + (char) 140 + RegEx.OR + (char) 141 + RegEx.OR + (char) 142 + RegEx.OR + (char) 143 + RegEx.OR + (char) 144 + RegEx.OR + (char) 145 + RegEx.OR + (char) 146 + RegEx.OR + (char) 147 + RegEx.OR + (char) 148 + RegEx.OR + (char) 149 + RegEx.OR + (char) 150 + RegEx.OR + (char) 151 + RegEx.OR + (char) 152 + RegEx.OR + (char) 153 + RegEx.OR + (char) 154 + RegEx.OR + (char) 155 + RegEx.OR + (char) 156 + RegEx.OR + (char) 157 + RegEx.OR + (char) 158 + RegEx.OR + (char) 159 + RegEx.OR + (char) 160 + RegEx.OR + (char) 161 + RegEx.OR + (char) 162 + RegEx.OR + (char) 163 + RegEx.OR + (char) 164 + RegEx.OR + (char) 165 + RegEx.OR + (char) 166 + RegEx.OR + (char) 167 + RegEx.OR + (char) 168 + RegEx.OR + (char) 169 + RegEx.OR + (char) 170 + RegEx.OR + (char) 171 + RegEx.OR + (char) 172 + RegEx.OR + (char) 173 + RegEx.OR + (char) 174 + RegEx.OR + (char) 175 + RegEx.OR + (char) 176 + RegEx.OR + (char) 177 + RegEx.OR + (char) 178 + RegEx.OR + (char) 179 + RegEx.OR + (char) 180 + RegEx.OR + (char) 181 + RegEx.OR + (char) 182 + RegEx.OR + (char) 183 + RegEx.OR + (char) 184 + RegEx.OR + (char) 185 + RegEx.OR + (char) 186 + RegEx.OR + (char) 187 + RegEx.OR + (char) 188 + RegEx.OR + (char) 189 + RegEx.OR + (char) 190 + RegEx.OR + (char) 191 + RegEx.OR + (char) 192 + RegEx.OR + (char) 193 + RegEx.OR + (char) 194 + RegEx.OR + (char) 195 + RegEx.OR + (char) 196 + RegEx.OR + (char) 197 + RegEx.OR + (char) 198 + RegEx.OR + (char) 199 + RegEx.OR + (char) 200 + RegEx.OR + (char) 201 + RegEx.OR + (char) 202 + RegEx.OR + (char) 203 + RegEx.OR + (char) 204 + RegEx.OR + (char) 205 + RegEx.OR + (char) 206 + RegEx.OR + (char) 207 + RegEx.OR + (char) 208 + RegEx.OR + (char) 209 + RegEx.OR + (char) 210 + RegEx.OR + (char) 211 + RegEx.OR + (char) 212 + RegEx.OR + (char) 213 + RegEx.OR + (char) 214 + RegEx.OR + (char) 215 + RegEx.OR + (char) 216 + RegEx.OR + (char) 217 + RegEx.OR + (char) 218 + RegEx.OR + (char) 219 + RegEx.OR + (char) 220 + RegEx.OR + (char) 221 + RegEx.OR + (char) 222 + RegEx.OR + (char) 223 + RegEx.OR + (char) 224 + RegEx.OR + (char) 225 + RegEx.OR + (char) 226 + RegEx.OR + (char) 227 + RegEx.OR + (char) 228 + RegEx.OR + (char) 229 + RegEx.OR + (char) 230 + RegEx.OR + (char) 231 + RegEx.OR + (char) 232 + RegEx.OR + (char) 233 + RegEx.OR + (char) 234 + RegEx.OR + (char) 235 + RegEx.OR + (char) 236 + RegEx.OR + (char) 237 + RegEx.OR + (char) 238 + RegEx.OR + (char) 239 + RegEx.OR + (char) 240 + RegEx.OR + (char) 241 + RegEx.OR + (char) 242 + RegEx.OR + (char) 243 + RegEx.OR + (char) 244 + RegEx.OR + (char) 245 + RegEx.OR + (char) 246 + RegEx.OR + (char) 247 + RegEx.OR + (char) 248 + RegEx.OR + (char) 249 + RegEx.OR + (char) 250 + RegEx.OR + (char) 251 + RegEx.OR + (char) 252 + RegEx.OR + (char) 253 + RegEx.OR + (char) 254 + RegEx.OR + (char) 255 + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE + '"';
	private final static String CHAR = "" + '\'' + RegEx.OPEN_PARENTHESIS + (char) 0 + RegEx.OR + (char) 1 + RegEx.OR + (char) 2 + RegEx.OR + (char) 3 + RegEx.OR + (char) 4 + RegEx.OR + (char) 5 + RegEx.OR + (char) 6 + RegEx.OR + (char) 7 + RegEx.OR + (char) 8 + RegEx.OR + (char) 9 + RegEx.OR + (char) 10 + RegEx.OR + (char) 11 + RegEx.OR + (char) 12 + RegEx.OR + (char) 13 + RegEx.OR + (char) 14 + RegEx.OR + (char) 15 + RegEx.OR + (char) 16 + RegEx.OR + (char) 17 + RegEx.OR + (char) 18 + RegEx.OR + (char) 19 + RegEx.OR + (char) 20 + RegEx.OR + (char) 21 + RegEx.OR + (char) 22 + RegEx.OR + (char) 23 + RegEx.OR + (char) 24 + RegEx.OR + (char) 25 + RegEx.OR + (char) 26 + RegEx.OR + (char) 27 + RegEx.OR + (char) 28 + RegEx.OR + (char) 29 + RegEx.OR + (char) 30 + RegEx.OR + (char) 31 + RegEx.OR + (char) 32 + RegEx.OR + (char) 33 + RegEx.OR + (char) 34 + RegEx.OR + (char) 35 + RegEx.OR + (char) 36 + RegEx.OR + (char) 37 + RegEx.OR + (char) 38 + RegEx.OR + (char) 40 + RegEx.OR + (char) 41 + RegEx.OR + (char) 42 + RegEx.OR + (char) 43 + RegEx.OR + (char) 44 + RegEx.OR + (char) 45 + RegEx.OR + (char) 46 + RegEx.OR + (char) 47 + RegEx.OR + (char) 48 + RegEx.OR + (char) 49 + RegEx.OR + (char) 50 + RegEx.OR + (char) 51 + RegEx.OR + (char) 52 + RegEx.OR + (char) 53 + RegEx.OR + (char) 54 + RegEx.OR + (char) 55 + RegEx.OR + (char) 56 + RegEx.OR + (char) 57 + RegEx.OR + (char) 58 + RegEx.OR + (char) 59 + RegEx.OR + (char) 60 + RegEx.OR + (char) 61 + RegEx.OR + (char) 62 + RegEx.OR + (char) 63 + RegEx.OR + (char) 64 + RegEx.OR + (char) 65 + RegEx.OR + (char) 66 + RegEx.OR + (char) 67 + RegEx.OR + (char) 68 + RegEx.OR + (char) 69 + RegEx.OR + (char) 70 + RegEx.OR + (char) 71 + RegEx.OR + (char) 72 + RegEx.OR + (char) 73 + RegEx.OR + (char) 74 + RegEx.OR + (char) 75 + RegEx.OR + (char) 76 + RegEx.OR + (char) 77 + RegEx.OR + (char) 78 + RegEx.OR + (char) 79 + RegEx.OR + (char) 80 + RegEx.OR + (char) 81 + RegEx.OR + (char) 82 + RegEx.OR + (char) 83 + RegEx.OR + (char) 84 + RegEx.OR + (char) 85 + RegEx.OR + (char) 86 + RegEx.OR + (char) 87 + RegEx.OR + (char) 88 + RegEx.OR + (char) 89 + RegEx.OR + (char) 90 + RegEx.OR + (char) 91 + RegEx.OR + (char) 92 + RegEx.OR + (char) 93 + RegEx.OR + (char) 94 + RegEx.OR + (char) 95 + RegEx.OR + (char) 96 + RegEx.OR + (char) 97 + RegEx.OR + (char) 98 + RegEx.OR + (char) 99 + RegEx.OR + (char) 100 + RegEx.OR + (char) 101 + RegEx.OR + (char) 102 + RegEx.OR + (char) 103 + RegEx.OR + (char) 104 + RegEx.OR + (char) 105 + RegEx.OR + (char) 106 + RegEx.OR + (char) 107 + RegEx.OR + (char) 108 + RegEx.OR + (char) 109 + RegEx.OR + (char) 110 + RegEx.OR + (char) 111 + RegEx.OR + (char) 112 + RegEx.OR + (char) 113 + RegEx.OR + (char) 114 + RegEx.OR + (char) 115 + RegEx.OR + (char) 116 + RegEx.OR + (char) 117 + RegEx.OR + (char) 118 + RegEx.OR + (char) 119 + RegEx.OR + (char) 120 + RegEx.OR + (char) 121 + RegEx.OR + (char) 122 + RegEx.OR + (char) 123 + RegEx.OR + (char) 124 + RegEx.OR + (char) 125 + RegEx.OR + (char) 126 + RegEx.OR + (char) 127 + RegEx.OR + (char) 128 + RegEx.OR + (char) 129 + RegEx.OR + (char) 130 + RegEx.OR + (char) 131 + RegEx.OR + (char) 132 + RegEx.OR + (char) 133 + RegEx.OR + (char) 134 + RegEx.OR + (char) 135 + RegEx.OR + (char) 136 + RegEx.OR + (char) 137 + RegEx.OR + (char) 138 + RegEx.OR + (char) 139 + RegEx.OR + (char) 140 + RegEx.OR + (char) 141 + RegEx.OR + (char) 142 + RegEx.OR + (char) 143 + RegEx.OR + (char) 144 + RegEx.OR + (char) 145 + RegEx.OR + (char) 146 + RegEx.OR + (char) 147 + RegEx.OR + (char) 148 + RegEx.OR + (char) 149 + RegEx.OR + (char) 150 + RegEx.OR + (char) 151 + RegEx.OR + (char) 152 + RegEx.OR + (char) 153 + RegEx.OR + (char) 154 + RegEx.OR + (char) 155 + RegEx.OR + (char) 156 + RegEx.OR + (char) 157 + RegEx.OR + (char) 158 + RegEx.OR + (char) 159 + RegEx.OR + (char) 160 + RegEx.OR + (char) 161 + RegEx.OR + (char) 162 + RegEx.OR + (char) 163 + RegEx.OR + (char) 164 + RegEx.OR + (char) 165 + RegEx.OR + (char) 166 + RegEx.OR + (char) 167 + RegEx.OR + (char) 168 + RegEx.OR + (char) 169 + RegEx.OR + (char) 170 + RegEx.OR + (char) 171 + RegEx.OR + (char) 172 + RegEx.OR + (char) 173 + RegEx.OR + (char) 174 + RegEx.OR + (char) 175 + RegEx.OR + (char) 176 + RegEx.OR + (char) 177 + RegEx.OR + (char) 178 + RegEx.OR + (char) 179 + RegEx.OR + (char) 180 + RegEx.OR + (char) 181 + RegEx.OR + (char) 182 + RegEx.OR + (char) 183 + RegEx.OR + (char) 184 + RegEx.OR + (char) 185 + RegEx.OR + (char) 186 + RegEx.OR + (char) 187 + RegEx.OR + (char) 188 + RegEx.OR + (char) 189 + RegEx.OR + (char) 190 + RegEx.OR + (char) 191 + RegEx.OR + (char) 192 + RegEx.OR + (char) 193 + RegEx.OR + (char) 194 + RegEx.OR + (char) 195 + RegEx.OR + (char) 196 + RegEx.OR + (char) 197 + RegEx.OR + (char) 198 + RegEx.OR + (char) 199 + RegEx.OR + (char) 200 + RegEx.OR + (char) 201 + RegEx.OR + (char) 202 + RegEx.OR + (char) 203 + RegEx.OR + (char) 204 + RegEx.OR + (char) 205 + RegEx.OR + (char) 206 + RegEx.OR + (char) 207 + RegEx.OR + (char) 208 + RegEx.OR + (char) 209 + RegEx.OR + (char) 210 + RegEx.OR + (char) 211 + RegEx.OR + (char) 212 + RegEx.OR + (char) 213 + RegEx.OR + (char) 214 + RegEx.OR + (char) 215 + RegEx.OR + (char) 216 + RegEx.OR + (char) 217 + RegEx.OR + (char) 218 + RegEx.OR + (char) 219 + RegEx.OR + (char) 220 + RegEx.OR + (char) 221 + RegEx.OR + (char) 222 + RegEx.OR + (char) 223 + RegEx.OR + (char) 224 + RegEx.OR + (char) 225 + RegEx.OR + (char) 226 + RegEx.OR + (char) 227 + RegEx.OR + (char) 228 + RegEx.OR + (char) 229 + RegEx.OR + (char) 230 + RegEx.OR + (char) 231 + RegEx.OR + (char) 232 + RegEx.OR + (char) 233 + RegEx.OR + (char) 234 + RegEx.OR + (char) 235 + RegEx.OR + (char) 236 + RegEx.OR + (char) 237 + RegEx.OR + (char) 238 + RegEx.OR + (char) 239 + RegEx.OR + (char) 240 + RegEx.OR + (char) 241 + RegEx.OR + (char) 242 + RegEx.OR + (char) 243 + RegEx.OR + (char) 244 + RegEx.OR + (char) 245 + RegEx.OR + (char) 246 + RegEx.OR + (char) 247 + RegEx.OR + (char) 248 + RegEx.OR + (char) 249 + RegEx.OR + (char) 250 + RegEx.OR + (char) 251 + RegEx.OR + (char) 252 + RegEx.OR + (char) 253 + RegEx.OR + (char) 254 + RegEx.OR + (char) 255 + RegEx.CLOSE_PARENTHESIS + '\'';
	private final static String CHR = "CHR(" + NUMBER + ")";
	private final static String COMMENT = "" + '#' + RegEx.OPEN_PARENTHESIS + (char) 0 + RegEx.OR + (char) 1 + RegEx.OR + (char) 2 + RegEx.OR + (char) 3 + RegEx.OR + (char) 4 + RegEx.OR + (char) 5 + RegEx.OR + (char) 6 + RegEx.OR + (char) 7 + RegEx.OR + (char) 8 + RegEx.OR + (char) 9 + RegEx.OR + (char) 11 + RegEx.OR + (char) 12 + RegEx.OR + (char) 13 + RegEx.OR + (char) 14 + RegEx.OR + (char) 15 + RegEx.OR + (char) 16 + RegEx.OR + (char) 17 + RegEx.OR + (char) 18 + RegEx.OR + (char) 19 + RegEx.OR + (char) 20 + RegEx.OR + (char) 21 + RegEx.OR + (char) 22 + RegEx.OR + (char) 23 + RegEx.OR + (char) 24 + RegEx.OR + (char) 25 + RegEx.OR + (char) 26 + RegEx.OR + (char) 27 + RegEx.OR + (char) 28 + RegEx.OR + (char) 29 + RegEx.OR + (char) 30 + RegEx.OR + (char) 31 + RegEx.OR + (char) 32 + RegEx.OR + (char) 33 + RegEx.OR + (char) 34 + RegEx.OR + (char) 35 + RegEx.OR + (char) 36 + RegEx.OR + (char) 37 + RegEx.OR + (char) 38 + RegEx.OR + (char) 39 + RegEx.OR + (char) 40 + RegEx.OR + (char) 41 + RegEx.OR + (char) 42 + RegEx.OR + (char) 43 + RegEx.OR + (char) 44 + RegEx.OR + (char) 45 + RegEx.OR + (char) 46 + RegEx.OR + (char) 47 + RegEx.OR + (char) 48 + RegEx.OR + (char) 49 + RegEx.OR + (char) 50 + RegEx.OR + (char) 51 + RegEx.OR + (char) 52 + RegEx.OR + (char) 53 + RegEx.OR + (char) 54 + RegEx.OR + (char) 55 + RegEx.OR + (char) 56 + RegEx.OR + (char) 57 + RegEx.OR + (char) 58 + RegEx.OR + (char) 59 + RegEx.OR + (char) 60 + RegEx.OR + (char) 61 + RegEx.OR + (char) 62 + RegEx.OR + (char) 63 + RegEx.OR + (char) 64 + RegEx.OR + (char) 65 + RegEx.OR + (char) 66 + RegEx.OR + (char) 67 + RegEx.OR + (char) 68 + RegEx.OR + (char) 69 + RegEx.OR + (char) 70 + RegEx.OR + (char) 71 + RegEx.OR + (char) 72 + RegEx.OR + (char) 73 + RegEx.OR + (char) 74 + RegEx.OR + (char) 75 + RegEx.OR + (char) 76 + RegEx.OR + (char) 77 + RegEx.OR + (char) 78 + RegEx.OR + (char) 79 + RegEx.OR + (char) 80 + RegEx.OR + (char) 81 + RegEx.OR + (char) 82 + RegEx.OR + (char) 83 + RegEx.OR + (char) 84 + RegEx.OR + (char) 85 + RegEx.OR + (char) 86 + RegEx.OR + (char) 87 + RegEx.OR + (char) 88 + RegEx.OR + (char) 89 + RegEx.OR + (char) 90 + RegEx.OR + (char) 91 + RegEx.OR + (char) 92 + RegEx.OR + (char) 93 + RegEx.OR + (char) 94 + RegEx.OR + (char) 95 + RegEx.OR + (char) 96 + RegEx.OR + (char) 97 + RegEx.OR + (char) 98 + RegEx.OR + (char) 99 + RegEx.OR + (char) 100 + RegEx.OR + (char) 101 + RegEx.OR + (char) 102 + RegEx.OR + (char) 103 + RegEx.OR + (char) 104 + RegEx.OR + (char) 105 + RegEx.OR + (char) 106 + RegEx.OR + (char) 107 + RegEx.OR + (char) 108 + RegEx.OR + (char) 109 + RegEx.OR + (char) 110 + RegEx.OR + (char) 111 + RegEx.OR + (char) 112 + RegEx.OR + (char) 113 + RegEx.OR + (char) 114 + RegEx.OR + (char) 115 + RegEx.OR + (char) 116 + RegEx.OR + (char) 117 + RegEx.OR + (char) 118 + RegEx.OR + (char) 119 + RegEx.OR + (char) 120 + RegEx.OR + (char) 121 + RegEx.OR + (char) 122 + RegEx.OR + (char) 123 + RegEx.OR + (char) 124 + RegEx.OR + (char) 125 + RegEx.OR + (char) 126 + RegEx.OR + (char) 127 + RegEx.OR + (char) 128 + RegEx.OR + (char) 129 + RegEx.OR + (char) 130 + RegEx.OR + (char) 131 + RegEx.OR + (char) 132 + RegEx.OR + (char) 133 + RegEx.OR + (char) 134 + RegEx.OR + (char) 135 + RegEx.OR + (char) 136 + RegEx.OR + (char) 137 + RegEx.OR + (char) 138 + RegEx.OR + (char) 139 + RegEx.OR + (char) 140 + RegEx.OR + (char) 141 + RegEx.OR + (char) 142 + RegEx.OR + (char) 143 + RegEx.OR + (char) 144 + RegEx.OR + (char) 145 + RegEx.OR + (char) 146 + RegEx.OR + (char) 147 + RegEx.OR + (char) 148 + RegEx.OR + (char) 149 + RegEx.OR + (char) 150 + RegEx.OR + (char) 151 + RegEx.OR + (char) 152 + RegEx.OR + (char) 153 + RegEx.OR + (char) 154 + RegEx.OR + (char) 155 + RegEx.OR + (char) 156 + RegEx.OR + (char) 157 + RegEx.OR + (char) 158 + RegEx.OR + (char) 159 + RegEx.OR + (char) 160 + RegEx.OR + (char) 161 + RegEx.OR + (char) 162 + RegEx.OR + (char) 163 + RegEx.OR + (char) 164 + RegEx.OR + (char) 165 + RegEx.OR + (char) 166 + RegEx.OR + (char) 167 + RegEx.OR + (char) 168 + RegEx.OR + (char) 169 + RegEx.OR + (char) 170 + RegEx.OR + (char) 171 + RegEx.OR + (char) 172 + RegEx.OR + (char) 173 + RegEx.OR + (char) 174 + RegEx.OR + (char) 175 + RegEx.OR + (char) 176 + RegEx.OR + (char) 177 + RegEx.OR + (char) 178 + RegEx.OR + (char) 179 + RegEx.OR + (char) 180 + RegEx.OR + (char) 181 + RegEx.OR + (char) 182 + RegEx.OR + (char) 183 + RegEx.OR + (char) 184 + RegEx.OR + (char) 185 + RegEx.OR + (char) 186 + RegEx.OR + (char) 187 + RegEx.OR + (char) 188 + RegEx.OR + (char) 189 + RegEx.OR + (char) 190 + RegEx.OR + (char) 191 + RegEx.OR + (char) 192 + RegEx.OR + (char) 193 + RegEx.OR + (char) 194 + RegEx.OR + (char) 195 + RegEx.OR + (char) 196 + RegEx.OR + (char) 197 + RegEx.OR + (char) 198 + RegEx.OR + (char) 199 + RegEx.OR + (char) 200 + RegEx.OR + (char) 201 + RegEx.OR + (char) 202 + RegEx.OR + (char) 203 + RegEx.OR + (char) 204 + RegEx.OR + (char) 205 + RegEx.OR + (char) 206 + RegEx.OR + (char) 207 + RegEx.OR + (char) 208 + RegEx.OR + (char) 209 + RegEx.OR + (char) 210 + RegEx.OR + (char) 211 + RegEx.OR + (char) 212 + RegEx.OR + (char) 213 + RegEx.OR + (char) 214 + RegEx.OR + (char) 215 + RegEx.OR + (char) 216 + RegEx.OR + (char) 217 + RegEx.OR + (char) 218 + RegEx.OR + (char) 219 + RegEx.OR + (char) 220 + RegEx.OR + (char) 221 + RegEx.OR + (char) 222 + RegEx.OR + (char) 223 + RegEx.OR + (char) 224 + RegEx.OR + (char) 225 + RegEx.OR + (char) 226 + RegEx.OR + (char) 227 + RegEx.OR + (char) 228 + RegEx.OR + (char) 229 + RegEx.OR + (char) 230 + RegEx.OR + (char) 231 + RegEx.OR + (char) 232 + RegEx.OR + (char) 233 + RegEx.OR + (char) 234 + RegEx.OR + (char) 235 + RegEx.OR + (char) 236 + RegEx.OR + (char) 237 + RegEx.OR + (char) 238 + RegEx.OR + (char) 239 + RegEx.OR + (char) 240 + RegEx.OR + (char) 241 + RegEx.OR + (char) 242 + RegEx.OR + (char) 243 + RegEx.OR + (char) 244 + RegEx.OR + (char) 245 + RegEx.OR + (char) 246 + RegEx.OR + (char) 247 + RegEx.OR + (char) 248 + RegEx.OR + (char) 249 + RegEx.OR + (char) 250 + RegEx.OR + (char) 251 + RegEx.OR + (char) 252 + RegEx.OR + (char) 253 + RegEx.OR + (char) 254 + RegEx.OR + (char) 255  + RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE + '\n'; 
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Cadena de los Tokens contenidos en el  */
	private Token[] tokens;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	public CocolReader( String path ) throws Exception{
		
		// Prepara el Lexer
		Lexer lexer = new Lexer();
		lexer.addKeyword("COMPILER", "COMPILER");
		lexer.addKeyword("END", "END");
		lexer.addKeyword("CHARACTERS", "CHARACTERS");
		lexer.addKeyword("KEYWORDS", "KEYWORDS");
		lexer.addKeyword("TOKENS", "TOKENS");
		lexer.addKeyword("EXCEPT KEYWORDS",	"EXCEPT KEYWORDS");
		lexer.addKeyword("IGNORE", "IGNORE");
		lexer.addKeyword("PRODUCTIONS", "PRODUCTIONS");
		lexer.addKeyword("ANY", "ANY");
		lexer.addKeyword("ABRE_PARÉNTESIS","(");
		lexer.addKeyword("CIERRA_PARÉNTESIS",")");
		lexer.addKeyword("ABRE_KLEENE", "{");
		lexer.addKeyword("CIERRA_KLEENE", "}");
		lexer.addKeyword("ABRE_OPC", "[");
		lexer.addKeyword("CIERRA_OPC", "]");
		lexer.addKeyword("OR", "|");
		lexer.addKeyword("IGUAL", "=");
		lexer.addKeyword("PUNTO", ".");
		lexer.addKeyword("UNTIL", "..");
		lexer.addKeyword("MAS", "+");
		lexer.addKeyword("MENOS", "-");
		lexer.addToken("ident", IDENT);
		lexer.addToken("string",  STRING);
		lexer.addToken("char", CHAR);
		lexer.addToken("chr", CHR);
		//lexer.addToken("comment", COMMENT);
		lexer.setWhitespace(" " + RegEx.OR + "\n" + RegEx.OR + "\t" + RegEx.OR + RegEx.OPEN_PARENTHESIS + COMMENT + RegEx.CLOSE_PARENTHESIS);
		
		// Lee el archivo y lo asigna al Lexer
		FileReader reader = new FileReader( path );
		int charActual = reader.read();
		String cadena = "";
		while( charActual != -1 ){
			cadena += (char)charActual;
			charActual = reader.read();
		}
		lexer.asignarCadena(cadena);
		
		tokens = lexer.tokenize();
		//for( Token t : tokens ) Test.print(t);
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	void verificarInicio()throws CocolException{
		if( !tokens[0].esTipo("COMPILER") ) throw new CocolException("Un archivo de Cocol debe empezar por la palabra \"COMPILER\" y un identificador para el compilador");
		if( !tokens[tokens.length-3].esTipo("END") || !tokens[tokens.length-1].esTipo("PUNTO") ) throw new CocolException("El fin del archivo debe ser \"END\" + nombre_del_compilador + \".\"");
		if( !tokens[1].equals(tokens[tokens.length-2]) ) throw new CocolException("El nombre del compilador no es consistente.");
	}
	
	void leerCharacters(){
		
		ArrayList<Token[]> chardefs = obtenerCharDefs();
		//*
		for( Token[] tarray : chardefs ){
			Test.print("------------------CHR----------------------");
			for( Token t : tarray )
				Test.print(t);
			Test.print("-------------------------------------------");
			Test.print();
		}//*/
		ArrayList<Token[]> kwdefs = obtenerKeywordDefs();
		//*
		for( Token[] tarray : kwdefs ){
			Test.print("------------------KWD----------------------");
			for( Token t : tarray )
				Test.print(t);
			Test.print("-------------------------------------------");
			Test.print();
		}//*/
		
		ArrayList<Token[]> tokendefs = obtenerTokenDefs();
		//*
		for( Token[] tarray : tokendefs ){
			Test.print("------------------TKN----------------------");
			for( Token t : tarray )
				Test.print(t);
			Test.print("-------------------------------------------");
			Test.print();
		}//*/
	}
	
	ArrayList<Token[]> obtenerCharDefs(){
		
		// Obtiene el índice del cual inician las declaraciones de caracteres
		// Si no hay, retorna null.
		int i;
		for( i = 0; i < tokens.length; i++ ){
			Token token = tokens[i];
			if( token.esTipo("CHARACTERS") )
				break;
			if( i == tokens.length-1 && !token.esTipo("CHARACTERS") )
				return new ArrayList<Token[]>(0);
		}
		
		/*
		 * Empieza a obtener las diferentes declaraciones de CHARACTERS.
		 * Cada declaración termina cuando hay lo sigue un ident seguido de '='.
		 * Todas las declaraciones terminan con el fin del archivo o con
		 * TOKENS, IGNORE, PRODUCTIONS o END.
		 */
		ArrayList<Token[]> preArray = new ArrayList<Token[]>();
		ArrayList<Token> chardef = new ArrayList<Token>();
		for( i = i+1; i < tokens.length; i++ ){
			Token token = tokens[i];
			Token next = tokens[i+1];
			// Si ya se acabaron las definiciones de CHARACTER, se sale del ciclo
			if( token.esTipo("KEYWORDS") || token.esTipo("TOKENS") || token.esTipo("IGNORE") || token.esTipo("PRODUCTIONS") || token.esTipo("END") ){
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				preArray.remove(0);
				return preArray;
			}
			// Si es un ident seguido de igual, se empieza una nueva lista de Tokens
			if( token.esTipo("ident") && next.esTipo("IGUAL") ){
				// Se almacena la lista que se llevaba
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				chardef.clear();
				// Se inicia la lista de nuevo
			}
			chardef.add(token);
		}
		return null; // Sanity check
		
	}
	
	ArrayList<Token[]> obtenerKeywordDefs(){
		
		// Obtiene el índice del cual inician las declaraciones de caracteres
		// Si no hay, retorna null.
		int i;
		for( i = 0; i < tokens.length; i++ ){
			Token token = tokens[i];
			if( token.esTipo("KEYWORDS") )
				break;
			if( i == tokens.length-1 && !token.esTipo("KEYWORDS") )
				return new ArrayList<Token[]>(0);
		}
		
		/*
		 * Empieza a obtener las diferentes declaraciones de CHARACTERS.
		 * Cada declaración termina cuando hay lo sigue un ident seguido de '='.
		 * Todas las declaraciones terminan con el fin del archivo o con
		 * TOKENS, IGNORE, PRODUCTIONS o END.
		 */
		ArrayList<Token[]> preArray = new ArrayList<Token[]>();
		ArrayList<Token> chardef = new ArrayList<Token>();
		for( i = i+1; i < tokens.length; i++ ){
			Token token = tokens[i];
			Token next = tokens[i+1];
			// Si ya se acabaron las definiciones de CHARACTER, se sale del ciclo
			if( token.esTipo("TOKENS") || token.esTipo("IGNORE") || token.esTipo("PRODUCTIONS") || token.esTipo("END") ){
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				preArray.remove(0);
				return preArray;
			}
			// Si es un ident seguido de igual, se empieza una nueva lista de Tokens
			if( token.esTipo("ident") && next.esTipo("IGUAL") ){
				// Se almacena la lista que se llevaba
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				chardef.clear();
				// Se inicia la lista de nuevo
			}
			chardef.add(token);
		}
		return null; // Sanity check

	}
	
	ArrayList<Token[]> obtenerTokenDefs(){
		
		// Obtiene el índice del cual inician las declaraciones de caracteres
		// Si no hay, retorna null.
		int i;
		for( i = 0; i < tokens.length; i++ ){
			Token token = tokens[i];
			if( token.esTipo("TOKENS") )
				break;
			if( i == tokens.length-1 && !token.esTipo("TOKENS") )
				return new ArrayList<Token[]>(0);
		}
		
		/*
		 * Empieza a obtener las diferentes declaraciones de CHARACTERS.
		 * Cada declaración termina cuando hay lo sigue un ident seguido de '='.
		 * Todas las declaraciones terminan con el fin del archivo o con
		 * TOKENS, IGNORE, PRODUCTIONS o END.
		 */
		ArrayList<Token[]> preArray = new ArrayList<Token[]>();
		ArrayList<Token> chardef = new ArrayList<Token>();
		for( i = i+1; i < tokens.length; i++ ){
			Token token = tokens[i];
			Token next = tokens[i+1];
			// Si ya se acabaron las definiciones de CHARACTER, se sale del ciclo
			if( token.esTipo("IGNORE") || token.esTipo("PRODUCTIONS") || token.esTipo("END") ){
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				preArray.remove(0);
				return preArray;
			}
			// Si es un ident seguido de igual, se empieza una nueva lista de Tokens
			if( token.esTipo("ident") && next.esTipo("IGUAL") ){
				// Se almacena la lista que se llevaba
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				chardef.clear();
				// Se inicia la lista de nuevo
			}
			chardef.add(token);
		}
		return null; // Sanity check
		
	}
	
	/**
	 * Excepción que se dispara si hay un error en la sintaxis de Cocol 
	 * @author ale
	 */
	class CocolException extends Exception{
		public CocolException(String mensaje){
			super(mensaje);
		}
	}
	
}
