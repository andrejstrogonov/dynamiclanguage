package org.andreistrogonov;

import java.lang.annotation.Annotation;

public class PythonAnnotations implements ToInteger, ToDouble {

    /**
     * @return
     */
    @Override
    public String toDouble() {
        return Double.toString(0.0);
    }

    /**
     * @return
     */
    @Override
    public String toInteger() {
        return Integer.toString((int) 0.0);
    }

    /**
     * Returns the annotation interface of this annotation.
     *
     * @return the annotation interface of this annotation
     * @apiNote Implementation-dependent classes are used to provide
     * the implementations of annotations. Therefore, calling {@link
     * Object#getClass getClass} on an annotation will return an
     * implementation-dependent class. In contrast, this method will
     * reliably return the annotation interface of the annotation.
     * @see Enum#getDeclaringClass
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
