package edu.isistan.spellchecker.corrector.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

@State(Scope.Benchmark)
public class SwapCorrectorBenchmark {
	private SwapCorrector small, big;
	
	@Setup(Level.Trial)//cambiar a invocation
    public void setUp() throws IOException {
        small = new SwapCorrector(new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt"))));
        big = new SwapCorrector(new Dictionary(new TokenScanner(new FileReader("dictionary.txt"))));
    }

    @Param({"ay", "pa", "evangelien", "rceam", "americainzation", "atencion", "gemma", "ciivlian", "pollo", "thorw"})
    //@Param({"teh", "ay", "h","pelota", "banane"})
    private String input;

	@Benchmark
    public void bmSwapCorrectionsSmall() {
		small.getCorrections(input);
    }

    @Benchmark
    public void bmSwapCorrectionsBig() {
        big.getCorrections(input);
    }
}
