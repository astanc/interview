package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Iterables;

public class SplitterTest {

	@Test(expected = IllegalArgumentException.class)
	public void testThatPartitionSizeCannotBeZero() {
		MyIterators.partitionsInclusive(Collections.<Integer>emptyList(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatPartitionSizeCannotBeOne() {
		MyIterators.partitionsInclusive(Collections.<Integer>emptyList(), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatInputCannotBeNull() {
		MyIterators.partitionsInclusive(null, 2);
	}

	@Test
	public void testEmpty() {
		assertTrue(Iterables.isEmpty(MyIterators.partitionsInclusive(Collections.<Integer>emptyList(), 2)));
	}

	@Test
	public void testThatSequenceSmallerThanPartitionIsProcessedCorrectly() {
		List<List<Integer>> expected = Arrays.asList(Arrays.asList(1,2));
		assertEqualElements(expected, MyIterators.partitionsInclusive(Arrays.asList(1, 2), 3));
	}
	
	@Test
	public void testThatSequenceWithoutTailIsProcessedCorrectly() {
		List<List<Integer>> expected = Arrays.asList(Arrays.asList(1,2), Arrays.asList(2, 3));
		assertEqualElements(expected, MyIterators.partitionsInclusive(Arrays.asList(1, 2, 3), 2));
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
	
	@Test
	public void testThatSequenceWithTailIsProcessedCorrectly() {
		List<List<Integer>> expected = Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(3,4));
		assertEqualElements(expected, MyIterators.partitionsInclusive(Arrays.asList(1, 2, 3, 4), 3));
	}
}
