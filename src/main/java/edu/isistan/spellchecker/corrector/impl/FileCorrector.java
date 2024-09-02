package edu.isistan.spellchecker.corrector.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

import java.io.*;

/**
 * Corrector basado en un archivo.
 * 
 */
public class FileCorrector extends Corrector {

	/** Clase especial que se utiliza al tener 
	 * alg�n error de formato en el archivo de entrada.
	 */
	public static class FormatException extends Exception {
		public FormatException(String msg) {
			super(msg);
		}
	}

	public HashMap<String, Set<String>> corrections;

	/**
	 * Constructor del FileReader
	 * Utilice un BufferedReader para leer el archivo de definici�n
	 *
	 * <p> 
	 * Cada l�nea del archivo del diccionario tiene el siguiente formato: 
	 * misspelled_word,corrected_version
	 *
	 * <p>
	 *Ejemplo:<br>
	 * <pre>
	 * aligatur,alligator<br>
	 * baloon,balloon<br>
	 * inspite,in spite<br>
	 * who'ev,who've<br>
	 * ther,their<br>
	 * ther,there<br>
	 * </pre>
	 * <p>
	 * Estas l�neas no son case-insensitive, por lo que todas deber�an generar el mismo efecto:<br>
	 * <pre>
	 * baloon,balloon<br>
	 * Baloon,balloon<br>
	 * Baloon,Balloon<br>
	 * BALOON,balloon<br>
	 * bAlOon,BALLOON<br>
	 * </pre>
	 * <p>
	 * Debe ignorar todos los espacios vacios alrededor de las palabras, por lo
	 * que estas entradas son todas equivalentes:<br>
	 * <pre>
	 * inspite,in spite<br>
	 *    inspite,in spite<br>
	 * inspite   ,in spite<br>
	 *  inspite ,   in spite  <br>
	 * </pre>
	 * Los espacios son permitidos dentro de las sugerencias. 
	 *
	 * <p>
	 * Deber�a arrojar <code>FileCorrector.FormatException</code> si se encuentra alg�n
	 * error de formato:<br>
	 * <pre>
	 * ,correct<br>
	 * wrong,<br>
	 * wrong correct<br>
	 * wrong,correct,<br>
	 * </pre>
	 * <p>
	 *
	 * @param r Secuencia de caracteres 
	 * @throws IOException error leyendo el archivo
	 * @throws FileCorrector.FormatException error de formato
	 * @throws IllegalArgumentException reader es null
	 */
	public FileCorrector(Reader r) throws IOException, FormatException {
		if (r == null) {
			throw new IllegalArgumentException();
		}
		
		corrections = new HashMap<>();
		BufferedReader br = new BufferedReader(r);
		String line;
		int numLine = 1;
		
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(",");
			
			if (parts.length != 2 || parts[0].isEmpty()) {
				throw new FormatException("Formato invalido en la linea " + numLine + ":\"" + line + "\".\nSe esperaba: <wrong>,<correct>");
			}
			
			parts[0] = parts[0].trim().toLowerCase();
			parts[1] = parts[1].trim().toLowerCase();
			
			Set<String> aux = corrections.computeIfAbsent(parts[0], k -> new HashSet<>());
			aux.add(parts[1]);
			numLine++;
		}
	}

	/** Construye el Filereader.
	 *
	 * @param filename 
	 * @throws IOException 
	 * @throws FileCorrector.FormatException 
	 * @throws FileNotFoundException 
	 */
	public static FileCorrector make(String filename) throws IOException, FormatException {
		Reader r = new FileReader(filename);
		FileCorrector fc;
		try {
			fc = new FileCorrector(r);
		} finally {
			if (r != null) { r.close(); }
		}
		return fc;
	}

	/**
	 * Retorna una lista de correcciones para una palabra dada.
	 * Si la palabra mal escrita no est� en el diccionario el set es vacio.
	 * <p>
	 * Ver superclase.
	 *
	 * @param wrong 
	 * @return retorna un conjunto (potencialmente vac�o) de sugerencias.
	 * @throws IllegalArgumentException si la entrada no es una palabra v�lida 
	 */
	public Set<String> getCorrections(String wrong) {
		if (!TokenScanner.isWord(wrong))
			throw new IllegalArgumentException();
		
		return matchCase(wrong, corrections.getOrDefault(wrong.trim().toLowerCase(), new HashSet<>()));
	}
}
