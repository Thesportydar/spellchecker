package edu.isistan.spellchecker.corrector.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

@State(Scope.Benchmark)
public class SwapCorrectorBenchmark {
	private Dictionary dictionary;
	private SwapCorrector swap;
	
	@Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        dictionary = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        swap = new SwapCorrector(dictionary);
    }
	
	@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void bmSwapCorrectionsSimple() {
		swap.getCorrections("oYurs");
    }
	
}
