package org.andreistrogonov.annotations;

/**
 * Annotation to map keys to values.
 */
public @interface Map {
    /**
     * The key to map from.
     */
    String key() default "";

    /**
     * The value to map to.
     */
    String value() default "";
}

