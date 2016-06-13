package com.example;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Iterators;

public class MyIterators {

	public static <T> Iterable<Iterable<T>> partitionsInclusive(final Iterable<T> input, final int partitionSize) {
		Validate.isTrue(partitionSize > 1, "Partition size should be greater than 1.", partitionSize);
		Validate.isTrue(input != null, "Input sequence should not be null");
		final Iterator<T> iterator = input.iterator();
		return new Iterable<Iterable<T>>() {
			@Override
			public Iterator<Iterable<T>> iterator() {
				if (iterator.hasNext()) {
					return new PartsIterator<T>(iterator, partitionSize);
				} else {
					return Iterators.emptyIterator();
				}
			}
		};
	}
	
	private static class PartsIterator<T> implements Iterator<Iterable<T>> {

		private final Iterator<List<T>> baseIterator;
		private final int partitionSize;
		private final int lastIndexInPartition;
		private T first;
		
		public PartsIterator(Iterator<T> input, int partitionSize) {
			this.partitionSize = partitionSize - 1;
			this.lastIndexInPartition = partitionSize - 2;
			this.first = input.next();
			this.baseIterator = Iterators.partition(input, this.partitionSize);
		}
		
		@Override
		public boolean hasNext() {
			return baseIterator.hasNext();
		}

		@Override
		public Iterable<T> next() {
			List<T> partition = baseIterator.next();
			final Iterator<T> result = Iterators.concat(Iterators.singletonIterator(first), partition.iterator());
			if (baseIterator.hasNext()) {
				first = partition.get(lastIndexInPartition);
			}
			return new Iterable<T>() {

				@Override
				public Iterator<T> iterator() {
					return result;
				}
			};
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	public static <T> Stream<Collection<T>> partitionWithStreams(final Stream<T> input, final int partitionSize) {
		return input.map(partition(new PartitionWrapper<>(partitionSize))).filter(e -> e.size() > 0);
	}
	

	private static class PartitionWrapper<T> {
		final int size;
		private List<T> partition = new LinkedList<>();
		
		PartitionWrapper(final int size) {
			this.size = size - 1;
		}
		
		void add(T element) {
			partition.add(element);
		}
		
		Collection<T> getPartition() {
			return partition;
		}
		
		void reset() {
			partition = new LinkedList<>();
		}

	};
	
	private static <T> Function<T, Collection<T>> partition(final PartitionWrapper<T> partiotionWrapper) {
		
		return new Function<T, Collection<T>>() {
			int i = 0;
			
			@Override
			public Collection<T> apply(T t) {
				partiotionWrapper.add(t);
				if (i++ % partiotionWrapper.size == 0 && i > 1) {
					Collection<T> partition = partiotionWrapper.getPartition();
					partiotionWrapper.reset();
					partiotionWrapper.add(t);
					return partition;
				} else {
					return Collections.<T>emptyList();
				}
			}
		};
	}
}
