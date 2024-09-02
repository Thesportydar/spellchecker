package edu.isistan.spellchecker.corrector.impl;
import java.util.HashSet;
import java.util.Set;

import edu.isistan.spellchecker.corrector.AbstractDictionary;
import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 *
 * Un corrector inteligente que utiliza "edit distance" para generar correcciones.
 * La distancia de Levenshtein es el n�mero minimo de ediciones que se deber
 * realizar a un string para igualarlo a otro. Por edici�n se entiende:
 * <ul>
 * <li> insertar una letra
 * <li> borrar una letra
 * <li> cambiar una letra
 * </ul>
 *
 * Una "letra" es un caracter a-z (no contar los apostrofes).
 * Intercambiar letras (thsi -> this) <it>no</it> cuenta como una edici�n.
 * <p>
 * Este corrector sugiere palabras que esten a edit distance uno.
 */
public class Levenshtein extends Corrector {

	AbstractDictionary dict;
	/**
	 * Construye un Levenshtein Corrector usando un Dictionary.
	 * Debe arrojar <code>IllegalArgumentException</code> si el diccionario es null.
	 *
	 * @param dict
	 */
	public Levenshtein(AbstractDictionary dict) {
		if (dict == null) {
			throw new IllegalArgumentException();
		}
		this.dict = dict;
	}

	/**
	 * @param s palabra
	 * @return todas las palabras a erase distance uno
	 */
	Set<String> getDeletions(String s) {
		Set<String> deletions = new HashSet<>();
		
		for (int i = 0; i < s.length(); i++) {
			String aux = s.substring(0, i) + s.substring(i+1);
			if (dict.isWord(aux)) {
				deletions.add(aux);
			}
		}
		return deletions;
	}

	/**
	 * @param s palabra
	 * @return todas las palabras a substitution distance uno
	 */
	public Set<String> getSubstitutions(String s) {
		Set<String> substitutions = new HashSet<>();
		
		for (int i = 0; i < s.length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				if (c != s.charAt(i)) {
					String aux = s.substring(0, i) + c + s.substring(i+1);
					if (dict.isWord(aux)) {
						substitutions.add(aux);
					}
				}
			}
		}
		return substitutions;
	}


	/**
	 * @param s palabra
	 * @return todas las palabras a insert distance uno
	 */
	public Set<String> getInsertions(String s) {
		Set<String> insertions = new HashSet<>();
		
		for (int i = 0; i <= s.length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				String aux = s.substring(0, i) + c + s.substring(i);
				if (dict.isWord(aux)) {
					insertions.add(aux);
				}
			}
		}
		return insertions;
	}

	public Set<String> getCorrections(String wrong) {
		if (!TokenScanner.isWord(wrong))
			throw new IllegalArgumentException();
		
		Set<String> result = getDeletions(wrong);
		result.addAll(getSubstitutions(wrong));
		result.addAll(getInsertions(wrong));
		
		return matchCase(wrong, result);
	}
}
