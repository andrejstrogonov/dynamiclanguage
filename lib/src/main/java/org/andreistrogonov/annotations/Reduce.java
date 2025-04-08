package org.andreistrogonov.annotations;

public @interface Reduce {
    /**
     * The operation to perform during reduction (e.g., sum, average).
     */
    String operation() default "sum";

    /**
     * The initial value for the reduction operation.
     */
    int initialValue() default 0;
}