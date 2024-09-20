package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class TrieBenchmark {

    private Trie smallTrie, trie;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        smallTrie = new Trie(new TokenScanner(new FileReader("smallDictionary.txt")));
        trie = new Trie(new TokenScanner(new FileReader("dictionary.txt")));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmTrieDictionaryContainsSmall() {
        smallTrie.isWord("apple");
        smallTrie.isWord("Banana");
        smallTrie.isWord("pineapple");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmTrieDictionaryCreateSmall() throws IOException {
        new Trie(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmTrieDictionaryContains() {
        trie.isWord("apple");
        trie.isWord("Banana");
        trie.isWord("pineapple");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmTrieDictionaryCreate() throws IOException {
        new Trie(new TokenScanner(new FileReader("dictionary.txt")));
    }
}
