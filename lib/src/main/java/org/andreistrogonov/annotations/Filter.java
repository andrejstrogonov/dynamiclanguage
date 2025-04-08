package org.andreistrogonov.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to filter methods or data based on specified criteria.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Filter {
    /**
     * The criteria to filter by.
     */
    String criteria() default "";

    /**
     * The priority of the filter.
     */
    int priority() default 0;
}