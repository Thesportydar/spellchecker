package edu.isistan.spellchecker.corrector.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        small = new LevenshteinTrie(new Trie(new TokenScanner(new FileReader("smallDictionary.txt"))));
        big = new LevenshteinTrie(new Trie(new TokenScanner(new FileReader("dictionary.txt"))));
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
