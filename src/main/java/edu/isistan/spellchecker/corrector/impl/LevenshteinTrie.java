package edu.isistan.spellchecker.corrector.impl;
import java.util.HashSet;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Trie;
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
public class LevenshteinTrie extends Corrector {

    Trie trie;
    /**
     * Construye un Levenshtein Corrector usando un trie.
     * Debe arrojar <code>IllegalArgumentException</code> si el trie es null.
     *
     * @param trie arbol de prefijos con las palabras del diccionario
     */
    public LevenshteinTrie(Trie trie) {
        if (trie == null) {
            throw new IllegalArgumentException();
        }
        this.trie = trie;
    }

    /**
     * @param s palabra
     * @return todas las palabras a erase distance uno
     */
    Set<String> getDeletions(String s) {
        Set<String> deletions = new HashSet<>();

        // valid representa un prefijo de la palabra que existe en el trie y ya se probaron deletions con sus caracteres
        Trie valid = trie;

        for (int i = 0; i < s.length()-1; i++) {
            if (valid.isWord(s.substring(i+1))) { // verifica que el nodo actual es sucedido por el resto de la palabra(menos el char a eliminar)
                deletions.add(s.substring(0, i) + s.substring(i + 1));
            }

            if ((valid = valid.getSubTrie(s.charAt(i))) == null) // pasa a probar agregando el caracter anteriormente eliminado
                break; // no existe este prefijo en el trie, se poda

        }
        if (valid != null && valid.isWord("")) { // una iteracion mas para tener en cuenta la deletion del ultimo caracter
            deletions.add(s.substring(0, s.length()-1));
        }
        return deletions;
    }

    /**
     * @param s palabra
     * @return todas las palabras a substitution distance uno
     */
    public Set<String> getSubstitutions(String s) {
        Set<String> substitutions = new HashSet<>();

        // valid representa un prefijo de la palabra que existe en el trie y ya se probaron deletions con sus caracteres
        Trie valid = trie;

        for (int i = 0; i < s.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != s.charAt(i)) {
                    if (valid.isWord(c + s.substring(i + 1))) { // verifica que el nodo actual es sucedido por el resto de la palabra(menos el char a eliminar)
                        substitutions.add(s.substring(0, i) + c + s.substring(i + 1));
                    }
                }
            }
            if ((valid = valid.getSubTrie(s.charAt(i))) == null) // pasa a probar agregando el caracter anteriormente sustituido
                break; // no existe este prefijo en el trie, se poda
        }
        return substitutions;
    }


    /**
     * @param s palabra
     * @return todas las palabras a insert distance uno
     */
    public Set<String> getInsertions(String s) {
        Set<String> insertions = new HashSet<>();

        // valid representa un prefijo de la palabra que existe en el trie y ya se probaron deletions con sus caracteres
        Trie valid = trie;

        for (int i = 0; i <= s.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (i == s.length() || c != s.charAt(i)) {
                    if (valid.isWord(c + s.substring(i))) { // verifica que el nodo actual es sucedido por el resto de la palabra(menos el char a eliminar)
                        insertions.add(s.substring(0, i) + c + s.substring(i));
                    }
                }
            }
            if (i == s.length() || (valid = valid.getSubTrie(s.charAt(i))) == null) // pasa a probar agregando el caracter anteriormente sustituido
                break; // no existe este prefijo en el trie, se poda
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