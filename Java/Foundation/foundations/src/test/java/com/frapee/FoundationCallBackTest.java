package com.frapee;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoundationCallBackTest {

    /**
     * Compare function using predefined Arrays class
     * @param actual - actual results from test
     * @param expected - expected values for the test
     */
    private void compareResultsWithArrays(List<Integer> actual, List<Integer> expected) {
        assertThat(actual.size(), equalTo(expected.size()));
        assertTrue(actual.containsAll(expected));
    }

    private FoundationCallBack fcb;

    @BeforeEach
    public void setupTests() {
        CallBackImplementation cbi = new CallBackImplementation();
        fcb = new FoundationCallBack(cbi);
    }

    @Test
    public void runCallBackTest() {
        List<Integer> actual = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = fcb.runCallBack();
        compareResultsWithArrays(result, actual);
    }

    @Test
    public void runCallBackAccept() {
        // final calculated added number
        AtomicInteger addedNumber = new AtomicInteger();
        int initialNumber = 20; // start number
        int addNumber = 5; // value being added

        // The consumer function is implemented as lambda
        fcb.getNumbers(initialNumber, (addNumbers) -> {
            fcb.addNumbers(initialNumber, addNumber, (newNumber) -> {
                addedNumber.set(newNumber);
             });
        });
        assertThat(initialNumber + addNumber, equalTo(addedNumber.get()));
    }
}
