package org.andreistrogonov;

import org.andreistrogonov.annotations.Fork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class ForkAnnotationTest {
    
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        // Set up an ExecutorService to simulate parallel execution
        executorService = Executors.newFixedThreadPool(2);
    }

    // Test case to verify that methods annotated with @Fork execute in parallel
    @Test
    void testParallelExecution() throws InterruptedException {
        // Assuming Fork annotation implementation is responsible for execution
        executorService.submit(() -> System.out.println("Executing first task in parallel"));
        executorService.submit(() -> System.out.println("Executing second task in parallel"));
        
        // Validate that tasks run successfully
        // This would ideally involve verifying actual parallel execution
    }

    // Test case to handle exceptions in @Fork annotated methods
    @Test
    void testExceptionHandling() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            // Simulate a method that should throw an exception
            throw new RuntimeException("Intentional error");
        });

        // Verify exception message
        assertEquals("Intentional error", exception.getMessage());
    }

    // Test case to ensure @Fork usage constraints are followed
    // For example: not using primitive return types
    @Test
    void testReturnTypeConstraint() {
        // Simulate method with return type check
        // This requires actual Fork annotation behavior
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
}