package org.andreistrogonov.annotations;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Fork {
}

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
}