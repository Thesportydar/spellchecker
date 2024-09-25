package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.openjdk.jmh.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class TrieBenchmark {

    private Trie smallTrie;

    @Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        smallTrie = new Trie(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    @Param({"ah", "te", "tah", "ai", "ey", "rey", "carrot", "banana", "durian", "tomato","pepper","radish"})
    private String input;

    @Benchmark
    public void bmTrieSmallContains() {
        smallTrie.isWord(input);
    }

    @Benchmark
    public void bmTrieDictionaryCreateSmall() throws IOException {
        new Trie(new TokenScanner(new FileReader("smallDictionary.txt")));
    }

    @Benchmark
    public void bmTrieDictionaryCreate() throws IOException {
        new Trie(new TokenScanner(new FileReader("dictionary.txt")));
    }
}
