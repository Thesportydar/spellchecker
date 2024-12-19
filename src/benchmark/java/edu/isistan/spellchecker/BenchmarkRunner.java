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
                .include("bmLevenshtein(?:Insert|Deletions|Substitution|Correction)")
                .forks(1)  // Usar 1 fork para reducir la ejecución
                .warmupIterations(1)  // 2 iteraciones de calentamiento
                .warmupTime(org.openjdk.jmh.runner.options.TimeValue.seconds(1))
                .measurementIterations(1)  // 5 iteraciones de medición
                .measurementTime(org.openjdk.jmh.runner.options.TimeValue.seconds(1))
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .build();

        new Runner(opt).run();
    }
}