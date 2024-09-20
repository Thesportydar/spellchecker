package edu.isistan.spellchecker;

import edu.isistan.spellchecker.corrector.impl.LevenshteinBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include("bmLevenshtein.*")
                .forks(1)  // Usar 1 fork para reducir la ejecución
                .warmupIterations(2)  // 3 iteraciones de calentamiento
                .measurementIterations(5)  // 5 iteraciones de medición
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .param("input", "teh", "h", "ay", "sch", "add", "allan", "marsh", "minnesot", "pam", "gemma")
                .build();

        new Runner(opt).run();
    }
}