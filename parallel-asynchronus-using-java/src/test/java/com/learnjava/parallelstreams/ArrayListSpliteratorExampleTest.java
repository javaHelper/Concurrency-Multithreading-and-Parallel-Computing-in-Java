package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSpliteratorExampleTest {

    private ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_parallel() {
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_IntStream() {
        int n = 1000000;
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(1000000, 2, false);

        assertEquals(n, result.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_IntStream_parallel() {
        int n = 1000000;

        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(1000000, 2, true);
        assertEquals(n, result.size());
    }
}