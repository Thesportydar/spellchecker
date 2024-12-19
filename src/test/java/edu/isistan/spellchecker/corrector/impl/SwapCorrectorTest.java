package edu.isistan.spellchecker.corrector.impl;
import org.junit.*;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

import java.io.*;
import java.util.TreeSet;
import java.util.Set;



public class SwapCorrectorTest {


	private Set<String> makeSet(String[] strings) {
		Set<String> mySet = new TreeSet<String>();
		for (String s : strings) {
			mySet.add(s);
		}
		return mySet;
	}


	@Test
	public void testSwapCorrections() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt")) {
			if (inputStream == null) {
				throw new FileNotFoundException("Archivo 'dictionary.txt' no encontrado en resources.");
			}

			try (Reader reader = new InputStreamReader(inputStream)) {
				Dictionary d = new Dictionary(new TokenScanner(reader));
				SwapCorrector swap = new SwapCorrector(d);
				//assertEquals("cya -> {cay}", makeSet(new String[]{"cay"}), swap.getCorrections("cya"));
				//assertEquals("oYurs -> {yours}", makeSet(new String[]{"yours"}), swap.getCorrections("oYurs"));
				System.out.println(swap.getCorrections("civiliun"));
			}
		}
	}

}
