package org.andreistrogonov;

import org.andreistrogonov.annotations.Fork;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForkProcessor {
    
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void executeForks(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Fork.class)) {
                executorService.submit(() -> {
                    try {
                        method.invoke(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void process() {
        try {
            // Using reflection to find and execute methods with @Fork annotation
            Method[] methods = this.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Fork.class)) {
                    executorService.submit(() -> {
                        try {
                            method.invoke(this);
                        } catch (Exception e) {
                            throw new RuntimeException("Error executing forked method", e);
                        }
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing methods", e);
        }
    }

    public void processNullCase() {
        try {
            // Placeholder for handling null-related logic
            // Here you should include logic specific for null value handling as needed
            // No action if no special logic required
        } catch (Exception e) {
            throw new RuntimeException("Error handling null case", e);
        }
    }
}
