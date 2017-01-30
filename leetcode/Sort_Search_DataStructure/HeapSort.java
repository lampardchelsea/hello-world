// Solution 1: Start at array position 1
/**
 * Refer to
 * http://algs4.cs.princeton.edu/24pq/
 * http://algs4.cs.princeton.edu/24pq/Heap.java.html
 * 
 * Heapsort. 
 * We can use any priority queue to develop a sorting method. We insert all the keys to be sorted 
 * into a minimum-oriented priority queue, then repeatedly use remove the minimum to remove them all in order. 
 * When using a heap for the priority queue, we obtain heapsort.
 * Focusing on the task of sorting, we abandon the notion of hiding the heap representation of the priority 
 * queue and use swim() and sink() directly. Doing so allows us to sort an array without needing any extra 
 * space, by maintaining the heap within the array to be sorted. Heapsort breaks into two phases: heap construction, 
 * where we reorganize the original array into a heap, and the sortdown, where we pull the items out of the heap 
 * in decreasing order to build the sorted result.
 * 
 * Heap construction. 
 * We can accomplish this task in time proportional to N lg N, by proceeding from left to 
 * right through the array, using swim() to ensure that the entries to the left of the scanning pointer make 
 * up a heap-ordered complete tree, like successive priority queue insertions. A clever method that is much 
 * more efficient is to proceed from right to left, using sink() to make subheaps as we go. Every position 
 * in the array is the root of a small subheap; sink() works or such subheaps, as well. If the two children 
 * of a node are heaps, then calling sink() on that node makes the subtree rooted there a heap.
 * 
 * Sortdown. 
 * Most of the work during heapsort is done during the second phase, where we remove the largest remaining 
 * items from the heap and put it into the array position vacated as the heap shrinks.
 */
public class HeapSort {
	public static void sort(Comparable[] pq) {
		// Build the heap from smallest subheap (parent position at n/2 if valid index 
		// start from 1 in array, at n/2 - 1 if valid index start from 0 in array),
		// rearrange the array (position 0 not used)
		int n = pq.length;
		for(int k = n / 2; k >= 1; k--) {
			sink(pq, k, n);
		}
		// Sort down
		while(n > 1) {
			// At this point, the largest item is stored at the root of the heap. 
			// Replace it with the last item of the heap followed by reducing the 
			// size of heap by 1. Finally, heapify from root of tree each iteration.
			exch(pq, 1, n--);
			sink(pq, 1, n);
		}
	}
	
	// Proceed from right to left, using sink() to make subheaps.
	// Every position in the array is the root of a small subheap.
	public static void sink(Comparable[] pq, int k, int n) {
		while(2 * k <= n) {
			int j = 2 * k;
			// Find the larger one in two children
			if(j < n && less(pq, j, j + 1)) {
				j++;
			}
			// Compare the parent node with larger child
			// If pq[k - 1] > pq[j - 1], then break as
			// it satisfy the rule of heap(parent larger
			// two children), else need to exchange parent
			// and larger child position and assign j to k
			// for next loop
			if(!less(pq, k, j)) {
				break;
			}
			exch(pq, k, j);
			// Set k to j (2 * k) which prepare sequence
			// comparison happen on chain, because original
			// parent at position k exchange to position j,
			// need trace on possible comparison between
			// original parent (position = 2k) and 
			// new children (position = 4k/4k + 1)
			k = j;
		}
	}
	
	public static void exch(Object[] pq, int i, int j) {
		Object swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}
	
	// Helper function for comparisons and swaps,
	// indices are "off-by-one" to support 1-based indexing
	public static boolean less(Comparable[] pq, int i, int j) {
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}
	
	public static void show(Comparable[] a) {
		for(int i = 0; i < a.length; i++) {
			StdOut.print(a[i]);
		}
	}
	
	public static void main(String[] args) {
		String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		sort(a);
		show(a);
	}
}

// Solution 2: Start at array position 0

