package benchmark;

import corona.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@State(Scope.Benchmark) //@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})

public class BenchmarkCoronaVirus {

    private final ArrayList<URL> urls = new ArrayList<>();

    @Param({"20"})//@Param({"20", "5000", "1000000"})
    private String size;


    @Benchmark // ! avoid calls that return nothing
    public int benchmarkCoronaVirus() {
        String folder = "data";
        String size = "20";
        String country = "FrItSp";
        CoronaVirus covidVirus = new CoronaVirus(folder, size, country);
        covidVirus.methodNaive();
        return 1;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkCoronaVirus.class.getSimpleName())
                //.forks(1)
                .build();

        new Runner(opt).run();
    }


}
