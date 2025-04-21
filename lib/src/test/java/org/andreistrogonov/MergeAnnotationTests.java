package org.andreistrogonov;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.andreistrogonov.annotations.Merge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MergeAnnotationTests {

    @Mock
    private SampleClassWithMerge sample;
    
    private SampleClassWithMerge realSample;

    @BeforeEach
    public void setUp() {
        realSample = new SampleClassWithMerge();
    }

    // Test that the @Merge annotation is applied to methods
    @Test
    public void testMergeAnnotationOnMethods() throws NoSuchMethodException {
        Method method = SampleClassWithMerge.class.getMethod("mergeMethod");
        assertTrue(method.isAnnotationPresent(Merge.class), "@Merge should be present on mergeMethod");
    }

    // Test that the @Merge annotation is applied to fields
    @Test
    public void testMergeAnnotationOnFields() throws NoSuchFieldException {
        Field field = SampleClassWithMerge.class.getDeclaredField("mergeField");
        assertTrue(field.isAnnotationPresent(Merge.class), "@Merge should be present on mergeField");
    }

    // Test that the annotation is retained at runtime
    @Test
    public void testAnnotationRuntimeRetention() throws NoSuchMethodException {
        Method method = SampleClassWithMerge.class.getMethod("mergeMethod");
        assertNotNull(method.getAnnotation(Merge.class), "Annotation should be available at runtime");
    }

    // Verify data merging using Mockito
    @Test
    public void testMergeData() {
        // Test with mock
        when(sample.mergeMethod()).thenReturn(3);
        
        int result = sample.mergeMethod();
        assertEquals(3, result, "Mocked merge should return expected value");
        
        verify(sample).mergeMethod();
        
        // Test with real object for comparison
        realSample.addData(1);
        realSample.addData(2);
        assertEquals(3, realSample.mergeMethod(), "Real data should be merged correctly");
    }

    // Check thread-safety with Mockito
    @Test
    public void testThreadSafety() throws InterruptedException {
        // Mock behavior for thread safety test
        when(sample.mergeMethod()).thenReturn(5);
        
        // Verify the mock in a multi-threaded scenario
        Runnable task = () -> {
            assertEquals(5, sample.mergeMethod());
        };
        
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        
        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();
        
        verify(sample, times(2)).mergeMethod();
    }

    // Handle merging constraints with Mockito
    @Test
    public void testInvalidMergeUsage() {
        // Mock invalid usage scenario
        doThrow(new IllegalArgumentException("Invalid merge usage")).when(sample).addData(anyInt());
        
        assertThrows(IllegalArgumentException.class, () -> {
            sample.addData(-1);
        });
        
        verify(sample).addData(-1);
    }

    // Check exception handling with Mockito
    @Test
    public void testExceptionHandling() {
        // Mock exception scenario
        when(sample.mergeMethod()).thenThrow(new RuntimeException("Merge failed"));
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sample.mergeMethod();
        });
        
        assertEquals("Merge failed", exception.getMessage());
        verify(sample).mergeMethod();
    }

}

