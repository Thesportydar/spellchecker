package edu.isistan.spellchecker.corrector.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Corrector;

@State(Scope.Benchmark)
public class FileCorrectorBenchmark {
	private Corrector cSmall;
	private Corrector c;
	
	@Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException, FileCorrector.FormatException {
		c = FileCorrector.make("Misspellings.txt");
		cSmall = FileCorrector.make("smallMisspellings.txt");
	}
	
	@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void bmFileCorrectorSmallCorrections() {
		cSmall.getCorrections("TIGGER");
	}
	
	@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void bmFileCorrectorCorrections() {
		c.getCorrections("TIGGER");
	}
}
