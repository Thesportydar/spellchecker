package edu.isistan.spellchecker.corrector.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.isistan.spellchecker.corrector.Trie;
import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

@State(Scope.Benchmark)
public class LevenshteinBenchmark {
	private Levenshtein small, big, trie;

    //@Param({"teh"})//, "h", "ay"})//, "add", "allan", "marsh", "minnesot", "pam", "sch", "gemma"})
    @Param({})
    private String input;
	
	@Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
		small = new Levenshtein(new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt"))));
        big = new Levenshtein(new Dictionary(new TokenScanner(new FileReader("dictionary.txt"))));
        trie = new Levenshtein(new Trie(new TokenScanner(new FileReader("dictionary.txt"))));
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
