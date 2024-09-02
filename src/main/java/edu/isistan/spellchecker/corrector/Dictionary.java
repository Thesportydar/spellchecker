package edu.isistan.spellchecker.corrector;

import java.io.*;
import java.util.Set;
import java.util.HashSet;

import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 * El diccionario maneja todas las palabras conocidas.
 * El diccionario es case insensitive
 * Una palabra "v�lida" es una secuencia de letras (determinado por Character.isLetter) 
 * o apostrofes.
 */
public class Dictionary extends AbstractDictionary {

	Set<String> words;
	/**
	 * Construye un diccionario usando un TokenScanner
	 * <p>
	 * Una palabra v�lida es una secuencia de letras (ver Character.isLetter) o apostrofes.
	 * Toda palabra no v�lida se debe ignorar
	 *
	 * <p>
	 *
	 * @param ts
	 * @throws IOException Error leyendo el archivo
	 * @throws IllegalArgumentException el TokenScanner es null
	 */
	
	public Dictionary(TokenScanner ts) throws IOException {
		if (ts == null) {
			throw new IllegalArgumentException("El TokenScanner es null");
		}
		this.words = new HashSet<>();
		loadWords(ts);
	}

	/**
	 * Construye un diccionario usando un archivo.
	 *
	 *
	 * @param filename 
	 * @throws FileNotFoundException si el archivo no existe
	 * @throws IOException Error leyendo el archivo
	 */
	public static Dictionary make(String filename) throws IOException {
		Reader r = new FileReader(filename);
		Dictionary d = new Dictionary(new TokenScanner(r));
		r.close();
		return d;
	}

	/**
	 * Retorna el n�mero de palabras correctas en el diccionario.
	 * Recuerde que como es case insensitive si Dogs y doGs est�n en el 
	 * diccionario, cuentan como una sola palabra.
	 * 
	 * @return n�mero de palabras �nicas
	 */
	@Override
	public int getNumWords() {
		return words.size();
	}

	/**
	 * Implementacion del add para agregar una palabra al diccionario
	 * Recordar que es case insensitive
	 * @param word
	 * @return
	 */
	@Override
	public boolean add(String word) {
		return words.add(word.toLowerCase());
	}

	/**
	 * Testea si una palabra es parte del diccionario. Si la palabra no est� en
	 * el diccionario debe retornar false. null debe retornar falso.
	 * Si en el diccionario est� la palabra Dog y se pregunta por la palabra dog
	 * debe retornar true, ya que es case insensitive.
	 *Llamar a este m�todo no debe reabrir el archivo de palabras.
	 *
	 * @param word verifica si la palabra est� en el diccionario. 
	 * Asuma que todos los espacios en blanco antes y despues de la palabra fueron removidos.
	 * @return si la palabra est� en el diccionario.
	 */
	@Override
	public boolean isWord(String word) {
        return word != null && !word.isEmpty() && words.contains(word.toLowerCase());
    }
}