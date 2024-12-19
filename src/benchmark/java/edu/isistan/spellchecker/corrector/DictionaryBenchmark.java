package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.corrector.impl.Levenshtein;
import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DictionaryBenchmark {
    private Dictionary small;
    @Param({"ah", "te", "tah", "ai", "ey", "rey", "carrot", "banana", "durian", "tomato","pepper","radish"})
    private String input;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }
            small = new Dictionary(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }

    @Benchmark
    public void bmDictionarySmallContains() {
        small.isWord(input);
    }

    @Benchmark
    public void bmDictionarySmallCreate() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }
            new Dictionary(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }

    @Benchmark
    public void bmDictionaryCreate() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("smallDictionary.txt")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo '" + "smallDictionary.txt" + "' no encontrado en resources.");
            }
            new Dictionary(new TokenScanner(new InputStreamReader(inputStream)));
        }
    }
}
