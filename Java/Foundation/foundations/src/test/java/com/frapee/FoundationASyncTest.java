package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FoundationASyncTest {

    private FoundationAsync fat;
    private final int NUMBER_TEST = 20;
    private final long NUMBER_EXPECTED = 2432902008176640000L;

    @BeforeEach
    public void setupTests() {
        fat = new FoundationAsync();
    }

    @Test
    public void testAsyncThread() {
        long actual = fat.useThread(NUMBER_TEST);
        assertThat(actual, equalTo(NUMBER_EXPECTED));
    }

    @Test
    public void testAsyncVirtualThread() {
        long actual = fat.useVirtualThread(NUMBER_TEST);
        assertThat(actual, equalTo(NUMBER_EXPECTED));
    }    

    @Test
    public void testAsyncTask() {
        Pair<Long, Long> actual = fat.useTask(NUMBER_TEST);
        assertThat(actual.getValue0(), greaterThan(0L));
        assertThat(actual.getValue1(), equalTo(NUMBER_EXPECTED));
    }

    @Test
    public void testAsyncCompletable() {
        Pair<Long, Long> actual = fat.useCompletable(NUMBER_TEST);
        assertThat(actual.getValue0(), greaterThan(0L));
        assertThat(actual.getValue1(), equalTo(NUMBER_EXPECTED));
    }
}
