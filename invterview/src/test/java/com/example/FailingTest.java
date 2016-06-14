package com.example;

import org.junit.Test;

import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

public class FailingTest {


    /**
     * This test is supposed to show how lazy bastard is.
     */
    @Test
    public void testLazyBastard() throws Exception {

        final Iterable<Integer> iterable = getIntegers();

        Iterable<Iterable<Integer>> iterables = MyIterators.partitionsInclusive(iterable, 2);
        Iterator<Iterable<Integer>> iterator = iterables.iterator();

        Iterator<Integer> chain1 = iterator.next().iterator();
        Iterator<Integer> chain2 = iterator.next().iterator();

        assertEquals("First chain element is [0, 1]", valueOf(0), chain1.next());
        assertEquals("Second chain element is [1, 2]", valueOf(1), chain2.next());
        assertEquals("Next and last elem in chain #1 is 1", valueOf(1), chain1.next());
        assertEquals("Next and last elem in chain #2 is 2", valueOf(2), chain2.next());

        // TODO: reasonable question how many elements we can afford to cache?

    }

    /**
     * Verify actual laziness (reads minimum elements from input sequence)
     * We read 0 elements, though it fails miserably.
     * So bastard is not lazy enough.
     */
    @Test
    public void absoluteLaziness() throws Exception {
        final Iterable<Integer> iterable = getIntegers();
        Iterable<Iterable<Integer>> iterables = MyIterators.partitionsInclusive(iterable, Integer.MAX_VALUE);
        Iterator<Iterable<Integer>> bastard = iterables.iterator();
        bastard.next();
    }

    private Iterable<Integer> getIntegers() {
        final Stream<Integer> myInfiniteStream = IntStream.iterate(0, operand -> operand++).boxed();
        return myInfiniteStream::iterator;
    }
}
