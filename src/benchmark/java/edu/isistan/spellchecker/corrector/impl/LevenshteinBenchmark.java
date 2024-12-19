package edu.isistan.spellchecker.corrector.impl;

import java.io.*;
import java.util.concurrent.TimeUnit;

import edu.isistan.spellchecker.corrector.Trie;
import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

@State(Scope.Benchmark)
public class LevenshteinBenchmark {
	private Levenshtein small, big, trie;

    @Param({"teh", "ay", "evangelint", "ream", "americanizatio", "atencion", "gemma", "civilin", "pollo", "theow"})
    //@Param({"teh", "ay", "h","pelota", "bananan"})
    private String input;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }
            small = new Levenshtein(new Dictionary(new TokenScanner(new InputStreamReader(inputStream))));
        }
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "dictionary.txt" + "' no encontrado en resources.");
            }
            big = new Levenshtein(new Dictionary(new TokenScanner(new InputStreamReader(inputStream))));
        }
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "dictionary.txt" + "' no encontrado en resources.");
            }
            trie = new Levenshtein(new Trie(new TokenScanner(new InputStreamReader(inputStream))));
        }
    }
	
	@Benchmark
	public void bmLevenshteinDeletionsSmall() {
        small.getDeletions(input);
	}
	
	@Benchmark
	public void bmLevenshteinInsertSmall() {
        small.getInsertions(input);
	}
	
	@Benchmark
	public void bmLevenshteinSubstitutionSmall() {
        small.getSubstitutions(input);
	}
	
	@Benchmark
	public void bmLevenshteinCorrectionSmall() {
        small.getCorrections(input);
	}

    @Benchmark
    public void bmLevenshteinDeletionsBig() {
        big.getDeletions(input);
    }

    @Benchmark
    public void bmLevenshteinInsertBig() {
        big.getInsertions(input);
    }

    @Benchmark
    public void bmLevenshteinSubstitutionBig() {
        big.getSubstitutions(input);
    }

    @Benchmark
    public void bmLevenshteinCorrectionBig() {
        big.getCorrections(input);
    }

    @Benchmark
    public void bmLevenshteinDeletionsTrie() {
        trie.getDeletions(input);
    }

    @Benchmark
    public void bmLevenshteinInsertTrie() {
        trie.getInsertions(input);
    }

    @Benchmark
    public void bmLevenshteinSubstitutionTrie() {
        trie.getSubstitutions(input);
    }

    @Benchmark
    public void bmLevenshteinCorrectionTrie() {
        trie.getCorrections(input);
    }
}
