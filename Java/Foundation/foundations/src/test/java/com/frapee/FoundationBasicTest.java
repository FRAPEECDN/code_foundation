package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Foundation Basic, aka example callings
 */
public class FoundationBasicTest {

    private FoundationBasics fb;

    /**
     * Compare function using predefined Arrays class
     * @param actual - actual results from test
     * @param expected - expected values for the test
     */
    private void compareResultsWithArrays(int[] actual, int[] expected) {
        assertThat(Arrays.compare(actual, expected), equalTo(0));
    }   

    @BeforeEach
    public void setupTests() {
        fb = new FoundationBasics();
    }

    @Test
    public void testFunctions() {
        int startValue = 20;
        fb.passByValueOnly(startValue);
        assertThat(startValue, equalTo(20));
        startValue = fb.returnChanged(startValue);
        assertThat(startValue, not(equalTo(20)));
    }

    @Test
    public void testIfs() {
        int valueA = 10;
        int valueB = 20;

        // Only if tests
        int result = fb.ifOnly(valueA, valueB);
        assertThat(result, equalTo(-1));
        result = fb.ifOnly(valueB, valueA);
        assertThat(result, equalTo(0));

        // Test if/else
        result = fb.ifAndElse(valueA, valueB);
        assertThat(result, equalTo(-1));
        result = fb.ifAndElse(valueB, valueA);
        assertThat(result, equalTo(1));

        valueA = 5;
        valueB = 15;
        result = fb.ifMulti(valueA, valueB);
        assertThat(result, equalTo(-1));

        valueA = 15;
        valueB = 5;
        result = fb.ifMulti(valueA, valueB);
        assertThat(result, equalTo(1));

        valueA = 5;
        valueB = 5;
        result = fb.ifMulti(valueA, valueB);
        assertThat(result, equalTo(0));
    }

    @Test
    public void testSwitch() {
        for (int input = 0; input < 5; input++) {
            int resultO = fb.switchOlder(input);
            assertThat(resultO, equalTo(input == 0 ? 0 : (input == 1) ? 1 : -1));
            int resultN = fb.switchNew(input);
            assertThat(resultN, equalTo(input == 0 ? 0 : (input == 1) ? 1 : -1));
        }
    }

    @Test
    public void testSwitchUsingString() {
        String inputString = "ABCDEF";
        for (int i = 0; i < 5; i++) {
            String input = inputString.substring(i, i + 1);
            int resultO = fb.switchString(input);
            assertThat(resultO, equalTo(input.equals("A") ? 0 : (input.equals("B") ? 1 : -1)));
        }
    }

    @Test
    public void testLoops() {
        int[] expectedArray = {0, -1, -2, -3, -4, -5, -6, -7, -8, -9};
        int[] result = fb.whileLoop(10);
        compareResultsWithArrays(result, expectedArray);
        int[] result2 = fb.repeatLoop(10);
        compareResultsWithArrays(result2, expectedArray);
        int[] result3 = fb.forLoop(10);
        compareResultsWithArrays(result3, expectedArray);
        int[] setupArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] result4 = fb.forEachLoop(setupArray);
        compareResultsWithArrays(result4, expectedArray);
    }
}
