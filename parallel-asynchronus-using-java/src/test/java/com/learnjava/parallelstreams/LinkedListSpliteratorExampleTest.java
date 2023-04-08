package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListSpliteratorExampleTest {

    private LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> result = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false);
        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_parallel() {
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> result = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        assertEquals(size, result.size());
    }
}