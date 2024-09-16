package ru.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Arrays;

@Fork(0)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class SortingBenchmark {

    @Param({"ascending", "descending", "random"})
    public String dataType;

    private Integer[] testArray;

    private static final int SIZE = 10000;

    @Setup(Level.Invocation)
    public void setUp() {
        Integer[] originalArray = generateArray(SIZE);
        testArray = Arrays.copyOf(originalArray, originalArray.length);

        switch (dataType) {
            case "ascending":
                Arrays.sort(testArray);
                break;
            case "descending":
                Arrays.sort(testArray, (a, b) -> b - a);
                break;
            case "random":
                break;
        }
    }

    @Benchmark
    public void testBubbleSort() {
        Integer[] copy = Arrays.copyOf(testArray, testArray.length);
        SortAlgorithms.bubbleSort(copy);
    }

    @Benchmark
    public void testQuickSort() {
        Integer[] copy = Arrays.copyOf(testArray, testArray.length);
        SortAlgorithms.quickSort(copy);
    }

    /**
     * Генерация рандомного массива.
     *
     * @param size размер массива
     *
     * @return массив
     */
    private Integer[] generateArray(int size) {
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
}
