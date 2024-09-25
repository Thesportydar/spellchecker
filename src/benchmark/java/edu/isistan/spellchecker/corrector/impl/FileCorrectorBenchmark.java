package edu.isistan.spellchecker.corrector.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Corrector;

@State(Scope.Benchmark)
public class FileCorrectorBenchmark {
	private Corrector small, big;
	
	@Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException, FileCorrector.FormatException {
		big = FileCorrector.make("misspellings.txt");
		small = FileCorrector.make("smallMisspellings.txt");
	}

	@Param({"adres", "airporta", "algoritm", "cruz", "archivo", "aclimatacion" })
	//@Param({"tigger", "chimpanze","pelota", "gose", "banana", "tomate"})
	private String input;

	@Benchmark
	public void bmFileCorrectorCorrectionsSmall() {
		small.getCorrections(input);
	}
	
	@Benchmark
	public void bmFileCorrectorCorrectionsBig() { big.getCorrections(input); }
}
