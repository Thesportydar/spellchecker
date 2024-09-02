/**
 * 
 */
package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.tokenizer.TokenScanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * Implementacion de la clase AbstractDictionary con estructura interna de trie.
 * El diccionario maneja todas las palabras conocidas y es case insensitive.
 * * Una palabra "v�lida" es una secuencia de letras (determinado por Character.isLetter)
 * o apostrofes.
 */
public class Trie extends AbstractDictionary {

	public class Node {
		private HashMap<Character, Node> child;
		private boolean wordEnd;
		private int size;
		
		public Node() {
			wordEnd = false;
			child = new HashMap<>(27);
			size = 0;
		}
	}

	private final Node root;

	/**
	 * Construye un trie usando un TokenScanner
	 * <p>
	 * Una palabra v�lida es una secuencia de letras (ver Character.isLetter) o apostrofes.
	 * Toda palabra no v�lida se debe ignorar
	 *
	 * <p>
	 *
	 * @param ts tokenscanner
	 * @throws IllegalArgumentException el TokenScanner es null
	 */
	public Trie(TokenScanner ts) {
		if (ts == null) {
			throw new IllegalArgumentException();
		}
		root = new Node();
		loadWords(ts);
	}

	/**
	 * Construye un trie a partir de un nodo de un trie ya existente
	 * @param root
	 */
	public Trie(Node root) {
		this.root = root;
	}

	/**
	 * Construye un trie usando un archivo.
	 *
	 *
	 * @param filename
	 * @throws FileNotFoundException si el archivo no existe
	 * @throws IOException Error leyendo el archivo
	 */
	public static Trie make(String filename) throws IOException {
		Reader r = new FileReader(filename);
		Trie t = new Trie(new TokenScanner(r));
		r.close();
		return t;
	}

	@Override
	public boolean isWord(String word) {
		Node currentNode = root;

        for (char c : word.toLowerCase().toCharArray()) {
            currentNode = currentNode.child.get(c);

            if (currentNode == null) {
                return false;
            }
        }
		return currentNode.wordEnd;
	}

	@Override
	public boolean add(String e) {
		Node currentNode = root;
		
		for (char c : e.toLowerCase().toCharArray()) {
			if (!currentNode.child.containsKey(c)) {
				currentNode.child.put(c, new Node());
			}
			
			currentNode = currentNode.child.get(c);
			currentNode.size++;
		}
		
		currentNode.wordEnd = true;
		root.size++;
		return true;
	}

	/**
	 * Retorna el n�mero de palabras correctas en el trie.
	 * Recuerde que como es case insensitive si Dogs y doGs est�n en el
	 * diccionario, cuentan como una sola palabra.
	 * @return numero de palabras unicas
	 */
	@Override
	public int getNumWords() {
		return root.size;
	}

	/**
	 * Dado un prefijo, secuencia de caracteres o caracter unico, devuelve el
	 * subarbol del trie cuyo nodo raiz representa al ultimo caracter del prefijo.
	 *
	 * @param word prefijo a buscar en el trie
	 * @return Subarbol del Trie que representa la el nodo donde termina el prefijo.
	 * Retorna null si el prefijo no existe en el trie.
	 */
	public Trie getSubTrie(String word) {
		Node currentNode = root;

		for (char c : word.toLowerCase().toCharArray()) {
			currentNode = currentNode.child.get(c);
			if (currentNode == null) {
				return null;
			}
		}
		return new Trie(currentNode);
	}

	public Trie getSubTrie(char c) {
		return getSubTrie(Character.toString(c));
	}
}
