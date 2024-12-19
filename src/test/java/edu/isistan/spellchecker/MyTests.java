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

	public Dictionary createDictionary(String filename) throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo '" + filename + "' no encontrado en resources.");
			}
			return new Dictionary(new TokenScanner(new InputStreamReader(inputStream)));
		}
	}


	// TEST TOKENIZER
	@Test(timeout=500) public void testEmptyEntry() throws IOException {
		Reader in = new StringReader("");
		TokenScanner t = new TokenScanner(in);
		try {
			assertFalse("reached end of stream", t.hasNext());
		} finally {
			in.close();
		}
	}
	
	@Test(timeout=500) public void testOneWord() throws IOException {
		Reader in = new StringReader("Word");
		TokenScanner t = new TokenScanner(in);
		try {
			assertTrue("has next", t.hasNext());
		    assertEquals("Word", t.next());
		    assertTrue(TokenScanner.isWord("Word"));
		    
			assertFalse("reached end of stream", t.hasNext());
		} finally {
			in.close();
		}
	}
	
	@Test(timeout=500) public void testOneNoWord() throws IOException {
		Reader in = new StringReader("1998@");
		TokenScanner t = new TokenScanner(in);
		try {
			assertTrue("has next", t.hasNext());
		    assertEquals("1998@", t.next());
		    assertFalse(TokenScanner.isWord("1998@"));
		    
			assertFalse("reached end of stream", t.hasNext());
		} finally {
			in.close();
		}
	}
	
	@Test(timeout=500) public void testEndWithWord() throws IOException {
		Reader in = new StringReader("1998 year");
		TokenScanner t = new TokenScanner(in);
		try {
			assertTrue("has next", t.hasNext());
		    assertEquals("1998 ", t.next());
		    assertFalse(TokenScanner.isWord("1998 "));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals("year", t.next());
		    assertTrue(TokenScanner.isWord("year"));
		    
			assertFalse("reached end of stream", t.hasNext());
		} finally {
			in.close();
		}
	}
	
	@Test(timeout=500) public void testEndWithNoWord() throws IOException {
		Reader in = new StringReader("Have a nice day!");
		TokenScanner t = new TokenScanner(in);
		try {
			assertTrue("has next", t.hasNext());
		    assertEquals("Have", t.next());
		    assertTrue(TokenScanner.isWord("Have"));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals(" ", t.next());
		    assertFalse(TokenScanner.isWord(" "));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals("a", t.next());
		    assertTrue(TokenScanner.isWord("a"));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals(" ", t.next());
		    assertFalse(TokenScanner.isWord(" "));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals("nice", t.next());
		    assertTrue(TokenScanner.isWord("nice"));

		    assertTrue("has next", t.hasNext());
		    assertEquals(" ", t.next());
		    assertFalse(TokenScanner.isWord(" "));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals("day", t.next());
		    assertTrue(TokenScanner.isWord("day"));
		    
		    assertTrue("has next", t.hasNext());
		    assertEquals("!", t.next());
		    assertFalse(TokenScanner.isWord("!"));
		    
			assertFalse("reached end of stream", t.hasNext());
		} finally {
			in.close();
		}
	}
	
	// TEST DICCIONARIO
	@Test(timeout=500) public void testDictionaryExistingWord() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
		assertTrue("'apple' -> should be true ('apple' in file)", d.isWord("apple"));
	}
	
	@Test(timeout=500) public void testDictionaryNonExistingWord() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
		assertFalse("'applee' -> should be false ('apple' NOT in file)", d.isWord("applee"));
	}
	
	@Test(timeout=500) public void testDictionarySize() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
		d.getNumWords();
	}
	
	@Test(timeout=500) public void testDictionaryCheckEmptyString() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
		assertFalse("Empty string is NOT a valid word", d.isWord(""));
	}
	
	@Test(timeout=500) public void testDictionaryCheckCapitalization() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
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
		Dictionary d = createDictionary("smallDictionary.txt");
		SwapCorrector swap = new SwapCorrector(d);
		assertEquals("carrot -> {}", makeSet(new String[]{}), swap.getCorrections("carrot"));
	}
	
@Test public void testSwapCapitalization() throws IOException {
		Dictionary d = createDictionary("smallDictionary.txt");
		SwapCorrector swap = new SwapCorrector(d);
		assertEquals("carrTO -> {carrot}", makeSet(new String[]{"carrot"}), swap.getCorrections("carrTO"));
		assertEquals("CARrto -> {Carrot}", makeSet(new String[]{"Carrot"}), swap.getCorrections("CARrto"));
		assertEquals("caRRto -> {carrot}", makeSet(new String[]{"carrot"}), swap.getCorrections("caRRto"));
	}
}