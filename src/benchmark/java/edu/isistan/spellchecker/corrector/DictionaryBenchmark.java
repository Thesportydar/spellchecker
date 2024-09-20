package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DictionaryBenchmark {
    private Dictionary small, dictionary;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        small = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        dictionary = new Dictionary(new TokenScanner(new FileReader("dictionary.txt")));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmDictionaryContainsSmall() {
        small.isWord("apple");
        small.isWord("Banana");
        small.isWord("pineapple");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmDictionaryCreateSmall() throws IOException {
        new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmDictionaryContains() {
        dictionary.isWord("apple");
        dictionary.isWord("Banana");
        dictionary.isWord("pineapple");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmDictionaryCreate() throws IOException {
        new Dictionary(new TokenScanner(new FileReader("dictionary.txt")));
    }
}
