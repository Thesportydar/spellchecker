package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DictionaryBenchmark {
    private Dictionary small;
    @Param({"ah", "te", "tah", "ai", "ey", "rey", "carrot", "banana", "durian", "tomato","pepper","radish"})
    private String input;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        small = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    @Benchmark
    public void bmDictionarySmallContains() {
        small.isWord(input);
    }

    //@Benchmark
    public void bmDictionarySmallCreate() throws IOException {
        new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    //@Benchmark
    public void bmDictionaryCreate() throws IOException {
        new Dictionary(new TokenScanner(new FileReader("dictionary.txt")));
    }
}
