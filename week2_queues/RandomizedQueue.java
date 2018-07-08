import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] a;
	private int size;

	public RandomizedQueue() {
		a = (Item[]) new Object[2];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException("input is null");
		}
		if (size == a.length) {
			resize(2 * a.length);
		}
		a[size++] = item;
	}

	public Item dequeue() {
		int random;
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		if (size > 1) {
			random = StdRandom.uniform(size - 1);
		} else {
			random = 0;
		}
		Item item = a[random];
		a[random] = a[size - 1];
		a[size - 1] = null;
		size--;
		if (size > 0 && size == a.length / 4) {
			resize(a.length / 2);
		}
		return item;
	}

	public Item sample() {
		int random;
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		if (size > 1) {
			random = StdRandom.uniform(size - 1);
		} else {
			random = 0;
		}
		Item item = a[random];
		return item;
	}

	public Iterator<Item> iterator() {
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<Item> {
		private int i;
		private Item[] shuffledArray;

		private void shuffleArray() {
			shuffledArray = (Item[]) new Object[size];
			for (int i = 0; i < size; i++) {
				shuffledArray[i] = a[i];
			}
			StdRandom.shuffle(shuffledArray);
		}

		public ReverseArrayIterator() {
			i = size - 1;
			shuffleArray();
		}

		public boolean hasNext() {
			return i >= 0;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			return shuffledArray[i--];
		}
	}

	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}

//	public static void main(String[] args) {		
//	}

}
