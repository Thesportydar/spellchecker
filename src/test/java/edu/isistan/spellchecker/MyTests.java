package edu.isistan.spellchecker;
import org.junit.*;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

import static org.junit.Assert.*;
import java.io.*;

/** Cree sus propios tests. */
public class MyTests {
	
	@Test(timeout=500) public void testDictionaryExistingWord() throws IOException {
		Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
		assertTrue("'apple' -> should be true ('apple' in file)", d.isWord("apple"));
	}
	
	@Test(timeout=500) public void testDictionaryNonExistingWord() throws IOException {
		Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
		assertFalse("'applee' -> should be false ('apple' NOT in file)", d.isWord("applee"));
	}
	
	@Test(timeout=500) public void testDictionarySize() throws IOException {
		Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
		d.getNumWords();
	}
	
	@Test(timeout=500) public void testDictionaryCheckEmptyString() throws IOException {
		Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
		assertFalse("Empty string is NOT a valid word", d.isWord(""));
	}
	
	@Test(timeout=500) public void testDictionaryCheckCapitalization() throws IOException {
		Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
		assertTrue("'apple' -> should be true ('apple' in file)", d.isWord("apple"));
		assertTrue("'Apple' -> should be true ('apple' in file)", d.isWord("Apple"));
		assertTrue("'APPLE' -> should be true ('apple' in file)", d.isWord("APPLE"));
	}
}