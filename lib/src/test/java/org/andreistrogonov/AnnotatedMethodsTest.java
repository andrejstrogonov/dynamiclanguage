package org.andreistrogonov;

import org.andreistrogonov.annotations.Map;
import org.andreistrogonov.annotations.Reduce;
import org.andreistrogonov.annotations.Filter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotatedMethodsTest {

    @Test
    @Map
    public void testMapAnnotationTransformsElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> expected = Arrays.asList(2, 4, 6, 8);
        List<Integer> result = numbers.stream()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    @Reduce
    public void testReduceAnnotationCombinesElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        Integer expected = 10;
        Integer result = numbers.stream()
            .reduce(0, Integer::sum);
        assertEquals(expected, result);
    }

    @Test
    @Filter
    public void testFilterAnnotationRemovesElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> expected = Arrays.asList(2, 4);
        List<Integer> result = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testMapAnnotationWithInvalidMethod() {
        assertThrows(IllegalStateException.class, () -> {
            // simulate a method with side effects or invalid application of @Map
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
            numbers.forEach(n -> {
                if (n == 2) {
                    throw new IllegalStateException("Side effect detected");
                }
            });

            numbers.stream()
                .map(n -> {
                    if (n == 2) {
                        throw new IllegalStateException("Side effect detected");
                    }
                    return n * 2;
                })
                .collect(Collectors.toList());
        });
    }
}