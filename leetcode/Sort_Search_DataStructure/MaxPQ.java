import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Refer to
 * http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
 * 
 *  Compilation:  javac MaxPQ.java
 *  Execution:    java MaxPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *  
 *  Generic max priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  % java MaxPQ < tinyPQ.txt 
 *  Q X P (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges 
 *  (ala insertion sort).
 *  
 */
public class MaxPQ<Key> implements Iterable<Key> {
	// Store items at indices 1 to n
	private Key[] pq;
	// Number of items on priority queue
	private int n;
	// Optional Comparator
	private Comparator<Key> comparator;
	
	/**
	 * Initializes an empty priority queue with the given initial capacity
	 * @param initCapacity the initial capacity of this priority queue
	 */
	public MaxPQ(int initCapacity) {
		// "+1" to store items from index 1
		pq = (Key[]) new Object[initCapacity + 1];
		n = 0;
	}
	
	/**
	 * Initializes an empty priority queue
	 */
	public MaxPQ() {
		this(1);
	}
	
	/**
	 * Initializes an empty priority queue with the given initial capacity,
	 * using the given comparator
	 * @param initCapacity the initial capacity of this priority queue
	 * @param comparator the order in which to compare the keys
	 */
	public MaxPQ(int initCapacity, Comparator<Key> comparator) {
		this.comparator = comparator;
		pq = (Key[]) new Object[initCapacity + 1];
		n = 0;
	}
	
	/**
	 * Initializes an empty priority queue using the given comparator
	 * @param comparator
	 */
	public MaxPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}
	
	/**
	 * Initializes a priority queue from the array of keys
	 * Takes time proportional to the number of keys, using sink-based heap construction
	 * @param keys
	 */
	public MaxPQ(Key[] keys) {
		n = keys.length;
		pq = (Key[]) new Object[n + 1];
		// Copy keys into pq array start from index 1
		for(int i = 0; i < n; i++) {
			pq[i + 1] = keys[i];
		}
		for(int k = n/2; k >= 1; k--) {
			sink(k);
		}
	}
	
	/**
	 * Returns true if the priority queue is empty
	 * @return
	 */
	public boolean isEmpty() {
		return n == 0;
	}
	
	/**
	 * Returns the number of keys on this priority queue
	 * @return
	 */
	public int size() {
		return n;
	}
	
	/**
	 * Returns a largest key on this priority queue
	 * @return
	 */
	public Key max() {
		if(isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
		return pq[1];
	}
	
	/**
	 * Helper function to double the size of the heap array
	 * @param capacity
	 */
	public void resize(int capacity) {
		Key[] temp = (Key[]) new Object[capacity];
		for(int i = 1; i <= n; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}
	
	/**
	 * Adds a new key to this priority queue
	 * @param x
	 */
	public void insert(Key x) {
		// Double size of array if necessary
		if(n >= pq.length - 1) {
			resize(2 * pq.length);
		}
		// add x, and percolate it up to maintain heap invariant
		pq[++n] = x;
		swim(n);
	}
	
	/**
	 * Removes and returns a largest key on this priority queue
	 * @return
	 */
	public Key delMax() {
		if(isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
		Key max = pq[1];
		exch(1, n--);
		sink(1);
		// To avoid loitering and help with garbage collection
		pq[n + 1] = null; 
		if((n > 0) && (n == (pq.length - 1) / 4)) {
			resize(pq.length / 2);
		}
		return max;
	}
	
	
	public void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	public boolean less(int i, int j) {
		if(comparator == null) {
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
		} else {
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}
	
	
	public void swim(int k) {
		while(k > 1 && less(k/2, k)) {
			exch(k/2, k);
			k = k/2;
		}
	}
	
	public void sink(int k) {
		while(k <= n/2) {
			int j = 2 * k;
			if(j < n && less(j, j + 1)) {
				j++;
			}
			if(!less(k, j)) {
				break;
			}
			exch(k, j);
			k = j;
		}
	}
	
	@Override
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	/**
	 * Returns an iterator that iterators over the keys on this priority queue
	 * in descending order
	 * The iterator doesn't implement remove() since it is optional
	 */
	private class HeapIterator implements Iterator<Key> {
		// Create a new pq
		private MaxPQ<Key> copy;
		
		// Add all items to copy of heap, takes linear time since
		// already in heap order so no keys move
		public HeapIterator() {
			if(comparator == null) {
				copy = new MaxPQ<Key>(size());
			} else {
				copy = new MaxPQ<Key>(size(), comparator);
			}
			for(int i = 1; i <= n; i++) {
				copy.insert(pq[i]);
			}
		}
		
		
		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public Key next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return copy.delMax();
		}

		@Override
		public void remove() {}
		
	}
	
	public static void main(String[] args) {
		MaxPQ<String> pq = new MaxPQ<String>();
		String[] a = {"P", "Q", "E", "-", "X", "A", "M", "-", "P", "L", "E", "-"};
		for(int i = 0; i < a.length; i++) {
			if(!a[i].equals("-")) {
				pq.insert(a[i]);
			} else if(!pq.isEmpty()) {
				StdOut.print(pq.delMax() + " ");
			}
		}
		StdOut.println("(" + pq.size() + " left on pq)");
	}
}

