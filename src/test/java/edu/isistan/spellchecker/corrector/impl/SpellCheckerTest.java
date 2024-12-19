package edu.isistan.spellchecker.corrector.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.*;


import org.junit.Test;

import edu.isistan.spellchecker.SpellChecker;
import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;


public class SpellCheckerTest {


	public static void spellCheckFiles(String fdict, int dictSize, String fcorr,
			String fdoc, String fout, String finput) 
					throws IOException, FileCorrector.FormatException
	{
		Dictionary dict = Dictionary.make(fdict);

		Corrector corr = null;
		if (fcorr == null) {
			corr = new SwapCorrector(dict);
		} else {
			corr = FileCorrector.make(fcorr);
		}


		if(dictSize >= 0) 
			assertEquals("Dictionary size = " + dictSize, dictSize,
					dict.getNumWords());

		InputStream input = SpellCheckerTest.class.getClassLoader().getResourceAsStream(finput);
		if (input == null) {
			throw new FileNotFoundException("Archivo '" + finput + "' no encontrado en resources.");
		}

		InputStream doc = SpellCheckerTest.class.getClassLoader().getResourceAsStream(fdoc);
		if (doc == null) {
			throw new FileNotFoundException("Archivo '" + fdoc + "' no encontrado en resources.");
		}
		Reader in = new BufferedReader(new InputStreamReader(doc));

		Writer out = new BufferedWriter(new FileWriter(fout));

		SpellChecker sc = new SpellChecker(corr,dict);

		sc.checkDocument(in, input, out);
		in.close();
		input.close();
		out.flush();
		out.close();
	}



	@Test(timeout=500) public void testCheckFoxGood() throws IOException, FileCorrector.FormatException {
		spellCheckFiles("theFoxDictionary.txt",7, "theFoxMisspellings.txt",
				"theFox.txt", "foxout.txt", "theFox_goodinput.txt");
		compareDocs("foxout.txt", "theFox_expected_output.txt");
	}


	@Test(timeout=500) public void testCheckMeanInput() throws IOException, FileCorrector.FormatException {
		spellCheckFiles("theFoxDictionary.txt",7, "theFoxMisspellings.txt",
				"theFox.txt", "foxout.txt", "theFox_meaninput.txt");
		compareDocs("foxout.txt", "theFox_expected_output.txt");
	}


	@Test(timeout=500) public void testCheckGettysburgSwap() throws IOException, FileCorrector.FormatException {
		// Use the SwapCorrector instead!
		spellCheckFiles("dictionary.txt",60822,null,
				"Gettysburg.txt", "Gettysburg-out.txt",
				"Gettysburg_input.txt");
		compareDocs("Gettysburg-out.txt", "Gettysburg_expected_output.txt");
	}




	public static void compareDocs(String out, String expected) 
			throws IOException, FileNotFoundException 
	{
		InputStream outStream = SpellCheckerTest.class.getClassLoader().getResourceAsStream(out);
		InputStream expectedStream = SpellCheckerTest.class.getClassLoader().getResourceAsStream(expected);
		if (outStream == null) {
			throw new FileNotFoundException("Archivo '" + out + "' no encontrado en resources.");
		}
		if (expectedStream == null) {
			throw new FileNotFoundException("Archivo '" + out + "' no encontrado en resources.");
		}
		BufferedReader f1 = new BufferedReader(new InputStreamReader(outStream));
		BufferedReader f2 = new BufferedReader(new InputStreamReader(expectedStream));

		try{
			String line1 = f1.readLine();
			String line2 = f2.readLine();
			while(line1 != null && line2 != null){
				assertEquals("Output file did not match expected output.", line2, line1);
				line1 = f1.readLine();
				line2 = f2.readLine();
			}
			if(line1 != null) {
				fail("Expected end of file, but found extra lines in the output.");
			} else if(line2 != null) {
				fail("Expected more lines, but found end of file in the output. ");
			}
		}
		finally{
			f1.close();
			f2.close();

		}
	}

}
