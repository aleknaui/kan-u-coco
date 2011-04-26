import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Esta clase interpreta el lenguaje Cocol para
 * generar un analizador sintáctico.
 * Genera el proyecto que contiene el código.
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
	
	/** Esta constante no es para la definición de Tokens, sino para que el reader no tenga que generar código con cada aparición de ANY */
	private final static String ANY = "" + (char) 0 + (char) 1 + (char) 2 + (char) 3 + (char) 4 + (char) 5 + (char) 6 + (char) 7 + (char) 8 + (char) 9 + (char) 10 + (char) 11 + (char) 12 + (char) 13 + (char) 14 + (char) 15 + (char) 16 + (char) 17 + (char) 18 + (char) 19 + (char) 20 + (char) 21 + (char) 22 + (char) 23 + (char) 24 + (char) 25 + (char) 26 + (char) 27 + (char) 28 + (char) 29 + (char) 30 + (char) 31 + (char) 32 + (char) 33 + (char) 34 + (char) 35 + (char) 36 + (char) 37 + (char) 38 + (char) 39 + (char) 40 + (char) 41 + (char) 42 + (char) 43 + (char) 44 + (char) 45 + (char) 46 + (char) 47 + (char) 48 + (char) 49 + (char) 50 + (char) 51 + (char) 52 + (char) 53 + (char) 54 + (char) 55 + (char) 56 + (char) 57 + (char) 58 + (char) 59 + (char) 60 + (char) 61 + (char) 62 + (char) 63 + (char) 64 + (char) 65 + (char) 66 + (char) 67 + (char) 68 + (char) 69 + (char) 70 + (char) 71 + (char) 72 + (char) 73 + (char) 74 + (char) 75 + (char) 76 + (char) 77 + (char) 78 + (char) 79 + (char) 80 + (char) 81 + (char) 82 + (char) 83 + (char) 84 + (char) 85 + (char) 86 + (char) 87 + (char) 88 + (char) 89 + (char) 90 + (char) 91 + (char) 92 + (char) 93 + (char) 94 + (char) 95 + (char) 96 + (char) 97 + (char) 98 + (char) 99 + (char) 100 + (char) 101 + (char) 102 + (char) 103 + (char) 104 + (char) 105 + (char) 106 + (char) 107 + (char) 108 + (char) 109 + (char) 110 + (char) 111 + (char) 112 + (char) 113 + (char) 114 + (char) 115 + (char) 116 + (char) 117 + (char) 118 + (char) 119 + (char) 120 + (char) 121 + (char) 122 + (char) 123 + (char) 124 + (char) 125 + (char) 126 + (char) 127 + (char) 128 + (char) 129 + (char) 130 + (char) 131 + (char) 132 + (char) 133 + (char) 134 + (char) 135 + (char) 136 + (char) 137 + (char) 138 + (char) 139 + (char) 140 + (char) 141 + (char) 142 + (char) 143 + (char) 144 + (char) 145 + (char) 146 + (char) 147 + (char) 148 + (char) 149 + (char) 150 + (char) 151 + (char) 152 + (char) 153 + (char) 154 + (char) 155 + (char) 156 + (char) 157 + (char) 158 + (char) 159 + (char) 160 + (char) 161 + (char) 162 + (char) 163 + (char) 164 + (char) 165 + (char) 166 + (char) 167 + (char) 168 + (char) 169 + (char) 170 + (char) 171 + (char) 172 + (char) 173 + (char) 174 + (char) 175 + (char) 176 + (char) 177 + (char) 178 + (char) 179 + (char) 180 + (char) 181 + (char) 182 + (char) 183 + (char) 184 + (char) 185 + (char) 186 + (char) 187 + (char) 188 + (char) 189 + (char) 190 + (char) 191 + (char) 192 + (char) 193 + (char) 194 + (char) 195 + (char) 196 + (char) 197 + (char) 198 + (char) 199 + (char) 200 + (char) 201 + (char) 202 + (char) 203 + (char) 204 + (char) 205 + (char) 206 + (char) 207 + (char) 208 + (char) 209 + (char) 210 + (char) 211 + (char) 212 + (char) 213 + (char) 214 + (char) 215 + (char) 216 + (char) 217 + (char) 218 + (char) 219 + (char) 220 + (char) 221 + (char) 222 + (char) 223 + (char) 224 + (char) 225 + (char) 226 + (char) 227 + (char) 228 + (char) 229 + (char) 230 + (char) 231 + (char) 232 + (char) 233 + (char) 234 + (char) 235 + (char) 236 + (char) 237 + (char) 238 + (char) 239 + (char) 240 + (char) 241 + (char) 242 + (char) 243 + (char) 244 + (char) 245 + (char) 246 + (char) 247 + (char) 248 + (char) 249 + (char) 250 + (char) 251 + (char) 252 + (char) 253 + (char) 254 + (char) 255;
	
	// --------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------
	
	/** Cadena de los Tokens contenidos en el archivo */
	private Token[] tokens;
	/** Tabla de referencia para las diferentes expresiones regulares de las definiciones de characters */
	private HashMap<String, String> characters;
	/** Expresiones regulares de los diferentes tokens que no contienen EXCEPT KEYWORDS*/
	private HashMap<String, String> tokenNoExcept;
	/** Expresiones regulares de los diferentes tokens que contienen EXCEPT KEYWORDS */
	private HashMap<String, String> tokenExcept;
	/** Lista de Keywords */
	private HashMap<String, String> keywords;
	/** Lista de elementos que causan Whitespace */
	private ArrayList<String> whitespace;
	
	// --------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------
	
	/**
	 * Lee un archivo ATG, lo interpreta
	 * y genera un proyecto de eclipse con
	 * las especificaciones del mismo.
	 */
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
		lexer.addKeyword("ABRE_PARENTESIS","(");
		lexer.addKeyword("CIERRA_PARENTESIS",")");
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
		//lexer.addWhitespace(" " + RegEx.OR + "\n" + RegEx.OR + "\t" + RegEx.OR + RegEx.OPEN_PARENTHESIS + COMMENT + RegEx.CLOSE_PARENTHESIS);
		lexer.addWhitespace(" ");
		lexer.addWhitespace("\n");
		lexer.addWhitespace("\t");
		lexer.addWhitespace("\r");
		lexer.addWhitespace(COMMENT);
		// Lee el archivo y lo analiza
		FileReader reader = new FileReader( path );
		int charActual = reader.read();
		String cadena = "";
		while( charActual != -1 ){
			cadena += (char)charActual;
			charActual = reader.read();
		}
		lexer.asignarCadena(cadena);
		
		tokens = lexer.tokenize();
		
		// Inicializa los atributos
		characters = new HashMap<String, String>();
		tokenNoExcept = new HashMap<String, String>();
		tokenExcept = new HashMap<String, String>();
		keywords = new HashMap<String, String>();
		whitespace = new ArrayList<String>();
		
		characters.put( "ANY" , ANY );
		
		// Interpreta el archivo y genera el código.
		verificarInicio();
		generarCharacters();
		generarKeywords();
		generarTokens();
		generarWhitespace();
		generarProyecto();
		generarCodigo();
	}
	
	// --------------------------------------------------------------------------------
	// Métodos
	// --------------------------------------------------------------------------------
	
	/**
	 * Genera el archivo .java que realizará el análisis léxico
	 */
	private void generarCodigo(){
		String nombreProy = tokens[1].darLexema();
		String path = ".." + File.separator + nombreProy + File.separator + "src" + File.separator  + nombreProy + "Lexer.java";
		File archivo = new File( path );
		
		try {
			PrintWriter writer = new PrintWriter( archivo );
			
			writer.println("import java.io.*;");
			writer.println("import javax.swing.*;");
			writer.println();
			writer.println("public class " + nombreProy + "Lexer extends JFrame{");
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Constantes de las Keywords");
			writer.println("\t// --------------------------------------------------------------------------------");
				Iterator it = keywords.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tprivate final static String regex_" + e.getKey() + " = \"\" + " + e.getValue() + ";");
				}
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Constantes de los Tokens");
			writer.println("\t// --------------------------------------------------------------------------------");
				it = tokenNoExcept.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tprivate final static String regex_" + e.getKey() + " = \"\" + " + e.getValue() + ";");
				}
				it = tokenExcept.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tprivate final static String regex_" + e.getKey() + " = \"\" + " + e.getValue() + ";");
				}
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Constantes de Whitespace");
			writer.println("\t// --------------------------------------------------------------------------------");
				for( int i = 0; i < whitespace.size(); i++ ){
					writer.println("\tprivate final static String whitespace_" + i + " = \"\" + " + whitespace.get(i) + ";");
				}
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Atributos");
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\tprivate Token[] tokens;");
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Constructor");
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println();
			writer.println("\tpublic " + nombreProy + "Lexer(  ) throws Exception{");
			writer.println();
			writer.println("\t//Creación y preparación del Lexer");
			writer.println("\tLexer lexer = new Lexer();");
				it = tokenNoExcept.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tlexer.addToken(\"" + e.getKey() + "\", " + "regex_" + e.getKey() + ");");
				}
				it = keywords.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tlexer.addKeyword(\"" + e.getKey() + "\", " + "regex_" + e.getKey() + ");");
				}
				it = tokenExcept.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
					writer.println("\tlexer.addToken(\"" + e.getKey() + "\", " + "regex_" + e.getKey() + ");");
				}
				for( int i = 0; i < whitespace.size(); i++ ){
					writer.println("\tlexer.addWhitespace(" + "whitespace_" + i + ");");
				}
			writer.println();
			writer.println("\tsetVisible(false);");
			writer.println("\tString path = \"\";");
			writer.println("\tlong tiempoInicial = 0;");
			writer.println("\tsetDefaultCloseOperation(DISPOSE_ON_CLOSE);");
			writer.println("\tTest.print(\"Seleccione el archivo que desea analizar\");");
			writer.println("\tJFileChooser chooser = new JFileChooser();");
			writer.println("\tchooser.setCurrentDirectory(new File(\".\"));");
			writer.println("\tchooser.setDialogTitle(\" " + nombreProy + " - Analizador Léxico \");");
			writer.println("\tchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);");
			writer.println("\tchooser.setMultiSelectionEnabled(false);");
			writer.println("\tif (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){");
			writer.println("\t\tTest.print(\"Se ha seleccionado el archivo \" + chooser.getSelectedFile().getAbsolutePath());");
			writer.println("\t\tTest.print(\"Leyendo archivo...\");");
			writer.println("\t\tpath = chooser.getSelectedFile().getAbsolutePath();");
			writer.println("\t\ttiempoInicial = System.currentTimeMillis();");
			writer.println("\t} else{ Test.print(\"Usted no seleccionó ningún archivo. Adiós.\"); dispose(); return;}");
			writer.println();
			writer.println("\tFileReader reader = new FileReader( path );");
			writer.println("\tint charActual = reader.read();");
			writer.println("\tString cadena = \"\";");
			writer.println("\twhile( charActual > 0 ){");
			writer.println("\t\tcadena += (char) charActual;");
			writer.println("\t\tcharActual = reader.read();");
			writer.println("\t}");
			writer.println("\tlexer.asignarCadena(cadena);");
			writer.println();
			writer.println("\tTest.print(\"Analizando...\");");
			writer.println("\ttokens = lexer.tokenize();");
			writer.println("\tTest.print();");
			writer.println("\tTest.print(\"Tokens Obtenidos: \");");
			writer.println("\tfor( Token t : tokens ) Test.print('\\t' + t.toString());");
			writer.println("\tTest.print(\"Tiempo de ejecución: \" + (System.currentTimeMillis() - tiempoInicial) + \" ms\");");
			writer.println("\tdispose();");
			writer.println("\treturn;");
			writer.println();
			writer.println("\t}");
			writer.println();
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println("\t// Método Main");
			writer.println("\t// --------------------------------------------------------------------------------");
			writer.println();
			writer.println("\tpublic static void main(String[] args){");
			writer.println();
			writer.println("\ttry{");
			writer.println("\t\tnew " + nombreProy + "Lexer();");
			writer.println("\t} catch (Exception e){");
			writer.println("\t\tTest.print(e.getMessage());");
			writer.println("\t\tSystem.exit(ERROR);");
			writer.println("\t}");
			writer.println();
			writer.println("\t}");
			writer.println();
			writer.println("}");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Genera la estructura de directorios de el proyecto de Eclipse
	 * que contendrá el código que se generará.
	 */
	private void generarProyecto(){
		
		// Crea la estructura básica del proyecto
		String nombreProy = tokens[1].darLexema();
		String path = ".." + File.separator + nombreProy;
		File directorio = new File(path);
		directorio.mkdir();
		directorio = new File( path + File.separator + "bin" );
		directorio.mkdir();
		directorio = new File( path + File.separator + "src" );
		directorio.mkdir();
		
		// Agrega el archivo classpath
		File classpath = new File( path + File.separator + ".classpath" );
		try {
			// 
			FileReader reader = new FileReader( "./classpath" );
			int charActual = reader.read();
			String cadena = "";
			while( charActual != -1 ){
				cadena += (char)charActual;
				charActual = reader.read();
			}
			FileWriter writer = new FileWriter( classpath );
			writer.write(cadena);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Agrega el archivo project
		File project = new File( path + File.separator + ".project" );
		try {
			// 
			FileReader reader = new FileReader( "./project" );
			int charActual = reader.read();
			String cadena = "";
			while( charActual != -1 ){
				cadena += (char)charActual;
				charActual = reader.read();
			}
			FileWriter writer = new FileWriter( project );
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					"<projectDescription>\n" +
					"\t<name>" + nombreProy + "</name>\n");
			writer.write(cadena);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifica que la estructura super básica del ATG sea correcta.
	 */
	private void verificarInicio()throws CocolException{
		if( !tokens[0].esTipo("COMPILER") ) throw new CocolException("Un archivo de Cocol debe empezar por la palabra \"COMPILER\" y un identificador para el compilador");
		if( !tokens[tokens.length-3].esTipo("END") || !tokens[tokens.length-1].esTipo("PUNTO") ) throw new CocolException("El fin del archivo debe ser \"END\" + nombre_del_compilador + \".\"");
		if( !tokens[1].equals(tokens[tokens.length-2]) ) throw new CocolException("El nombre del compilador no es consistente.");
	}
	
	/**
	 * Genera las diferentes definiciones de characters (si las hay)
	 * @throws CocolException Errores de sintaxis
	 */
	private void generarCharacters() throws CocolException{
		ArrayList<Token[]> definiciones = obtenerCharDefs();
		
		for( Token[] definicion : definiciones ){
			
			// Verifica que la definicion sea del tipo 'ident = *'
			if( ! (definicion[0].esTipo("ident") && definicion[1].esTipo("IGUAL"))  )
				throw new CocolException("Declaración incorrecta de una definición de Character");
			String nombre = definicion[0].darLexema();
			String chars = "";
			for( int i = 2; i < definicion.length; i++ ){
				Token token = definicion[i];
				// Verifica si es una cadena y actua correspondientemente
				if( token.esTipo("string") ){
					chars += token.darLexema().replaceAll("\"", "");
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
				}
				// Verifica si es una referencia a otra cadena ya creada o la cadena ANY
				else if( token.esTipo("ident") || token.esTipo("ANY") ){
					if( characters.get(token.darLexema()) == null )
						throw new CocolException("La definición de Character \"" + token.darLexema() + "\" no existe" );
					chars += characters.get(token.darLexema());
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
				}
				// Verifica si es un caracter, ya sea '*' o CHR(#)
				else if( token.esTipo("char") || token.esTipo("chr") ){
					int ord1 = getCharValue( token );
					// Verifica si es UNTIL
					if( i < definicion.length-2 ){
						if( definicion[i+1].esTipo("UNTIL") ){
							if( !( definicion[i+2].esTipo("char") || definicion[i+2].esTipo("chr") ) )
								throw new CocolException("Definición incorrecta de un intervalo de caracteres.");
							int ord2 = getCharValue( definicion[i+2] );
							for( int ord = ord1; ord <= ord2; ord++ ){
								chars += (char) ord;
							}
							i = i+2;
						} else{
							chars += (char) ord1;
						}
					} else chars += (char) ord1;
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
						
				}
				else if( token.esTipo( "MAS" ) || token.esTipo( "MENOS" ) ){
					// Obtiene el operador y operando bajo el cual se trabaja
					char operador = token.darLexema().charAt(0);
					String operando = "";
					// Avanza al siguiente BasicSet
					i++;
					token = definicion[i];
					
					// Verifica si es una cadena y actua correspondientemente
					if( token.esTipo("string") ){
						operando += token.darLexema().replaceAll("\"", "");
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					// Verifica si es una referencia a otra cadena ya creada o la cadena ANY
					else if( token.esTipo("ident") || token.esTipo("ANY") ){
						if( characters.get(token.darLexema()) == null )
							throw new CocolException("La definición de Character \"" + token.darLexema() + "\" no existe" );
						operando += characters.get(token.darLexema());
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					// Verifica si es un caracter, ya sea '*' o CHR(#)
					else if( token.esTipo("char") || token.esTipo("chr") ){
						int ord1 = getCharValue( token );
						// Verifica si es UNTIL
						if( i < definicion.length-2 ){
							if( definicion[i+1].esTipo("UNTIL") ){
								if( !( definicion[i+2].esTipo("char") || definicion[i+2].esTipo("chr") ) )
									throw new CocolException("Definición incorrecta de un intervalo de caracteres.");
								int ord2 = getCharValue( definicion[i+2] );
								for( int ord = ord1; ord <= ord2; ord++ ){
									operando += (char) ord;
								}
								i = i+2;
							} else{
								operando += (char) ord1;
							}
						} else operando += (char) ord1;
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					else
						throw new CocolException("Token inválido: " + token.toString());
					switch( operador ){
					case '+':
						for( int c = 0; c < operando.length(); c++ )
							if( ! chars.contains(""+operando.charAt(c)) )
								chars += operando.charAt(c);
						break;
					case '-':
						for( int c = 0; c < operando.length(); c++ ){
							chars = chars.replace(operando.charAt(c), (char)-1);
							chars = chars.replace("" + (char)-1, "");
						}
						break;
					}
				}
				else
					throw new CocolException("Token inválido: " + token.toString());
			}
			characters.put(nombre, chars);
		}
	}
	
	/**
	 * Genera las diferentes expresiones regulares que reconocen los keywords.
	 * @throws CocolException Errores de sintaxis.
	 */
	private void generarKeywords() throws CocolException{
		ArrayList<Token[]> definiciones = obtenerKeywordDefs();
		
		for( Token[] definicion : definiciones ){
			
			for( int i = 0; i < definicion.length; i++ ){
				// La estructura de estas definiciones es estática, así que
				// se chequea que concuerden y si concuerdan, se almacena.
				if( ! ( definicion.length == 4 && definicion[0].esTipo("ident") &&
						definicion[1].esTipo("IGUAL") && definicion[2].esTipo("string") &&
						definicion[3].esTipo("PUNTO")) )
					throw new CocolException("Declaración incorrecta de una Keyword.");
				keywords.put(definicion[0].darLexema(), this.generarStringRegEx(definicion[2].darLexema()));
			}
			
		}
	}
	
	/**
	 * Genera las diferentes expresiones regulares que reconocen los tokens.
	 * @throws CocolException Errores de sintaxis.
	 */
	private void generarTokens() throws CocolException{
		ArrayList<Token[]> definiciones = obtenerTokenDefs();
		
		for( Token[] definicion : definiciones ){
			
			verificarParentesis( definicion );
			
			// Verifica que la definicion sea del tipo 'ident = *'
			if( ! (definicion[0].esTipo("ident") && definicion[1].esTipo("IGUAL"))  )
				throw new CocolException("Declaración incorrecta de una definición de Token");
			if( ! definicion[definicion.length-1].esTipo("PUNTO") )
				throw new CocolException("Declaración incorrecta del final de una definición de Token");
			
			String nombre = definicion[0].darLexema();
			String regex = "RegEx.OPEN_PARENTHESIS + ";
			
			// Itera el cuerpo de la definición, sin contar el punto.
			for( int i = 2; i < definicion.length-1; i++ ){
				Token token = definicion[i];
				
				if( token.esTipo("ident") ){
					if( characters.get(token.darLexema()) == null )
						throw new CocolException("La definición de Character \"" + token.darLexema() + "\" no existe" );
					regex += generarCharRegEx(characters.get(token.darLexema()));
					//Test.print(regex.length());
				}
				else if( token.esTipo("string")){
					regex += generarStringRegEx(token.darLexema());
				}
				else if( token.esTipo("char") ){
					regex += "RegEx.OPEN_PARENTHESIS + (char) " + getCharValue(token) + " + RegEx.CLOSE_PARENTHESIS";
				}
				else if( token.esTipo("ABRE_PARENTESIS") || token.esTipo("ABRE_KLEENE") || token.esTipo("ABRE_OPC")){
					regex += "RegEx.OPEN_PARENTHESIS";
				}
				else if( token.esTipo("CIERRA_PARENTESIS") ){
					regex += "RegEx.CLOSE_PARENTHESIS";
				}
				else if( token.esTipo("CIERRA_KLEENE") ){
					regex += "RegEx.CLOSE_PARENTHESIS + RegEx.KLEENE";
				}
				else if( token.esTipo("CIERRA_OPC") ){
					regex += "RegEx.CLOSE_PARENTHESIS + RegEx.PREGUNTA";
				}
				else if( token.esTipo("OR") ){
					Token next = definicion[i+1]; 
					if( ! (next.esTipo("string") || next.esTipo("char") || next.esTipo("ident") || next.darTipo().startsWith("ABRE_") || next.darTipo().startsWith("CIERRA_")) )
						throw new CocolException("Un OR statement debe ser seguido por un término");
					regex += "RegEx.OR";
				} else if( token.esTipo("EXCEPT KEYWORDS") ) break;
				else throw new CocolException("Token inválido para una definición de Token: " + token);
				
				if( ( i == definicion.length-3 && definicion[definicion.length-2].esTipo("EXCEPT KEYWORDS")) || ( i == definicion.length-2 ) ) regex += " + RegEx.CLOSE_PARENTHESIS";
				else regex += " + ";
			}
			
			if( definicion[definicion.length-2].esTipo("EXCEPT KEYWORDS") )
				tokenExcept.put(nombre, regex);
			else
				tokenNoExcept.put(nombre, regex);
			
		}
	}
	
	/**
	 * Genera las diferentes expresiones regulares que reconocen los whitespace.
	 * @throws CocolException Errores de sintaxis.
	 */
	private void generarWhitespace() throws CocolException{
		ArrayList<Token[]> definiciones = obtenerWhitespaceDefs();
		
		for( Token[] definicion : definiciones ){
			//Test.print(definicion[0]);
			// Verifica que la definicion sea del tipo 'ident = *'
			//if( ! (definicion[0].esTipo("IGNORE") ) )
			//	throw new CocolException("Declaración incorrecta de una definición de Whitespace");
			String chars = "";
			for( int i = 0; i < definicion.length; i++ ){
				Token token = definicion[i];
				// Verifica si es una cadena y actua correspondientemente
				if( token.esTipo("string") ){
					chars += token.darLexema().replaceAll("\"", "");
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
				}
				// Verifica si es una referencia a otra cadena ya creada o la cadena ANY
				else if( token.esTipo("ident") || token.esTipo("ANY") ){
					if( characters.get(token.darLexema()) == null )
						throw new CocolException("La definición de Character \"" + token.darLexema() + "\" no existe" );
					chars += characters.get(token.darLexema());
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
				}
				// Verifica si es un caracter, ya sea '*' o CHR(#)
				else if( token.esTipo("char") || token.esTipo("chr") ){
					int ord1 = getCharValue( token );
					// Verifica si es UNTIL
					if( i < definicion.length-2 ){
						if( definicion[i+1].esTipo("UNTIL") ){
							if( !( definicion[i+2].esTipo("char") || definicion[i+2].esTipo("chr") ) )
								throw new CocolException("Definición incorrecta de un intervalo de caracteres.");
							int ord2 = getCharValue( definicion[i+2] );
							for( int ord = ord1; ord <= ord2; ord++ ){
								chars += (char) ord;
							}
							i = i+2;
						} else{
							chars += (char) ord1;
						}
					} else chars += (char) ord1;
					if( i != definicion.length-1 )
						if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
							throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
						
				}
				else if( token.esTipo( "MAS" ) || token.esTipo( "MENOS" ) ){
					// Obtiene el operador y operando bajo el cual se trabaja
					char operador = token.darLexema().charAt(0);
					String operando = "";
					// Avanza al siguiente BasicSet
					i++;
					token = definicion[i];
					
					// Verifica si es una cadena y actua correspondientemente
					if( token.esTipo("string") ){
						operando += token.darLexema().replaceAll("\"", "");
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					// Verifica si es una referencia a otra cadena ya creada o la cadena ANY
					else if( token.esTipo("ident") || token.esTipo("ANY") ){
						if( characters.get(token.darLexema()) == null )
							throw new CocolException("La definición de Character \"" + token.darLexema() + "\" no existe" );
						operando += characters.get(token.darLexema());
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					// Verifica si es un caracter, ya sea '*' o CHR(#)
					else if( token.esTipo("char") || token.esTipo("chr") ){
						int ord1 = getCharValue( token );
						// Verifica si es UNTIL
						if( i < definicion.length-2 ){
							if( definicion[i+1].esTipo("UNTIL") ){
								if( !( definicion[i+2].esTipo("char") || definicion[i+2].esTipo("chr") ) )
									throw new CocolException("Definición incorrecta de un intervalo de caracteres.");
								int ord2 = getCharValue( definicion[i+2] );
								for( int ord = ord1; ord <= ord2; ord++ ){
									operando += (char) ord;
								}
								i = i+2;
							} else{
								operando += (char) ord1;
							}
						} else operando += (char) ord1;
						if( i != definicion.length-1 )
							if( ! (definicion[i+1].esTipo("MAS") || definicion[i+1].esTipo("MENOS")) )
								throw new CocolException("Debe concatenar los tokens " + token + " y " + definicion[i+1] +" ya sea con '+' o '-'.");
					}
					else
						throw new CocolException("Token inválido: " + token.toString());
					switch( operador ){
					case '+':
						for( int c = 0; c < operando.length(); c++ )
							if( ! chars.contains(""+operando.charAt(c)) )
								chars += operando.charAt(c);
						break;
					case '-':
						for( int c = 0; c < operando.length(); c++ ){
							chars = chars.replace(operando.charAt(c), (char)-1);
							chars = chars.replace("" + (char)-1, "");
						}
						break;
					}
				}
				else
					throw new CocolException("Token inválido: " + token.toString());
			}
			whitespace.add( generarCharRegEx( chars ) );
			//Test.print(generarCharRegEx( chars ));
		}
	}
	
	/**
	 * Verifica que los paréntesis, corchetes y llaves estén balanceados en una cadena de Tokens.
	 * @param expresion La cadena de tokens a analizar
	 * @throws CocolException Si hay algún paréntesis, corchete o llave desbalanceado.
	 */
	private void verificarParentesis( Token[] expresion ) throws CocolException{
		String exp = "";
		int parentesis = 0;
		int kleene = 0;
		int opc = 0;
		for( Token t : expresion ){
			exp += t.darLexema() + " ";
			
			if( t.esTipo("ABRE_PARENTESIS") )
				parentesis++;
			else if( t.esTipo("ABRE_KLEENE") )
				kleene++;
			else if( t.esTipo("ABRE_OPC") )
				opc++;
			else if( t.esTipo("CIERRA_PARENTESIS") )
				parentesis--;
			else if( t.esTipo("CIERRA_KLEENE") )
				kleene--;
			else if( t.esTipo("CIERRA_OPC") )
				opc--;
		}
		
		if(parentesis < 0)
			throw new CocolException("Hay " + (parentesis*-1) + " paréntesis que no han sido abiertos en la expresión \"" + exp + "\"");
		else if( parentesis > 0 )
			throw new CocolException("Hay " + parentesis + " paréntesis que no han sido cerrados en la expresión \"" + exp + "\"");
		
		if( kleene < 0 )
			throw new CocolException("Hay " + (kleene*-1) + " llaves que no han sido abiertas en la expresión \"" + exp + "\"");
		else if( kleene > 0 )
			throw new CocolException("Hay " + kleene + " llaves que no han sido cerradas en la expresión \"" + exp + "\"");
		
		if( opc < 0 )
			throw new CocolException("Hay " + (opc*-1) + " corchetes que no han sido abiertos en la expresión \"" + exp + "\"");
		else if( opc > 0 )
			throw new CocolException("Hay " + opc + " corchetes que no han sido cerrados en la expresión \"" + exp + "\"");
	}
	
	/**
	 * Obtiene el valor entero ASCII del caracter que contiene el token.
	 * @param t El token a analizar
	 * @return El valor entero ASCII del caracter que contiene el token.
	 */
	private int getCharValue( Token t ){
		if( t.esTipo("char") )
			return t.darLexema().charAt(1);
		else if( t.esTipo("chr") ){
			String lexema = t.darLexema();
			return Integer.parseInt(lexema.substring(4, lexema.length()-1));
		} else Test.print("Ale, porfavor, programa bien...");
		return -1;
	}
	
	/**
	 * Genera una expresión regular para una definición de caracteres.
	 * @param s La cadena que contiene todos los caracteres de la definición.
	 * @return La expresión regular que reconoce cualquiera de estos caracteres.
	 */
	private String generarCharRegEx( String s ){
		
		String regex = "RegEx.OPEN_PARENTHESIS + ";
		
		for( int i = 0; i < s.length(); i++ )
			regex += "(char) " + (int) s.charAt(i) + " + RegEx.OR + ";
		
		regex = regex.substring(0, regex.length()-11) + "RegEx.CLOSE_PARENTHESIS";
		//Test.print(regex);
		return regex;
	}
	
	/**
	 * Genera una expresión regular en base a una string.
	 * @param s La cadena a analizar
	 * @return Una expresión regular que reconoce la cadena ingresada.
	 */
	private String generarStringRegEx( String s ){
		String regex = "RegEx.OPEN_PARENTHESIS + ";
		s = s.substring(1, s.length()-1);
		for( int i = 0; i < s.length(); i++ )
			regex += "(char) " + (int) s.charAt(i) + " + ";
		
		regex += "RegEx.CLOSE_PARENTHESIS";
		//Test.print(regex);
		return regex;
	}
	
	/**
	 * Obtiene diferentes cadenas de tokens que definen un Character
	 * @return Un arreglo con todas las definiciones de Characters
	 */
	private ArrayList<Token[]> obtenerCharDefs(){
		
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
		 * KEYWORDS, TOKENS, IGNORE, PRODUCTIONS o END.
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
	
	/**
	 * Obtiene diferentes cadenas de tokens que definen una Keyword
	 * @return Un arreglo con todas las definiciones de Keywords
	 */
	private ArrayList<Token[]> obtenerKeywordDefs(){
		
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
		 * Empieza a obtener las diferentes declaraciones de KEYWORDS.
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
	
	/**
	 * Obtiene diferentes cadenas de tokens que definen un Token
	 * @return Un arreglo con todas las definiciones de Tokens
	 */
	private ArrayList<Token[]> obtenerTokenDefs(){
		
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
		 * Empieza a obtener las diferentes declaraciones de TOKENS.
		 * Cada declaración termina cuando hay lo sigue un ident seguido de '='.
		 * Todas las declaraciones terminan con el fin del archivo o con
		 * IGNORE, PRODUCTIONS o END.
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
	 * Obtiene diferentes cadenas de tokens que definen un Whitespace
	 * @return Un arreglo con todas las definiciones de Whitespaces
	 */
	private ArrayList<Token[]> obtenerWhitespaceDefs(){
		
		// Obtiene el índice del cual inician las declaraciones de whitespace
		// Si no hay, retorna null.
		int i;
		for( i = 0; i < tokens.length; i++ ){
			Token token = tokens[i];
			if( token.esTipo("IGNORE") )
				break;
			if( i == tokens.length-1 && !token.esTipo("IGNORE") )
				return new ArrayList<Token[]>(0);
		}
		
		/*
		 * Empieza a obtener las diferentes declaraciones de WHITESPACE.
		 * Cada declaración termina cuando hay lo sigue un ident seguido de '='.
		 * Todas las declaraciones terminan con el fin del archivo o con
		 * PRODUCTIONS o END.
		 */
		ArrayList<Token[]> preArray = new ArrayList<Token[]>();
		ArrayList<Token> chardef = new ArrayList<Token>();
		for( i = i; i < tokens.length; i++ ){
			Token token = tokens[i];
			Token next = tokens[i+1];
			// Si ya se acabaron las definiciones de CHARACTER, se sale del ciclo
			if( token.esTipo("PRODUCTIONS") || token.esTipo("END") ){
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				preArray.remove(0);
				/*for( Token[] tarray : preArray ){
					for( Token t : tarray )
						Test.print(t);
				}*/
				return preArray;
			}
			// Si es un ident seguido de igual, se empieza una nueva lista de Tokens
			if( token.esTipo("IGNORE")){
				// Se almacena la lista que se llevaba
				Token[] cdef = chardef.toArray(new Token[ chardef.size() ]);
				preArray.add( cdef );
				chardef.clear();
				// Se inicia la lista de nuevo
			} else chardef.add(token);
		}
		return null; // Sanity check
	}
	
	
	/**
	 * Excepción que se dispara si hay un error en la sintaxis de Cocol 
	 * @author AleKnaui
	 */
	class CocolException extends Exception{
		public CocolException(String mensaje){
			super(mensaje);
		}
	}
	
}
