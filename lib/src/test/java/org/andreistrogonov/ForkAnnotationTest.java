package org.andreistrogonov;

import org.andreistrogonov.annotations.Fork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ForkAnnotationTest {
    
    @Mock
    private ExecutorService executorService;
    
    @InjectMocks
    private ForkProcessor forkProcessor;

    @BeforeEach
    void setUp() {
        // ExecutorService is mocked, no need to initialize
    }

    // Test case to verify that methods annotated with @Fork execute in parallel
    @Test
    void testParallelExecution() {
        // Setup mock behavior
        doNothing().when(executorService).submit(any(Runnable.class));
        
        // Execute the processor that should use the Fork annotation
        forkProcessor.process();
        
        // Verify that tasks were submitted to the executor service
        verify(executorService, times(2)).submit(any(Runnable.class));
    }

    // Test case to handle exceptions in @Fork annotated methods
    @Test
    void testExceptionHandling() {
        // Setup mock to throw exception when submit is called
        doThrow(new RuntimeException("Intentional error"))
            .when(executorService).submit(any(Runnable.class));
            
        // Execute and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            forkProcessor.process();
        });

        // Verify exception message
        assertEquals("Intentional error", exception.getMessage());
        
        // Verify the interaction happened
        verify(executorService).submit(any(Runnable.class));
    }

    // Test case to ensure @Fork usage constraints are followed
    // For example: not using primitive return types
    @Test
    void testReturnTypeConstraint() {
        // Create a mock ForkProcessor class with methods to check
        ForkProcessor mockProcessor = mock(ForkProcessor.class);
        
        // Simulate method with return type check
        boolean validReturnType = true;
        for (Method method : ForkProcessor.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Fork.class)) {
                if (method.getReturnType().isPrimitive()) {
                    validReturnType = false;
                    break;
                }
            }
        }
        assertTrue(validReturnType, "Methods annotated with @Fork should not return primitive types.");
    }

    // Test case to ensure proper usage documentation
    @Test
    void testDocumentationPresent() {
        // Check if javadoc documentation is present for Fork annotation
        // This test should introspect the Fork class for documentation comments
        try {
            InputStream stream = Fork.class.getResourceAsStream("/com/example/annotations/Fork.java");
            if (stream == null) {
                fail("Fork.java source file not found in resources.");
            }

            String fileContents = new BufferedReader(new InputStreamReader(stream))
                    .lines().collect(Collectors.joining("\n"));

            boolean javadocExists = fileContents.contains("/**") && fileContents.contains("*/")
                    && fileContents.contains("@Fork");

            assertTrue(javadocExists, "Documentation (Javadoc) for @Fork annotation is not present.");
        } catch (Exception e) {
            fail("Exception occurred while checking documentation presence: " + e.getMessage());
        }
    }
    
    // Test case to verify Fork annotation processor handles null values correctly
    @Test
    void testNullHandling() {
        // Setup mock to handle null values
        doNothing().when(executorService).submit(isNull());
        
        // Verify the processor handles null values appropriately
        assertDoesNotThrow(() -> {
            forkProcessor.processNullCase();
        });
        
        // Verify interaction with null parameter
        verify(executorService).submit(isNull());
    }
}
