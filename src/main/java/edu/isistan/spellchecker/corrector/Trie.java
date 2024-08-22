/**
 * 
 */
package edu.isistan.spellchecker.corrector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 */
public class Trie implements Set<String> {

	private class Node {
		Node[] child;
		boolean wordEnd;
		
		public Node() {
			wordEnd = false;
			child = new Node[26];
		}
	}

	private Node root;
	/**
	 * 
	 */
	public Trie() {
		root = new Node();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		/*
		Node currentNode = root;
		
		for (char c : e.toLowerCase().toCharArray()) {
			if (currentNode.child[c - 'a'] == null) {
				currentNode.child[c - 'a'] = new Node();
			}
			
			currentNode = currentNode.child[c - 'a'];
		}
		
		currentNode.wordEnd = true;*/
		return true;
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(String e) {
		Node currentNode = root;
		
		for (char c : e.toLowerCase().toCharArray()) {
			if (currentNode.child[c - 'a'] == null) {
				currentNode.child[c - 'a'] = new Node();
			}
			
			currentNode = currentNode.child[c - 'a'];
		}
		
		currentNode.wordEnd = true;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
