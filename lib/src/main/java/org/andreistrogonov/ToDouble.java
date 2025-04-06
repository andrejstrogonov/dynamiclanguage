package org.andreistrogonov;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate a method's result should be converted to double.
 * Can specify a default double value.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToDouble {
    /**
     * Default value to use when conversion is not possible.
     *
     * @return the default value
     */
    double defaultValue() default 0.0;
}
