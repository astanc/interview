package com.example;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.Validate;

public class MyIterators {

	public static <T> Iterable<Iterable<T>> partitionsInclusive(final Iterable<T> input, final int partitionSize) {
		Validate.isTrue(partitionSize > 1, "Partition size should be greater than 1.", partitionSize);
		Validate.isTrue(input != null, "Input sequence should not be null");
		final Iterator<T> iterator = input.iterator();
		return () -> new PartitioningIterator<T>(iterator, partitionSize);
	}
	
	private static class PartitioningIterator<E> implements Iterator<Iterable<E>> {

		private final Iterator<E> baseIterator;
		private final int chunkSize;
		private Optional<E> lastElement;

		PartitioningIterator(final Iterator<E> baseIterator, int chunkSize) {
			this.baseIterator = baseIterator;
			this.chunkSize = chunkSize;
			lastElement = Optional.empty();
		}
		
		@Override
		public boolean hasNext() {
			return baseIterator.hasNext();
		}

		@Override
		public Iterable<E> next() {
			final IntStream chunk = IntStream.range(0, chunkSize);
			return () -> {
				return chunk.mapToObj(e -> {
					if (baseIterator.hasNext()) {
						if (e > 0 && e < chunkSize - 1) {
							return baseIterator.next();
						} else if (e == 0) {
							return lastElement.orElseGet(baseIterator::next);
						} else {
							lastElement = Optional.of(baseIterator.next());
							return lastElement.get();
						}
					} else {
						return null;
					}
				}).filter(Objects::nonNull).iterator();
			};
		}
	}
}
