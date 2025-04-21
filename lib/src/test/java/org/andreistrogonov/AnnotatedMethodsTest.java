package org.andreistrogonov;

import org.andreistrogonov.annotations.Map;
import org.andreistrogonov.annotations.Reduce;
import org.andreistrogonov.annotations.Filter;
import org.gradle.internal.impldep.org.testng.annotations.DataProvider;
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

    @Mock
    private DataProvider dataProvider;  // Mocked data provider

    @InjectMocks
    private AnnotationProcessor annotationProcessor;

    @Test
    @Map
    public void testMapAnnotationTransformsElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        when(dataProvider.getNumbers()).thenReturn(numbers);
        
        List<Integer> result = annotationProcessor.mapOperation();
        
        List<Integer> expected = Arrays.asList(2, 4, 6, 8);
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    @Reduce
    public void testReduceAnnotationCombinesElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        when(dataProvider.getNumbers()).thenReturn(numbers);
        
        Integer result = annotationProcessor.reduceOperation();
        
        Integer expected = 10;
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    @Filter
    public void testFilterAnnotationRemovesElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        when(dataProvider.getNumbers()).thenReturn(numbers);
        
        List<Integer> result = annotationProcessor.filterOperation();
        
        List<Integer> expected = Arrays.asList(2, 4);
        assertEquals(expected, result);
        Mockito.verify(dataProvider).getNumbers();
    }

    @Test
    public void testMapAnnotationWithInvalidMethod() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        when(dataProvider.getNumbers()).thenReturn(numbers);
        when(annotationProcessor.mapOperationWithSideEffects()).thenThrow(new IllegalStateException("Side effect detected"));
        
        assertThrows(IllegalStateException.class, () -> {
            annotationProcessor.mapOperationWithSideEffects();
        });
        
        Mockito.verify(dataProvider).getNumbers();
    }
}
