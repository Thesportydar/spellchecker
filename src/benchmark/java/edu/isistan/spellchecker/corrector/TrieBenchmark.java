package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.corrector.impl.LevenshteinTrie;
import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class TrieBenchmark {

    private Trie smallTrie;


    @Setup(Level.Trial)
    public void setUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }

            smallTrie = new Trie(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }

    @Param({"ah", "te", "tah", "ai", "ey", "rey", "carrot", "banana", "durian", "tomato","pepper","radish"})
    private String input;

    @Benchmark
    public void bmTrieSmallContains() {
        smallTrie.isWord(input);
    }

    @Benchmark
    public void bmTrieDictionaryCreateSmall() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }

            new Trie(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }

    @Benchmark
    public void bmTrieDictionaryCreate() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "dictionary.txt" + "' no encontrado en resources.");
            }

            new Trie(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }
}
