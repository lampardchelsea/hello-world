package test;

import java.util.Iterator;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>{
	// maximum number of elements on PQ
	int maxN;
	// number of elements on PQ
	int n;
	// binary heap using 1-based indexing
	int[] pq;
	// inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	int[] qp;
	// keys[i] = priority of i
	Key[] keys;
	public IndexMinPQ(int maxN) {
		this.maxN = maxN;
		n = 0;
		keys = (Key[]) new Comparable[maxN + 1];
		pq = new int[maxN + 1];
		qp = new int[maxN + 1];
		for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
	}
	
	// Associates key with index i
	public void insert(int i, Key key) {
		n++;
		qp[i] = n;
		pq[n] = i;
		keys[i] = key;
		swim(n);
	}

	public int delMin() {
		int min = pq[1];
		exch(1, n--);
		sink(1);
		// delete
		qp[min] = -1;
		// to help with garbage collection
		keys[min] = null;
		return min;
	}
	
	// Decrease the key associate with index i to the
	// specific value
	public void decreaseKey(int i, Key key) {
		if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
	    keys[i] = key;
	    swim(qp[i]);
	}
	
	// Increase the key associate with index i to the
	// specific value
	public void increaseKey(int i, Key key) {
		if (keys[i].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
		keys[i] = key;
		sink(qp[i]);
	}
	
	public void sink(int k) {
		while(2 * k <= n) {
			int j = 2 * k;
			if(j < n && greater(j, j + 1)) {
				j++;
			}
			if(!greater(k, j)) {
				break;
			}
			exch(k, j);
			k = j;
		}
	}
	
	public void swim(int k) {
		while(k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	private void exch(int i, int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}

	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	// Is an index on this priority queue ?
	public boolean contains(int i) {
		if (i < 0 || i >= maxN) throw new IllegalArgumentException();
		return qp[i] != -1;
	}
}
