package org.andreistrogonov;
import org.andreistrogonov.annotations.Map;
import org.andreistrogonov.annotations.Fork;
import org.andreistrogonov.annotations.Merge;
import org.andreistrogonov.annotations.Filter;
import org.andreistrogonov.annotations.Reduce;

import java.util.List;
import java.util.stream.Collectors;

public class AnnotationProcessor {

    @Map
    public List<Integer> doubleValues(List<Integer> numbers) {
        return numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());
    }

    @Fork
    public void processInParallel(Runnable task1, Runnable task2) {
        new Thread(task1).start();
        new Thread(task2).start();
    }

    @Merge
    public int sumAndMerge(List<Integer> numbers1, List<Integer> numbers2) {
        return numbers1.stream().mapToInt(Integer::intValue).sum() +
                numbers2.stream().mapToInt(Integer::intValue).sum();
    }

    @Filter
    public List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }

    @Reduce
    public int reduceToSum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::sum);
    }
}
