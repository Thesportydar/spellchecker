package edu.isistan.spellchecker;
import org.junit.*;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.corrector.impl.FileCorrector;
import edu.isistan.spellchecker.corrector.impl.SwapCorrector;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

import static org.junit.Assert.*;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/** Cree sus propios tests. */
public class MyTests {
	
	private Set<String> makeSet(String[] strings) {
	    Set<String> mySet = new TreeSet<String>();
	    for (String s : strings) {
	    	mySet.add(s);
	    }
	    return mySet;
	}
	
	// TEST DICCIONARIO
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
	
	// TEST FILE CORRECTOR
	@Test public void testTrim() throws IOException, FileCorrector.FormatException  {
	    Corrector c = FileCorrector.make("wrongMisspellings.txt");
	    assertEquals("lyon -> lion", makeSet(new String[]{"lion"}), c.getCorrections("lyon"));
	    assertEquals("TIGGER -> {Trigger,Tiger}", makeSet(new String[]{"Trigger","Tiger"}), c.getCorrections("TIGGER"));
	}
	
	@Test public void testGetNonExistingWord() throws IOException, FileCorrector.FormatException  {
	    Corrector c = FileCorrector.make("smallMisspellings.txt");
	    assertEquals("apple -> {}", makeSet(new String[] {}), c.getCorrections("apple"));
	}
	
	@Test public void testMultipleCorrections() throws IOException, FileCorrector.FormatException  {
	    Corrector c = FileCorrector.make("smallMisspellings.txt");
	    assertEquals("TIGGER -> {Trigger,Tiger}", makeSet(new String[]{"Trigger","Tiger"}), c.getCorrections("TIGGER"));
	}
	
	@Test public void testCapitalization() throws IOException, FileCorrector.FormatException  {
	    Corrector c = FileCorrector.make("smallMisspellings.txt");
	    assertEquals("lyon -> {}", makeSet(new String[] {"lion"}), c.getCorrections("lyon"));
	    assertEquals("Lyon -> {}", makeSet(new String[] {"Lion"}), c.getCorrections("Lyon"));
	    assertEquals("LyON -> {}", makeSet(new String[] {"Lion"}), c.getCorrections("LyON"));
	}
	
	//TEST SWAP CORRECTOR
	@Test public void testDictionaryNull() throws IOException {
		try {
			new SwapCorrector(null);
		    fail("Expected an IllegalArgumentException - cannot create FileCorrector with null.");
		} catch (IllegalArgumentException f) {    
		      //Do nothing. It's supposed to throw an exception
		}
	}
	
	@Test public void testSwapExistingWord() throws IOException {
		Reader reader = new FileReader("smallDictionary.txt");
		try {
			Dictionary d = new Dictionary(new TokenScanner(reader));
			SwapCorrector swap = new SwapCorrector(d);
			assertEquals("carrot -> {}", makeSet(new String[]{}), swap.getCorrections("carrot"));
		} finally {
			reader.close();
		}
	}
	
	@Test public void testSwapCapitalization() throws IOException {
		Reader reader = new FileReader("smallDictionary.txt");
		try {
			Dictionary d = new Dictionary(new TokenScanner(reader));
			SwapCorrector swap = new SwapCorrector(d);
			assertEquals("carrTO -> {carrot}", makeSet(new String[]{"carrot"}), swap.getCorrections("carrTO"));
			assertEquals("CARrto -> {Carrot}", makeSet(new String[]{"Carrot"}), swap.getCorrections("CARrto"));
			assertEquals("caRRto -> {carrot}", makeSet(new String[]{"carrot"}), swap.getCorrections("caRRto"));
		} finally {
			reader.close();
		}
	}
}