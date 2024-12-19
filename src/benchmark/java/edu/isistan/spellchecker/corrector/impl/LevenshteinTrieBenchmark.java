package edu.isistan.spellchecker.corrector.impl;

import java.io.*;
import java.util.concurrent.TimeUnit;

import edu.isistan.spellchecker.corrector.Dictionary;
import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Trie;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

@State(Scope.Benchmark)
public class LevenshteinTrieBenchmark {
    private LevenshteinTrie small, big;

    //@Param({"teh"})//, "h", "ay"})//, "add", "allan", "marsh", "minnesot", "pam", "sch", "gemma"})
    @Param({"teh", "ay", "h","pelota", "bananan"})
    private String input;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }
            small = new LevenshteinTrie(new Trie(new TokenScanner(new InputStreamReader(inputStream))));
        }
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "dictionary.txt" + "' no encontrado en resources.");
            }
            big = new LevenshteinTrie(new Trie(new TokenScanner(new InputStreamReader(inputStream))));
        }
    }

    @Benchmark
    public void bmLevenshteinTrieInsertSmall() {
        small.getInsertions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieDeletionsSmall() {
        small.getDeletions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieSubstitutionSmall() {
        small.getSubstitutions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieCorrectionSmall() {
        small.getCorrections(input);
    }

    @Benchmark
    public void bmLevenshteinTrieInsertBig() {
        big.getInsertions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieDeletionsBig() {
        big.getDeletions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieSubstitutionBig() {
        big.getSubstitutions(input);
    }

    @Benchmark
    public void bmLevenshteinTrieCorrectionBig() {
        big.getCorrections(input);
    }
}
