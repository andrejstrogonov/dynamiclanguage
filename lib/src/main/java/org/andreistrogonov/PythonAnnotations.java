package org.andreistrogonov;

public class PythonAnnotations {
    public PythonAnnotations() {
    }

    /**
     * Converts an object to an Integer if possible.
     *
     * @param obj the object to convert
     * @return the Integer representation or null if conversion is not possible
     */
    public Integer toInteger(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Converts an object to a Double if possible.
     *
     * @param obj the object to convert
     * @return the Double representation or null if conversion is not possible
     */
    public Double toDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        } else if (obj instanceof String) {
            try {
                return Double.parseDouble((String) obj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Converts an object to a String.
     *
     * @param obj the object to convert
     * @return the String representation
     */
    public String toString(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}
