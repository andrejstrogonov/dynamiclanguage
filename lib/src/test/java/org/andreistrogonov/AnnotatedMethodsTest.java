package org.andreistrogonov;

import org.andreistrogonov.annotations.Map;
import org.andreistrogonov.annotations.Reduce;
import org.andreistrogonov.annotations.Filter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AnnotatedMethodsTest {

    // Mock an internal DataProvider instance
    // Create a simple class to simulate data provision
    static class DataProvider {
        List<Integer> getNumbers() {
            return Arrays.asList(1, 2, 3, 4);
        }
    }

    @Mock
    private DataProvider dataProvider;  // Mocked data provider

    @InjectMocks
    private AnnotationProcessor annotationProcessor;

    @Test
    @Map
    public void testMapAnnotationTransformsElements() {
        when(dataProvider.getNumbers()).thenReturn(Arrays.asList(1, 2, 3, 4));
        
        List<Integer> result = annotationProcessor.doubleValues(dataProvider.getNumbers());
        
        List<Integer> expected = Arrays.asList(2, 4, 6, 8);
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    @Reduce
    public void testReduceAnnotationCombinesElements() {
        when(dataProvider.getNumbers()).thenReturn(Arrays.asList(1, 2, 3, 4));
        
        Integer result = annotationProcessor.reduceToSum(dataProvider.getNumbers());
        
        Integer expected = 10;
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    @Filter
    public void testFilterAnnotationRemovesElements() {
        when(dataProvider.getNumbers()).thenReturn(Arrays.asList(1, 2, 3, 4));
        
        List<Integer> result = annotationProcessor.filterEvenNumbers(dataProvider.getNumbers());
        
        List<Integer> expected = Arrays.asList(2, 4);
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    public void testMapAnnotationWithInvalidMethod() {
        when(dataProvider.getNumbers()).thenReturn(Arrays.asList(1, 2, 3, 4));
        when(annotationProcessor.doubleValues(dataProvider.getNumbers())).thenThrow(new IllegalStateException("Side effect detected"));
        
        assertThrows(IllegalStateException.class, () -> {
            annotationProcessor.doubleValues(dataProvider.getNumbers());
        });
        
        Mockito.verify(dataProvider).getNumbers();
    }
}
