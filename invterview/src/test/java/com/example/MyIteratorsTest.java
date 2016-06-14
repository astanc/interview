package com.example;

import com.google.common.collect.Iterables;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyIteratorsTest {

	@Test
    public void testStreams() {
		MyIterators.partitionWithStreams(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), 5).forEach(e -> System.out.println(e));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatPartitionSizeCannotBeZero() {
		MyIterators.partitionWithStreams(Collections.<Integer>emptyList(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatPartitionSizeCannotBeOne() {
		MyIterators.partitionWithStreams(Collections.<Integer>emptyList(), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatInputCannotBeNull() {
		MyIterators.partitionWithStreams(null, 2);
	}

	@Test
	public void testEmpty() {
		assertTrue(Iterables.isEmpty(MyIterators.partitionWithStreams(Collections.<Integer>emptyList(), 2)));
	}

	@Ignore
	@Test
	public void testThatSequenceSmallerThanPartitionIsProcessedCorrectly() {
		List<List<Integer>> expected = asList(asList(1, 2));
		assertEqualElements(expected, MyIterators.partitionWithStreams(asList(1, 2), 3));
	}
	
	@Test
	public void testThatSequenceWithoutTailIsProcessedCorrectly() {
		List<List<Integer>> expected = asList(asList(1, 2), asList(2, 3));
		assertEqualElements(expected, MyIterators.partitionWithStreams(asList(1, 2, 3), 2));
	}

	private void assertEqualElements(List<List<Integer>> expected,
			Iterable<Iterable<Integer>> partitions) {
		List<List<Integer>> actual = new ArrayList<>();
		for (Iterable<Integer> partition : partitions) {
			List<Integer> list = new LinkedList<>();
			for (Integer element : partition) {
				list.add(element);
			}
			actual.add(list);
		}
		assertEquals(expected, actual);
	}
	
	@Ignore
	@Test
	public void testThatSequenceWithTailIsProcessedCorrectly() {
		List<List<Integer>> expected = asList(asList(1, 2, 3), asList(3, 4));
		assertEqualElements(expected, MyIterators.partitionWithStreams(asList(1, 2, 3, 4), 3));
	}
}
