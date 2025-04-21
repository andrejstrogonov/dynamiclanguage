package org.andreistrogonov;

import org.andreistrogonov.annotations.Merge;

public class SampleClassWithMerge {
    @Merge
    private int mergeField;

    private int data = 0;

    public void addData(int value) {
        data += value;
    }

    @Merge
    public int mergeMethod() {
        // Simple merge logic for testing
        return data;
    }
}
