import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node first, last;

	private class Node {
	    Item item;
		Node next;
		Node previous;
	}

	public Deque() {
		first = null;
		last = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		first.next = oldFirst;

		if (isEmpty()) {
			last = first;
		} else {
			oldFirst.previous = first;
		}
		size++;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.previous = oldLast;
		if (isEmpty()) {
			first = last;
		} else {
			oldLast.next = last;
		}
		size++;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item = first.item;
		first = first.next;
		size--;
		if (isEmpty()) {
			last = first;
		} else {
			first.previous = null;
		}
		return item;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item = last.item;
		last = last.previous;
		size--;
		if (isEmpty()) {
			first = last;
		} else {
			last.next = null;
		}
		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// public static void main(String[] args) {
	//
	// }

}
